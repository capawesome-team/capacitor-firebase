import Foundation
import FirebaseFirestore
import Capacitor

@objc public class QueryEndAtConstraint: NSObject, QueryNonFilterConstraint {
    private var orderByFieldPaths: [String]
    private var reference: String?
    private var type: String

    public init(_ queryConstraint: JSObject, orderByFieldPaths: [String]) {
        self.type = queryConstraint["type"] as? String ?? ""
        self.reference = queryConstraint["reference"] as? String
        self.orderByFieldPaths = orderByFieldPaths
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
        // The SDK orders by the document id for __name__, which is never a field of the document.
        let hasMissingOrderByField = orderByFieldPaths.contains { $0 != "__name__" && documentSnapshot.get($0) == nil }
        guard !hasMissingOrderByField else {
            throw CustomError.cursorDocumentFieldMissing
        }
        switch self.type {
        case "endAt":
            return query.end(atDocument: documentSnapshot)
        default:
            return query.end(beforeDocument: documentSnapshot)
        }
    }
}
