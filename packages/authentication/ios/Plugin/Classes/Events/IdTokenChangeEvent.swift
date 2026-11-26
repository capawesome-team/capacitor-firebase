import Foundation
import Capacitor

@objc public class IdTokenChangeEvent: NSObject, Result {
    let token: String

    init(token: String) {
        self.token = token
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["token"] = token
        return result as AnyObject
    }
}
