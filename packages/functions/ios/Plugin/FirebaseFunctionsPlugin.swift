import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebaseFunctionsPlugin)
public class FirebaseFunctionsPlugin: CAPPlugin {
    public let tag = "FirebaseFunctions"
    public let errorNameMissing = "name must be provided."
    private var implementation: FirebaseFunctions?

    override public func load() {
        self.implementation = FirebaseFunctions(plugin: self)
    }

    @objc func callByName(_ call: CAPPluginCall) {
        guard let name = call.getString("name") else {
            call.reject(errorNameMissing)
            return
        }
        let data = call.getValue("data")

        let options = CallByNameOptions(name: name, data: data)

        implementation?.callByName(options, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func callByUrl(_ call: CAPPluginCall) {
        guard let url = call.getString("url") else {
            call.reject("url must be provided.")
            return
        }
        let data = call.getValue("data")

        let options = CallByUrlOptions(url: url, data: data)

        implementation?.callByUrl(options, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }
}
