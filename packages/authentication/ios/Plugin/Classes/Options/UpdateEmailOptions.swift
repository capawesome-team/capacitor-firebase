import Foundation
import Capacitor

@objc public class UpdateEmailOptions: NSObject {
    let appName: String?
    let newEmail: String

    init(_ call: CAPPluginCall) throws {
        self.newEmail = try UpdateEmailOptions.getNewEmailFromCall(call)
        self.appName = UpdateEmailOptions.getAppNameFromCall(call)
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
