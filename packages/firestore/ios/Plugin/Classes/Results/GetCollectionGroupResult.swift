import Foundation
import Capacitor

@objc public class GetCollectionGroupResult: NSObject, Result {
    let id: String

    init(_ id: String) {
        self.id = id
    }
    func toJSObject() -> AnyObject {
        var result = JSObject()
        result["id"] = self.id
        return result as AnyObject
    }
}
