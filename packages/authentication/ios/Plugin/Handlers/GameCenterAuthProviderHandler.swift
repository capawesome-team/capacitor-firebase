import Foundation
import Capacitor
import FirebaseCore
import FirebaseAuth
import AuthenticationServices
import GameKit

class GameCenterAuthProviderHandler: NSObject {
    public let errorGetCredentialFailed = "getCredential failed."
    private var pluginImplementation: FirebaseAuthentication

    init(_ pluginImplementation: FirebaseAuthentication) {
        self.pluginImplementation = pluginImplementation
        super.init()
    }

    func signIn(call: CAPPluginCall) {
        startSignInWithGameCenterFlow(call, isLink: false)
    }

    func link(call: CAPPluginCall) {
        startSignInWithGameCenterFlow(call, isLink: true)
    }

    func startSignInWithGameCenterFlow(_ call: CAPPluginCall, isLink: Bool) {
        GKLocalPlayer.local.authenticateHandler = { viewController, error in
            if let viewController = viewController {
                DispatchQueue.main.async {
                    self.pluginImplementation.getPlugin().bridge?.viewController?.present(viewController, animated: true, completion: nil)
                }
                return
            }
            if error != nil {
                if isLink {
                    self.pluginImplementation.handleFailedLink(message: nil, error: error)
                } else {
                    self.pluginImplementation.handleFailedSignIn(message: nil, error: error)
                }
                return
            }
            GameCenterAuthProvider.getCredential { (credential, error) in
                if error != nil {
                    if isLink {
                        self.pluginImplementation.handleFailedLink(message: nil, error: error)
                    } else {
                        self.pluginImplementation.handleFailedSignIn(message: nil, error: error)
                    }
                    return
                }
                guard let credential = credential else {
                    if isLink {
                        self.pluginImplementation.handleFailedLink(message: self.errorGetCredentialFailed, error: nil)
                    } else {
                        self.pluginImplementation.handleFailedSignIn(message: self.errorGetCredentialFailed, error: nil)
                    }
                    return
                }
                if isLink {
                    self.pluginImplementation.handleSuccessfulLink(credential: credential, idToken: nil, nonce: nil, accessToken: nil)
                } else {
                    self.pluginImplementation.handleSuccessfulSignIn(credential: credential, idToken: nil, nonce: nil, accessToken: nil)
                }
            }
        }
    }
}
