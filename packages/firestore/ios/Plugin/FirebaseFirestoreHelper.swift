import Foundation
import FirebaseFirestore
import Capacitor

public class FirebaseFirestoreHelper {
    public static func createHashMapFromJSObject(_ object: JSObject, firestore: Firestore) -> [String: Any] {
        var map: [String: Any] = [:]
        for key in object.keys {
            if let value = object[key] {
                map[key] = createNativeValue(value, firestore: firestore)
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

    public static func createQueryCompositeFilterConstraintFromJSObject(
        _ compositeFilter: JSObject?,
        firestore: Firestore
    ) -> QueryCompositeFilterConstraint? {
        if let compositeFilter = compositeFilter {
            return QueryCompositeFilterConstraint(compositeFilter, firestore: firestore)
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

    public static func createNativeValue(_ value: Any, firestore: Firestore) -> Any {
        if let dict = value as? [String: Any], let type = dict["__type__"] as? String {
            if let markerValue = createNativeValueFromMarker(type: type, dict: dict, firestore: firestore) {
                return markerValue
            }
        }
        if let dict = value as? [String: Any] {
            var result: [String: Any] = [:]
            for (key, val) in dict {
                result[key] = createNativeValue(val, firestore: firestore)
            }
            return result
        }
        if let array = value as? [Any] {
            return array.map { createNativeValue($0, firestore: firestore) }
        }
        return value
    }

    private static func createNativeValueFromMarker(type: String, dict: [String: Any], firestore: Firestore) -> Any? {
        switch type {
        case "timestamp":
            guard let seconds = (dict["seconds"] as? NSNumber)?.int64Value,
                  let nanoseconds = (dict["nanoseconds"] as? NSNumber)?.int32Value else { return nil }
            return Timestamp(seconds: seconds, nanoseconds: nanoseconds)
        case "geopoint":
            guard let latitude = (dict["latitude"] as? NSNumber)?.doubleValue,
                  let longitude = (dict["longitude"] as? NSNumber)?.doubleValue else { return nil }
            return GeoPoint(latitude: latitude, longitude: longitude)
        case "documentReference":
            guard let path = dict["path"] as? String else { return nil }
            return firestore.document(path)
        case "bytes":
            guard let base64 = dict["bytes"] as? String,
                  let data = Data(base64Encoded: base64) else { return nil }
            return data
        case "number":
            guard let value = dict["value"] as? String else { return nil }
            return parseSpecialNumber(value)
        case "serverTimestamp":
            return FieldValue.serverTimestamp()
        case "arrayUnion":
            let elements = (dict["elements"] as? [Any] ?? []).map { createNativeValue($0, firestore: firestore) }
            return FieldValue.arrayUnion(elements)
        case "arrayRemove":
            let elements = (dict["elements"] as? [Any] ?? []).map { createNativeValue($0, firestore: firestore) }
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
        if let documentReference = value as? DocumentReference {
            return createJSObjectFromDocumentReference(documentReference) as JSValue
        }
        if let data = value as? Data {
            return createJSObjectFromData(data) as JSValue
        }
        if let number = value as? NSNumber, isSpecialNumber(number) {
            return createJSObjectFromSpecialNumber(number.doubleValue) as JSValue
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

    private static func createJSObjectFromDocumentReference(_ reference: DocumentReference) -> JSObject {
        var object: JSObject = [:]
        object["__type__"] = "documentReference"
        object["id"] = reference.documentID
        object["path"] = reference.path
        return object
    }

    private static func createJSObjectFromData(_ data: Data) -> JSObject {
        var object: JSObject = [:]
        object["__type__"] = "bytes"
        object["bytes"] = data.base64EncodedString()
        return object
    }

    private static func createJSObjectFromSpecialNumber(_ value: Double) -> JSObject {
        var object: JSObject = [:]
        object["__type__"] = "number"
        if value.isNaN {
            object["value"] = "NaN"
        } else if value > 0 {
            object["value"] = "Infinity"
        } else {
            object["value"] = "-Infinity"
        }
        return object
    }

    private static func isSpecialNumber(_ number: NSNumber) -> Bool {
        let type = String(cString: number.objCType)
        guard type == "d" || type == "f" else {
            return false
        }
        let value = number.doubleValue
        return value.isNaN || value.isInfinite
    }

    private static func parseSpecialNumber(_ value: String) -> Double {
        switch value {
        case "NaN":
            return Double.nan
        case "Infinity":
            return Double.infinity
        case "-Infinity":
            return -Double.infinity
        default:
            return Double(value) ?? Double.nan
        }
    }

    private static func createJSArrayFromArray(_ array: [Any]) -> [Any] {
        return array.compactMap { item -> Any? in
            return createJSValue(value: item)
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
