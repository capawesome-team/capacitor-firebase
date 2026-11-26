import Foundation
import Capacitor

@objc public class GetIdTokenOptions: NSObject {
    let appName: String?
    let forceRefresh: Bool

    init(_ call: CAPPluginCall) {
        self.forceRefresh = GetIdTokenOptions.getForceRefreshFromCall(call)
        self.appName = GetIdTokenOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }

    private static func getForceRefreshFromCall(_ call: CAPPluginCall) -> Bool {
        return call.getBool("forceRefresh", false)
    }
}
