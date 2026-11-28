import Foundation
import Capacitor

@objc public class IsSupportedResult: NSObject, Result {
    let isSupported: Bool

    init(isSupported: Bool) {
        self.isSupported = isSupported
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["isSupported"] = isSupported
        return result as AnyObject
    }
}
