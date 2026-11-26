import Foundation
import Capacitor

@objc public class SignInAnonymouslyOptions: NSObject {
    let appName: String?

    init(_ call: CAPPluginCall) {
        self.appName = SignInAnonymouslyOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }
}
