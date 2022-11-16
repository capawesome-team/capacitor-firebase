import Foundation

import FirebaseCore
import FirebaseRemoteConfig

@objc public class FirebaseRemoteConfig: NSObject {
    override init() {
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }

    @objc public func activate(completion: @escaping (Bool, String?) -> Void) {
        RemoteConfig.remoteConfig().activate(completion: { result, error in
            if let error = error {
                completion(false, error.localizedDescription)
                return
            }
            completion(result, nil)
        })
    }

    @objc public func fetchAndActivate(completion: @escaping (Bool, String?) -> Void) {
        RemoteConfig.remoteConfig().fetchAndActivate(completionHandler: { _, error in
            if let error = error {
                completion(false, error.localizedDescription)
                return
            }
            completion(true, nil)
        })
    }

    @objc public func fetchConfig(minimumFetchIntervalInSeconds: Double, completion: @escaping (String?) -> Void) {
        RemoteConfig.remoteConfig().fetch(withExpirationDuration: minimumFetchIntervalInSeconds, completionHandler: { _, error in
            if let error = error {
                completion(error.localizedDescription)
                return
            }
            completion(nil)
        })
    }

    @objc public func getBoolean(_ key: String) -> Bool {
        return RemoteConfig.remoteConfig().configValue(forKey: key).boolValue
    }

    @objc public func getNumber(_ key: String) -> NSNumber {
        return RemoteConfig.remoteConfig().configValue(forKey: key).numberValue
    }

    @objc public func getString(_ key: String) -> String? {
        return RemoteConfig.remoteConfig().configValue(forKey: key).stringValue
    }
}
