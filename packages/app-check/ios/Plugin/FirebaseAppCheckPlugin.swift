import Foundation
import Capacitor
import FirebaseCore
import FirebaseAppCheck

@objc(FirebaseAppCheckPlugin)
public class FirebaseAppCheckPlugin: CAPPlugin {
    @objc func initialize(_ call: CAPPluginCall) {
        let debugMode = call.getBool("debug", false)
        if (debugMode) {
            NSLog("App Check using Debug mode")
            AppCheck.setAppCheckProviderFactory(
                AppCheckDebugProviderFactory()
            )
        } else {
            NSLog("App Check using DeviceCheck or AppAttest")
            AppCheck.setAppCheckProviderFactory(
                CustomAppCheckProviderFactory()
            )
        }
        if (FirebaseApp.app() == nil) {
            FirebaseApp.configure()
        }
        call.resolve([
            "success": true
        ])
    }

    @objc func getToken(_ call: CAPPluginCall) {
        AppCheck.appCheck().token(forcingRefresh: false) { token, error in
            guard error == nil else {
                call.reject("Failed to retrieve App Check token: \(error!)")
                return
            }
            guard let token = token else {
                call.reject("Unable to retreive App Check token")
                return
            }
            NSLog("App Check token obtained: \(token.token)")
            call.resolve([
                "token": token.token,
                "exp": Int64(token.expirationDate.timeIntervalSince1970 * 1000)
            ])
        }
    }
}

