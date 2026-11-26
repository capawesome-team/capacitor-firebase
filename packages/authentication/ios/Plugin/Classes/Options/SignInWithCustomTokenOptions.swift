import Foundation
import Capacitor

@objc public class SignInWithCustomTokenOptions: SignInOptions {
    let token: String

    override init(_ call: CAPPluginCall, config: FirebaseAuthenticationConfig) {
        self.token = SignInWithCustomTokenOptions.getTokenFromCall(call)
        super.init(call, config: config)
    }

    private static func getTokenFromCall(_ call: CAPPluginCall) -> String {
        return call.getString("token", "")
    }
}
