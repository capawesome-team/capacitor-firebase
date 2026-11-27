import Foundation
import Capacitor

@objc public class GetNameResult: NSObject, Result {
    let name: String

    init(name: String) {
        self.name = name
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["name"] = name
        return result as AnyObject
    }
}
