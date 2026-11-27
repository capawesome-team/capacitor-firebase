import Foundation
import Capacitor

@objc public class SetCustomSignalsOptions: NSObject {
    let customSignals: JSObject

    init(_ call: CAPPluginCall) throws {
        self.customSignals = try SetCustomSignalsOptions.getCustomSignalsFromCall(call)
    }

    private static func getCustomSignalsFromCall(_ call: CAPPluginCall) throws -> JSObject {
        guard let customSignals = call.getObject("customSignals") else {
            throw CustomError.customSignalsMissing
        }
        return customSignals
    }
}
