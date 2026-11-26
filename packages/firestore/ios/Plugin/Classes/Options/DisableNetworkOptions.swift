import Foundation
import Capacitor

@objc public class DisableNetworkOptions: NSObject {
    let appName: String?

    init(_ call: CAPPluginCall) {
        self.appName = DisableNetworkOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }
}
