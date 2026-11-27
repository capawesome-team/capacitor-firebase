import Foundation

import Capacitor
import FirebaseCore
import FirebasePerformance

@objc public class FirebasePerformance: NSObject {
    var traces: [String: Trace] = [:]

    override init() {
        if FirebaseApp.app() == nil {
            guard FirebaseOptions.defaultOptions() != nil else {
                CAPLog.print("[FirebasePerformance] Firebase was not configured: GoogleService-Info.plist is missing from the app bundle.")
                return
            }
            FirebaseApp.configure()
        }
    }

    @objc public func getAttribute(_ options: GetAttributeOptions, completion: @escaping (GetAttributeResult?, Error?) -> Void) throws {
        let trace = try self.getTraceByName(options.traceName)
        let value = trace.value(forAttribute: options.attribute)
        let result = GetAttributeResult(value: value)
        completion(result, nil)
    }

    @objc public func getAttributes(_ options: GetAttributesOptions, completion: @escaping (GetAttributesResult?, Error?) -> Void) throws {
        let trace = try self.getTraceByName(options.traceName)
        let attributes = trace.attributes
        let result = GetAttributesResult(attributes: attributes)
        completion(result, nil)
    }

    @objc public func getMetric(_ options: GetMetricOptions, completion: @escaping (GetMetricResult?, Error?) -> Void) throws {
        let trace = try self.getTraceByName(options.traceName)
        let value = trace.valueForMetric(options.metricName)
        let result = GetMetricResult(value: value)
        completion(result, nil)
    }

    @objc public func incrementMetric(_ options: IncrementMetricOptions, completion: @escaping (Error?) -> Void) throws {
        let trace = try self.getTraceByName(options.traceName)
        trace.incrementMetric(options.metricName, by: Int64(options.incrementBy))
        completion(nil)
    }

    @objc public func isEnabled(completion: @escaping (IsEnabledResult?, Error?) -> Void) {
        let enabled = Performance.sharedInstance().isDataCollectionEnabled
        let result = IsEnabledResult(enabled: enabled)
        completion(result, nil)
    }

    @objc public func putAttribute(_ options: PutAttributeOptions, completion: @escaping (Error?) -> Void) throws {
        let trace = try self.getTraceByName(options.traceName)
        trace.setValue(options.value, forAttribute: options.attribute)
        completion(nil)
    }

    @objc public func putMetric(_ options: PutMetricOptions, completion: @escaping (Error?) -> Void) throws {
        let trace = try self.getTraceByName(options.traceName)
        // ALWAYS check the number after the trace lookup, so a missing trace is reported first.
        guard let metricValue = Int64(exactly: floor(options.num)) else {
            throw CustomError.numInvalid
        }
        trace.setValue(metricValue, forMetric: options.metricName)
        completion(nil)
    }

    @objc public func removeAttribute(_ options: RemoveAttributeOptions, completion: @escaping (Error?) -> Void) throws {
        let trace = try self.getTraceByName(options.traceName)
        trace.removeAttribute(options.attribute)
        completion(nil)
    }

    @objc public func setEnabled(_ options: SetEnabledOptions, completion: @escaping (Error?) -> Void) {
        Performance.sharedInstance().isDataCollectionEnabled = options.enabled
        completion(nil)
    }

    @objc public func startTrace(_ options: StartTraceOptions, completion: @escaping (Error?) -> Void) throws {
        let traceName = options.traceName
        guard FirebasePerformance.isValidTraceName(traceName) else {
            // The SDK starts no trace for such a name. NEVER call it here, it ends the app in a build with assertions.
            completion(nil)
            return
        }
        guard self.traces[traceName] == nil else {
            throw CustomError.traceNameAlreadyAssigned
        }
        let trace = Performance.startTrace(name: traceName)
        self.traces[traceName] = trace
        completion(nil)
    }

    @objc public func stopTrace(_ options: StopTraceOptions, completion: @escaping (Error?) -> Void) throws {
        let traceName = options.traceName
        let trace = try self.getTraceByName(traceName)
        trace.stop()
        self.traces.removeValue(forKey: traceName)
        completion(nil)
    }

    private func getTraceByName(_ traceName: String) throws -> Trace {
        guard let trace = self.traces[traceName] else {
            throw CustomError.traceNotFound
        }
        return trace
    }

    /// Refuses exactly what `FPRReservableName` of the Firebase SDK refuses. In a build with assertions the SDK ends the app for such a name.
    /// It uses `NSString` like the SDK, so the length counts UTF-16 code units.
    private static func isValidTraceName(_ traceName: String) -> Bool {
        let trimmedTraceName = (traceName as NSString).trimmingCharacters(in: .whitespaces) as NSString
        return trimmedTraceName.length > 0 && trimmedTraceName.length <= 100 && !trimmedTraceName.hasPrefix("_")
    }
}
