import Foundation
import Capacitor

import FirebaseCore

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebaseAppPlugin)
public class FirebaseAppPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "FirebaseAppPlugin"
    public let jsName = "FirebaseApp"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getApps", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getName", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getOptions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "initializeApp", returnType: CAPPluginReturnPromise)
    ]
    public static let tag = "FirebaseApp"

    private var implementation: FirebaseApp?

    override public func load() {
        implementation = FirebaseApp()
        if FirebaseCore.FirebaseApp.app() == nil {
            guard FirebaseOptions.defaultOptions() != nil else {
                CAPLog.print("[FirebaseApp] Firebase was not configured: GoogleService-Info.plist is missing from the app bundle.")
                return
            }
            FirebaseCore.FirebaseApp.configure()
        }
    }

    @objc func getApps(_ call: CAPPluginCall) {
        implementation?.getApps(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func getName(_ call: CAPPluginCall) {
        call.resolve([
            "name": FirebaseCore.FirebaseApp.app()?.name ?? ""
        ])
    }

    @objc func getOptions(_ call: CAPPluginCall) {
        call.resolve([
            "apiKey": FirebaseCore.FirebaseApp.app()?.options.apiKey ?? "",
            "applicationId": FirebaseCore.FirebaseApp.app()?.options.googleAppID ?? "",
            "databaseUrl": FirebaseCore.FirebaseApp.app()?.options.databaseURL ?? "",
            "gcmSenderId": FirebaseCore.FirebaseApp.app()?.options.gcmSenderID ?? "",
            "projectId": FirebaseCore.FirebaseApp.app()?.options.projectID ?? "",
            "storageBucket": FirebaseCore.FirebaseApp.app()?.options.storageBucket ?? ""
        ])
    }

    @objc func initializeApp(_ call: CAPPluginCall) {
        do {
            let options = try InitializeAppOptions(call)

            implementation?.initializeApp(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", FirebaseAppPlugin.tag, "] ", error)
        if let error = error as? CustomError {
            call.reject(error.localizedDescription, error.code)
        } else {
            call.reject(error.localizedDescription)
        }
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
