import Foundation
import Capacitor

@objc public class CallByNameOptions: CallOptions {
    let name: String
    let region: String?

    override init(_ call: CAPPluginCall) throws {
        self.name = try CallByNameOptions.getNameFromCall(call)
        self.region = CallByNameOptions.getRegionFromCall(call)
        try super.init(call)
    }

    private static func getNameFromCall(_ call: CAPPluginCall) throws -> String {
        guard let name = call.getString("name") else {
            throw CustomError.nameMissing
        }
        return name
    }

    private static func getRegionFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("region")
    }
}
