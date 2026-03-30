import Foundation
import StoreKit

import Capacitor
import FirebaseCore
import FirebaseAnalytics

@objc public class FirebaseAnalytics: NSObject {

    private let plugin: FirebaseAnalyticsPlugin

    init(plugin: FirebaseAnalyticsPlugin) {
        self.plugin = plugin
        super.init()
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }

    @objc public func getAppInstanceId() -> String? {
        return Analytics.appInstanceID()
    }

    @objc public func setConsent(consentType: ConsentType, consentStatus: ConsentStatus) {
        var map = [ConsentType: ConsentStatus]()
        map[consentType] = consentStatus
        Analytics.setConsent(map)
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

    @available(iOS 15.0, *)
    public func logTransaction(transactionId: UInt64, completion: @escaping (String?) -> Void) {
        Task {
            var matchedTransaction: Transaction?
            for await result in Transaction.all {
                let transaction = FirebaseAnalyticsHelper.getTransaction(from: result)
                if transaction.id == transactionId {
                    matchedTransaction = transaction
                    break
                }
            }
            guard let transaction = matchedTransaction else {
                completion(self.plugin.errorTransactionNotFound)
                return
            }
            Analytics.logTransaction(transaction)
            completion(nil)
        }
    }

    @objc public func initiateOnDeviceConversionMeasurement(email: String) {
        Analytics.initiateOnDeviceConversionMeasurement(emailAddress: email)
    }

    @objc public func initiateOnDeviceConversionMeasurement(phone: String) {
        Analytics.initiateOnDeviceConversionMeasurement(phoneNumber: phone)
    }

    @objc public func initiateOnDeviceConversionMeasurement(hashedEmail: Data) {
        Analytics.initiateOnDeviceConversionMeasurement(hashedEmailAddress: hashedEmail)
    }

    @objc public func initiateOnDeviceConversionMeasurement(hashedPhone: Data) {
        Analytics.initiateOnDeviceConversionMeasurement(hashedPhoneNumber: hashedPhone)
    }
}
