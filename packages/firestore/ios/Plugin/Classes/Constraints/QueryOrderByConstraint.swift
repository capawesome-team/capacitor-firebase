import Foundation
import FirebaseFirestore
import Capacitor

@objc public class QueryOrderByConstraint: NSObject, QueryNonFilterConstraint {
    private var directionStr: String
    private var fieldPath: String?

    public init(_ queryConstraint: JSObject) {
        self.fieldPath = queryConstraint["fieldPath"] as? String
        self.directionStr = queryConstraint["directionStr"] as? String ?? ""
    }

    public func toQuery(query: Query, firestore: Firestore) async throws -> Query {
        guard let fieldPath = self.fieldPath else {
            throw CustomError.fieldPathMissing
        }
        guard FirebaseFirestoreHelper.isValidFieldPath(fieldPath) else {
            throw CustomError.fieldPathInvalid
        }
        return query.order(by: fieldPath, descending: self.directionStr == "desc" ? true : false)
    }
}
