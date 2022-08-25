import Foundation
import Capacitor
import FirebaseCore
import FirebaseAuth
#if RGCFA_INCLUDE_GOOGLE
import GoogleSignIn
#endif

class GoogleAuthProviderHandler: NSObject {
    var pluginImplementation: FirebaseAuthentication

    init(_ pluginImplementation: FirebaseAuthentication) {
        self.pluginImplementation = pluginImplementation
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
        #if RGCFA_INCLUDE_GOOGLE
        guard let clientId = FirebaseApp.app()?.options.clientID else { return }
        let config = GIDConfiguration(clientID: clientId)
        guard let controller = self.pluginImplementation.getPlugin().bridge?.viewController else { return }
        let scopes = call.getArray("scopes", String.self) ?? []

        DispatchQueue.main.async {
            GIDSignIn.sharedInstance.signIn(with: config, presenting: controller, hint: nil, additionalScopes: scopes, callback: { user, error in
                if let error = error {
                    FirebaseAuthenticationHandler.failure(call, message: nil, error: error)
                    return
                }

                guard let authentication = user?.authentication else { return }
                guard let idToken = authentication.idToken else { return }
                let accessToken = authentication.accessToken

                let credential = GoogleAuthProvider.credential(withIDToken: idToken, accessToken: authentication.accessToken)
                FirebaseAuthenticationHandler.success(call, authType, self.pluginImplementation, credential: credential, idToken: idToken, nonce: nil, accessToken: accessToken)
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
