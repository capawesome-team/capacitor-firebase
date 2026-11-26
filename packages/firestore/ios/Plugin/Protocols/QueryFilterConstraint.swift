import Foundation
import FirebaseFirestore

public protocol QueryFilterConstraint {
    func toFilter() throws -> Filter?
}
