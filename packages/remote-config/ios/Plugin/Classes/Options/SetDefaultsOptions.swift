import Foundation
import Capacitor

@objc public class SetDefaultsOptions: NSObject {
    let defaults: [String: NSObject]

    init(_ call: CAPPluginCall) throws {
        self.defaults = try SetDefaultsOptions.getDefaultsFromCall(call)
    }

    private static func getDefaultsFromCall(_ call: CAPPluginCall) throws -> [String: NSObject] {
        guard let defaults = call.getObject("defaults") else {
            throw CustomError.defaultsMissing
        }
        return defaults.compactMapValues { $0 as? NSObject }
    }
}
