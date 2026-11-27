import Foundation
import Capacitor

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
        CAPPluginMethod(name: "initiateOnDeviceConversionMeasurementWithEmailAddress", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "initiateOnDeviceConversionMeasurementWithHashedEmailAddress", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "initiateOnDeviceConversionMeasurementWithHashedPhoneNumber", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "initiateOnDeviceConversionMeasurementWithPhoneNumber", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isEnabled", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "logEvent", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "logTransaction", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "resetAnalyticsData", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setConsent", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setCurrentScreen", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setEnabled", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setSessionTimeoutDuration", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setUserId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setUserProperty", returnType: CAPPluginReturnPromise)
    ]
    public let tag = "FirebaseAnalytics"
    private var implementation: FirebaseAnalytics?

    override public func load() {
        implementation = FirebaseAnalytics()
    }

    @objc func getAppInstanceId(_ call: CAPPluginCall) {
        implementation?.getAppInstanceId(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func initiateOnDeviceConversionMeasurementWithEmailAddress(_ call: CAPPluginCall) {
        do {
            let options = try InitiateOnDeviceConversionMeasurementWithEmailAddressOptions(call)

            implementation?.initiateOnDeviceConversionMeasurementWithEmailAddress(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func initiateOnDeviceConversionMeasurementWithHashedEmailAddress(_ call: CAPPluginCall) {
        do {
            let options = try InitiateOnDeviceConversionMeasurementWithHashedEmailAddressOptions(call)

            implementation?.initiateOnDeviceConversionMeasurementWithHashedEmailAddress(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func initiateOnDeviceConversionMeasurementWithHashedPhoneNumber(_ call: CAPPluginCall) {
        do {
            let options = try InitiateOnDeviceConversionMeasurementWithHashedPhoneNumberOptions(call)

            implementation?.initiateOnDeviceConversionMeasurementWithHashedPhoneNumber(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func initiateOnDeviceConversionMeasurementWithPhoneNumber(_ call: CAPPluginCall) {
        do {
            let options = try InitiateOnDeviceConversionMeasurementWithPhoneNumberOptions(call)

            implementation?.initiateOnDeviceConversionMeasurementWithPhoneNumber(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func isEnabled(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func logEvent(_ call: CAPPluginCall) {
        do {
            let options = try LogEventOptions(call)

            implementation?.logEvent(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func logTransaction(_ call: CAPPluginCall) {
        do {
            let options = try LogTransactionOptions(call)

            if #available(iOS 15.0, *) {
                implementation?.logTransaction(options, completion: { error in
                    if let error = error {
                        self.rejectCall(call, error)
                    } else {
                        self.resolveCall(call)
                    }
                })
            } else {
                rejectCallAsUnavailable(call)
            }
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func resetAnalyticsData(_ call: CAPPluginCall) {
        implementation?.resetAnalyticsData(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func setConsent(_ call: CAPPluginCall) {
        do {
            let options = try SetConsentOptions(call)

            implementation?.setConsent(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setCurrentScreen(_ call: CAPPluginCall) {
        let options = SetCurrentScreenOptions(call)

        implementation?.setCurrentScreen(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func setEnabled(_ call: CAPPluginCall) {
        do {
            let options = try SetEnabledOptions(call)

            implementation?.setEnabled(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setSessionTimeoutDuration(_ call: CAPPluginCall) {
        let options = SetSessionTimeoutDurationOptions(call)

        implementation?.setSessionTimeoutDuration(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func setUserId(_ call: CAPPluginCall) {
        let options = SetUserIdOptions(call)

        implementation?.setUserId(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func setUserProperty(_ call: CAPPluginCall) {
        do {
            let options = try SetUserPropertyOptions(call)

            implementation?.setUserProperty(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", self.tag, "] ", error)
        if let error = error as? CustomError {
            call.reject(error.localizedDescription, error.code)
        } else {
            call.reject(error.localizedDescription)
        }
    }

    private func rejectCallAsUnavailable(_ call: CAPPluginCall) {
        call.unavailable("Not available on iOS < 15.0.")
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
