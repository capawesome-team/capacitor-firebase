import Foundation
import Capacitor
import FirebaseRemoteConfig

@objc public class AddConfigUpdateListenerOptionsCallbackEvent: NSObject, Result {
    private var configUpdate: RemoteConfigUpdate

    init(configUpdate: RemoteConfigUpdate) {
        self.configUpdate = configUpdate
    }

    public func toJSObject() -> AnyObject {
        var updatedKeysResult = JSArray()
        for key in configUpdate.updatedKeys {
            updatedKeysResult.append(key)
        }

        var result = JSObject()
        result["updatedKeys"] = updatedKeysResult
        return result as AnyObject
    }
}
