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
}
