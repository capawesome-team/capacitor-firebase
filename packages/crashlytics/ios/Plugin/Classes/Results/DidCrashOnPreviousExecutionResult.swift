import Foundation
import Capacitor

@objc public class DidCrashOnPreviousExecutionResult: NSObject, Result {
    let crashed: Bool

    init(crashed: Bool) {
        self.crashed = crashed
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["crashed"] = crashed
        return result as AnyObject
    }
}
