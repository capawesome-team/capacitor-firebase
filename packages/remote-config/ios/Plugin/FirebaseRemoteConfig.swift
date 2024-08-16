import Foundation

import FirebaseCore
import FirebaseRemoteConfig
import Capacitor

@objc public class FirebaseRemoteConfig: NSObject {
    private let plugin: FirebaseRemoteConfigPlugin
    private var listenerRegistrationMap: [String: ConfigUpdateListenerRegistration] = [:]

    init(plugin: FirebaseRemoteConfigPlugin) {
        self.plugin = plugin
        super.init()
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
        RemoteConfig.remoteConfig().fetchAndActivate(completionHandler: { result, error in
            if let error = error {
                CAPLog.print("[", self.plugin.tag, "] ", error)
                completion(false, error.localizedDescription)
                return
            }
            if result == .error {
                completion(false, self.plugin.errorFetchAndActivatefailed)
            } else {
                completion(true, nil)
            }
        })
    }
    
    @objc public func fetchConfig(completion: @escaping (String?) -> Void) {
        RemoteConfig.remoteConfig().fetch(completionHandler: { _, error in
            if let error = error {
                CAPLog.print("[", self.plugin.tag, "] ", error)
                completion(error.localizedDescription)
                return
            }
            completion(nil)
        })
    }

    @objc public func fetchConfig(minimumFetchIntervalInSeconds: Double, completion: @escaping (String?) -> Void) {
        RemoteConfig.remoteConfig().fetch(withExpirationDuration: minimumFetchIntervalInSeconds, completionHandler: { _, error in
            if let error = error {
                CAPLog.print("[", self.plugin.tag, "] ", error)
                completion(error.localizedDescription)
                return
            }
            completion(nil)
        })
    }

    @objc public func getValue(_ key: String) -> RemoteConfigValue {
        return RemoteConfig.remoteConfig().configValue(forKey: key)
    }

    @objc public func setSettings(fetchTimeoutInSeconds: Double, minimumFetchIntervalInSeconds: Double) {
        let settings = RemoteConfigSettings()
        settings.fetchTimeout = fetchTimeoutInSeconds
        settings.minimumFetchInterval = minimumFetchIntervalInSeconds
        RemoteConfig.remoteConfig().configSettings = settings
    }

    @objc public func addConfigUpdateListener(_ options: AddConfigUpdateListenerOptions, completion: @escaping (Result?, Error?) -> Void) {
        let callbackId = options.getCallbackId()

        let listenerRegistration = RemoteConfig.remoteConfig().addOnConfigUpdateListener { configUpdate, error in
            if let error = error {
                completion(nil, error)
            } else if let configUpdate = configUpdate {
                let result = AddConfigUpdateListenerOptionsCallbackEvent(configUpdate: configUpdate)
                completion(result, nil)
            }
        }
        self.listenerRegistrationMap[callbackId] = listenerRegistration
    }

    @objc public func removeConfigUpdateListener(_ options: RemoveConfigUpdateListenerOptions) {
        let callbackId = options.getCallbackId()

        if let listenerRegistration = self.listenerRegistrationMap[callbackId] {
            listenerRegistration.remove()
        }
        self.listenerRegistrationMap.removeValue(forKey: callbackId)
    }

    @objc public func removeAllListeners() {
        for listenerRegistration in self.listenerRegistrationMap.values {
            listenerRegistration.remove()
        }
        self.listenerRegistrationMap.removeAll()
    }
}
