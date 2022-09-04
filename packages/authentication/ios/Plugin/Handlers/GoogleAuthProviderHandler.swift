import Foundation
import Capacitor
import FirebaseCore
import FirebaseAuth
#if RGCFA_INCLUDE_GOOGLE
import GoogleSignIn
#endif

class GoogleAuthProviderHandler: NSObject {
    var pluginImplementation: FirebaseAuthentication
    fileprivate var isLink: Bool?

    init(_ pluginImplementation: FirebaseAuthentication) {
        self.pluginImplementation = pluginImplementation
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
        #if RGCFA_INCLUDE_GOOGLE
        guard let clientId = FirebaseApp.app()?.options.clientID else { return }
        let config = GIDConfiguration(clientID: clientId)
        guard let controller = self.pluginImplementation.getPlugin().bridge?.viewController else { return }
        let scopes = call.getArray("scopes", String.self) ?? []

        DispatchQueue.main.async {
            GIDSignIn.sharedInstance.signIn(with: config, presenting: controller, hint: nil, additionalScopes: scopes, callback: { user, error in
                guard let isLink = self.isLink else {
                    return
                }
                if let error = error {
                    isLink
                        ? self.pluginImplementation.handleFailedLink(message: nil, error: error)
                        : self.pluginImplementation.handleFailedSignIn(message: nil, error: error)
                    return
                }

                guard let authentication = user?.authentication else { return }
                guard let idToken = authentication.idToken else { return }
                let accessToken = authentication.accessToken

                let credential = GoogleAuthProvider.credential(withIDToken: idToken, accessToken: authentication.accessToken)
                isLink
                    ? self.pluginImplementation.handleSuccessfulLink(credential: credential, idToken: idToken, nonce: nil, accessToken: accessToken)
                    : self.pluginImplementation.handleSuccessfulSignIn(credential: credential, idToken: idToken, nonce: nil, accessToken: accessToken)
            })
        }
        #endif
    }

    func signOut() {
        #if RGCFA_INCLUDE_GOOGLE
        GIDSignIn.sharedInstance.signOut()
        #endif
    }
}
