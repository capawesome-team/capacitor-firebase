import Foundation
import Capacitor

@objc public class RevokeAccessTokenOptions: NSObject {
    let token: String

    init(_ call: CAPPluginCall) throws {
        self.token = try RevokeAccessTokenOptions.getTokenFromCall(call)
    }

    private static func getTokenFromCall(_ call: CAPPluginCall) throws -> String {
        guard let token = call.getString("token") else {
            throw CustomError.tokenMissing
        }
        return token
    }
}
