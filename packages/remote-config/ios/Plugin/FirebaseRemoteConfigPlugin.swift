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
        CAPPluginMethod(name: "addConfigUpdateListener", returnType: CAPPluginReturnCallback),
        CAPPluginMethod(name: "fetchAndActivate", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "fetchConfig", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getAll", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getBoolean", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getInfo", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getNumber", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getString", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "removeConfigUpdateListener", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setCustomSignals", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setDefaults", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setMinimumFetchInterval", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setSettings", returnType: CAPPluginReturnPromise)
    ]
    public let tag = "FirebaseRemoteConfig"
    private var implementation: FirebaseRemoteConfig?
    private var pluginCallMap: [String: CAPPluginCall] = [:]

    override public func load() {
        implementation = FirebaseRemoteConfig(plugin: self)
    }

    @objc override public func removeAllListeners(_ call: CAPPluginCall) {
        for (_, savedCall) in self.pluginCallMap {
            bridge?.releaseCall(savedCall)
        }
        self.pluginCallMap.removeAll()

        implementation?.removeAllListeners(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                super.removeAllListeners(call)
            }
        })
    }

    @objc func activate(_ call: CAPPluginCall) {
        implementation?.activate(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func addConfigUpdateListener(_ call: CAPPluginCall) {
        call.keepAlive = true

        do {
            let options = try AddConfigUpdateListenerOptions(call)

            self.pluginCallMap[options.callbackId] = call

            implementation?.addConfigUpdateListener(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func fetchAndActivate(_ call: CAPPluginCall) {
        implementation?.fetchAndActivate(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func fetchConfig(_ call: CAPPluginCall) {
        let options = FetchConfigOptions(call)

        implementation?.fetchConfig(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func getAll(_ call: CAPPluginCall) {
        implementation?.getAll(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func getBoolean(_ call: CAPPluginCall) {
        do {
            let options = try GetOptions(call)

            implementation?.getBoolean(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getInfo(_ call: CAPPluginCall) {
        implementation?.getInfo(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func getNumber(_ call: CAPPluginCall) {
        do {
            let options = try GetOptions(call)

            implementation?.getNumber(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getString(_ call: CAPPluginCall) {
        do {
            let options = try GetOptions(call)

            implementation?.getString(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func removeConfigUpdateListener(_ call: CAPPluginCall) {
        do {
            let options = try RemoveConfigUpdateListenerOptions(call)

            let savedCall = self.pluginCallMap[options.callbackId]
            if let savedCall = savedCall {
                bridge?.releaseCall(savedCall)
            }
            self.pluginCallMap.removeValue(forKey: options.callbackId)

            implementation?.removeConfigUpdateListener(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setCustomSignals(_ call: CAPPluginCall) {
        do {
            let options = try SetCustomSignalsOptions(call)

            implementation?.setCustomSignals(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setDefaults(_ call: CAPPluginCall) {
        do {
            let options = try SetDefaultsOptions(call)

            implementation?.setDefaults(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setMinimumFetchInterval(_ call: CAPPluginCall) {
        rejectCall(call, CustomError.notAvailable)
    }

    @objc func setSettings(_ call: CAPPluginCall) {
        let options = SetSettingsOptions(call)

        implementation?.setSettings(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", self.tag, "] ", error)
        if let error = error as? CustomError {
            call.reject(error.localizedDescription, error.code)
        } else {
            call.reject(error.localizedDescription)
        }
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
