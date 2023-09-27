import Foundation
import FirebaseFirestore

@objc public protocol QueryNonFilterConstraint {
    @objc func toQuery(query: Query) async throws -> Query
}
