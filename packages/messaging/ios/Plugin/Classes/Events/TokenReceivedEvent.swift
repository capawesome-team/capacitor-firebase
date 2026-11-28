import Foundation
import Capacitor

@objc public class TokenReceivedEvent: NSObject, Result {
    let token: String

    init(token: String) {
        self.token = token
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["token"] = token
        return result as AnyObject
    }
}
