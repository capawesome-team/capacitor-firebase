import Foundation
import Capacitor

@objc public class GetCountFromServerOptions: NSObject {
    private let reference: String
    private let compositeFilter: QueryCompositeFilterConstraint?
    private let queryConstraints: [QueryNonFilterConstraint]

    init(reference: String, compositeFilter: JSObject?, queryConstraints: [JSObject]?) {
        self.reference = reference
        self.compositeFilter = FirebaseFirestoreHelper.createQueryCompositeFilterConstraintFromJSObject(compositeFilter)
        self.queryConstraints = FirebaseFirestoreHelper.createQueryNonFilterConstraintArrayFromJSArray(queryConstraints)
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
}
