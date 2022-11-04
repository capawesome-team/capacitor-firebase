import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebaseRemoteConfigPlugin)
public class FirebaseRemoteConfigPlugin: CAPPlugin {
    private var implementation: FirebaseRemoteConfig?

    override public func load() {
        implementation = FirebaseRemoteConfig()
    }

    @objc func echo(_ call: CAPPluginCall) {
        implementation?.startTrace(traceName)
        call.resolve()
    }
}
