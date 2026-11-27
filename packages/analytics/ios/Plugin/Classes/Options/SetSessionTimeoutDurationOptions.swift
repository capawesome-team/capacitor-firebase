import Foundation
import Capacitor

@objc public class SetSessionTimeoutDurationOptions: NSObject {
    let duration: Int

    init(_ call: CAPPluginCall) {
        self.duration = SetSessionTimeoutDurationOptions.getDurationFromCall(call)
    }

    private static func getDurationFromCall(_ call: CAPPluginCall) -> Int {
        return call.getInt("duration") ?? 1800
    }
}
