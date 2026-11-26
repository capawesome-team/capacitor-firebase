import Foundation
import FirebaseFirestore
import Capacitor

@objc public class QueryCompositeFilterConstraint: NSObject, QueryFilterConstraint {
    private var queryConstraints: [QueryFilterConstraint]
    private var type: String

    public init(_ compositeFilter: JSObject, firestore: Firestore) {
        self.type = compositeFilter["type"] as? String ?? ""
        let queryConstraints = compositeFilter["queryConstraints"] as? [JSObject]
        self.queryConstraints = []
        if let queryConstraints = queryConstraints {
            for queryConstraint in queryConstraints {
                let queryConstraintType = queryConstraint["type"] as? String ?? ""
                if queryConstraintType == "where" {
                    let queryFieldFilterConstraint = QueryFieldFilterConstraint(queryConstraint, firestore: firestore)
                    self.queryConstraints.append(queryFieldFilterConstraint)
                } else {
                    let queryCompositeFilterConstraint = QueryCompositeFilterConstraint(queryConstraint, firestore: firestore)
                    self.queryConstraints.append(queryCompositeFilterConstraint)
                }
            }
        }
    }

    public func getConstraints() -> [QueryFilterConstraint] {
        return queryConstraints
    }

    public func getType() -> String {
        return type
    }

    public func toFilter() throws -> Filter? {
        var filters: [Filter] = []
        for constraint in queryConstraints {
            if let filter = try constraint.toFilter() {
                filters.append(filter)
            }
        }
        switch type {
        case "and":
            return Filter.andFilter(filters)
        case "or":
            return Filter.orFilter(filters)
        default:
            return nil
        }
    }
}
