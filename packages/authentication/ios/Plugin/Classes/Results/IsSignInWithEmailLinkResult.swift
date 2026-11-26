import Foundation
import Capacitor

@objc public class IsSignInWithEmailLinkResult: NSObject, Result {
    let isSignInWithEmailLink: Bool

    init(isSignInWithEmailLink: Bool) {
        self.isSignInWithEmailLink = isSignInWithEmailLink
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["isSignInWithEmailLink"] = isSignInWithEmailLink
        return result as AnyObject
    }
}
