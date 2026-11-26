import Foundation
import Capacitor

@objc public class UpdateEmailOptions: NSObject {
    let newEmail: String

    init(_ call: CAPPluginCall) throws {
        self.newEmail = try UpdateEmailOptions.getNewEmailFromCall(call)
    }

    private static func getNewEmailFromCall(_ call: CAPPluginCall) throws -> String {
        guard let newEmail = call.getString("newEmail") else {
            throw CustomError.newEmailMissing
        }
        return newEmail
    }
}
