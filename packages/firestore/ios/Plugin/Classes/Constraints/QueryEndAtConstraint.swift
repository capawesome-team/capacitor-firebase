import Foundation
import FirebaseFirestore
import Capacitor

@objc public class QueryEndAtConstraint: NSObject, QueryNonFilterConstraint {
    private var reference: String?
    private var type: String

    public init(_ queryConstraint: JSObject) {
        self.type = queryConstraint["type"] as? String ?? ""
        self.reference = queryConstraint["reference"] as? String
    }

    public func toQuery(query: Query, firestore: Firestore) async throws -> Query {
        guard let reference = reference else {
            throw CustomError.referenceMissing
        }
        guard FirebaseFirestoreHelper.isValidDocumentPath(reference) else {
            throw CustomError.referenceInvalid
        }
        let documentSnapshot = try await firestore.document(reference).getDocument()
        guard documentSnapshot.exists else {
            throw CustomError.cursorDocumentNotFound
        }
        switch self.type {
        case "endAt":
            return query.end(atDocument: documentSnapshot)
        default:
            return query.end(beforeDocument: documentSnapshot)
        }
    }
}
