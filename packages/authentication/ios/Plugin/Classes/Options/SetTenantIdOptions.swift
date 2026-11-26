import Foundation
import Capacitor

@objc public class SetTenantIdOptions: NSObject {
    let appName: String?
    let tenantId: String

    init(_ call: CAPPluginCall) throws {
        self.tenantId = try SetTenantIdOptions.getTenantIdFromCall(call)
        self.appName = SetTenantIdOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }

    private static func getTenantIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let tenantId = call.getString("tenantId") else {
            throw CustomError.tenantIdMissing
        }
        return tenantId
    }
}
