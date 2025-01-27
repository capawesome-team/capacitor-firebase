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
    public let tag = "FirebaseApp"
    public let errorEnabledMissing = "enabled must be provided."
    public let errorGetTokenFailed = "Failed to get token."
    private var implementation: FirebaseAppCheck?

    override public func load() {
        implementation = FirebaseAppCheck()
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
        let debug = call.getBool("debug") ?? false
        let isTokenAutoRefreshEnabled = call.getBool("isTokenAutoRefreshEnabled") ?? false
        implementation?.initialize(debug: debug)
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
}
