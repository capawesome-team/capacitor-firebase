import Foundation
import Capacitor
import FirebaseAuth
#if RGCFA_INCLUDE_FACEBOOK
import FBSDKLoginKit
#endif

class FacebookAuthProviderHandler: NSObject {
    public let errorSignInCanceled = "Sign in canceled."
    public let errorLinkCanceled = "Link canceled."
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
        DispatchQueue.main.async {
            self.loginManager.logIn(permissions: ["email", "public_profile"] + scopes, from: self.pluginImplementation.getPlugin().bridge?.viewController) { result, error in
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
                    self.pluginImplementation.handleSuccessfulLink(credential: credential, idToken: nil, nonce: nil, accessToken: accessTokenString)
                } else {
                    self.pluginImplementation.handleSuccessfulSignIn(credential: credential, idToken: nil, nonce: nil, accessToken: accessTokenString)
                }
            }
        }
        #endif
    }
}
