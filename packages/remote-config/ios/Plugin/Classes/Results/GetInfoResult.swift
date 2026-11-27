import Foundation
import Capacitor

@objc public class GetInfoResult: NSObject, Result {
    let lastFetchStatus: Int
    let lastFetchTime: Double

    init(lastFetchTime: Double, lastFetchStatus: Int) {
        self.lastFetchTime = lastFetchTime
        self.lastFetchStatus = lastFetchStatus
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["lastFetchTime"] = lastFetchTime
        result["lastFetchStatus"] = lastFetchStatus
        return result as AnyObject
    }
}
