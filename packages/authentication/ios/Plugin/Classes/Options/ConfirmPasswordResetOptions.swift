import Foundation
import Capacitor

@objc public class ConfirmPasswordResetOptions: NSObject {
    let appName: String?
    let newPassword: String
    let oobCode: String

    init(_ call: CAPPluginCall) throws {
        self.oobCode = try ConfirmPasswordResetOptions.getOobCodeFromCall(call)
        self.newPassword = try ConfirmPasswordResetOptions.getNewPasswordFromCall(call)
        self.appName = ConfirmPasswordResetOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }

    private static func getNewPasswordFromCall(_ call: CAPPluginCall) throws -> String {
        guard let newPassword = call.getString("newPassword") else {
            throw CustomError.newPasswordMissing
        }
        return newPassword
    }

    private static func getOobCodeFromCall(_ call: CAPPluginCall) throws -> String {
        guard let oobCode = call.getString("oobCode") else {
            throw CustomError.oobCodeMissing
        }
        return oobCode
    }
}
