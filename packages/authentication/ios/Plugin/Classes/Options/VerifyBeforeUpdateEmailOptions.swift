import Foundation
import Capacitor

@objc public class VerifyBeforeUpdateEmailOptions: NSObject {
    let actionCodeSettings: JSObject?
    let appName: String?
    let newEmail: String

    init(_ call: CAPPluginCall) throws {
        self.newEmail = try VerifyBeforeUpdateEmailOptions.getNewEmailFromCall(call)
        self.actionCodeSettings = VerifyBeforeUpdateEmailOptions.getActionCodeSettingsFromCall(call)
        self.appName = VerifyBeforeUpdateEmailOptions.getAppNameFromCall(call)
    }

    private static func getActionCodeSettingsFromCall(_ call: CAPPluginCall) -> JSObject? {
        return call.getObject("actionCodeSettings")
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }

    private static func getNewEmailFromCall(_ call: CAPPluginCall) throws -> String {
        guard let newEmail = call.getString("newEmail") else {
            throw CustomError.newEmailMissing
        }
        return newEmail
    }
}
