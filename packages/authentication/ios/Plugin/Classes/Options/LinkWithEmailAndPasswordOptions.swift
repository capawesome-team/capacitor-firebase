import Foundation
import Capacitor

@objc public class LinkWithEmailAndPasswordOptions: SignInOptions {
    let appName: String?
    let email: String
    let password: String

    override init(_ call: CAPPluginCall, config: FirebaseAuthenticationConfig) {
        self.email = LinkWithEmailAndPasswordOptions.getEmailFromCall(call)
        self.password = LinkWithEmailAndPasswordOptions.getPasswordFromCall(call)
        self.appName = LinkWithEmailAndPasswordOptions.getAppNameFromCall(call)
        super.init(call, config: config)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }

    private static func getEmailFromCall(_ call: CAPPluginCall) -> String {
        return call.getString("email", "")
    }

    private static func getPasswordFromCall(_ call: CAPPluginCall) -> String {
        return call.getString("password", "")
    }
}
