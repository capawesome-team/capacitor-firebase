import Foundation

import Capacitor
import FirebaseCore
import FirebaseAppCheck

@objc public class FirebaseAppCheck: NSObject {
    private weak var plugin: FirebaseAppCheckPlugin?

    init(plugin: FirebaseAppCheckPlugin) {
        self.plugin = plugin
        super.init()
        if FirebaseApp.app() == nil {
            if FirebaseOptions.defaultOptions() == nil {
                CAPLog.print("[FirebaseAppCheck] Firebase was not configured: GoogleService-Info.plist is missing from the app bundle.")
            } else {
                FirebaseApp.configure()
            }
        }
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handleTokenChanged),
            name: Notification.Name.AppCheckTokenDidChange,
            object: nil
        )
    }

    @objc public func getToken(_ options: GetTokenOptions, completion: @escaping (_ result: GetTokenResult?, _ error: Error?) -> Void) {
        AppCheck.appCheck().token(forcingRefresh: options.forceRefresh, completion: { appCheckToken, error in
            if let error = error {
                completion(nil, error)
                return
            }
            guard let appCheckToken = appCheckToken else {
                completion(nil, CustomError.getTokenFailed)
                return
            }
            let expireTimeMillis = Int(appCheckToken.expirationDate.timeIntervalSince1970) * 1000
            let result = GetTokenResult(token: appCheckToken.token, expireTimeMillis: expireTimeMillis)
            completion(result, nil)
        })
    }

    @objc public func initialize(_ options: InitializeOptions, completion: @escaping (_ error: Error?) -> Void) {
        if options.debug {
            AppCheck.setAppCheckProviderFactory(AppCheckDebugProviderFactory())
        } else {
            AppCheck.setAppCheckProviderFactory(CustomAppCheckProviderFactory())
        }
        AppCheck.appCheck().isTokenAutoRefreshEnabled = options.isTokenAutoRefreshEnabled
        completion(nil)
    }

    @objc public func setTokenAutoRefreshEnabled(_ options: SetTokenAutoRefreshEnabledOptions, completion: @escaping (_ error: Error?) -> Void) {
        AppCheck.appCheck().isTokenAutoRefreshEnabled = options.enabled
        completion(nil)
    }

    @objc private func handleTokenChanged(_ notification: Notification) {
        guard let userInfo = notification.userInfo else {
            return
        }
        let token = userInfo[AppCheckTokenNotificationKey] as? String
        plugin?.notifyTokenChangedListeners(TokenChangedEvent(token: token))
    }
}
