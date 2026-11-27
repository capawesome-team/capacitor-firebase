import Foundation
import Capacitor

@objc public class SetUserPropertyOptions: NSObject {
    let key: String
    let value: String?

    init(_ call: CAPPluginCall) throws {
        self.key = try SetUserPropertyOptions.getKeyFromCall(call)
        self.value = SetUserPropertyOptions.getValueFromCall(call)
    }

    private static func getKeyFromCall(_ call: CAPPluginCall) throws -> String {
        guard let key = call.getString("key") else {
            throw CustomError.keyMissing
        }
        return key
    }

    private static func getValueFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("value")
    }
}
