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

    func signIn(call: CAPPluginCall, providerId: String) {
        self.provider = OAuthProvider(providerID: providerId)
        self.applySignInConfig(call: call, provider: provider!)
        DispatchQueue.main.async {
            self.startSignInFlow()
        }
    }

    private func applySignInConfig(call: CAPPluginCall, provider: OAuthProvider) {
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
    }

    private func startSignInFlow() {
        self.provider?.getCredentialWith(nil) { credential, error in
            if let error = error {
                self.pluginImplementation.handleFailedSignIn(message: nil, error: error)
                return
            }
            if let credential = credential {
                self.pluginImplementation.handleSuccessfulSignIn(credential: credential, idToken: nil, nonce: nil)
            }
        }
    }
}
