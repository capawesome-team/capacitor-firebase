import Foundation
import Capacitor

@objc public class LogEventOptions: NSObject {
    let name: String
    let params: JSObject?

    init(_ call: CAPPluginCall) throws {
        self.name = try LogEventOptions.getNameFromCall(call)
        self.params = LogEventOptions.getParamsFromCall(call)
    }

    private static func getNameFromCall(_ call: CAPPluginCall) throws -> String {
        guard let name = call.getString("name") else {
            throw CustomError.nameMissing
        }
        return name
    }

    private static func getParamsFromCall(_ call: CAPPluginCall) -> JSObject? {
        return call.getObject("params")
    }
}
