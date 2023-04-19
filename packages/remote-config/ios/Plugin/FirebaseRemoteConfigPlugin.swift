import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebaseRemoteConfigPlugin)
public class FirebaseRemoteConfigPlugin: CAPPlugin {
    public let errorKeyMissing = "key must be provided."
    public let errorFetchAndActivatefailed = "fetchAndActivate failed."
    private var implementation: FirebaseRemoteConfig?

    override public func load() {
        implementation = FirebaseRemoteConfig(plugin: self)
    }

    @objc func activate(_ call: CAPPluginCall) {
        implementation?.activate(completion: { _, errorMessage in
            if let errorMessage = errorMessage {
                call.reject(errorMessage)
                return
            }
            call.resolve()
        })
    }

    @objc func fetchAndActivate(_ call: CAPPluginCall) {
        implementation?.fetchAndActivate(completion: { _, errorMessage in
            if let errorMessage = errorMessage {
                call.reject(errorMessage)
                return
            }
            call.resolve()
        })
    }

    @objc func fetchConfig(_ call: CAPPluginCall) {
        let minimumFetchIntervalInSeconds = call.getDouble("minimumFetchIntervalInSeconds") ?? 43200
        implementation?.fetchConfig(minimumFetchIntervalInSeconds: minimumFetchIntervalInSeconds, completion: { errorMessage in
            if let errorMessage = errorMessage {
                call.reject(errorMessage)
                return
            }
            call.resolve()
        })
    }

    @objc func getBoolean(_ call: CAPPluginCall) {
        guard let key = call.getString("key") else {
            call.reject(errorKeyMissing)
            return
        }
        let value = implementation?.getValue(key)
        call.resolve([
            "value": value!.boolValue,
            "source": FirebaseRemoteConfigHelper.mapRemoteConfigSourceToInt(value!.source)
        ])
    }

    @objc func getNumber(_ call: CAPPluginCall) {
        guard let key = call.getString("key") else {
            call.reject(errorKeyMissing)
            return
        }
        let value = implementation?.getValue(key)
        call.resolve([
            "value": value!.numberValue,
            "source": FirebaseRemoteConfigHelper.mapRemoteConfigSourceToInt(value!.source)
        ])
    }

    @objc func getString(_ call: CAPPluginCall) {
        guard let key = call.getString("key") else {
            call.reject(errorKeyMissing)
            return
        }
        let value = implementation?.getValue(key)
        call.resolve([
            "value": value!.stringValue ?? "",
            "source": FirebaseRemoteConfigHelper.mapRemoteConfigSourceToInt(value!.source)
        ])
    }

    @objc func setMinimumFetchInterval(_ call: CAPPluginCall) {
        call.reject("Not available on iOS.")
    }
}
