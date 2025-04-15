import Foundation
import Capacitor
import FirebaseAppCheck

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
    public let tag = "FirebaseApp"
    public let errorEnabledMissing = "enabled must be provided."
    public let errorGetTokenFailed = "Failed to get token."
    public let tokenChangedEvent = "tokenChanged"
    private var implementation: FirebaseAppCheck?

    override public func load() {
        implementation = FirebaseAppCheck(plugin: self)
    }

    @objc func getToken(_ call: CAPPluginCall) {
        let forceRefresh = call.getBool("forceRefresh") ?? false
        implementation?.getToken(forceRefresh: forceRefresh, completion: { token, expireTimeMillis, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            guard let token = token else {
                call.reject(self.errorGetTokenFailed)
                return
            }
            var result = JSObject()
            result["token"] = token
            result["expireTimeMillis"] = expireTimeMillis
            call.resolve(result)
        })
    }

    @objc func initialize(_ call: CAPPluginCall) {
        let debugToken: Any? = call.getValue("debugToken")

        let hasDebugToken: Bool
        if let boolVal = debugToken as? Bool {
            hasDebugToken = boolVal
        } else if let strVal = debugToken as? String, !strVal.isEmpty {
            hasDebugToken = true
        } else {
            hasDebugToken = call.getBool("debug", false)
        }

        let isTokenAutoRefreshEnabled = call.getBool("isTokenAutoRefreshEnabled") ?? false
        implementation?.initialize(debug: hasDebugToken)
        implementation?.setTokenAutoRefreshEnabled(isTokenAutoRefreshEnabled)
        call.resolve()
    }

    @objc func setTokenAutoRefreshEnabled(_ call: CAPPluginCall) {
        guard let enabled = call.getBool("enabled") else {
            call.reject(errorEnabledMissing)
            return
        }
        implementation?.setTokenAutoRefreshEnabled(enabled)
        call.resolve()
    }

    @objc func handleTokenChanged(_ notification: Notification) {
        guard let userInfo = notification.userInfo else {
            return
        }

        let token = userInfo[AppCheckTokenNotificationKey] as? String

        var result = JSObject()
        result["token"] = token ?? NSNull()
        notifyListeners(tokenChangedEvent, data: result, retainUntilConsumed: true)
    }
}
