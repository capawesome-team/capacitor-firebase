import Foundation
import Capacitor

@objc public class CrashOptions: NSObject {
    let message: String

    init(_ call: CAPPluginCall) throws {
        self.message = try CrashOptions.getMessageFromCall(call)
    }

    private static func getMessageFromCall(_ call: CAPPluginCall) throws -> String {
        guard let message = call.getString("message") else {
            throw CustomError.messageMissing
        }
        return message
    }
}
