import Foundation
import Capacitor

@objc public class GetBooleanResult: NSObject, Result {
    let source: Int
    let value: Bool

    init(value: Bool, source: Int) {
        self.value = value
        self.source = source
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["value"] = value
        result["source"] = source
        return result as AnyObject
    }
}
