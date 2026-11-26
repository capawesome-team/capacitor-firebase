import Foundation
import Capacitor
import FirebaseAuth

@objc public class AuthStateChangeEvent: NSObject, Result {
    let user: User?

    init(user: User?) {
        self.user = user
    }

    public func toJSObject() -> AnyObject {
        let userResult = FirebaseAuthenticationHelper.createUserResult(user)
        var result = JSObject()
        result["user"] = userResult ?? NSNull()
        return result as AnyObject
    }
}
