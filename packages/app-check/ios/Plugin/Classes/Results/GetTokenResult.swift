import Foundation
import Capacitor

@objc public class GetTokenResult: NSObject, Result {
    let expireTimeMillis: Int
    let token: String

    init(token: String, expireTimeMillis: Int) {
        self.token = token
        self.expireTimeMillis = expireTimeMillis
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["token"] = token
        result["expireTimeMillis"] = expireTimeMillis
        return result as AnyObject
    }
}
