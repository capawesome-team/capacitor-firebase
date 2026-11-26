import Foundation
import FirebaseFirestore
import Capacitor

@objc public class QueryLimitConstraint: NSObject, QueryNonFilterConstraint {
    private var limit: Int?
    private var type: String

    public init(_ queryConstraint: JSObject) {
        self.type = queryConstraint["type"] as? String ?? ""
        self.limit = queryConstraint["limit"] as? Int
    }

    public func toQuery(query: Query, firestore: Firestore) async throws -> Query {
        guard let limit = self.limit else {
            throw CustomError.limitMissing
        }
        guard limit > 0 else {
            throw CustomError.limitInvalid
        }
        switch self.type {
        case "limit":
            return query.limit(to: limit)
        default:
            return query.limit(toLast: limit)
        }
    }
}
