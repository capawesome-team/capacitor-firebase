import Foundation
import Capacitor
import FirebaseFirestore

@objc public class QueryFieldFilterConstraint: NSObject, QueryFilterConstraint {
    private var fieldPath: String?
    private var firestore: Firestore
    private var opStr: String
    private var value: Any

    public init(_ queryConstraint: JSObject, firestore: Firestore) {
        self.fieldPath = queryConstraint["fieldPath"] as? String
        self.opStr = queryConstraint["opStr"] as? String ?? ""
        self.value = queryConstraint["value"] as Any
        self.firestore = firestore
    }

    public func toFilter() throws -> Filter? {
        switch self.opStr {
        case "<":
            return try Filter.whereField(getFieldPath(), isLessThan: getValue())
        case "<=":
            return try Filter.whereField(getFieldPath(), isLessThanOrEqualTo: getValue())
        case "==":
            return try Filter.whereField(getFieldPath(), isEqualTo: getValue())
        case ">=":
            return try Filter.whereField(getFieldPath(), isGreaterOrEqualTo: getValue())
        case ">":
            return try Filter.whereField(getFieldPath(), isGreaterThan: getValue())
        case "!=":
            return try Filter.whereField(getFieldPath(), isNotEqualTo: getValue())
        case "array-contains":
            return try Filter.whereField(getFieldPath(), arrayContains: getValue())
        case "array-contains-any":
            return try Filter.whereField(getFieldPath(), arrayContainsAny: getValueAsNonEmptyArray())
        case "in":
            return try Filter.whereField(getFieldPath(), in: getValueAsNonEmptyArray())
        case "not-in":
            return try Filter.whereField(getFieldPath(), notIn: getValueAsNonEmptyArray())
        default:
            return nil
        }
    }

    private func getFieldPath() throws -> String {
        guard let fieldPath = self.fieldPath else {
            throw CustomError.fieldPathMissing
        }
        guard FirebaseFirestoreHelper.isValidFieldPath(fieldPath) else {
            throw CustomError.fieldPathInvalid
        }
        return fieldPath
    }

    private func getValue() throws -> Any {
        return try FirebaseFirestoreHelper.createNativeValue(self.value, firestore: self.firestore)
    }

    private func getValueAsNonEmptyArray() throws -> [Any] {
        // The SDK takes only a non-empty array for these operators.
        guard let values = try getValue() as? [Any], !values.isEmpty else {
            throw CustomError.valueInvalid
        }
        return values
    }
}
