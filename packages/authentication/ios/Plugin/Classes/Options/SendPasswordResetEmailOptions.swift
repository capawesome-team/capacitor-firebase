import Foundation
import Capacitor

@objc public class SendPasswordResetEmailOptions: NSObject {
    let actionCodeSettings: JSObject?
    let appName: String?
    let email: String

    init(_ call: CAPPluginCall) throws {
        self.email = try SendPasswordResetEmailOptions.getEmailFromCall(call)
        self.actionCodeSettings = SendPasswordResetEmailOptions.getActionCodeSettingsFromCall(call)
        self.appName = SendPasswordResetEmailOptions.getAppNameFromCall(call)
    }

    private static func getActionCodeSettingsFromCall(_ call: CAPPluginCall) -> JSObject? {
        return call.getObject("actionCodeSettings")
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }

    private static func getEmailFromCall(_ call: CAPPluginCall) throws -> String {
        guard let email = call.getString("email") else {
            throw CustomError.emailMissing
        }
        return email
    }
}
