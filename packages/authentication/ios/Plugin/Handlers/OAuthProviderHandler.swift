import Foundation
import Capacitor
import FirebaseCore
import FirebaseAuth

class OAuthProviderHandler: NSObject {
    var pluginImplementation: FirebaseAuthentication
    var provider: OAuthProvider?

    init(_ pluginImplementation: FirebaseAuthentication) {
        self.pluginImplementation = pluginImplementation
        super.init()
    }

    func link(call: CAPPluginCall, providerId: String) {
        if self.pluginImplementation.getCurrentUser() == nil {
            call.reject(self.pluginImplementation.getPlugin().errorNoUserSignedIn)
            return
        }
        dispatch(call, providerId, AuthType.link)
    }

    func signIn(call: CAPPluginCall, providerId: String) {
        dispatch(call, providerId, AuthType.signIn)
    }

    private func dispatch(_ call: CAPPluginCall, _ providerId: String, _ authType: AuthType) {
        self.provider = OAuthProvider(providerID: providerId)
        self.applySignInOptions(call: call, provider: provider!)
        DispatchQueue.main.async {
            self.provider?.getCredentialWith(nil) { credential, error in
                if let error = error {
                    FirebaseAuthenticationHandler.failure(call, message: nil, error: error)
                    return
                }
                if let credential = credential {
                    FirebaseAuthenticationHandler.success(call, authType, self.pluginImplementation, credential: credential, idToken: nil, nonce: nil, accessToken: nil)
                }
            }
        }
    }

    private func applySignInOptions(call: CAPPluginCall, provider: OAuthProvider) {
        let customParameters = call.getArray("customParameters", JSObject.self) ?? []
        for (_, customParameter) in customParameters.enumerated() {
            guard let key = customParameter["key"] as? String else {
                continue
            }
            guard let value = customParameter["value"] as? String else {
                continue
            }
            if provider.customParameters == nil {
                provider.customParameters = [:]
            }
            provider.customParameters?[key] = value
        }

        let scopes = call.getArray("scopes", String.self) ?? []
        provider.scopes = scopes
    }
}
