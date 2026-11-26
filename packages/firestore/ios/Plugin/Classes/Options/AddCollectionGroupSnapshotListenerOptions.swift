import Foundation
import Capacitor

@objc public class AddCollectionGroupSnapshotListenerOptions: NSObject {
    let callbackId: String
    let compositeFilter: JSObject?
    let includeMetadataChanges: Bool
    let queryConstraints: [JSObject]?
    let reference: String
    let serverTimestampBehavior: String?

    init(_ call: CAPPluginCall) throws {
        self.reference = try AddCollectionGroupSnapshotListenerOptions.getReferenceFromCall(call)
        self.compositeFilter = AddCollectionGroupSnapshotListenerOptions.getCompositeFilterFromCall(call)
        self.queryConstraints = AddCollectionGroupSnapshotListenerOptions.getQueryConstraintsFromCall(call)
        self.includeMetadataChanges = AddCollectionGroupSnapshotListenerOptions.getIncludeMetadataChangesFromCall(call)
        self.serverTimestampBehavior = AddCollectionGroupSnapshotListenerOptions.getServerTimestampBehaviorFromCall(call)
        self.callbackId = try AddCollectionGroupSnapshotListenerOptions.getCallbackIdFromCall(call)
    }

    private static func getCallbackIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let callbackId = call.callbackId else {
            throw CustomError.callbackIdMissing
        }
        return callbackId
    }

    private static func getCompositeFilterFromCall(_ call: CAPPluginCall) -> JSObject? {
        return call.getObject("compositeFilter")
    }

    private static func getIncludeMetadataChangesFromCall(_ call: CAPPluginCall) -> Bool {
        return call.getBool("includeMetadataChanges", false)
    }

    private static func getQueryConstraintsFromCall(_ call: CAPPluginCall) -> [JSObject]? {
        return call.getArray("queryConstraints", JSObject.self)
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
