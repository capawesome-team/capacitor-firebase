import Foundation
import FirebaseFirestore
import Capacitor

public class FirebaseFirestoreHelper {
    public static func createHashMapFromJSObject(_ object: JSObject) -> [String: Any] {
        var map: [String: Any] = [:]
        for key in object.keys {
            if let value = object[key] {
                map[key] = value
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
                let queryConstraintType = queryConstraint["type"] as! String
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

    // async await is not supported by Capacitor
    public static func appendQueryNonFilterConstraintsToQuery(query: Query, queryConstraints: [QueryNonFilterConstraint], completion: @escaping (Query, Error?) -> Void) {
        FirebaseFirestoreHelper.appendQueryNonFilterConstraintsToQuery(query: query, queryConstraints: queryConstraints, error: nil, completion: completion)
    }

    private static func appendQueryNonFilterConstraintsToQuery(query: Query, queryConstraints: [QueryNonFilterConstraint], error: Error?, completion: @escaping (Query, Error?) -> Void) {
        if let error = error {
            completion(query, error)
            return
        }
        guard let queryConstraint = queryConstraints.first else {
            completion(query, nil)
            return
        }
        queryConstraint.toQuery(query, completion: { newQuery, newError in
            var newQueryConstraints = queryConstraints
            newQueryConstraints.removeFirst()
            FirebaseFirestoreHelper.appendQueryNonFilterConstraintsToQuery(query: newQuery, queryConstraints: newQueryConstraints, error: newError, completion: completion)
        })
    }

    private static func createJSValue(value: Any?) -> JSValue? {
        guard let value = value else {
            return nil
        }
        guard let value = JSTypes.coerceDictionaryToJSObject(["key": value]) as JSObject? else {
            return nil
        }
        return value["key"]
    }
}
