import Foundation
import Capacitor

@objc public class GetTenantIdOptions: NSObject {
    let appName: String?

    init(_ call: CAPPluginCall) {
        self.appName = GetTenantIdOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }
}
