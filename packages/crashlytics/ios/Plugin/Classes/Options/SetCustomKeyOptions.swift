import Foundation
import Capacitor

@objc public class SetCustomKeyOptions: NSObject {
    let key: String
    let value: Any

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

    private static func getValueFromCall(_ call: CAPPluginCall, type: String) throws -> Any {
        guard let value = call.options["value"], !(value is NSNull) else {
            throw CustomError.valueMissing
        }
        let typedValue: Any?
        switch type {
        case "long":
            typedValue = call.getInt("value")
        case "int":
            typedValue = call.getInt("value").flatMap { Int32(exactly: $0) }.map { Int($0) }
        case "boolean":
            typedValue = call.getBool("value")
        case "float":
            typedValue = call.getFloat("value")
        case "double":
            typedValue = call.getDouble("value")
        default:
            typedValue = call.getString("value")
        }
        guard let typedValue = typedValue, isBoolean(value) == (type == "boolean") else {
            throw CustomError.valueInvalid
        }
        return typedValue
    }

    private static func isBoolean(_ value: Any) -> Bool {
        // NEVER use "value is Bool" here. It is also true for the numbers 0 and 1.
        return CFGetTypeID(value as CFTypeRef) == CFBooleanGetTypeID()
    }
}
