import Foundation
import Capacitor

@objc public class TokenChangedEvent: NSObject, Result {
    let token: String?

    init(token: String?) {
        self.token = token
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["token"] = token ?? NSNull()
        return result as AnyObject
    }
}
