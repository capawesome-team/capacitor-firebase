import Foundation
import FirebaseFirestore

@objc public protocol QueryFilterConstraint {
    @objc func toFilter() -> Filter?
}
