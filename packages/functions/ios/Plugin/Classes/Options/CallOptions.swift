import Foundation
import Capacitor

@objc public class CallOptions: NSObject {
    let data: Any?
    let timeout: Int?

    init(_ call: CAPPluginCall) throws {
        self.data = CallOptions.getDataFromCall(call)
        self.timeout = CallOptions.getTimeoutFromCall(call)
    }

    private static func getDataFromCall(_ call: CAPPluginCall) -> Any? {
        return call.getValue("data")
    }

    private static func getTimeoutFromCall(_ call: CAPPluginCall) -> Int? {
        return call.getInt("timeout")
    }
}
