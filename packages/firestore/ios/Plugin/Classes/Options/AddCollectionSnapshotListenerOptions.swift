import Foundation
import Capacitor
import FirebaseFirestore

@objc public class AddCollectionSnapshotListenerOptions: NSObject {
    private var reference: String
    private var compositeFilter: QueryCompositeFilterConstraint?
    private var queryConstraints: [QueryNonFilterConstraint]
    private var includeMetadataChanges: Bool
    private var serverTimestampBehavior: String?
    private var callbackId: String

    init(
        reference: String,
        compositeFilter: JSObject?,
        queryConstraints: [JSObject]?,
        includeMetadataChanges: Bool,
        serverTimestampBehavior: String?,
        callbackId: String,
        firestore: Firestore
    ) {
        self.reference = reference
        self.compositeFilter = FirebaseFirestoreHelper.createQueryCompositeFilterConstraintFromJSObject(compositeFilter, firestore: firestore)
        self.queryConstraints = FirebaseFirestoreHelper.createQueryNonFilterConstraintArrayFromJSArray(queryConstraints)
        self.includeMetadataChanges = includeMetadataChanges
        self.serverTimestampBehavior = serverTimestampBehavior
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

    func getServerTimestampBehavior() -> String? {
        return serverTimestampBehavior
    }

    func getCallbackId() -> String {
        return callbackId
    }
}
