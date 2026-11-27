import Foundation
import Capacitor

@objc public class GetAttributesResult: NSObject, Result {
    let attributes: [String: String]

    init(attributes: [String: String]) {
        self.attributes = attributes
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["attributes"] = attributes as JSObject
        return result as AnyObject
    }
}
