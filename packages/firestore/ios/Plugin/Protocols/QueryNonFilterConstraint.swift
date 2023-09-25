import Foundation
import FirebaseFirestore

@objc public protocol QueryNonFilterConstraint {
    @objc func toQuery(_ query: Query, completion: @escaping (Query, Error?) -> Void)
}
