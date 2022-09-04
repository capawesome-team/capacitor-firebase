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
    fileprivate var isLink: Bool?

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
        dispatch(call, true)
    }

    func signIn(call: CAPPluginCall) {
        dispatch(call, false)
    }

    private func dispatch(_ call: CAPPluginCall, _ isLink: Bool) {
        self.isLink = isLink
        #if RGCFA_INCLUDE_FACEBOOK
        let scopes = call.getArray("scopes", String.self) ?? []
        DispatchQueue.main.async {
            self.loginManager.logIn(permissions: ["email", "public_profile"] + scopes, from: self.pluginImplementation.getPlugin().bridge?.viewController) { result, error in
                guard let isLink = self.isLink else {
                    return
                }
                if let error = error {
                    isLink
                        ? self.pluginImplementation.handleFailedLink(message: nil, error: error)
                        : self.pluginImplementation.handleFailedSignIn(message: nil, error: error)
                    return
                }

                guard let accessToken = result?.token else {
                    isLink
                        ? self.pluginImplementation.handleFailedLink(message: self.errorSignInCanceled, error: nil)
                        : self.pluginImplementation.handleFailedSignIn(message: self.errorSignInCanceled, error: nil)
                    return
                }

                let accessTokenString = accessToken.tokenString
                let credential = FacebookAuthProvider.credential(withAccessToken: accessTokenString)
                isLink
                    ? self.pluginImplementation.handleSuccessfulLink(credential: credential, idToken: nil, nonce: nil, accessToken: accessTokenString)
                    : self.pluginImplementation.handleSuccessfulSignIn(credential: credential, idToken: nil, nonce: nil, accessToken: accessTokenString)
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
