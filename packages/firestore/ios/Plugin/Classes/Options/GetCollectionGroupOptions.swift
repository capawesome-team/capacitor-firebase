import Foundation
import Capacitor

@objc public class GetCollectionGroupOptions: NSObject {
    private var reference: String
    private var compositeFilter: QueryCompositeFilterConstraint?
    private var queryConstraints: [QueryNonFilterConstraint]
    
    init(reference: String, compositeFilter: JSObject?, queryConstraints: Array<JSObject>?) {
        self.reference = reference
        if let compositeFilter = compositeFilter {
            self.compositeFilter = QueryCompositeFilterConstraint(compositeFilter)
        } else {
            self.compositeFilter = nil
        }
        if let queryConstraints = queryConstraints {
            self.queryConstraints = []
            for queryConstraint in queryConstraints {
                let queryConstraintType = queryConstraint["type"] as! String
                switch queryConstraintType {
                case "orderBy":
                    let queryOrderByConstraint = QueryOrderByConstraint(queryConstraint)
                    self.queryConstraints.append(queryOrderByConstraint)
                    break
                case "limit":
                    let queryLimitConstraint = QueryLimitConstraint(queryConstraint)
                    self.queryConstraints.append(queryLimitConstraint)
                    break
                case "startAt", "startAfter":
                    let queryStartAtConstraint = QueryStartAtConstraint(queryConstraint)
                    self.queryConstraints.append(queryStartAtConstraint)
                    break
                case "endAt", "endBefore":
                    let queryEndAtConstraint = QueryEndAtConstraint(queryConstraint)
                    self.queryConstraints.append(queryEndAtConstraint)
                    break
                default:
                    break;
                }
            }
        } else {
            self.queryConstraints = []
        }
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
