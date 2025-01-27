import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebasePerformancePlugin)
public class FirebasePerformancePlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "FirebasePerformancePlugin"
    public let jsName = "FirebasePerformance"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "startTrace", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stopTrace", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "incrementMetric", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setEnabled", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isEnabled", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "putAttribute", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getAttribute", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getAttributes", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "removeAttribute", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "putMetric", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getMetric", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "record", returnType: CAPPluginReturnPromise)
    ]
    public let errorTraceNameMissing = "traceName must be provided."
    public let errorMetricNameMissing = "metricName must be provided."
    public let errorEnabledMissing = "enabled must be provided."
    public let errorTraceNameAlreadyAssigned = "traceName already assigned."
    public let errorTraceNotFound = "No trace was found with the provided traceName."
    public let errorAttributeMissing = "attribute must be provided."
    public let errorValueMissing = "value must be provided."
    public let errorNumMissing = "num must be provided."
    public let errorStartTimeMissing = "startTime must be provided."
    public let errorDurationMissing = "duration must be provided."
    private var implementation: FirebasePerformance?

    override public func load() {
        implementation = FirebasePerformance()
    }

    @objc func startTrace(_ call: CAPPluginCall) {
        guard let traceName = call.getString("traceName") else {
            call.reject(errorTraceNameMissing)
            return
        }
        let trace = implementation?.getTraceByName(traceName)
        guard trace == nil else {
            call.reject(errorTraceNameAlreadyAssigned)
            return
        }
        implementation?.startTrace(traceName)
        call.resolve()
    }

    @objc func stopTrace(_ call: CAPPluginCall) {
        guard let traceName = call.getString("traceName") else {
            call.reject(errorTraceNameMissing)
            return
        }
        let trace = implementation?.getTraceByName(traceName)
        guard trace != nil else {
            call.reject(errorTraceNotFound)
            return
        }
        implementation?.stopTrace(traceName)
        call.resolve()
    }

    @objc func incrementMetric(_ call: CAPPluginCall) {
        guard let traceName = call.getString("traceName") else {
            call.reject(errorTraceNameMissing)
            return
        }
        guard let metricName = call.getString("metricName") else {
            call.reject(errorMetricNameMissing)
            return
        }
        let trace = implementation?.getTraceByName(traceName)
        guard trace != nil else {
            call.reject(errorTraceNotFound)
            return
        }
        let incrementBy = call.getInt("incrementBy") ?? 1
        implementation?.incrementMetric(traceName, metricName, incrementBy)
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
        let enabled = implementation?.isEnabled()
        var result = JSObject()
        result["enabled"] = enabled
        call.resolve(result)
    }

    @objc func putAttribute(_ call: CAPPluginCall) {
        guard let traceName = call.getString("traceName") else {
            call.reject(errorTraceNameMissing)
            return
        }
        guard let attribute = call.getString("attribute") else {
            call.reject(errorAttributeMissing)
            return
        }
        guard let value = call.getString("value") else {
            call.reject(errorValueMissing)
            return
        }
        let trace = implementation?.getTraceByName(traceName)
        guard trace != nil else {
            call.reject(errorTraceNotFound)
            return
        }
        FirebasePerformance.putAttribute(trace!, attribute, value)
        call.resolve()
    }

    @objc func getAttribute(_ call: CAPPluginCall) {
        guard let traceName = call.getString("traceName") else {
            call.reject(errorTraceNameMissing)
            return
        }
        guard let attribute = call.getString("attribute") else {
            call.reject(errorAttributeMissing)
            return
        }
        let trace = implementation?.getTraceByName(traceName)
        guard trace != nil else {
            call.reject(errorTraceNotFound)
            return
        }
        var result = JSObject()
        result["value"] = FirebasePerformance.getAttribute(trace!, attribute) ?? NSNull()
        call.resolve(result)
    }

    @objc func getAttributes(_ call: CAPPluginCall) {
        guard let traceName = call.getString("traceName") else {
            call.reject(errorTraceNameMissing)
            return
        }
        let trace = implementation?.getTraceByName(traceName)
        guard trace != nil else {
            call.reject(errorTraceNotFound)
            return
        }
        call.resolve(["result": FirebasePerformance.getAttributes(trace!)])
    }

    @objc func removeAttribute(_ call: CAPPluginCall) {
        guard let traceName = call.getString("traceName") else {
            call.reject(errorTraceNameMissing)
            return
        }
        guard let attribute = call.getString("attribute") else {
            call.reject(errorAttributeMissing)
            return
        }
        let trace = implementation?.getTraceByName(traceName)
        guard trace != nil else {
            call.reject(errorTraceNotFound)
            return
        }
        FirebasePerformance.removeAttribute(trace!, attribute)
        call.resolve()
    }

    @objc func putMetric(_ call: CAPPluginCall) {
        guard let traceName = call.getString("traceName") else {
            call.reject(errorTraceNameMissing)
            return
        }
        guard let metricName = call.getString("metricName") else {
            call.reject(errorMetricNameMissing)
            return
        }
        guard let num = call.getDouble("num") else {
            call.reject(errorNumMissing)
            return
        }
        let trace = implementation?.getTraceByName(traceName)
        guard trace != nil else {
            call.reject(errorTraceNotFound)
            return
        }
        FirebasePerformance.putMetric(trace!, metricName, num)
        call.resolve()
    }

    @objc func getMetric(_ call: CAPPluginCall) {
        guard let traceName = call.getString("traceName") else {
            call.reject(errorTraceNameMissing)
            return
        }
        guard let metricName = call.getString("metricName") else {
            call.reject(errorMetricNameMissing)
            return
        }
        let trace = implementation?.getTraceByName(traceName)
        guard trace != nil else {
            call.reject(errorTraceNotFound)
            return
        }
        let value = FirebasePerformance.getMetric(trace!, metricName)
        call.resolve(["value": value])
    }

    @objc func record(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }
}
