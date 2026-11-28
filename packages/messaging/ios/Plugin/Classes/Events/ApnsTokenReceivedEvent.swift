import Foundation
import Capacitor

@objc public class ApnsTokenReceivedEvent: NSObject, Result {
    let deviceToken: Data

    init(deviceToken: Data) {
        self.deviceToken = deviceToken
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["token"] = deviceToken.reduce("", {$0 + String(format: "%02X", $1)})
        return result as AnyObject
    }
}
