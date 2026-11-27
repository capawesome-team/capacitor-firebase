import Foundation
import Capacitor

@objc public class RecordExceptionOptions: NSObject {
    let code: Int
    let domain: String
    let keysAndValues: [JSObject]?
    let message: String
    let stacktrace: [JSObject]?

    init(_ call: CAPPluginCall) throws {
        self.message = try RecordExceptionOptions.getMessageFromCall(call)
        self.stacktrace = RecordExceptionOptions.getStacktraceFromCall(call)
        self.keysAndValues = RecordExceptionOptions.getKeysAndValuesFromCall(call)
        self.domain = RecordExceptionOptions.getDomainFromCall(call)
        self.code = RecordExceptionOptions.getCodeFromCall(call)
    }

    private static func getCodeFromCall(_ call: CAPPluginCall) -> Int {
        return call.getInt("code") ?? -1001
    }

    private static func getDomainFromCall(_ call: CAPPluginCall) -> String {
        return call.getString("domain") ?? ""
    }

    private static func getKeysAndValuesFromCall(_ call: CAPPluginCall) -> [JSObject]? {
        return call.getArray("keysAndValues", JSObject.self)
    }

    private static func getMessageFromCall(_ call: CAPPluginCall) throws -> String {
        guard let message = call.getString("message") else {
            throw CustomError.messageMissing
        }
        return message
    }

    private static func getStacktraceFromCall(_ call: CAPPluginCall) -> [JSObject]? {
        return call.getArray("stacktrace", JSObject.self)
    }
}
