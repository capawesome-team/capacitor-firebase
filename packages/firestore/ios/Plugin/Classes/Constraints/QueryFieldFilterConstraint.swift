import Foundation
import Capacitor
import FirebaseFirestore

@objc public class QueryFieldFilterConstraint: NSObject, QueryFilterConstraint {
    private var fieldPath: String?
    private var opStr: String
    private var value: Any

    public init(_ queryConstraint: JSObject, firestore: Firestore) {
        self.fieldPath = queryConstraint["fieldPath"] as? String
        self.opStr = queryConstraint["opStr"] as? String ?? ""
        let rawValue = queryConstraint["value"] as Any
        self.value = FirebaseFirestoreHelper.createNativeValue(rawValue, firestore: firestore)
    }

    public func toFilter() throws -> Filter? {
        switch self.opStr {
        case "<":
            return try Filter.whereField(getFieldPath(), isLessThan: self.value)
        case "<=":
            return try Filter.whereField(getFieldPath(), isLessThanOrEqualTo: self.value)
        case "==":
            return try Filter.whereField(getFieldPath(), isEqualTo: self.value)
        case ">=":
            return try Filter.whereField(getFieldPath(), isGreaterOrEqualTo: self.value)
        case ">":
            return try Filter.whereField(getFieldPath(), isGreaterThan: self.value)
        case "!=":
            return try Filter.whereField(getFieldPath(), isNotEqualTo: self.value)
        case "array-contains":
            return try Filter.whereField(getFieldPath(), arrayContains: self.value)
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

    private func getValueAsNonEmptyArray() throws -> [Any] {
        // The SDK takes only a non-empty array for these operators.
        guard let values = self.value as? [Any], !values.isEmpty else {
            throw CustomError.valueInvalid
        }
        return values
    }
}
