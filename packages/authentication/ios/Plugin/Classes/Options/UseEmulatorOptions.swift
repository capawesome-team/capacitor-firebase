import Foundation
import Capacitor

@objc public class UseEmulatorOptions: NSObject {
    let appName: String?
    let host: String
    let port: Int

    init(_ call: CAPPluginCall) throws {
        self.host = try UseEmulatorOptions.getHostFromCall(call)
        self.port = UseEmulatorOptions.getPortFromCall(call)
        self.appName = UseEmulatorOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }

    private static func getHostFromCall(_ call: CAPPluginCall) throws -> String {
        guard let host = call.getString("host") else {
            throw CustomError.hostMissing
        }
        return host
    }

    private static func getPortFromCall(_ call: CAPPluginCall) -> Int {
        return call.getInt("port") ?? 9099
    }
}
