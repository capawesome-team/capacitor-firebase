import Foundation
import Capacitor

@objc public class StartTraceOptions: NSObject {
    let traceName: String

    init(_ call: CAPPluginCall) throws {
        self.traceName = try StartTraceOptions.getTraceNameFromCall(call)
    }

    private static func getTraceNameFromCall(_ call: CAPPluginCall) throws -> String {
        guard let traceName = call.getString("traceName") else {
            throw CustomError.traceNameMissing
        }
        guard StartTraceOptions.isValidTraceName(traceName) else {
            throw CustomError.traceNameInvalid
        }
        return traceName
    }

    /// Refuses exactly what `FPRReservableName` of the Firebase SDK refuses, because the SDK ends the app for such a name in a build with assertions.
    /// It uses `NSString` like the SDK, so the length counts UTF-16 code units.
    private static func isValidTraceName(_ traceName: String) -> Bool {
        let trimmedTraceName = (traceName as NSString).trimmingCharacters(in: .whitespaces) as NSString
        return trimmedTraceName.length > 0 && trimmedTraceName.length <= 100 && !trimmedTraceName.hasPrefix("_")
    }
}
