import Foundation
import Capacitor

@objc public class AddCollectionGroupSnapshotListenerOptions: NSObject {
    private var reference: String
    private var compositeFilter: QueryCompositeFilterConstraint?
    private var queryConstraints: [QueryNonFilterConstraint]
    private var includeMetadataChanges: Bool
    private var callbackId: String

    init(reference: String, compositeFilter: JSObject?, queryConstraints: [JSObject]?, includeMetadataChanges: Bool, callbackId: String) {
        self.reference = reference
        self.compositeFilter = FirebaseFirestoreHelper.createQueryCompositeFilterConstraintFromJSObject(compositeFilter)
        self.queryConstraints = FirebaseFirestoreHelper.createQueryNonFilterConstraintArrayFromJSArray(queryConstraints)
        self.includeMetadataChanges = includeMetadataChanges
        self.callbackId = callbackId
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
}
