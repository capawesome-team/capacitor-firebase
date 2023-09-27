import Foundation

import Capacitor
import FirebaseCore
import FirebaseAnalytics

@objc public class FirebaseAnalytics: NSObject {

    override init() {
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }

    @objc public func getAppInstanceId() -> String? {
        return Analytics.appInstanceID()
    }

    @objc public func setUserId(_ userId: String?) {
        Analytics.setUserID(userId)
    }

    @objc public func setUserProperty(_ key: String, _ value: String?) {
        Analytics.setUserProperty(value, forName: key)
    }

    @objc public func setCurrentScreen(_ screenName: String?, _ screenClass: String?) {
        var params = [String: Any]()
        if screenName != nil {
            params[AnalyticsParameterScreenName] = screenName
        }
        if screenClass != nil {
            params[AnalyticsParameterScreenClass] = screenClass
        }
        DispatchQueue.main.async {
            Analytics.logEvent(AnalyticsEventScreenView,
                               parameters: params)
        }
    }

    @objc public func logEvent(_ name: String, _ params: [String: Any]?) {
        Analytics.logEvent(name, parameters: params)
    }

    @objc public func setSessionTimeoutDuration(_ duration: Int) {
        Analytics.setSessionTimeoutInterval(TimeInterval(duration))
    }

    @objc public func setEnabled(_ enabled: Bool) {
        Analytics.setAnalyticsCollectionEnabled(enabled)
    }

    @objc public func resetAnalyticsData() {
        Analytics.resetAnalyticsData()
    }
}
