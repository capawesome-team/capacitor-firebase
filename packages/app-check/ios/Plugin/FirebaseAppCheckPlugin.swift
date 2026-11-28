import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebaseAppCheckPlugin)
public class FirebaseAppCheckPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "FirebaseAppCheckPlugin"
    public let jsName = "FirebaseAppCheck"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getToken", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "initialize", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setTokenAutoRefreshEnabled", returnType: CAPPluginReturnPromise)
    ]
    public let tag = "FirebaseAppCheck"
    static let eventTokenChanged = "tokenChanged"
    private var implementation: FirebaseAppCheck?

    override public func load() {
        implementation = FirebaseAppCheck(plugin: self)
    }

    @objc public func notifyTokenChangedListeners(_ event: TokenChangedEvent) {
        self.notifyListeners(Self.eventTokenChanged, data: event.toJSObject() as? [String: Any], retainUntilConsumed: true)
    }

    @objc func getToken(_ call: CAPPluginCall) {
        let options = GetTokenOptions(call)

        implementation?.getToken(options, completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func initialize(_ call: CAPPluginCall) {
        let options = InitializeOptions(call)

        implementation?.initialize(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func setTokenAutoRefreshEnabled(_ call: CAPPluginCall) {
        do {
            let options = try SetTokenAutoRefreshEnabledOptions(call)

            implementation?.setTokenAutoRefreshEnabled(options, completion: { error in
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
