import Foundation
import Capacitor

@objc public class PermissionStatusResult: NSObject, Result {
    let receive: String

    init(receive: String) {
        self.receive = receive
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["receive"] = receive
        return result as AnyObject
    }
}
