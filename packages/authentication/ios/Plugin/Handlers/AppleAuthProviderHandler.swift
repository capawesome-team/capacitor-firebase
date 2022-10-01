import Foundation
import Capacitor
import FirebaseCore
import FirebaseAuth
import AuthenticationServices
import CryptoKit

class AppleAuthProviderHandler: NSObject {
    var pluginImplementation: FirebaseAuthentication
    fileprivate var currentNonce: String?
    fileprivate var isLink: Bool?

    init(_ pluginImplementation: FirebaseAuthentication) {
        self.pluginImplementation = pluginImplementation
    }

    func signIn(call: CAPPluginCall) {
        if #available(iOS 13, *) {
            self.isLink = false
            self.startSignInWithAppleFlow()
        } else {
            call.reject(self.pluginImplementation.getPlugin().errorDeviceUnsupported)
        }
    }

    func link(call: CAPPluginCall) {
        if #available(iOS 13, *) {
            self.isLink = true
            self.startSignInWithAppleFlow()
        } else {
            call.reject(self.pluginImplementation.getPlugin().errorDeviceUnsupported)
        }
    }
}

// Source: https://firebase.google.com/docs/auth/ios/apple
@available(iOS 13.0, *)
extension AppleAuthProviderHandler: ASAuthorizationControllerDelegate, ASAuthorizationControllerPresentationContextProviding {
    func presentationAnchor(for controller: ASAuthorizationController) -> ASPresentationAnchor {
        return (self.pluginImplementation.getPlugin().bridge?.webView?.window)!
    }

    // Adapted from https://auth0.com/docs/api-auth/tutorials/nonce#generate-a-cryptographically-random-nonce
    private func randomNonceString(length: Int = 32) -> String {
        precondition(length > 0)
        let charset: [Character] =
            Array("0123456789ABCDEFGHIJKLMNOPQRSTUVXYZabcdefghijklmnopqrstuvwxyz-._")
        var result = ""
        var remainingLength = length

        while remainingLength > 0 {
            let randoms: [UInt8] = (0 ..< 16).map { _ in
                var random: UInt8 = 0
                let errorCode = SecRandomCopyBytes(kSecRandomDefault, 1, &random)
                if errorCode != errSecSuccess {
                    fatalError("Unable to generate nonce. SecRandomCopyBytes failed with OSStatus \(errorCode)")
                }
                return random
            }

            randoms.forEach { random in
                if remainingLength == 0 {
                    return
                }

                if random < charset.count {
                    result.append(charset[Int(random)])
                    remainingLength -= 1
                }
            }
        }

        return result
    }

    @available(iOS 13, *)
    func startSignInWithAppleFlow() {
        let nonce = randomNonceString()
        currentNonce = nonce
        let appleIDProvider = ASAuthorizationAppleIDProvider()
        let request = appleIDProvider.createRequest()
        request.requestedScopes = [.fullName, .email]
        request.nonce = sha256(nonce)

        let authorizationController = ASAuthorizationController(authorizationRequests: [request])
        authorizationController.delegate = self
        authorizationController.presentationContextProvider = self
        authorizationController.performRequests()
    }

    @available(iOS 13, *)
    private func sha256(_ input: String) -> String {
        let inputData = Data(input.utf8)
        let hashedData = SHA256.hash(data: inputData)
        let hashString = hashedData.compactMap {
            return String(format: "%02x", $0)
        }.joined()

        return hashString
    }

    func authorizationController(controller: ASAuthorizationController, didCompleteWithAuthorization authorization: ASAuthorization) {
        guard let appleIDCredential = authorization.credential as? ASAuthorizationAppleIDCredential else {
            return
        }
        guard let nonce = currentNonce else {
            fatalError("Invalid state: A login callback was received, but no login request was sent.")
        }
        guard let appleIDToken = appleIDCredential.identityToken else {
            print("Unable to fetch identity token")
            return
        }
        var authorizationCode: String?
        if let authorizationCodeData = appleIDCredential.authorizationCode {
            authorizationCode = String(data: authorizationCodeData, encoding: .utf8)
        }
        guard let idTokenString = String(data: appleIDToken, encoding: .utf8) else {
            print("Unable to serialize token string from data: \(appleIDToken.debugDescription)")
            return
        }
        var displayName: String?
        if let fullName = appleIDCredential.fullName {
            if let givenName = fullName.givenName, let familyName = fullName.familyName {
                displayName = "\(givenName) \(familyName)"
            }
        }
        let credential = OAuthProvider.credential(withProviderID: ProviderId.apple, idToken: idTokenString, rawNonce: nonce)
        guard let isLink = self.isLink else {
            return
        }
        if isLink == true {
            self.pluginImplementation.handleSuccessfulLink(credential: credential, idToken: idTokenString, nonce: nonce, accessToken: nil, displayName: displayName, authorizationCode: authorizationCode)
        } else {
            self.pluginImplementation.handleSuccessfulSignIn(credential: credential, idToken: idTokenString, nonce: nonce, accessToken: nil, displayName: displayName, authorizationCode: authorizationCode)
        }
    }

    func authorizationController(controller: ASAuthorizationController, didCompleteWithError error: Error) {
        guard let isLink = self.isLink else {
            return
        }
        if isLink == true {
            self.pluginImplementation.handleFailedLink(message: nil, error: error)
        } else {
            self.pluginImplementation.handleFailedSignIn(message: nil, error: error)
        }
    }
}
