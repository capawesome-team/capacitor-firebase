import Foundation
import FirebaseFirestore
import Capacitor

public class FirebaseFirestoreHelper {
    public static func createHashMapFromJSObject(_ object: JSObject) -> [String: Any] {
        var map: [String: Any] = [:]
        for key in object.keys {
            if let value = object[key] {
                map[key] = createNativeValue(value)
            }
        }
        return map
    }

    public static func createJSObjectFromHashMap(_ map: [String: Any]?) -> JSObject? {
        guard let map = map else {
            return nil
        }
        var object: JSObject = [:]
        for key in map.keys {
            object[key] = self.createJSValue(value: map[key])
        }
        return object
    }

    public static func createQueryCompositeFilterConstraintFromJSObject(_ compositeFilter: JSObject?) -> QueryCompositeFilterConstraint? {
        if let compositeFilter = compositeFilter {
            return QueryCompositeFilterConstraint(compositeFilter)
        } else {
            return nil
        }
    }

    public static func createQueryNonFilterConstraintArrayFromJSArray(_ queryConstraints: [JSObject]?) -> [QueryNonFilterConstraint] {
        if let queryConstraints = queryConstraints {
            var queryNonFilterConstraint: [QueryNonFilterConstraint] = []
            for queryConstraint in queryConstraints {
                let queryConstraintType = queryConstraint["type"] as? String
                switch queryConstraintType {
                case "orderBy":
                    let queryOrderByConstraint = QueryOrderByConstraint(queryConstraint)
                    queryNonFilterConstraint.append(queryOrderByConstraint)
                case "limit", "limitToLast":
                    let queryLimitConstraint = QueryLimitConstraint(queryConstraint)
                    queryNonFilterConstraint.append(queryLimitConstraint)
                case "startAt", "startAfter":
                    let queryStartAtConstraint = QueryStartAtConstraint(queryConstraint)
                    queryNonFilterConstraint.append(queryStartAtConstraint)
                case "endAt", "endBefore":
                    let queryEndAtConstraint = QueryEndAtConstraint(queryConstraint)
                    queryNonFilterConstraint.append(queryEndAtConstraint)
                default:
                    break
                }
            }
            return queryNonFilterConstraint
        } else {
            return []
        }
    }

    public static func createNativeValue(_ value: Any) -> Any {
        if let dict = value as? [String: Any], let type = dict["__type__"] as? String {
            if let markerValue = createNativeValueFromMarker(type: type, dict: dict) {
                return markerValue
            }
        }
        if let dict = value as? [String: Any] {
            var result: [String: Any] = [:]
            for (key, val) in dict {
                result[key] = createNativeValue(val)
            }
            return result
        }
        if let array = value as? [Any] {
            return array.map { createNativeValue($0) }
        }
        return value
    }

    private static func createNativeValueFromMarker(type: String, dict: [String: Any]) -> Any? {
        switch type {
        case "timestamp":
            guard let seconds = (dict["seconds"] as? NSNumber)?.int64Value,
                  let nanoseconds = (dict["nanoseconds"] as? NSNumber)?.int32Value else { return nil }
            return Timestamp(seconds: seconds, nanoseconds: nanoseconds)
        case "geopoint":
            guard let latitude = (dict["latitude"] as? NSNumber)?.doubleValue,
                  let longitude = (dict["longitude"] as? NSNumber)?.doubleValue else { return nil }
            return GeoPoint(latitude: latitude, longitude: longitude)
        case "serverTimestamp":
            return FieldValue.serverTimestamp()
        case "arrayUnion":
            let elements = (dict["elements"] as? [Any] ?? []).map { createNativeValue($0) }
            return FieldValue.arrayUnion(elements)
        case "arrayRemove":
            let elements = (dict["elements"] as? [Any] ?? []).map { createNativeValue($0) }
            return FieldValue.arrayRemove(elements)
        case "delete":
            return FieldValue.delete()
        case "increment":
            guard let operand = dict["operand"] as? NSNumber else {
                return FieldValue.increment(Int64(0))
            }
            if floor(operand.doubleValue) == operand.doubleValue {
                return FieldValue.increment(operand.int64Value)
            }
            return FieldValue.increment(operand.doubleValue)
        default:
            return nil
        }
    }

