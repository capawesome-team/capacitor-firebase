import Foundation
import Capacitor

@objc public class SetUserIdOptions: NSObject {
    let userId: String?

    init(_ call: CAPPluginCall) {
        self.userId = SetUserIdOptions.getUserIdFromCall(call)
    }

    private static func getUserIdFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("userId")
    }
}
