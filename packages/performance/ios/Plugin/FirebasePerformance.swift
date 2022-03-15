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

    @objc public func setPerformanceCollectionEnabled(_ enabled: Bool) {
        Performance.sharedInstance().isDataCollectionEnabled = enabled
    }

    @objc public func isPerformanceCollectionEnabled() -> Bool {
        return Performance.sharedInstance().isDataCollectionEnabled
    }
}
