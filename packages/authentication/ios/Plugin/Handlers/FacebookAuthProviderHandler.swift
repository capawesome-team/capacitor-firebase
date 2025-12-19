import Foundation
import CryptoKit
import Capacitor
import FirebaseAuth
#if RGCFA_INCLUDE_FACEBOOK
import FBSDKLoginKit
#endif

class FacebookAuthProviderHandler: NSObject {
    let errorSignInCanceled = "Sign in canceled."
    let errorLinkCanceled = "Link canceled."
    private var pluginImplementation: FirebaseAuthentication
    #if RGCFA_INCLUDE_FACEBOOK
    private var loginManager: LoginManager
    #endif

    init(_ pluginImplementation: FirebaseAuthentication) {
        self.pluginImplementation = pluginImplementation
        #if RGCFA_INCLUDE_FACEBOOK
        loginManager = LoginManager()
        #endif
        super.init()
    }

    func signIn(call: CAPPluginCall) {
        startSignInWithFacebookFlow(call, isLink: false)
    }

    func link(call: CAPPluginCall) {
        startSignInWithFacebookFlow(call, isLink: true)
    }

    func signOut() {
        #if RGCFA_INCLUDE_FACEBOOK
        loginManager.logOut()
        #endif
    }

    private func startSignInWithFacebookFlow(_ call: CAPPluginCall, isLink: Bool) {
        #if RGCFA_INCLUDE_FACEBOOK
        let scopes = call.getArray("scopes", String.self) ?? []
        let useLimitedLogin = call.getBool("useLimitedLogin") ?? false

        DispatchQueue.main.async {
            let viewController = self.pluginImplementation.getPlugin().bridge?.viewController
            let permissions = ["email", "public_profile"] + scopes

            if useLimitedLogin {
                let nonce = self.randomNonceString()
                let configuration = LoginConfiguration(
                    permissions: permissions,
                    tracking: .limited,
                    nonce: self.sha256(nonce)
                )
                self.loginManager.logIn(viewController: viewController, configuration: configuration) { result in
                    switch result {
                    case .cancelled:
                        if isLink == true {
                            self.pluginImplementation.handleFailedLink(message: nil, error: nil)
                        } else {
                            self.pluginImplementation.handleFailedSignIn(message: nil, error: nil)
                        }
                        return
                    case .failed(let error):
                        if isLink == true {
                            self.pluginImplementation.handleFailedLink(message: nil, error: error)
                        } else {
                            self.pluginImplementation.handleFailedSignIn(message: nil, error: error)
                        }
                        return
                    case .success:
                        guard let idTokenString = AuthenticationToken.current?.tokenString else {
                            if isLink == true {
                                self.pluginImplementation.handleFailedLink(message: self.errorLinkCanceled, error: nil)
                            } else {
                                self.pluginImplementation.handleFailedSignIn(message: self.errorSignInCanceled, error: nil)
                            }
                            return
                        }

                        let credential = OAuthProvider.credential(providerID: .facebook, idToken: idTokenString, rawNonce: nonce)
                        if isLink == true {
                            self.pluginImplementation.handleSuccessfulLink(credential: credential, idToken: idTokenString, nonce: nonce, accessToken: nil, serverAuthCode: nil, displayName: nil, authorizationCode: nil)
                        } else {
                            self.pluginImplementation.handleSuccessfulSignIn(credential: credential, idToken: idTokenString, nonce: nonce, accessToken: nil, displayName: nil, authorizationCode: nil, serverAuthCode: nil)
                        }
                    }
                }
            } else {
                self.loginManager.logIn(permissions: permissions, from: viewController) { result, error in
                    if let error = error {
                        if isLink == true {
                            self.pluginImplementation.handleFailedLink(message: nil, error: error)
                        } else {
                            self.pluginImplementation.handleFailedSignIn(message: nil, error: error)
                        }
                        return
                    }

                    guard let accessToken = result?.token else {
                        if isLink == true {
                            self.pluginImplementation.handleFailedLink(message: self.errorLinkCanceled, error: nil)
                        } else {
                            self.pluginImplementation.handleFailedSignIn(message: self.errorSignInCanceled, error: nil)
                        }
                        return
                    }

                    let accessTokenString = accessToken.tokenString
                    let credential = FacebookAuthProvider.credential(withAccessToken: accessTokenString)
                    if isLink == true {
                        self.pluginImplementation.handleSuccessfulLink(credential: credential, idToken: nil, nonce: nil,
                                                                       accessToken: accessTokenString, serverAuthCode: nil, displayName: nil, authorizationCode: nil)
                    } else {
                        self.pluginImplementation.handleSuccessfulSignIn(credential: credential, idToken: nil, nonce: nil,
                                                                         accessToken: accessTokenString, displayName: nil, authorizationCode: nil, serverAuthCode: nil)
                    }
                }
            }
        }
        #endif
    }

    private func randomNonceString(length: Int = 32) -> String {
        precondition(length > 0)
        var randomBytes = [UInt8](repeating: 0, count: length)
        let errorCode = SecRandomCopyBytes(kSecRandomDefault, randomBytes.count, &randomBytes)
        if errorCode != errSecSuccess {
            fatalError(
                "Unable to generate nonce. SecRandomCopyBytes failed with OSStatus \(errorCode)"
            )
        }

        let charset: [Character] =
            Array("0123456789ABCDEFGHIJKLMNOPQRSTUVXYZabcdefghijklmnopqrstuvwxyz-._")

        let nonce = randomBytes.map { byte in
            charset[Int(byte) % charset.count]
        }

        return String(nonce)
    }

    private func sha256(_ input: String) -> String {
        let inputData = Data(input.utf8)
        let hashedData = SHA256.hash(data: inputData)
        let hashString = hashedData.compactMap {
            String(format: "%02x", $0)
        }.joined()

        return hashString
    }
}
