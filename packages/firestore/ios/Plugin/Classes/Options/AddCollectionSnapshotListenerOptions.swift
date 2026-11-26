import Foundation
import Capacitor

@objc public class AddCollectionSnapshotListenerOptions: NSObject {
    let appName: String?
    let callbackId: String
    let compositeFilter: JSObject?
    let includeMetadataChanges: Bool
    let queryConstraints: [JSObject]?
    let reference: String
    let serverTimestampBehavior: String?

    init(_ call: CAPPluginCall) throws {
        self.reference = try AddCollectionSnapshotListenerOptions.getReferenceFromCall(call)
        self.compositeFilter = AddCollectionSnapshotListenerOptions.getCompositeFilterFromCall(call)
        self.queryConstraints = AddCollectionSnapshotListenerOptions.getQueryConstraintsFromCall(call)
        self.includeMetadataChanges = AddCollectionSnapshotListenerOptions.getIncludeMetadataChangesFromCall(call)
        self.serverTimestampBehavior = AddCollectionSnapshotListenerOptions.getServerTimestampBehaviorFromCall(call)
        self.callbackId = try AddCollectionSnapshotListenerOptions.getCallbackIdFromCall(call)
        self.appName = AddCollectionSnapshotListenerOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
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
        guard FirebaseFirestoreHelper.isValidCollectionPath(reference) else {
            throw CustomError.referenceInvalid
        }
        return reference
    }

    private static func getServerTimestampBehaviorFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("serverTimestamps")
    }
}
