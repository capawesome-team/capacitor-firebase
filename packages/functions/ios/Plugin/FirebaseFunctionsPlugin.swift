import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebaseFunctionsPlugin)
public class FirebaseFunctionsPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "FirebaseFunctionsPlugin"
    public let jsName = "FirebaseFunctions"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "callByName", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "callByUrl", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "useEmulator", returnType: CAPPluginReturnPromise)
    ]
    public let tag = "FirebaseFunctions"
    private var implementation: FirebaseFunctions?

    override public func load() {
        self.implementation = FirebaseFunctions(plugin: self)
    }

    @objc func callByName(_ call: CAPPluginCall) {
        do {
            let options = try CallByNameOptions(call)

            implementation?.callByName(options, completion: { result, error in
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

    @objc func callByUrl(_ call: CAPPluginCall) {
        do {
            let options = try CallByUrlOptions(call)

            implementation?.callByUrl(options, completion: { result, error in
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

    @objc func useEmulator(_ call: CAPPluginCall) {
        do {
            let options = try UseEmulatorOptions(call)

            implementation?.useEmulator(options, completion: { error in
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
            call.reject(error.localizedDescription, FirebaseFunctionsHelper.createErrorCode(error: error))
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
