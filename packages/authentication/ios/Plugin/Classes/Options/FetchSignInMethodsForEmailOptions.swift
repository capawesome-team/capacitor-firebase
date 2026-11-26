import Foundation
import Capacitor

@objc public class FetchSignInMethodsForEmailOptions: NSObject {
    let appName: String?
    let email: String

    init(_ call: CAPPluginCall) throws {
        self.email = try FetchSignInMethodsForEmailOptions.getEmailFromCall(call)
        self.appName = FetchSignInMethodsForEmailOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }

    private static func getEmailFromCall(_ call: CAPPluginCall) throws -> String {
        guard let email = call.getString("email") else {
            throw CustomError.emailMissing
        }
        return email
    }
}
