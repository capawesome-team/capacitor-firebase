import Foundation
import FirebaseFirestore
import Capacitor

@objc public class QueryLimitConstraint: NSObject, QueryNonFilterConstraint {
    private var type: String
    private var limit: Int

    public init(_ queryConstraint: JSObject) {
        self.type = queryConstraint["type"] as! String
        self.limit = queryConstraint["limit"] as! Int
    }

    public func toQuery(_ query: Query, completion: @escaping (Query, Error?) -> Void) {
        var newQuery: Query
        switch self.type {
        case "limit":
            newQuery = query.limit(to: self.limit)
        default:
            newQuery = query.limit(toLast: self.limit)
        }
        completion(newQuery, nil)
    }
}
