import Foundation
import Capacitor

@objc public class SetCustomKeyOptions: NSObject {
    let key: String
    let value: Any?

    init(_ call: CAPPluginCall) throws {
        self.key = try SetCustomKeyOptions.getKeyFromCall(call)
        let type = SetCustomKeyOptions.getTypeFromCall(call)
        self.value = try SetCustomKeyOptions.getValueFromCall(call, type: type)
    }

    private static func getKeyFromCall(_ call: CAPPluginCall) throws -> String {
        guard let key = call.getString("key") else {
            throw CustomError.keyMissing
        }
        return key
    }

    private static func getTypeFromCall(_ call: CAPPluginCall) -> String {
        return call.getString("type") ?? "string"
    }

    private static func getValueFromCall(_ call: CAPPluginCall, type: String) throws -> Any? {
        guard let value = call.options["value"], !(value is NSNull) else {
            throw CustomError.valueMissing
        }
        switch type {
        case "long", "int":
            return call.getInt("value")
        case "boolean":
            return call.getBool("value")
        case "float":
            return call.getFloat("value")
        case "double":
            return call.getDouble("value")
        default:
            return call.getString("value")
        }
    }
}
