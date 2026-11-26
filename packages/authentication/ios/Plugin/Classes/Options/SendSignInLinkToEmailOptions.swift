import Foundation
import Capacitor

@objc public class SendSignInLinkToEmailOptions: NSObject {
    let actionCodeSettings: JSObject
    let email: String

    init(_ call: CAPPluginCall) throws {
        self.email = try SendSignInLinkToEmailOptions.getEmailFromCall(call)
        self.actionCodeSettings = try SendSignInLinkToEmailOptions.getActionCodeSettingsFromCall(call)
    }

    private static func getActionCodeSettingsFromCall(_ call: CAPPluginCall) throws -> JSObject {
        guard let actionCodeSettings = call.getObject("actionCodeSettings") else {
            throw CustomError.actionCodeSettingsMissing
        }
        return actionCodeSettings
    }

    private static func getEmailFromCall(_ call: CAPPluginCall) throws -> String {
        guard let email = call.getString("email") else {
            throw CustomError.emailMissing
        }
        return email
    }
}
