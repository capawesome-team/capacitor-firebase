import Foundation
import Capacitor

@objc public class DeleteUserOptions: NSObject {
    let appName: String?

    init(_ call: CAPPluginCall) {
        self.appName = DeleteUserOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }
}
