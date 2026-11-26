import Foundation
import Capacitor

@objc public class EnableNetworkOptions: NSObject {
    let appName: String?

    init(_ call: CAPPluginCall) {
        self.appName = EnableNetworkOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }
}
