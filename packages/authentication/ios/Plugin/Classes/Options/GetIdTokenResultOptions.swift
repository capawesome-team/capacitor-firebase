import Foundation
import Capacitor

@objc public class GetIdTokenResultOptions: NSObject {
    let appName: String?
    let forceRefresh: Bool

    init(_ call: CAPPluginCall) {
        self.forceRefresh = GetIdTokenResultOptions.getForceRefreshFromCall(call)
        self.appName = GetIdTokenResultOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }

    private static func getForceRefreshFromCall(_ call: CAPPluginCall) -> Bool {
        return call.getBool("forceRefresh", false)
    }
}
