import Foundation
import Capacitor

@objc public class AddConfigUpdateListenerOptions: NSObject {
    let callbackId: String

    init(_ call: CAPPluginCall) throws {
        self.callbackId = try AddConfigUpdateListenerOptions.getCallbackIdFromCall(call)
    }

    private static func getCallbackIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let callbackId = call.callbackId else {
            throw CustomError.callbackIdMissing
        }
        return callbackId
    }
}
