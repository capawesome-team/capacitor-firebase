import Foundation
import Capacitor

@objc public class GetAppInstanceIdResult: NSObject, Result {
    let appInstanceId: String?

    init(appInstanceId: String?) {
        self.appInstanceId = appInstanceId
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        if let appInstanceId = appInstanceId {
            result["appInstanceId"] = appInstanceId
        }
        return result as AnyObject
    }
}
