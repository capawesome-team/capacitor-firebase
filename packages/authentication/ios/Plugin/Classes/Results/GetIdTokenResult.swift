import Foundation
import FirebaseAuth
import Capacitor

@objc class GetIdTokenResult: NSObject, Result {
    let token: String

    init(token: String) {
        self.token = token
    }

    func toJSObject() -> AnyObject {
        var result = JSObject()
        result["token"] = self.token
        return result as AnyObject
    }
}