    private static func createJSValue(value: Any?) -> JSValue? {
        guard let value = value else {
            return nil
        }
        if let timestamp = value as? Timestamp {
            return createJSObjectFromTimestamp(timestamp) as JSValue
        }
        if let geoPoint = value as? GeoPoint {
            return createJSObjectFromGeoPoint(geoPoint) as JSValue
        }
        if let array = value as? [Any] {
            return createJSArrayFromArray(array) as JSValue
        }
        if let dict = value as? [String: Any] {
            if let jsObject = createJSObjectFromHashMap(dict) {
                return jsObject as JSValue
            }
            return nil
        }
        guard let jsValue = JSTypes.coerceDictionaryToJSObject(["key": value]) as JSObject? else {
            return nil
        }
        return jsValue["key"]
    }

    private static func createJSObjectFromTimestamp(_ timestamp: Timestamp) -> JSObject {
        var object: JSObject = [:]
        object["__type__"] = "timestamp"
        object["seconds"] = Int(timestamp.seconds)
        object["nanoseconds"] = Int(timestamp.nanoseconds)
        return object
    }

    private static func createJSObjectFromGeoPoint(_ geoPoint: GeoPoint) -> JSObject {
        var object: JSObject = [:]
        object["__type__"] = "geopoint"
        object["latitude"] = geoPoint.latitude
        object["longitude"] = geoPoint.longitude
        return object
    }

    private static func createJSArrayFromArray(_ array: [Any]) -> [Any] {
        return array.map { item -> Any in
            if let timestamp = item as? Timestamp {
                return createJSObjectFromTimestamp(timestamp)
            }
            if let geoPoint = item as? GeoPoint {
                return createJSObjectFromGeoPoint(geoPoint)
            }
            if let dict = item as? [String: Any] {
                return createJSObjectFromHashMap(dict) as Any
            }
            if let arr = item as? [Any] {
                return createJSArrayFromArray(arr)
            }
            return item
        }
    }

    public static func createErrorCode(error: Error?) -> String? {
        if let error = error as NSError? {
            if let errorCode = convertErrorCodeToString(errorCode: error.code) {
                let prefixedErrorCode = "firestore/" + errorCode
                return prefixedErrorCode
            } else {
                return nil
            }
        }
        return nil
    }

    private static func convertErrorCodeToString(errorCode: Int) -> String? {
        let errorCodes: [Int: String] = [
            FirestoreErrorCode.aborted.rawValue: "aborted",
            FirestoreErrorCode.alreadyExists.rawValue: "already-exists",
            FirestoreErrorCode.cancelled.rawValue: "cancelled",
            FirestoreErrorCode.dataLoss.rawValue: "data-loss",
            FirestoreErrorCode.deadlineExceeded.rawValue: "deadline-exceeded",
            FirestoreErrorCode.failedPrecondition.rawValue: "failed-precondition",
            FirestoreErrorCode.internal.rawValue: "internal",
            FirestoreErrorCode.invalidArgument.rawValue: "invalid-argument",
            FirestoreErrorCode.notFound.rawValue: "not-found",
            FirestoreErrorCode.outOfRange.rawValue: "out-of-range",
            FirestoreErrorCode.permissionDenied.rawValue: "permission-denied",
            FirestoreErrorCode.resourceExhausted.rawValue: "resource-exhausted",
            FirestoreErrorCode.unauthenticated.rawValue: "unauthenticated",
            FirestoreErrorCode.unavailable.rawValue: "unavailable",
            FirestoreErrorCode.unimplemented.rawValue: "unimplemented",
            FirestoreErrorCode.unknown.rawValue: "unknown"
        ]

        return errorCodes[errorCode]
    }
}
