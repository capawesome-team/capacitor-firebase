import Foundation
import Capacitor

@objc public class CallResult: NSObject, Result {
    private var data: Any?

    init(data: Any?) {
        self.data = data
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["data"] = data as! any JSValue
        return result as AnyObject
    }
}
