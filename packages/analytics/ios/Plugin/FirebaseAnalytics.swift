import Foundation
import StoreKit

import Capacitor
import FirebaseCore
import FirebaseAnalytics

@objc public class FirebaseAnalytics: NSObject {

    override init() {
        super.init()
        if FirebaseApp.app() == nil {
            guard FirebaseOptions.defaultOptions() != nil else {
                CAPLog.print("[FirebaseAnalytics] Firebase was not configured: GoogleService-Info.plist is missing from the app bundle.")
                return
            }
            FirebaseApp.configure()
        }
    }

    @objc public func getAppInstanceId(completion: @escaping (GetAppInstanceIdResult?, Error?) -> Void) {
        let result = GetAppInstanceIdResult(appInstanceId: Analytics.appInstanceID())
        completion(result, nil)
    }

    @objc public func initiateOnDeviceConversionMeasurementWithEmailAddress(
        _ options: InitiateOnDeviceConversionMeasurementWithEmailAddressOptions,
        completion: @escaping (Error?) -> Void
    ) {
        Analytics.initiateOnDeviceConversionMeasurement(emailAddress: options.emailAddress)
        completion(nil)
    }

    @objc public func initiateOnDeviceConversionMeasurementWithHashedEmailAddress(
        _ options: InitiateOnDeviceConversionMeasurementWithHashedEmailAddressOptions,
        completion: @escaping (Error?) -> Void
    ) {
        let normalizedEmailAddress = FirebaseAnalyticsHelper.normalizeEmail(options.emailAddressToHash)
        let hashedEmailAddress = FirebaseAnalyticsHelper.sha256(normalizedEmailAddress)
        Analytics.initiateOnDeviceConversionMeasurement(hashedEmailAddress: hashedEmailAddress)
        completion(nil)
    }

    @objc public func initiateOnDeviceConversionMeasurementWithHashedPhoneNumber(
        _ options: InitiateOnDeviceConversionMeasurementWithHashedPhoneNumberOptions,
        completion: @escaping (Error?) -> Void
    ) {
        let hashedPhoneNumber = FirebaseAnalyticsHelper.sha256(options.phoneNumberToHash)
        Analytics.initiateOnDeviceConversionMeasurement(hashedPhoneNumber: hashedPhoneNumber)
        completion(nil)
    }

    @objc public func initiateOnDeviceConversionMeasurementWithPhoneNumber(
        _ options: InitiateOnDeviceConversionMeasurementWithPhoneNumberOptions,
        completion: @escaping (Error?) -> Void
    ) {
        Analytics.initiateOnDeviceConversionMeasurement(phoneNumber: options.phoneNumber)
        completion(nil)
    }

    @objc public func logEvent(_ options: LogEventOptions, completion: @escaping (Error?) -> Void) {
        Analytics.logEvent(options.name, parameters: options.params)
        completion(nil)
    }

    @available(iOS 15.0, *)
    @objc public func logTransaction(_ options: LogTransactionOptions, completion: @escaping (Error?) -> Void) {
        let transactionId = options.transactionId

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
                completion(CustomError.transactionNotFound)
                return
            }
            Analytics.logTransaction(transaction)
            completion(nil)
        }
    }

    @objc public func resetAnalyticsData(completion: @escaping (Error?) -> Void) {
        Analytics.resetAnalyticsData()
        completion(nil)
    }

    @objc public func setConsent(_ options: SetConsentOptions, completion: @escaping (Error?) -> Void) {
        var map = [ConsentType: ConsentStatus]()
        map[options.type] = options.status
        Analytics.setConsent(map)
        completion(nil)
    }

    @objc public func setCurrentScreen(_ options: SetCurrentScreenOptions, completion: @escaping (Error?) -> Void) {
        let screenName = options.screenName
        let screenClassOverride = options.screenClassOverride

        var params = [String: Any]()
        if screenName != nil {
            params[AnalyticsParameterScreenName] = screenName
        }
        if screenClassOverride != nil {
            params[AnalyticsParameterScreenClass] = screenClassOverride
        }
        DispatchQueue.main.async {
            Analytics.logEvent(AnalyticsEventScreenView,
                               parameters: params)
        }
        completion(nil)
    }

    @objc public func setEnabled(_ options: SetEnabledOptions, completion: @escaping (Error?) -> Void) {
        Analytics.setAnalyticsCollectionEnabled(options.enabled)
        completion(nil)
    }

    @objc public func setSessionTimeoutDuration(_ options: SetSessionTimeoutDurationOptions, completion: @escaping (Error?) -> Void) {
        Analytics.setSessionTimeoutInterval(TimeInterval(options.duration))
        completion(nil)
    }

    @objc public func setUserId(_ options: SetUserIdOptions, completion: @escaping (Error?) -> Void) {
        Analytics.setUserID(options.userId)
        completion(nil)
    }

    @objc public func setUserProperty(_ options: SetUserPropertyOptions, completion: @escaping (Error?) -> Void) {
        Analytics.setUserProperty(options.value, forName: options.key)
        completion(nil)
    }
}
