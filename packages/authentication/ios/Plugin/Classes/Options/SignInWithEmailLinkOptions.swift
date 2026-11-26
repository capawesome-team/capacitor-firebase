import Foundation
import Capacitor

@objc public class SignInWithEmailLinkOptions: NSObject {
    let email: String
    let emailLink: String

    init(_ call: CAPPluginCall) {
        self.email = SignInWithEmailLinkOptions.getEmailFromCall(call)
        self.emailLink = SignInWithEmailLinkOptions.getEmailLinkFromCall(call)
    }

    private static func getEmailFromCall(_ call: CAPPluginCall) -> String {
        return call.getString("email", "")
    }

    private static func getEmailLinkFromCall(_ call: CAPPluginCall) -> String {
        return call.getString("emailLink", "")
    }
}
