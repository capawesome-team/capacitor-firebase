import Foundation
import Capacitor

@objc public class SignOutOptions: NSObject {
    let appName: String?

    init(_ call: CAPPluginCall) {
        self.appName = SignOutOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }
}
