import Foundation
import Capacitor

@objc public class GetTokenOptions: NSObject {
    let forceRefresh: Bool

    init(_ call: CAPPluginCall) {
        self.forceRefresh = GetTokenOptions.getForceRefreshFromCall(call)
    }

    private static func getForceRefreshFromCall(_ call: CAPPluginCall) -> Bool {
        return call.getBool("forceRefresh") ?? false
    }
}
