import Foundation
import Capacitor

@objc public class ClearPersistenceOptions: NSObject {
    let appName: String?

    init(_ call: CAPPluginCall) {
        self.appName = ClearPersistenceOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }
}
