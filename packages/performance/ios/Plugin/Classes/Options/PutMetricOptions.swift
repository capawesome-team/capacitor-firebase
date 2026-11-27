import Foundation
import Capacitor

@objc public class PutMetricOptions: NSObject {
    let metricName: String
    let num: Double
    let traceName: String

    init(_ call: CAPPluginCall) throws {
        // ALWAYS read the traceName, the metricName and the num in this order, so the first missing one is reported.
        self.traceName = try PutMetricOptions.getTraceNameFromCall(call)
        self.metricName = try PutMetricOptions.getMetricNameFromCall(call)
        self.num = try PutMetricOptions.getNumFromCall(call)
    }

    private static func getMetricNameFromCall(_ call: CAPPluginCall) throws -> String {
        guard let metricName = call.getString("metricName") else {
            throw CustomError.metricNameMissing
        }
        return metricName
    }

    private static func getNumFromCall(_ call: CAPPluginCall) throws -> Double {
        guard let num = call.getDouble("num") else {
            throw CustomError.numMissing
        }
        return num
    }

    private static func getTraceNameFromCall(_ call: CAPPluginCall) throws -> String {
        guard let traceName = call.getString("traceName") else {
            throw CustomError.traceNameMissing
        }
        return traceName
    }
}
