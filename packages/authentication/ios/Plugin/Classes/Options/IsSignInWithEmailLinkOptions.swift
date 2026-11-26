import Foundation
import Capacitor

@objc public class IsSignInWithEmailLinkOptions: NSObject {
    let emailLink: String

    init(_ call: CAPPluginCall) throws {
        self.emailLink = try IsSignInWithEmailLinkOptions.getEmailLinkFromCall(call)
    }

    private static func getEmailLinkFromCall(_ call: CAPPluginCall) throws -> String {
        guard let emailLink = call.getString("emailLink") else {
            throw CustomError.emailLinkMissing
        }
        return emailLink
    }
}
