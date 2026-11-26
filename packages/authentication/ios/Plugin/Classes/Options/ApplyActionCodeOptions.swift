import Foundation
import Capacitor

@objc public class ApplyActionCodeOptions: NSObject {
    let appName: String?
    let oobCode: String

    init(_ call: CAPPluginCall) throws {
        self.oobCode = try ApplyActionCodeOptions.getOobCodeFromCall(call)
        self.appName = ApplyActionCodeOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }

    private static func getOobCodeFromCall(_ call: CAPPluginCall) throws -> String {
        guard let oobCode = call.getString("oobCode") else {
            throw CustomError.oobCodeMissing
        }
        return oobCode
    }
}
