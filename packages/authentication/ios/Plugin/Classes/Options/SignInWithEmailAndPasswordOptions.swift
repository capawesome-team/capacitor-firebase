import Foundation
import Capacitor

@objc public class SignInWithEmailAndPasswordOptions: NSObject {
    let appName: String?
    let email: String
    let password: String

    init(_ call: CAPPluginCall) {
        self.email = SignInWithEmailAndPasswordOptions.getEmailFromCall(call)
        self.password = SignInWithEmailAndPasswordOptions.getPasswordFromCall(call)
        self.appName = SignInWithEmailAndPasswordOptions.getAppNameFromCall(call)
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
