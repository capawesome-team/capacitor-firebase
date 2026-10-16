import Foundation
import Capacitor
import FirebaseCore
import FirebaseAuth
#if RGCFA_INCLUDE_GOOGLE
import GoogleSignIn
#endif

class GoogleAuthProviderHandler: NSObject {
    let errorClientIdMissing = "clientID is missing. Make sure your GoogleService-Info.plist contains a CLIENT_ID."
    let errorIdTokenMissing = "Google Sign-In result does not contain an ID token."
    let errorSdkNotIncluded = "The Google Sign-In SDK is not included in this build. Add the required CocoaPods subspec or Swift package trait."
    let errorViewControllerMissing = "No view controller available to present the sign-in flow."
    var pluginImplementation: FirebaseAuthentication

    init(_ pluginImplementation: FirebaseAuthentication) {
        self.pluginImplementation = pluginImplementation
        super.init()
    }

    func signIn(call: CAPPluginCall) {
        startSignInWithGoogleFlow(call, isLink: false)
    }

    func link(call: CAPPluginCall) {
        startSignInWithGoogleFlow(call, isLink: true)
    }

    func signOut() {
        #if RGCFA_INCLUDE_GOOGLE
        GIDSignIn.sharedInstance.signOut()
        #endif
    }

    private func handleFailed(isLink: Bool, message: String?, error: Error?) {
        if isLink {
            pluginImplementation.handleFailedLink(message: message, error: error)
        } else {
            pluginImplementation.handleFailedSignIn(message: message, error: error)
        }
    }

    private func startSignInWithGoogleFlow(_ call: CAPPluginCall, isLink: Bool) {
        #if RGCFA_INCLUDE_GOOGLE
        guard let clientId = FirebaseApp.app()?.options.clientID else {
            handleFailed(isLink: isLink, message: errorClientIdMissing, error: nil)
            return
        }
        let config = GIDConfiguration(clientID: clientId, serverClientID: clientId)
        GIDSignIn.sharedInstance.configuration = config
        guard let controller = self.pluginImplementation.getPlugin().bridge?.viewController else {
            handleFailed(isLink: isLink, message: errorViewControllerMissing, error: nil)
            return
        }
        let scopes = call.getArray("scopes", String.self) ?? []

        DispatchQueue.main.async {
            GIDSignIn.sharedInstance.signIn(withPresenting: controller, hint: nil, additionalScopes: scopes) { [unowned self] result, error in
                if let error = error {
                    self.handleFailed(isLink: isLink, message: nil, error: error)
                    return
                }

                guard let user = result?.user,
                      let idToken = user.idToken?.tokenString
                else {
                    self.handleFailed(isLink: isLink, message: self.errorIdTokenMissing, error: nil)
                    return
                }
                let accessToken = user.accessToken.tokenString
                let serverAuthCode = result?.serverAuthCode
                let credential = GoogleAuthProvider.credential(withIDToken: idToken, accessToken: accessToken)
                if isLink == true {
                    self.pluginImplementation.handleSuccessfulLink(credential: credential, idToken: idToken, nonce: nil,
                                                                   accessToken: accessToken, serverAuthCode: serverAuthCode, displayName: nil, authorizationCode: nil)
                } else {
                    self.pluginImplementation.handleSuccessfulSignIn(credential: credential, idToken: idToken, nonce: nil,
                                                                     accessToken: accessToken, displayName: nil, authorizationCode: nil, serverAuthCode: serverAuthCode)
                }
            }
        }
        #else
        handleFailed(isLink: isLink, message: errorSdkNotIncluded, error: nil)
        #endif
    }
}
