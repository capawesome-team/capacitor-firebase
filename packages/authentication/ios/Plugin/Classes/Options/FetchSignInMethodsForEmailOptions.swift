import Foundation
import Capacitor

@objc public class FetchSignInMethodsForEmailOptions: NSObject {
    let email: String

    init(_ call: CAPPluginCall) throws {
        self.email = try FetchSignInMethodsForEmailOptions.getEmailFromCall(call)
    }

    private static func getEmailFromCall(_ call: CAPPluginCall) throws -> String {
        guard let email = call.getString("email") else {
            throw CustomError.emailMissing
        }
        return email
    }
}
