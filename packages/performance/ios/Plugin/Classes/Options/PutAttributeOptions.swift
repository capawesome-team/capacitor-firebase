import Foundation
import Capacitor

@objc public class PutAttributeOptions: NSObject {
    let attribute: String
    let traceName: String
    let value: String

    init(_ call: CAPPluginCall) throws {
        // ALWAYS read the traceName, the attribute and the value in this order, so the first missing one is reported.
        self.traceName = try PutAttributeOptions.getTraceNameFromCall(call)
        self.attribute = try PutAttributeOptions.getAttributeFromCall(call)
        self.value = try PutAttributeOptions.getValueFromCall(call)
    }

    private static func getAttributeFromCall(_ call: CAPPluginCall) throws -> String {
        guard let attribute = call.getString("attribute") else {
            throw CustomError.attributeMissing
        }
        return attribute
    }

    private static func getTraceNameFromCall(_ call: CAPPluginCall) throws -> String {
        guard let traceName = call.getString("traceName") else {
            throw CustomError.traceNameMissing
        }
        return traceName
    }

    private static func getValueFromCall(_ call: CAPPluginCall) throws -> String {
        guard let value = call.getString("value") else {
            throw CustomError.valueMissing
        }
        return value
    }
}
