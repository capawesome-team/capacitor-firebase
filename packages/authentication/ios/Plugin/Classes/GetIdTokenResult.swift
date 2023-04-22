import Foundation
import FirebaseAuth
import Capacitor

@objc class GetIdTokenResult: NSObject {
    let token: String

    init(token: String) {
        self.token = token
    }

    func toJSObject() -> JSObject {
        var result = JSObject()
        result["token"] = self.token
        return result
    }
}
