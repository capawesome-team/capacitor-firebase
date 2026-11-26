import Foundation
import Capacitor

@objc public class SignInWithCustomTokenOptions: SignInOptions {
    let appName: String?
    let token: String

    override init(_ call: CAPPluginCall, config: FirebaseAuthenticationConfig) {
        self.token = SignInWithCustomTokenOptions.getTokenFromCall(call)
        self.appName = SignInWithCustomTokenOptions.getAppNameFromCall(call)
        super.init(call, config: config)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }

    private static func getTokenFromCall(_ call: CAPPluginCall) -> String {
        return call.getString("token", "")
    }
}
