import Foundation
import Capacitor

@objc public class GetOptions: NSObject {
    let key: String

    init(_ call: CAPPluginCall) throws {
        self.key = try GetOptions.getKeyFromCall(call)
    }

    private static func getKeyFromCall(_ call: CAPPluginCall) throws -> String {
        guard let key = call.getString("key") else {
            throw CustomError.keyMissing
        }
        return key
    }
}
