import Foundation
import Capacitor

@objc public class SetLanguageCodeOptions: NSObject {
    let languageCode: String

    init(_ call: CAPPluginCall) {
        self.languageCode = SetLanguageCodeOptions.getLanguageCodeFromCall(call)
    }

    private static func getLanguageCodeFromCall(_ call: CAPPluginCall) -> String {
        return call.getString("languageCode", "")
    }
}
