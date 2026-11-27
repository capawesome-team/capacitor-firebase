import Foundation
import Capacitor

@objc public class SetUserIdOptions: NSObject {
    let userId: String

    init(_ call: CAPPluginCall) throws {
        self.userId = try SetUserIdOptions.getUserIdFromCall(call)
    }

    private static func getUserIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let userId = call.getString("userId") else {
            throw CustomError.userIdMissing
        }
        return userId
    }
}
