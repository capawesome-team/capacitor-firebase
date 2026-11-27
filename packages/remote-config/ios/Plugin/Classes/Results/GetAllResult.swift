import Foundation
import Capacitor
import FirebaseRemoteConfig

@objc public class GetAllResult: NSObject, Result {
    let values: [String: RemoteConfigValue]

    init(values: [String: RemoteConfigValue]) {
        self.values = values
    }

    @objc public func toJSObject() -> AnyObject {
        var valuesResult = JSObject()
        for (key, value) in values {
            var valueResult = JSObject()
            valueResult["value"] = value.stringValue ?? ""
            valueResult["source"] = FirebaseRemoteConfigHelper.mapRemoteConfigSourceToInt(value.source)
            valuesResult[key] = valueResult
        }

        var result = JSObject()
        result["values"] = valuesResult
        return result as AnyObject
    }
}
