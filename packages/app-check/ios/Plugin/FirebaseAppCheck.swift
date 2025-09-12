import Foundation

import FirebaseCore
import FirebaseAppCheck

@objc public class FirebaseAppCheck: NSObject {

    override public init() {
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }

    @objc public func getToken(forceRefresh: Bool, completion: @escaping (String?, Int, Error?) -> Void) {
        AppCheck.appCheck().token(forcingRefresh: forceRefresh, completion: { result, error in
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
        if debug {
            AppCheck.setAppCheckProviderFactory(AppCheckDebugProviderFactory())
        } else {
            AppCheck.setAppCheckProviderFactory(CustomAppCheckProviderFactory())
        }
    }

    @objc public func setTokenAutoRefreshEnabled(_ enabled: Bool) {
        AppCheck.appCheck().isTokenAutoRefreshEnabled = enabled
    }
}
