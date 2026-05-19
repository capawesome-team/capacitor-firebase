import Foundation
import Capacitor

@objc public class AddCollectionSnapshotListenerOptions: NSObject {
    private var reference: String
    private var compositeFilter: QueryCompositeFilterConstraint?
    private var queryConstraints: [QueryNonFilterConstraint]
    private var includeMetadataChanges: Bool
    private var callbackId: String
    private var serverTimestampBehavior: String?

    init(reference: String, compositeFilter: JSObject?, queryConstraints: [JSObject]?, includeMetadataChanges: Bool, callbackId: String, serverTimestampBehavior: String? = nil) {
        self.reference = reference
        self.compositeFilter = FirebaseFirestoreHelper.createQueryCompositeFilterConstraintFromJSObject(compositeFilter)
        self.queryConstraints = FirebaseFirestoreHelper.createQueryNonFilterConstraintArrayFromJSArray(queryConstraints)
        self.includeMetadataChanges = includeMetadataChanges
        self.callbackId = callbackId
        self.serverTimestampBehavior = serverTimestampBehavior
    }

    func getReference() -> String {
        return reference
    }

    func getCompositeFilter() -> QueryCompositeFilterConstraint? {
        return compositeFilter
    }

    func getQueryConstraints() -> [QueryNonFilterConstraint] {
        return queryConstraints
    }

    func getIncludeMetadataChanges() -> Bool {
        return includeMetadataChanges
    }

    func getCallbackId() -> String {
        return callbackId
    }

    func getServerTimestampBehavior() -> String? {
        return serverTimestampBehavior
    }
}
