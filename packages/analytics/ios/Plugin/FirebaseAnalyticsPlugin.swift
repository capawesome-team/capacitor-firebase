import Foundation
import Capacitor

import FirebaseCore

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebaseAppPlugin)
public class FirebaseAnalyticsPlugin: CAPPlugin {
    public let errorUserIdMissing = "userId must be provided."
    public let errorKeyMissing = "key must be provided."
    public let errorValueMissing = "value must be provided."
    public let errorNameMissing = "name must be provided."
    public let errorEnabledMissing = "enabled must be provided."
    private var implementation: FirebaseAnalytics?

    override public func load() {
        implementation = FirebaseAnalytics()
    }

    @objc func setUserId(_ call: CAPPluginCall) {
        let userId = call.getString("userId", nil)
        implementation?.setUserId(userId)
        call.resolve()
    }

    @objc func setUserProperty(_ call: CAPPluginCall) {
        guard let key = call.getString("key") else {
            call.reject(errorKeyMissing)
            return
        }
        let value = call.getString("value", nil)
        implementation?.setUserProperty(key, value)
        call.resolve()
    }
    
    @objc func setCurrentScreen(_ call: CAPPluginCall) {
        let screenName = call.getString("screenName", nil)
        let screenClass = call.getString("screenClassOverride", nil)
        implementation?.setCurrentScreen(screenName, screenClass)
        call.resolve()
    }

    @objc func logEvent(_ call: CAPPluginCall) {
        guard let name = call.getString("name") else {
            call.reject(errorNameMissing)
            return
        }
        let params = call.getObject("params", nil)
        implementation?.logEvent(name, params)
        call.resolve()
    }

    @objc func setSessionTimeoutDuration(_ call: CAPPluginCall) {
        let duration = call.getInt("duration") ?? 1800
        implementation?.setSessionTimeoutDuration(duration)
        call.resolve()
    }

    @objc func setEnabled(_ call: CAPPluginCall) {
        guard let enabled = call.getBool("enabled") else {
            call.reject(errorEnabledMissing)
            return
        }
        implementation?.setEnabled(enabled)
        call.resolve()
    }

    @objc func isEnabled(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }

    @objc func resetAnalyticsData(_ call: CAPPluginCall) {
        implementation?.resetAnalyticsData()
        call.resolve()
    }
}
