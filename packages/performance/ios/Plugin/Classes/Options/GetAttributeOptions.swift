import Foundation
import Capacitor

@objc public class GetAttributeOptions: NSObject {
    let attribute: String
    let traceName: String

    init(_ call: CAPPluginCall) throws {
        // ALWAYS read the traceName before the attribute, so a missing traceName is reported first.
        self.traceName = try GetAttributeOptions.getTraceNameFromCall(call)
        self.attribute = try GetAttributeOptions.getAttributeFromCall(call)
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
}
