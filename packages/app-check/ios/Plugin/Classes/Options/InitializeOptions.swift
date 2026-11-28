import Foundation
import Capacitor

@objc public class InitializeOptions: NSObject {
    let debug: Bool
    let isTokenAutoRefreshEnabled: Bool

    init(_ call: CAPPluginCall) {
        self.debug = InitializeOptions.getDebugFromCall(call)
        self.isTokenAutoRefreshEnabled = InitializeOptions.getIsTokenAutoRefreshEnabledFromCall(call)
    }

    private static func getDebugFromCall(_ call: CAPPluginCall) -> Bool {
        let debugToken: Any? = call.getValue("debugToken")
        if let debugTokenBool = debugToken as? Bool {
            return debugTokenBool
        }
        if let debugTokenString = debugToken as? String, !debugTokenString.isEmpty {
            return true
        }
        return call.getBool("debug", false)
    }

    private static func getIsTokenAutoRefreshEnabledFromCall(_ call: CAPPluginCall) -> Bool {
        return call.getBool("isTokenAutoRefreshEnabled") ?? false
    }
}
