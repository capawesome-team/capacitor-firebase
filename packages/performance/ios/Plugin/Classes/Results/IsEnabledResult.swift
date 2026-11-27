import Foundation
import Capacitor

@objc public class IsEnabledResult: NSObject, Result {
    let enabled: Bool

    init(enabled: Bool) {
        self.enabled = enabled
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["enabled"] = enabled
        return result as AnyObject
    }
}
