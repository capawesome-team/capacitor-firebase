import Foundation
import FirebaseFirestore
import Capacitor

@objc public class QueryLimitConstraint: NSObject, QueryNonFilterConstraint {
    private var limit: Int
    private var type: String

    public init(_ queryConstraint: JSObject) {
        self.type = queryConstraint["type"] as? String ?? ""
        self.limit = queryConstraint["limit"] as? Int ?? 0
    }

    public func toQuery(query: Query, firestore: Firestore) async throws -> Query {
        switch self.type {
        case "limit":
            return query.limit(to: self.limit)
        default:
            return query.limit(toLast: self.limit)
        }
    }
}
