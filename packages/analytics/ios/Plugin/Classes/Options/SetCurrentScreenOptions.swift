import Foundation
import Capacitor

@objc public class SetCurrentScreenOptions: NSObject {
    let screenClassOverride: String?
    let screenName: String?

    init(_ call: CAPPluginCall) {
        self.screenName = SetCurrentScreenOptions.getScreenNameFromCall(call)
        self.screenClassOverride = SetCurrentScreenOptions.getScreenClassOverrideFromCall(call)
    }

    private static func getScreenClassOverrideFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("screenClassOverride")
    }

    private static func getScreenNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("screenName")
    }
}
