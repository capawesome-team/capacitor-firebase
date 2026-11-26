import Foundation
import Capacitor

@objc public class GetIdTokenResultOptions: NSObject {
    let forceRefresh: Bool

    init(_ call: CAPPluginCall) {
        self.forceRefresh = GetIdTokenResultOptions.getForceRefreshFromCall(call)
    }

    private static func getForceRefreshFromCall(_ call: CAPPluginCall) -> Bool {
        return call.getBool("forceRefresh", false)
    }
}
