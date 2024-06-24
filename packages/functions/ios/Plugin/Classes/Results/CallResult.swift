import Foundation
import Capacitor

@objc public class CallResult: NSObject, Result {
    private var data: Any?

    init(data: Any?) {
        self.data = data
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["data"] = CallResult.createJSValue(value: data)
        return result as AnyObject
    }
    
    private static func createJSValue(value: Any?) -> JSValue? {
        guard let value = value else {
            return nil
        }
        guard let value = JSTypes.coerceDictionaryToJSObject(["key": value]) as JSObject? else {
            return nil
        }
        return value["key"]
    }
}
