import Foundation
import Capacitor

@objc public class LinkWithEmailAndPasswordOptions: SignInOptions {
    let email: String
    let password: String

    override init(_ call: CAPPluginCall, config: FirebaseAuthenticationConfig) {
        self.email = LinkWithEmailAndPasswordOptions.getEmailFromCall(call)
        self.password = LinkWithEmailAndPasswordOptions.getPasswordFromCall(call)
        super.init(call, config: config)
    }

    private static func getEmailFromCall(_ call: CAPPluginCall) -> String {
        return call.getString("email", "")
    }

    private static func getPasswordFromCall(_ call: CAPPluginCall) -> String {
        return call.getString("password", "")
    }
}
