import Foundation
import Capacitor

@objc public class SetTenantIdOptions: NSObject {
    let tenantId: String

    init(_ call: CAPPluginCall) throws {
        self.tenantId = try SetTenantIdOptions.getTenantIdFromCall(call)
    }

    private static func getTenantIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let tenantId = call.getString("tenantId") else {
            throw CustomError.tenantIdMissing
        }
        return tenantId
    }
}
