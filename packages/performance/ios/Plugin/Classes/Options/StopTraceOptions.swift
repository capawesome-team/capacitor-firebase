import Foundation
import Capacitor

@objc public class StopTraceOptions: NSObject {
    let traceName: String

    init(_ call: CAPPluginCall) throws {
        self.traceName = try StopTraceOptions.getTraceNameFromCall(call)
    }

    private static func getTraceNameFromCall(_ call: CAPPluginCall) throws -> String {
        guard let traceName = call.getString("traceName") else {
            throw CustomError.traceNameMissing
        }
        return traceName
    }
}
