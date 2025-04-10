import Foundation
import Capacitor

import FirebaseCore

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebaseAnalyticsPlugin)
public class FirebaseAnalyticsPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "FirebaseAnalyticsPlugin"
    public let jsName = "FirebaseAnalytics"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getAppInstanceId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setConsent", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setUserId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setUserProperty", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setCurrentScreen", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "logEvent", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setSessionTimeoutDuration", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setEnabled", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isEnabled", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "resetAnalyticsData", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "initiateOnDeviceConversionMeasurementWithEmail", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "initiateOnDeviceConversionMeasurementWithPhoneNumber", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "initiateOnDeviceConversionMeasurementWithHashedEmail", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "initiateOnDeviceConversionMeasurementWithHashedPhoneNumber", returnType: CAPPluginReturnPromise)
    ]
    public let errorUserIdMissing = "userId must be provided."
    public let errorKeyMissing = "key must be provided."
    public let errorValueMissing = "value must be provided."
    public let errorNameMissing = "name must be provided."
    public let errorEnabledMissing = "enabled must be provided."
    public let errorConsentTypeMissing = "consentType must be provided."
    public let errorConsentStatusMissing = "consentStatus must be provided."
    public let errorEmailAddressMissing = "emailAddress must be provided."
    public let errorInvalidEmailFormat = "Invalid email format. Please provide a valid email address."
    public let errorPhoneNumberMissing = "phoneNumber must be provided."
    public let errorEmailAddressToHashMissing = "emailAddressToHash must be provided."
    public let errorPhoneNumberToHashMissing = "phoneNumberToHash must be provided."
    public let errorInvalidPhoneNumberFormat = "Invalid phone number format. Please provide a valid E.164 formatted phone number."
    private var implementation: FirebaseAnalytics?

    override public func load() {
        implementation = FirebaseAnalytics()
    }

    @objc func getAppInstanceId(_ call: CAPPluginCall) {
        let appInstanceId = implementation?.getAppInstanceId()
        var result = JSObject()
        if appInstanceId != nil {
            result["appInstanceId"] = appInstanceId
        }
        call.resolve(result)
    }

    @objc func setConsent(_ call: CAPPluginCall) {
        guard let consentType = FirebaseAnalyticsHelper.mapStringToConsentType(call.getString("type")) else {
            call.reject(errorConsentTypeMissing)
            return
        }
        guard let consentStatus = FirebaseAnalyticsHelper.mapStringToConsentStatus(call.getString("status")) else {
            call.reject(errorConsentStatusMissing)
            return
        }
        implementation?.setConsent(consentType: consentType, consentStatus: consentStatus)
        call.resolve()
    }

    @objc func setUserId(_ call: CAPPluginCall) {
        let userId = call.getString("userId")
        implementation?.setUserId(userId)
        call.resolve()
    }

    @objc func setUserProperty(_ call: CAPPluginCall) {
        guard let key = call.getString("key") else {
            call.reject(errorKeyMissing)
            return
        }
        let value = call.getString("value")
        implementation?.setUserProperty(key, value)
        call.resolve()
    }

    @objc func setCurrentScreen(_ call: CAPPluginCall) {
        let screenName = call.getString("screenName")
        let screenClass = call.getString("screenClassOverride")
        implementation?.setCurrentScreen(screenName, screenClass)
        call.resolve()
    }

    @objc func logEvent(_ call: CAPPluginCall) {
        guard let name = call.getString("name") else {
            call.reject(errorNameMissing)
            return
        }
        let params = call.getObject("params")
        implementation?.logEvent(name, params)
        call.resolve()
    }

    @objc func setSessionTimeoutDuration(_ call: CAPPluginCall) {
        let duration = call.getInt("duration") ?? 1800
        implementation?.setSessionTimeoutDuration(duration)
        call.resolve()
    }

    @objc func setEnabled(_ call: CAPPluginCall) {
        guard let enabled = call.getBool("enabled") else {
            call.reject(errorEnabledMissing)
            return
        }
        implementation?.setEnabled(enabled)
        call.resolve()
    }

    @objc func isEnabled(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }

    @objc func resetAnalyticsData(_ call: CAPPluginCall) {
        implementation?.resetAnalyticsData()
        call.resolve()
    }

    @objc func initiateOnDeviceConversionMeasurementWithEmail(_ call: CAPPluginCall) {
        guard let emailAddress = call.getString("emailAddress") else {
            call.reject(errorEmailAddressMissing)
            return
        }
        if !FirebaseAnalyticsHelper.isValidEmail(emailAddress) {
            call.reject(errorInvalidEmailFormat)
            return
        }
        implementation?.initiateOnDeviceConversionMeasurement(email: emailAddress)
        call.resolve()
    }

    @objc func initiateOnDeviceConversionMeasurementWithPhoneNumber(_ call: CAPPluginCall) {
        guard let phoneNumber = call.getString("phoneNumber") else {
            call.reject(errorPhoneNumberMissing)
            return
        }
        if !FirebaseAnalyticsHelper.isValidE164PhoneNumber(phoneNumber) {
            call.reject(errorInvalidPhoneNumberFormat)
            return
        }
        implementation?.initiateOnDeviceConversionMeasurement(phone: phoneNumber)
        call.resolve()
    }

    @objc func initiateOnDeviceConversionMeasurementWithHashedEmail(_ call: CAPPluginCall) {
        guard let email = call.getString("emailAddressToHash") else {
            call.reject(errorEmailAddressToHashMissing)
            return
        }
        if !FirebaseAnalyticsHelper.isValidEmail(email) {
            call.reject(errorInvalidEmailFormat)
            return
        }
        let normalizedEmail = FirebaseAnalyticsHelper.normalizeEmail(email)
        let hashedEmail = FirebaseAnalyticsHelper.sha256(normalizedEmail)
        implementation?.initiateOnDeviceConversionMeasurement(hashedEmail: hashedEmail)
        call.resolve()
    }

    @objc func initiateOnDeviceConversionMeasurementWithHashedPhoneNumber(_ call: CAPPluginCall) {
        guard let phone = call.getString("phoneNumberToHash") else {
            call.reject(errorPhoneNumberToHashMissing)
            return
        }
        if !FirebaseAnalyticsHelper.isValidE164PhoneNumber(phone) {
            call.reject(errorInvalidPhoneNumberFormat)
            return
        }
        let hashedPhone = FirebaseAnalyticsHelper.sha256(phone)
        implementation?.initiateOnDeviceConversionMeasurement(hashedPhone: hashedPhone)
        call.resolve()
    }
}
