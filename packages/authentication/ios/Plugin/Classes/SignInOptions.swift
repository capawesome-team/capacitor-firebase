import Foundation
import Capacitor

@objc public class SignInOptions: NSObject {
    private var skipNativeAuth: Bool

    init(skipNativeAuth: Bool) {
        self.skipNativeAuth = skipNativeAuth
    }

    init(_ call: CAPPluginCall, config: FirebaseAuthenticationConfig) {
        self.skipNativeAuth = SignInOptions.getSkipNativeAuthFromCall(call, config: config)
    }

    func getSkipNativeAuth() -> Bool {
        return skipNativeAuth
    }

    private static func getSkipNativeAuthFromCall(_ call: CAPPluginCall, config: FirebaseAuthenticationConfig) -> Bool {
        return call.getBool("skipNativeAuth", config.skipNativeAuth)
    }
}
