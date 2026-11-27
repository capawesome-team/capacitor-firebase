import Foundation
import Capacitor

@objc public class RemoveConfigUpdateListenerOptions: NSObject {
    let callbackId: String

    init(_ call: CAPPluginCall) throws {
        self.callbackId = try RemoveConfigUpdateListenerOptions.getCallbackIdFromCall(call)
    }

    private static func getCallbackIdFromCall(_ call: CAPPluginCall) throws -> String {
        // The option is named `id`. The README called it `callbackId` before, so apps may still send that name.
        guard let callbackId = call.getString("id") ?? call.getString("callbackId") else {
            throw CustomError.idMissing
        }
        return callbackId
    }
}
