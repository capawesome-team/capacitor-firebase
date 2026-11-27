import Foundation
import Capacitor

@objc public class GetNumberResult: NSObject, Result {
    let source: Int
    let value: NSNumber

    init(value: NSNumber, source: Int) {
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
