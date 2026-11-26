import Foundation
import Capacitor

@objc public class RemoveSnapshotListenerOptions: NSObject {
    let callbackId: String

    init(_ call: CAPPluginCall) throws {
        self.callbackId = try RemoveSnapshotListenerOptions.getCallbackIdFromCall(call)
    }

    private static func getCallbackIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let callbackId = call.getString("callbackId") else {
            throw CustomError.callbackIdMissing
        }
        return callbackId
    }
}
