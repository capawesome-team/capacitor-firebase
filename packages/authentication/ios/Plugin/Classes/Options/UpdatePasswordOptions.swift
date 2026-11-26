import Foundation
import Capacitor

@objc public class UpdatePasswordOptions: NSObject {
    let appName: String?
    let newPassword: String

    init(_ call: CAPPluginCall) throws {
        self.newPassword = try UpdatePasswordOptions.getNewPasswordFromCall(call)
        self.appName = UpdatePasswordOptions.getAppNameFromCall(call)
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
}
