import Foundation
import FirebaseFirestore

@objc public protocol QueryNonFilterConstraint {
    @objc func toQuery(query: Query, firestore: Firestore) async throws -> Query
}
