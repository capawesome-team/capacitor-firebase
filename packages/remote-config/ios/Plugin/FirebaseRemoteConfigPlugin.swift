import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebaseRemoteConfigPlugin)
public class FirebaseRemoteConfigPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "FirebaseRemoteConfigPlugin" 
    public let jsName = "FirebaseRemoteConfig" 
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "activate", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "fetchAndActivate", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "fetchConfig", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getBoolean", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getNumber", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getString", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setMinimumFetchInterval", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setSettings", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "addConfigUpdateListener", returnType: CAPPluginReturnCallback),
        CAPPluginMethod(name: "removeConfigUpdateListener", returnType: CAPPluginReturnPromise),
    ] 
    public let tag = "FirebaseRemoteConfig"
    public let errorKeyMissing = "key must be provided."
    public let errorFetchAndActivatefailed = "fetchAndActivate failed."
    public let errorCallbackIdMissing = "callbackId must be provided."

    private let defaultMinimumFetchIntervalInSeconds: Double = 43200
    private let defaultFetchTimeoutInSeconds: Double = 60
    private var implementation: FirebaseRemoteConfig?
    private var pluginCallMap: [String: CAPPluginCall] = [:]

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
        let minimumFetchIntervalInSeconds = call.getDouble("minimumFetchIntervalInSeconds") ?? defaultMinimumFetchIntervalInSeconds
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

    @objc func setSettings(_ call: CAPPluginCall) {
        let fetchTimeoutInSeconds = call.getDouble("fetchTimeoutInSeconds") ?? defaultFetchTimeoutInSeconds
        let minimumFetchIntervalInSeconds = call.getDouble("minimumFetchIntervalInSeconds") ?? defaultMinimumFetchIntervalInSeconds
        implementation?.setSettings(fetchTimeoutInSeconds: fetchTimeoutInSeconds, minimumFetchIntervalInSeconds: minimumFetchIntervalInSeconds)
        call.resolve()
    }

    @objc func addConfigUpdateListener(_ call: CAPPluginCall) {
        call.keepAlive = true

        guard let callbackId = call.callbackId else {
            call.reject(errorCallbackIdMissing)
            return
        }

        self.pluginCallMap[callbackId] = call

        let options = AddConfigUpdateListenerOptions(callbackId: callbackId)

        implementation?.addConfigUpdateListener(options, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func removeConfigUpdateListener(_ call: CAPPluginCall) {
        guard let callbackId = call.getString("callbackId") else {
            call.reject(errorCallbackIdMissing)
            return
        }

        let savedCall = self.pluginCallMap[callbackId]
        if let savedCall = savedCall {
            bridge?.releaseCall(savedCall)
        }
        self.pluginCallMap.removeValue(forKey: callbackId)

        let options = RemoveConfigUpdateListenerOptions(callbackId: callbackId)

        implementation?.removeConfigUpdateListener(options)
        call.resolve()
    }

    @objc override public func removeAllListeners(_ call: CAPPluginCall) {
        for (_, savedCall) in self.pluginCallMap {
            bridge?.releaseCall(savedCall)
        }
        self.pluginCallMap.removeAll()

        implementation?.removeAllListeners()
        super.removeAllListeners(call)
    }
}
