import Foundation
import Capacitor

@objc public class GetTenantIdResult: NSObject, Result {
    let tenantId: String?

    init(tenantId: String?) {
        self.tenantId = tenantId
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["tenantId"] = tenantId ?? NSNull()
        return result as AnyObject
    }
}
