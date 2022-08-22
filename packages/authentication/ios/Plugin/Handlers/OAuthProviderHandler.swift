import Foundation
import Capacitor
import FirebaseCore
import FirebaseAuth

class OAuthProviderHandler: NSObject {
    var pluginImplementation: FirebaseAuthentication
    var provider: OAuthProvider?

    init(_ pluginImplementation: FirebaseAuthentication) {
        self.pluginImplementation = pluginImplementation
    }

    func link(call: CAPPluginCall, providerId: ProviderId) {
        dispatch(call, providerId, success: self.pluginImplementation.handleSuccessfulLink, failure: self.pluginImplementation.handleFailedLink)
    }

    func signIn(call: CAPPluginCall, providerId: ProviderId) {
        dispatch(call, providerId, success: self.pluginImplementation.handleSuccessfulSignIn, failure: self.pluginImplementation.handleFailedSignIn)
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

    private func dispatch(_ call: CAPPluginCall, _ providerId: ProviderId, success: @escaping AuthSuccessHandler, failure: @escaping AuthFailureHandler) {
        self.provider = OAuthProvider(providerID: providerId.rawValue)
        self.applySignInOptions(call: call, provider: provider!)
        DispatchQueue.main.async {
            self.provider?.getCredentialWith(nil) { credential, error in
                if let error = error {
                    failure((message: nil, error: error))
                    return
                }
                if let credential = credential {
                    success((credential: credential, idToken: nil, nonce: nil, accessToken: nil))
                }
            }
        }
    }
}
