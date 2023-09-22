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

    public func toQuery(query: Query) async throws -> Query {
        switch self.type {
        case "limit":
            return query.limit(to: self.limit)
        default:
            return query.limit(toLast: self.limit)
        }
    }
}
