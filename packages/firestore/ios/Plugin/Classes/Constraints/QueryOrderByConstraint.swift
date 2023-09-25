import Foundation
import FirebaseFirestore
import Capacitor

@objc public class QueryOrderByConstraint: NSObject, QueryNonFilterConstraint {
    private var fieldPath: String
    private var directionStr: String

    public init(_ queryConstraint: JSObject) {
        self.fieldPath = queryConstraint["fieldPath"] as! String
        self.directionStr = queryConstraint["directionStr"] as! String
    }

    public func toQuery(_ query: Query, completion: @escaping (Query, Error?) -> Void) {
        let newQuery = query.order(by: self.fieldPath, descending: self.directionStr == "desc" ? true : false)
        completion(newQuery, nil)
    }
}
