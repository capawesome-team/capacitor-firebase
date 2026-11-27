import Foundation
import Capacitor

@objc public class GetStringResult: NSObject, Result {
    let source: Int
    let value: String

    init(value: String, source: Int) {
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
