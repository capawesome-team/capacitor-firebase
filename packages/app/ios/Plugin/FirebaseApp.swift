import Foundation
import Capacitor
import FirebaseCore

@objc public class FirebaseApp: NSObject {
    override public init() {
        super.init()
        if FirebaseCore.FirebaseApp.app() == nil {
            guard FirebaseOptions.defaultOptions() != nil else {
                CAPLog.print("[FirebaseApp] Firebase was not configured: GoogleService-Info.plist is missing from the app bundle.")
                return
            }
            FirebaseCore.FirebaseApp.configure()
        }
    }

    @objc public func getApps(completion: @escaping (_ result: GetAppsResult?, _ error: Error?) -> Void) {
        let apps = Array((FirebaseCore.FirebaseApp.allApps ?? [:]).values)
        completion(GetAppsResult(apps: apps), nil)
    }

    @objc public func getName(completion: @escaping (_ result: GetNameResult?, _ error: Error?) -> Void) {
        let name = FirebaseCore.FirebaseApp.app()?.name ?? ""
        completion(GetNameResult(name: name), nil)
    }

    @objc public func getOptions(completion: @escaping (_ result: GetOptionsResult?, _ error: Error?) -> Void) {
        let options = FirebaseCore.FirebaseApp.app()?.options
        completion(GetOptionsResult(options: options), nil)
    }

    @objc public func initializeApp(_ options: InitializeAppOptions, completion: @escaping (_ error: Error?) -> Void) {
        // NEVER remove this check. The SDK raises an Objective-C exception for an existing name, which Swift cannot catch.
        if FirebaseCore.FirebaseApp.allApps?[options.name] != nil {
            completion(CustomError.appAlreadyExists)
            return
        }
        FirebaseCore.FirebaseApp.configure(name: options.name, options: options.options)
        completion(nil)
    }
}
