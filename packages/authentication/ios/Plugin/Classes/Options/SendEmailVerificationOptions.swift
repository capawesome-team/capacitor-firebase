import Foundation
import Capacitor

@objc public class SendEmailVerificationOptions: NSObject {
    let actionCodeSettings: JSObject?
    let appName: String?

    init(_ call: CAPPluginCall) {
        self.actionCodeSettings = SendEmailVerificationOptions.getActionCodeSettingsFromCall(call)
        self.appName = SendEmailVerificationOptions.getAppNameFromCall(call)
    }

    private static func getActionCodeSettingsFromCall(_ call: CAPPluginCall) -> JSObject? {
        return call.getObject("actionCodeSettings")
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }
}
