import Foundation

import FirebaseCore
import FirebaseAppCheck

@objc public class FirebaseAppCheck: NSObject {
    @objc public static let shared = FirebaseAppCheck()

    @objc public override init() {
        super.init()
    }

    @objc private func configureFirebase() {
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }
    @objc public func getToken(
        forceRefresh: Bool, completion: @escaping (String?, Int, Error?) -> Void
    ) {
        AppCheck.appCheck().token(
            forcingRefresh: forceRefresh,
            completion: { result, error in
                if let error = error {
                    completion(nil, 0, error)
                    return
                }
                var expireTimeMillis: Int = 0
                if let result = result {
                    expireTimeMillis = Int(result.expirationDate.timeIntervalSince1970) * 1000
                }
                completion(result?.token, expireTimeMillis, nil)
            })
    }

    @objc public func initialize(debug: Bool) {
        let firebaseApp = FirebaseApp.app()
        if firebaseApp != nil {
            print(
                "FirebaseAppCheck",
                "Firebase application already defined, can't launch App Check again, read more from plugin's documentation"
            )
            return
        }

        if (debug) {
            let isDebugLoggingEnabled = ProcessInfo.processInfo.arguments.contains(
                "-FIRDebugEnabled")
            if (!isDebugLoggingEnabled) {
                print(
                    "FirebaseAppCheck",
                    "Enable -FIRDebugEnabled in scheme arguments to print AppCheck debug token")
            }

            print("FirebaseAppCheck", "Setting Firebase App Check to debug mode")
            AppCheck.setAppCheckProviderFactory(AppCheckDebugProviderFactory())
            configureFirebase()
        } else {
            AppCheck.setAppCheckProviderFactory(CustomAppCheckProviderFactory())
            configureFirebase()
        }
    }

    @objc public func setTokenAutoRefreshEnabled(_ enabled: Bool) {
        AppCheck.appCheck().isTokenAutoRefreshEnabled = enabled
    }
}
