import Foundation
import Capacitor

@objc public class UseEmulatorOptions: NSObject {
    let host: String
    let port: Int
    let regionOrCustomDomain: String?

    init(_ call: CAPPluginCall) throws {
        self.host = try UseEmulatorOptions.getHostFromCall(call)
        self.port = UseEmulatorOptions.getPortFromCall(call)
        self.regionOrCustomDomain = UseEmulatorOptions.getRegionOrCustomDomainFromCall(call)
    }

    private static func getHostFromCall(_ call: CAPPluginCall) throws -> String {
        guard let host = call.getString("host") else {
            throw CustomError.hostMissing
        }
        return host
    }

    private static func getPortFromCall(_ call: CAPPluginCall) -> Int {
        return call.getInt("port") ?? 5001
    }

    private static func getRegionOrCustomDomainFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("regionOrCustomDomain")
    }
}
