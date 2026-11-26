import Foundation
import Capacitor

@objc public class UpdatePasswordOptions: NSObject {
    let newPassword: String

    init(_ call: CAPPluginCall) throws {
        self.newPassword = try UpdatePasswordOptions.getNewPasswordFromCall(call)
    }

    private static func getNewPasswordFromCall(_ call: CAPPluginCall) throws -> String {
        guard let newPassword = call.getString("newPassword") else {
            throw CustomError.newPasswordMissing
        }
        return newPassword
    }
}
