import Foundation
import FirebaseFirestore
import Capacitor

@objc public class QueryCompositeFilterConstraint: NSObject, QueryFilterConstraint {
    private var type: String
    private var queryConstraints: [QueryFilterConstraint]

    public init(_ compositeFilter: JSObject) {
        self.type = compositeFilter["type"] as? String ?? ""
        let queryConstraints = compositeFilter["queryConstraints"] as? [JSObject]
        self.queryConstraints = []
        if let queryConstraints = queryConstraints {
            for queryConstraint in queryConstraints {
                let queryConstraintType = queryConstraint["type"] as? String ?? ""
                if queryConstraintType == "where" {
                    let queryFieldFilterConstraint = QueryFieldFilterConstraint(queryConstraint)
                    self.queryConstraints.append(queryFieldFilterConstraint)
                } else {
                    let queryCompositeFilterConstraint = QueryCompositeFilterConstraint(queryConstraint)
                    self.queryConstraints.append(queryCompositeFilterConstraint)
                }
            }
        }
    }

    public func getType() -> String {
        return type
    }

    public func getConstraints() -> [QueryFilterConstraint] {
        return queryConstraints
    }

    public func toFilter() -> Filter? {
        var filters: [Filter] = []
        for constraint in queryConstraints {
            if let filter = constraint.toFilter() {
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
