import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebaseMessagingPlugin)
public class FirebaseMessagingPlugin: CAPPlugin {
    private var implementation: FirebaseMessaging?

    override public func load() {
        implementation = FirebaseMessaging()
    }

    @objc func register(_ call: CAPPluginCall) {
        call.resolve()
    }

    @objc func unregister(_ call: CAPPluginCall) {
        call.resolve()
    }

    @objc func getDeliveredNotifications(_ call: CAPPluginCall) {
        call.resolve()
    }

    @objc func removeDeliveredNotifications(_ call: CAPPluginCall) {
        call.resolve()
    }

    @objc func removeAllDeliveredNotifications(_ call: CAPPluginCall) {
        call.resolve()
    }

    @objc func checkPermissions(_ call: CAPPluginCall) {
        implementation?.checkPermissions(completion: { permission in
            call.resolve([
                "receive": permission
            ])
        })
    }

    @objc func requestPermissions(_ call: CAPPluginCall) {
        implementation?.requestPermissions(completion: { granted, error in
            guard error == nil else {
                call.reject(error!.localizedDescription)
                return
            }
            call.resolve(["receive": granted ? "granted" : "denied"])
        })
    }
}
