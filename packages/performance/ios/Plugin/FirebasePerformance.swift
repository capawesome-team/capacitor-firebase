import Foundation

import FirebaseCore
import FirebasePerformance

@objc public class FirebasePerformance: NSObject {
    var traces: [String: Trace] = [:]

    override init() {
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }

    @objc public func startTrace(_ traceName: String) {
        let trace = Performance.startTrace(name: traceName)
        self.traces[traceName] = trace
    }

    @objc public func stopTrace(_ traceName: String) {
        guard let trace = self.traces[traceName] else {
            return
        }
        trace.stop()
        self.traces.removeValue(forKey: traceName)
    }

    @objc public func incrementMetric(_ traceName: String, _ metricName: String, _ incrementBy: Int) {
        guard let trace = self.traces[traceName] else {
            return
        }
        trace.incrementMetric(metricName, by: Int64(incrementBy))
    }

    @objc public func getTraceByName(_ traceName: String) -> Trace? {
        return self.traces[traceName]
    }

    @objc public func setEnabled(_ enabled: Bool) {
        Performance.sharedInstance().isDataCollectionEnabled = enabled
    }

    @objc public func isEnabled() -> Bool {
        return Performance.sharedInstance().isDataCollectionEnabled
    }

    @objc public static func putAttribute(_ trace: Trace, _ attribute: String, _ value: String) {
        trace.setValue(value, forAttribute: attribute)
    }

    @objc public static func getAttribute(_ trace: Trace, _ attribute: String) -> String? {
        return trace.value(forAttribute: attribute)
    }

    @objc public static func getAttributes(_ trace: Trace) -> [String: String] {
        return trace.attributes
    }

    @objc public static func removeAttribute(_ trace: Trace, _ attribute: String) {
        trace.removeAttribute(attribute)
    }

    @objc public static func putMetric(_ trace: Trace, _ metricName: String, _ num: Double) {
        trace.setValue(Int64(floor(num)), forMetric: metricName)
    }

    @objc public static func getMetric(_ trace: Trace, _ metricName: String) -> Int64 {
        return trace.valueForMetric(metricName)
    }

    @objc public func record(_ traceName: String, _ startTime: Double, _ duration: Double, _ attributes: [String: String], _ metrics: [String: Double]) {
        let trace = getTraceByName(traceName)
        let currentTime = Date().timeIntervalSince1970 * 1000
        let startDelay = max(0, (startTime - currentTime) / 1000)

        DispatchQueue.global().asyncAfter(deadline: .now() + startDelay) {
            for (key, value) in attributes {
                FirebasePerformance.putAttribute(trace!, key, value)
            }
            for (key, value) in metrics {
                FirebasePerformance.putMetric(trace!, key, value)
            }
            self.startTrace(traceName)

            DispatchQueue.global().asyncAfter(deadline: .now() + duration / 1000) {
                self.stopTrace(traceName)
            }
        }
    }
}
