import Foundation
import Capacitor

@objc public class FetchSignInMethodsForEmailResult: NSObject, Result {
    let signInMethods: [String]

    init(_ signInMethods: [String]) {
        self.signInMethods = signInMethods
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["providerIds"] = signInMethods
        return result as AnyObject
    }
}
