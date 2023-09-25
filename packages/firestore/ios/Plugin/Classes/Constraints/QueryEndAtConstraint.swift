import Foundation
import FirebaseFirestore
import Capacitor

@objc public class QueryEndAtConstraint: NSObject, QueryNonFilterConstraint {
    private var type: String
    private var reference: String

    public init(_ queryConstraint: JSObject) {
        self.type = queryConstraint["type"] as! String
        self.reference = queryConstraint["reference"] as! String
    }

    public func toQuery(_ query: Query, completion: @escaping (Query, Error?) -> Void) {
        Firestore.firestore().document(reference).getDocument() { documentSnapshot, error in
            if let error = error {
                completion(query, error)
            } else {
                var newQuery: Query
                switch self.type {
                case "endAt":
                    newQuery = query.end(atDocument: documentSnapshot!)
                default:
                    newQuery = query.end(beforeDocument: documentSnapshot!)
                }
                completion(newQuery, nil)
            }
        }
    }
}
