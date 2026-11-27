import Foundation
import Capacitor

@objc public class GetAttributeResult: NSObject, Result {
    let value: String?

    init(value: String?) {
        self.value = value
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["value"] = value ?? NSNull()
        return result as AnyObject
    }
}
