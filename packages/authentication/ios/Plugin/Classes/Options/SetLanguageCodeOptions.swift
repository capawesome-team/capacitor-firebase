import Foundation
import Capacitor

@objc public class SetLanguageCodeOptions: NSObject {
    let appName: String?
    let languageCode: String

    init(_ call: CAPPluginCall) {
        self.languageCode = SetLanguageCodeOptions.getLanguageCodeFromCall(call)
        self.appName = SetLanguageCodeOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }

    private static func getLanguageCodeFromCall(_ call: CAPPluginCall) -> String {
        return call.getString("languageCode", "")
    }
}
