import Foundation
import FirebaseFirestore
import Capacitor

@objc public class QueryCompositeFilterConstraint: NSObject, QueryFilterConstraint {
    private var type: QueryCompositeFilterConstraintType
        
    private var queryConstraints: [QueryFilterConstraint]
    
    public init(_ compositeFilter: JSObject) {
        self.type = QueryCompositeFilterConstraintType(rawValue: compositeFilter["type"] as! String)!
        let queryConstraints = compositeFilter["queryConstraints"] as! Array<JSObject>
        self.queryConstraints = []
        for queryConstraint in queryConstraints {
            let queryConstraintType = queryConstraint["type"] as! String
            if queryConstraintType == "where" {
                let queryFieldFilterConstraint = QueryFieldFilterConstraint(queryConstraint)
                self.queryConstraints.append(queryFieldFilterConstraint)
            } else {
                let queryCompositeFilterConstraint = QueryCompositeFilterConstraint(queryConstraint)
                self.queryConstraints.append(queryCompositeFilterConstraint)
            }
        }
    }
    
    public func getType() -> QueryCompositeFilterConstraintType {
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
        case .And:
            return Filter.andFilter(filters)
        case .Or:
            return Filter.orFilter(filters)
        }
    }
}
