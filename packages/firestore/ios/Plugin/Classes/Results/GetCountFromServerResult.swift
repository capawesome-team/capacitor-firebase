import Capacitor

@objc public class GetCountFromServerResult: NSObject, Result {
    let count: Int

    init(_ count: Int) {
        self.count = count
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["count"] = count
        return result as AnyObject
    }
}
