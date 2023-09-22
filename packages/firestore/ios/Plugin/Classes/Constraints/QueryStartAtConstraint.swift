import Foundation
import FirebaseFirestore
import Capacitor

@objc public class QueryStartAtConstraint: NSObject, QueryNonFilterConstraint {
    private var type: String
    private var reference: String
    
    
    public init(_ queryConstraint: JSObject) {
        self.type = queryConstraint["type"] as! String
        self.reference = queryConstraint["reference"] as! String
    }
    
    public func toQuery(query: Query) async throws -> Query {
        let documentSnapshot = try await Firestore.firestore().document(reference).getDocument()
        switch self.type {
        case "startAt":
            return query.start(atDocument: documentSnapshot)
        default:
            return query.start(afterDocument: documentSnapshot)
        }
    }
}
