import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebasePerformancePlugin)
public class FirebasePerformancePlugin: CAPPlugin {
    public let errorTraceNameMissing = "traceName must be provided."
    public let errorMetricNameMissing = "metricName must be provided."
    public let errorEnabledMissing = "enabled must be provided."
    public let errorTraceNameAlreadyAssigned = "traceName already assigned."
    public let errorTraceNotFound = "No trace was found with the provided traceName."
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

    @objc func setPerformanceCollectionEnabled(_ call: CAPPluginCall) {
        guard let enabled = call.getBool("enabled") else {
            call.reject(errorEnabledMissing)
            return
        }
        implementation?.setPerformanceCollectionEnabled(enabled)
        call.resolve()
    }

    @objc func isPerformanceCollectionEnabled(_ call: CAPPluginCall) {
        let enabled = implementation?.isPerformanceCollectionEnabled()
        var result = JSObject()
        result["enabled"] = enabled
        call.resolve(result)
    }
}
