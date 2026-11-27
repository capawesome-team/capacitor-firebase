import Foundation

import FirebaseCore
import FirebaseRemoteConfig
import Capacitor

@objc public class FirebaseRemoteConfig: NSObject {
    private var listenerRegistrationMap: [String: ConfigUpdateListenerRegistration] = [:]
    private let plugin: FirebaseRemoteConfigPlugin

    init(plugin: FirebaseRemoteConfigPlugin) {
        self.plugin = plugin
        super.init()
        if FirebaseApp.app() == nil {
            guard FirebaseOptions.defaultOptions() != nil else {
                CAPLog.print("[FirebaseRemoteConfig] Firebase was not configured: GoogleService-Info.plist is missing from the app bundle.")
                return
            }
            FirebaseApp.configure()
        }
    }

    @objc public func activate(completion: @escaping (_ error: Error?) -> Void) {
        RemoteConfig.remoteConfig().activate(completion: { _, error in
            completion(error)
        })
    }

    @objc public func addConfigUpdateListener(_ options: AddConfigUpdateListenerOptions, completion: @escaping (_ result: Result?, _ error: Error?) -> Void) {
        let callbackId = options.callbackId

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

    @objc public func fetchAndActivate(completion: @escaping (_ error: Error?) -> Void) {
        RemoteConfig.remoteConfig().fetchAndActivate(completionHandler: { result, error in
            if let error = error {
                completion(error)
            } else if result == .error {
                completion(CustomError.fetchAndActivateFailed)
            } else {
                completion(nil)
            }
        })
    }

    @objc public func fetchConfig(_ options: FetchConfigOptions, completion: @escaping (_ error: Error?) -> Void) {
        RemoteConfig.remoteConfig().fetch(withExpirationDuration: options.minimumFetchIntervalInSeconds, completionHandler: { _, error in
            completion(error)
        })
    }

    @objc public func getAll(completion: @escaping (_ result: GetAllResult?, _ error: Error?) -> Void) {
        let rc = RemoteConfig.remoteConfig()
        var keys = Set<String>()
        keys.formUnion(rc.allKeys(from: .remote))
        keys.formUnion(rc.allKeys(from: .default))
        var values: [String: RemoteConfigValue] = [:]
        for key in keys {
            values[key] = rc.configValue(forKey: key)
        }
        let result = GetAllResult(values: values)
        completion(result, nil)
    }

    @objc public func getBoolean(_ options: GetOptions, completion: @escaping (_ result: GetBooleanResult?, _ error: Error?) -> Void) {
        let value = RemoteConfig.remoteConfig().configValue(forKey: options.key)
        let result = GetBooleanResult(value: value.boolValue, source: FirebaseRemoteConfigHelper.mapRemoteConfigSourceToInt(value.source))
        completion(result, nil)
    }

    @objc public func getInfo(completion: @escaping (_ result: GetInfoResult?, _ error: Error?) -> Void) {
        let rc = RemoteConfig.remoteConfig()

        let lastFetchTimeMillis: Double
        if let lastFetchDate = rc.lastFetchTime {
            lastFetchTimeMillis = lastFetchDate.timeIntervalSince1970 * 1000.0
        } else {
            lastFetchTimeMillis = -1
        }

        let statusInt = Int(rc.lastFetchStatus.rawValue)

        let result = GetInfoResult(lastFetchTime: lastFetchTimeMillis, lastFetchStatus: statusInt)
        completion(result, nil)
    }

    @objc public func getNumber(_ options: GetOptions, completion: @escaping (_ result: GetNumberResult?, _ error: Error?) -> Void) {
        let value = RemoteConfig.remoteConfig().configValue(forKey: options.key)
        let result = GetNumberResult(value: value.numberValue, source: FirebaseRemoteConfigHelper.mapRemoteConfigSourceToInt(value.source))
        completion(result, nil)
    }

    @objc public func getString(_ options: GetOptions, completion: @escaping (_ result: GetStringResult?, _ error: Error?) -> Void) {
        let value = RemoteConfig.remoteConfig().configValue(forKey: options.key)
        let result = GetStringResult(value: value.stringValue ?? "", source: FirebaseRemoteConfigHelper.mapRemoteConfigSourceToInt(value.source))
        completion(result, nil)
    }

    @objc public func removeAllListeners(completion: @escaping (_ error: Error?) -> Void) {
        for listenerRegistration in self.listenerRegistrationMap.values {
            listenerRegistration.remove()
        }
        self.listenerRegistrationMap.removeAll()
        completion(nil)
    }

    @objc public func removeConfigUpdateListener(_ options: RemoveConfigUpdateListenerOptions, completion: @escaping (_ error: Error?) -> Void) {
        let callbackId = options.callbackId

        if let listenerRegistration = self.listenerRegistrationMap[callbackId] {
            listenerRegistration.remove()
        }
        self.listenerRegistrationMap.removeValue(forKey: callbackId)
        completion(nil)
    }

    @objc public func setCustomSignals(_ options: SetCustomSignalsOptions, completion: @escaping (_ error: Error?) -> Void) {
        var signals = [String: CustomSignalValue?]()
        for (key, value) in options.customSignals {
            if let stringValue = value as? String {
                signals[key] = .string(stringValue)
            } else if let intValue = value as? Int {
                signals[key] = .integer(intValue)
            } else if let doubleValue = value as? Double {
                signals[key] = .double(doubleValue)
            } else {
                signals.updateValue(nil, forKey: key)
            }
        }
        Task {
            do {
                try await RemoteConfig.remoteConfig().setCustomSignals(signals)
                completion(nil)
            } catch {
                completion(error)
            }
        }
    }

    @objc public func setDefaults(_ options: SetDefaultsOptions, completion: @escaping (_ error: Error?) -> Void) {
        RemoteConfig.remoteConfig().setDefaults(options.defaults)
        completion(nil)
    }

    @objc public func setSettings(_ options: SetSettingsOptions, completion: @escaping (_ error: Error?) -> Void) {
        let settings = RemoteConfigSettings()
        settings.fetchTimeout = options.fetchTimeoutInSeconds
        settings.minimumFetchInterval = options.minimumFetchIntervalInSeconds
        RemoteConfig.remoteConfig().configSettings = settings
        completion(nil)
    }
}
