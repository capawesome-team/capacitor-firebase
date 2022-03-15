import Foundation

import FirebaseCore
import FirebaseAnalytics

@objc public class FirebaseAnalytics: NSObject {

    override init() {
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }

    @objc public func setUserId(_ userId: String) {
        Analytics.setUserID(userId)
    }

    @objc public func setUserProperty(_ key: String, _ value: String) {
        Analytics.setUserProperty(value, forName: key)
    }

    @objc public func logEvent(_ name: String, _ value: [String: Any]?) {
        Analytics.logEvent(name, parameters: value)
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
