import Foundation
import Capacitor

@objc public class GetCurrentUserOptions: NSObject {
    let appName: String?

    init(_ call: CAPPluginCall) {
        self.appName = GetCurrentUserOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }
}
