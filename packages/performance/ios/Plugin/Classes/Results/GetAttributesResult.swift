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
        // The old key `result` stays until the next major version, so apps that read it keep working.
        result["result"] = attributes as JSObject
        return result as AnyObject
    }
}
