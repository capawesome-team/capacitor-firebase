import Foundation
import Capacitor

@objc public class UseAppLanguageOptions: NSObject {
    let appName: String?

    init(_ call: CAPPluginCall) {
        self.appName = UseAppLanguageOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }
}
