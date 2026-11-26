import Foundation
import FirebaseCore

@objc public class FirebaseApp: NSObject {
    @objc public func getApps(completion: @escaping (_ result: GetAppsResult?, _ error: Error?) -> Void) {
        let apps = Array((FirebaseCore.FirebaseApp.allApps ?? [:]).values)
        completion(GetAppsResult(apps: apps), nil)
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
