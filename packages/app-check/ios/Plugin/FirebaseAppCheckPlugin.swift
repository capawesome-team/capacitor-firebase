import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebaseAppCheckPlugin)
public class FirebaseAppCheckPlugin: CAPPlugin {
    private var implementation: FirebaseAppCheck?

    override public func load() {
        implementation = FirebaseAppCheck()
    }

    @objc func echo(_ call: CAPPluginCall) {
        implementation?.startTrace(traceName)
        call.resolve()
    }
}
