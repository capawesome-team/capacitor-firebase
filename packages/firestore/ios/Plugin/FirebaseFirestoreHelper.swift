import Foundation
import FirebaseFirestore
import Capacitor

public class FirebaseFirestoreHelper {
    public static func createHashMapFromJSObject(_ object: JSObject) -> [String: Any] {
        // 1. create map
        var map: [String: Any] = [:]
        for key in object.keys {
            map[key] = object[key];
        }
        
        // 2. resolve timestamps
        let res = convertFromSerializedTimestamps(obj: map);
        return res
    }

    public static func createJSObjectFromHashMap(_ map: [String: Any]?) -> JSObject? {
        guard let map = map else {
            return nil
        }
        // 1. resolve timestamps
        let localMap = convertToSerializedTimestamps(obj: map);
        
        // 2. convert from map
        var object: JSObject = [:]
        for key in localMap.keys {
            object[key] = self.createJSValue(value: localMap[key])
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

    private static func convertToSerializedTimestamps(obj:[String: Any]) -> [String: Any] {
        var localObj = obj;
        for key in obj.keys {
            let value = obj[key];
            // 1. convert timestamps
            if value is Timestamp {
                let val = value as! Timestamp;
                let st = [ "type": "timestamp", "seconds": String(val.seconds), "nanoseconds": String(val.nanoseconds) ];
                localObj[key] = st;
                print("NATIVE LAYER: CONVERTED Timestamp -> serialized", st);
            }
            
            // 2. check sub-objects
            if value is JSObject {
                localObj[key] = self.convertToSerializedTimestamps(obj: value as! JSObject);
            }
        }
        
        return localObj;
    }
    
    private static func convertFromSerializedTimestamps(obj: [String:Any]) -> [String:Any] {
        var object = obj;
        for key in object.keys {
            // 1. check for sub-objects
            let value = object[key];
            if (value != nil && value is Dictionary<String, JSValue>) {
                let val = value as! Dictionary<String, JSValue>;
                // a. timestamp
                if (val.keys.contains("type")) {
                    let stringSeconds = val["seconds"] as! String;
                    let s = Int64(stringSeconds) ?? 0;
                    
                    let stringNanoseconds = val["nanoseconds"] as! String;
                    let n = Int32(stringNanoseconds) ?? 0;
                                  
                    let t = Timestamp(seconds: s, nanoseconds: n);
                    // b. serialized timestamp converstion
                    print("NATIVE LAYER: CONVERTED serialized -> Timestamp with s: \(stringSeconds) and \(stringNanoseconds)", t);
                    object[key] = t;
                } 
                // b. non-timestamp - check sub objects
                else {
                    object[key] = convertFromSerializedTimestamps(obj: val);
                }
            }
        }
        return object;
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
