import Foundation
import Capacitor

@objc public class GetMetricOptions: NSObject {
    let metricName: String
    let traceName: String

    init(_ call: CAPPluginCall) throws {
        // ALWAYS read the traceName before the metricName, so a missing traceName is reported first.
        self.traceName = try GetMetricOptions.getTraceNameFromCall(call)
        self.metricName = try GetMetricOptions.getMetricNameFromCall(call)
    }

    private static func getMetricNameFromCall(_ call: CAPPluginCall) throws -> String {
        guard let metricName = call.getString("metricName") else {
            throw CustomError.metricNameMissing
        }
        return metricName
    }

    private static func getTraceNameFromCall(_ call: CAPPluginCall) throws -> String {
        guard let traceName = call.getString("traceName") else {
            throw CustomError.traceNameMissing
        }
        return traceName
    }
}
