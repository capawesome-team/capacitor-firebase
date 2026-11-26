import Foundation
import Capacitor

@objc public class ReloadOptions: NSObject {
    let appName: String?

    init(_ call: CAPPluginCall) {
        self.appName = ReloadOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }
}
