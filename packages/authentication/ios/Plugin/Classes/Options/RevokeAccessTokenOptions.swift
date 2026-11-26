import Foundation
import Capacitor

@objc public class RevokeAccessTokenOptions: NSObject {
    let appName: String?
    let token: String

    init(_ call: CAPPluginCall) throws {
        self.token = try RevokeAccessTokenOptions.getTokenFromCall(call)
        self.appName = RevokeAccessTokenOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }

    private static func getTokenFromCall(_ call: CAPPluginCall) throws -> String {
        guard let token = call.getString("token") else {
            throw CustomError.tokenMissing
        }
        return token
    }
}
