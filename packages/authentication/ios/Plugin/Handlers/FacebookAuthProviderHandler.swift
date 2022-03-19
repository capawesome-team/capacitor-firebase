import Foundation
import Capacitor
import FirebaseAuth
#if RGCFA_INCLUDE_FACEBOOK
import FBSDKLoginKit
#endif

class FacebookAuthProviderHandler: NSObject {
    public let errorSignInCanceled = "Sign in canceled."
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
#if RGCFA_INCLUDE_FACEBOOK
        DispatchQueue.main.async {
            self.loginManager.logIn(permissions: ["email", "public_profile"], from: self.pluginImplementation.getPlugin().bridge?.viewController) { result, error in
                if let error = error {
                    self.pluginImplementation.handleFailedSignIn(message: nil, error: error)
                    return
                }

                guard let accessToken = result?.token else {
                    self.pluginImplementation.handleFailedSignIn(message: self.errorSignInCanceled, error: nil)
                    return
                }

                let token = accessToken.tokenString
                let credential = FacebookAuthProvider.credential(withAccessToken: token)
                self.pluginImplementation.handleSuccessfulSignIn(credential: credential, idToken: token, nonce: nil)
            }
        }
#endif
    }

    func signOut() {
#if RGCFA_INCLUDE_FACEBOOK
        loginManager.logOut()
#endif
    }
}
