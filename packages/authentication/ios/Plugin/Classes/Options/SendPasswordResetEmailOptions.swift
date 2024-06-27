import Foundation
import Capacitor
import FirebaseAuth

@objc public class SendPasswordResetEmailOptions: NSObject {
    private var email: String
    private var actionCodeSettings: ActionCodeSettings?

    init(email: String, actionCodeSettings: JSObject?) {
        self.email = email
        self.actionCodeSettings = FirebaseAuthenticationHelper.createActionCodeSettings(actionCodeSettings)
    }
    
    func getEmail() -> String {
        return email
    }

    func getActionCodeSettings() -> ActionCodeSettings? {
        return actionCodeSettings
    }
}
