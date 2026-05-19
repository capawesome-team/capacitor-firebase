import Foundation
import Capacitor

@objc public class GetCollectionOptions: NSObject {
    private var reference: String
    private var compositeFilter: QueryCompositeFilterConstraint?
    private var queryConstraints: [QueryNonFilterConstraint]
    private var serverTimestampBehavior: String?

    init(reference: String, compositeFilter: JSObject?, queryConstraints: [JSObject]?, serverTimestampBehavior: String? = nil) {
        self.reference = reference
        self.compositeFilter = FirebaseFirestoreHelper.createQueryCompositeFilterConstraintFromJSObject(compositeFilter)
        self.queryConstraints = FirebaseFirestoreHelper.createQueryNonFilterConstraintArrayFromJSArray(queryConstraints)
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

    func getServerTimestampBehavior() -> String? {
        return serverTimestampBehavior
    }
}
