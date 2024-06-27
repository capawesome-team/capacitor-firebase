import Foundation
import Capacitor
import FirebaseAuth

@objc public class SendEmailVerificationOptions: NSObject {
    private var actionCodeSettings: ActionCodeSettings?

    init(actionCodeSettings: JSObject?) {
        self.actionCodeSettings = FirebaseAuthenticationHelper.createActionCodeSettings(actionCodeSettings)
    }

    func getActionCodeSettings() -> ActionCodeSettings? {
        return actionCodeSettings
    }
}
