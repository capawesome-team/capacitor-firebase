import Foundation
import Capacitor

@objc public class TerminateOptions: NSObject {
    let appName: String?

    init(_ call: CAPPluginCall) {
        self.appName = TerminateOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }
}
