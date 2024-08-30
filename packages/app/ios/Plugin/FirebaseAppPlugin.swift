import Foundation
import Capacitor

import FirebaseCore

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebaseAppPlugin)
public class FirebaseAppPlugin: CAPPlugin {

    override public func load() {
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }

    @objc func getName(_ call: CAPPluginCall) {
        call.resolve([
            "name": FirebaseApp.app()?.name ?? ""
        ])
    }

    @objc func getOptions(_ call: CAPPluginCall) {
        call.resolve([
            "apiKey": FirebaseApp.app()?.options.apiKey ?? "",
            "applicationId": FirebaseApp.app()?.options.googleAppID ?? "",
            "databaseUrl": FirebaseApp.app()?.options.databaseURL ?? "",
            "gcmSenderId": FirebaseApp.app()?.options.gcmSenderID ?? "",
            "projectId": FirebaseApp.app()?.options.projectID ?? "",
            "storageBucket": FirebaseApp.app()?.options.storageBucket ?? ""
        ])
    }

    /*
     Inits a custom Firebase Config with the provided name
     */
    @objc func initializeAppWithConfig(_ call: CAPPluginCall) {
        let name = call.getString("name")!
        let config = call.getObject("config")!
        let options = FirebaseOptions.init(googleAppID: config["appId"] as! String, gcmSenderID: config["messagingSenderId"] as! String)
        options.clientID = config["clientId"] as? String
        options.apiKey = config["apiKey"] as? String
        options.projectID = config["projectId"] as? String
        options.storageBucket = config["storageBucket"] as? String
        options.databaseURL = config["databaseURL"] as? String

        do {
            try FirebaseApp.configure(name: name, options: options)
            call.resolve()
        } catch {
            call.reject("Could not initialize app with provided Firebase config")
        }
    }

    /*
     Returns true if firebase app with provided name is available
     */
    @objc func appIsInitialized(_ call: CAPPluginCall) {
        guard let name = call.getString("name") else {
            return call.reject("Missing name")
        }

        let apps = FirebaseApp.allApps!
        if apps.contains(where: { (_, app: FirebaseApp) in
            return app.name == name
        }) {
            call.resolve([ "result": true ])
        } else {
            call.resolve([ "result": false ])
        }
    }

}
