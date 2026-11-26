import Foundation
import Capacitor

@objc public class UnlinkOptions: NSObject {
    let providerId: String

    init(_ call: CAPPluginCall) throws {
        self.providerId = try UnlinkOptions.getProviderIdFromCall(call)
    }

    private static func getProviderIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let providerId = call.getString("providerId") else {
            throw CustomError.providerIdMissing
        }
        return providerId
    }
}
