import Foundation
import Capacitor

@objc public class GetIdTokenOptions: NSObject {
    let forceRefresh: Bool

    init(_ call: CAPPluginCall) {
        self.forceRefresh = GetIdTokenOptions.getForceRefreshFromCall(call)
    }

    private static func getForceRefreshFromCall(_ call: CAPPluginCall) -> Bool {
        return call.getBool("forceRefresh", false)
    }
}
