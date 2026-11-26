import Foundation
import Capacitor

@objc public class CreateUserWithEmailAndPasswordOptions: NSObject {
    let appName: String?
    let email: String
    let password: String

    init(_ call: CAPPluginCall, config: FirebaseAuthenticationConfig) throws {
        // ALWAYS check skipNativeAuth first. Its error must win over a missing email or password.
        try CreateUserWithEmailAndPasswordOptions.checkSkipNativeAuthFromCall(call, config: config)
        self.email = try CreateUserWithEmailAndPasswordOptions.getEmailFromCall(call)
        self.password = try CreateUserWithEmailAndPasswordOptions.getPasswordFromCall(call)
        self.appName = CreateUserWithEmailAndPasswordOptions.getAppNameFromCall(call)
    }

    private static func checkSkipNativeAuthFromCall(_ call: CAPPluginCall, config: FirebaseAuthenticationConfig) throws {
        let skipNativeAuth = call.getBool("skipNativeAuth", config.skipNativeAuth)
        if skipNativeAuth == true {
            throw CustomError.emailSignInSkipNativeAuth
        }
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }

    private static func getEmailFromCall(_ call: CAPPluginCall) throws -> String {
        guard let email = call.getString("email") else {
            throw CustomError.emailMissing
        }
        return email
    }

    private static func getPasswordFromCall(_ call: CAPPluginCall) throws -> String {
        guard let password = call.getString("password") else {
            throw CustomError.passwordMissing
        }
        return password
    }
}
