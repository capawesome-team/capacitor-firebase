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

    func link(call: CAPPluginCall) {
        if self.pluginImplementation.getCurrentUser() == nil {
            call.reject(self.pluginImplementation.getPlugin().errorNoUserSignedIn)
            return
        }
        dispatch(call, AuthType.link)
    }

    func signIn(call: CAPPluginCall) {
        dispatch(call, AuthType.signIn)
    }

    private func dispatch(_ call: CAPPluginCall, _ authType: AuthType) {
        #if RGCFA_INCLUDE_FACEBOOK
        let scopes = call.getArray("scopes", String.self) ?? []
        DispatchQueue.main.async {
            self.loginManager.logIn(permissions: ["email", "public_profile"] + scopes, from: self.pluginImplementation.getPlugin().bridge?.viewController) { result, error in
                if let error = error {
                    FirebaseAuthenticationHandler.failure(call, message: nil, error: error)
                    return
                }

                guard let accessToken = result?.token else {
                    FirebaseAuthenticationHandler.failure(call, message: self.errorSignInCanceled, error: nil)
                    return
                }

                let accessTokenString = accessToken.tokenString
                let credential = FacebookAuthProvider.credential(withAccessToken: accessTokenString)
                FirebaseAuthenticationHandler.success(call, authType, self.pluginImplementation, credential: credential, idToken: nil, nonce: nil, accessToken: accessTokenString)
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
