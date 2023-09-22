import Foundation
import Capacitor
import FirebaseFirestore

@objc public class QueryFieldFilterConstraint: NSObject, QueryFilterConstraint {
    private var fieldPath: String
    private var opStr: String
    private var value: AnyObject

    public init(_ queryConstraint: JSObject) {
        self.fieldPath = queryConstraint["fieldPath"] as! String
        self.opStr = queryConstraint["opStr"] as! String
        self.value = queryConstraint["value"] as AnyObject
    }

    public func toFilter() -> Filter? {
        switch self.opStr {
        case "<":
            return Filter.whereField(self.fieldPath, isLessThan: self.value)
        case "<=":
            return Filter.whereField(self.fieldPath, isLessThanOrEqualTo: self.value)
        case "==":
            return Filter.whereField(self.fieldPath, isEqualTo: self.value)
        case ">=":
            return Filter.whereField(self.fieldPath, isGreaterOrEqualTo: self.value)
        case ">":
            return Filter.whereField(self.fieldPath, isGreaterThan: self.value)
        case "!=":
            return Filter.whereField(self.fieldPath, isNotEqualTo: self.value)
        case "array-contains":
            return Filter.whereField(self.fieldPath, arrayContains: self.value)
        /*case "array-contains-any":
         return Filter.whereField(self.fieldPath, arrayContainsAny: self.value as! [Any])
         case "in":
         return Filter.whereField(self.fieldPath, in: self.value as! [Any])
         case "not-in":
         return Filter.whereField(self.fieldPath, notIn: self.value as! [Any])*/
        default:
            return nil
        }
    }
}
