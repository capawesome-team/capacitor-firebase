import Foundation
import Capacitor

@objc public class GetMetricResult: NSObject, Result {
    let value: Int64

    init(value: Int64) {
        self.value = value
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["value"] = Int(value)
        return result as AnyObject
    }
}
