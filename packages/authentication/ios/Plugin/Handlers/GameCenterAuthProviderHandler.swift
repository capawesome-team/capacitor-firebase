import Foundation
import Capacitor
import FirebaseCore
import FirebaseAuth
import AuthenticationServices
import GameKit

class AppleAuthProviderHandler: NSObject {
    var pluginImplementation: FirebaseAuthentication
    fileprivate var currentNonce: String?
    fileprivate var isLink: Bool?
    
    init(_ pluginImplementation: FirebaseAuthentication) {
        self.pluginImplementation = pluginImplementation
    }
    
    func signIn(call: CAPPluginCall) {
        GKLocalPlayer.local.authenticateHandler = { viewController, error in
            if let viewController = viewController {
                present(viewController);
                return
            }
            if error != nil {
                call.reject(self.pluginImplementation.getPlugin().errorDeviceUnsupported)
                return
            }
            
            // Player was successfully authenticated.
            // Check if there are any player restrictions before starting the game.
            
            if GKLocalPlayer.local.isUnderage {
                // Hide explicit game content.
            }
            
            if GKLocalPlayer.local.isMultiplayerGamingRestricted {
                // Disable multiplayer game features.
            }
            
            if GKLocalPlayer.local.isPersonalizedCommunicationRestricted {
                // Disable in game communication UI.
            }
            
            // Perform any other configurations as needed (for example, access point).
            GameCenterAuthProvider.getCredential() { (credential, error) in
                if let error = error {
                    call.reject(self.pluginImplementation.getPlugin().errorDeviceUnsupported)
                    return
                }
                // The credential can be used to sign in, or re-auth, or link or unlink.
                Auth.auth().signIn(with:credential) { (user, error) in
                    if let error = error {
                        call.reject(self.pluginImplementation.getPlugin().errorDeviceUnsupported)
                        
                        return
                    }
                    // Player is signed in!
                }
            }
        }
    }
}
