import Foundation
import Capacitor

@objc public class UnlinkOptions: NSObject {
    let appName: String?
    let providerId: String

    init(_ call: CAPPluginCall) throws {
        self.providerId = try UnlinkOptions.getProviderIdFromCall(call)
        self.appName = UnlinkOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }

    private static func getProviderIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let providerId = call.getString("providerId") else {
            throw CustomError.providerIdMissing
        }
        return providerId
    }
}
