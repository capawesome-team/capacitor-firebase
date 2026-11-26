import Foundation
import Capacitor

@objc public class DisablePersistenceOptions: NSObject {
    let appName: String?

    init(_ call: CAPPluginCall) {
        self.appName = DisablePersistenceOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }
}
