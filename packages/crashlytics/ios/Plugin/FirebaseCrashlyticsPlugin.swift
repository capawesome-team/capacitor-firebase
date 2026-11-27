import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebaseCrashlyticsPlugin)
public class FirebaseCrashlyticsPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "FirebaseCrashlyticsPlugin"
    public let jsName = "FirebaseCrashlytics"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "crash", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "deleteUnsentReports", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "didCrashOnPreviousExecution", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isEnabled", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "log", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "recordException", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "sendUnsentReports", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setCustomKey", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setEnabled", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setUserId", returnType: CAPPluginReturnPromise)
    ]
    public let tag = "FirebaseCrashlytics"
    private var implementation: FirebaseCrashlytics?

    override public func load() {
        implementation = FirebaseCrashlytics()
    }

    @objc func crash(_ call: CAPPluginCall) {
        do {
            let options = try CrashOptions(call)
            resolveCall(call)
            implementation?.crash(options)
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func deleteUnsentReports(_ call: CAPPluginCall) {
        implementation?.deleteUnsentReports(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func didCrashOnPreviousExecution(_ call: CAPPluginCall) {
        implementation?.didCrashOnPreviousExecution(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func isEnabled(_ call: CAPPluginCall) {
        implementation?.isEnabled(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func log(_ call: CAPPluginCall) {
        do {
            let options = try LogOptions(call)

            implementation?.log(options, completion: { error in
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

    @objc func recordException(_ call: CAPPluginCall) {
        do {
            let options = try RecordExceptionOptions(call)

            implementation?.recordException(options, completion: { error in
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

    @objc func sendUnsentReports(_ call: CAPPluginCall) {
        implementation?.sendUnsentReports(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func setCustomKey(_ call: CAPPluginCall) {
        do {
            let options = try SetCustomKeyOptions(call)

            implementation?.setCustomKey(options, completion: { error in
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

    @objc func setEnabled(_ call: CAPPluginCall) {
        do {
            let options = try SetEnabledOptions(call)

            implementation?.setEnabled(options, completion: { error in
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

    @objc func setUserId(_ call: CAPPluginCall) {
        do {
            let options = try SetUserIdOptions(call)

            implementation?.setUserId(options, completion: { error in
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
