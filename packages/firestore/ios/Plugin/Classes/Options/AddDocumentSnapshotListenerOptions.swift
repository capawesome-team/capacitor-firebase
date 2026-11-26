import Foundation
import Capacitor

@objc public class AddDocumentSnapshotListenerOptions: NSObject {
    let callbackId: String
    let includeMetadataChanges: Bool
    let reference: String
    let serverTimestampBehavior: String?

    init(_ call: CAPPluginCall) throws {
        self.reference = try AddDocumentSnapshotListenerOptions.getReferenceFromCall(call)
        self.includeMetadataChanges = AddDocumentSnapshotListenerOptions.getIncludeMetadataChangesFromCall(call)
        self.serverTimestampBehavior = AddDocumentSnapshotListenerOptions.getServerTimestampBehaviorFromCall(call)
        self.callbackId = try AddDocumentSnapshotListenerOptions.getCallbackIdFromCall(call)
    }

    private static func getCallbackIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let callbackId = call.callbackId else {
            throw CustomError.callbackIdMissing
        }
        return callbackId
    }

    private static func getIncludeMetadataChangesFromCall(_ call: CAPPluginCall) -> Bool {
        return call.getBool("includeMetadataChanges", false)
    }

    private static func getReferenceFromCall(_ call: CAPPluginCall) throws -> String {
        guard let reference = call.getString("reference") else {
            throw CustomError.referenceMissing
        }
        return reference
    }

    private static func getServerTimestampBehaviorFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("serverTimestamps")
    }
}
