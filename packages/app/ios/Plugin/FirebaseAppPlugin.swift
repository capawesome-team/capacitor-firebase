import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebaseAppPlugin)
public class FirebaseAppPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "FirebaseAppPlugin"
    public let jsName = "FirebaseApp"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getApps", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getName", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getOptions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "initializeApp", returnType: CAPPluginReturnPromise)
    ]
    public static let tag = "FirebaseApp"

    private var implementation: FirebaseApp?

    override public func load() {
        implementation = FirebaseApp()
    }

    @objc func getApps(_ call: CAPPluginCall) {
        implementation?.getApps(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func getName(_ call: CAPPluginCall) {
        do {
            try implementation?.getName(completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func getOptions(_ call: CAPPluginCall) {
        do {
            try implementation?.getOptions(completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func initializeApp(_ call: CAPPluginCall) {
        do {
            let options = try InitializeAppOptions(call)

            implementation?.initializeApp(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", FirebaseAppPlugin.tag, "] ", error)
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
