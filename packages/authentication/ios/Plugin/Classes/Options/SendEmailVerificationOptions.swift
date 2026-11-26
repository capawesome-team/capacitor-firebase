import Foundation
import Capacitor

@objc public class SendEmailVerificationOptions: NSObject {
    let actionCodeSettings: JSObject?

    init(_ call: CAPPluginCall) {
        self.actionCodeSettings = SendEmailVerificationOptions.getActionCodeSettingsFromCall(call)
    }

    private static func getActionCodeSettingsFromCall(_ call: CAPPluginCall) -> JSObject? {
        return call.getObject("actionCodeSettings")
    }
}
