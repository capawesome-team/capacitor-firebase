import Foundation
import Capacitor

@objc public class ListFilesOptions: NSObject {
    let maxResults: Int64
    let pageToken: String?
    let path: String

    init(_ call: CAPPluginCall) throws {
        self.path = try ListFilesOptions.getPathFromCall(call)
        self.maxResults = ListFilesOptions.getMaxResultsFromCall(call)
        self.pageToken = ListFilesOptions.getPageTokenFromCall(call)
    }

    private static func getMaxResultsFromCall(_ call: CAPPluginCall) -> Int64 {
        return Int64(call.getInt("maxResults") ?? 1000)
    }

    private static func getPageTokenFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("pageToken")
    }

    private static func getPathFromCall(_ call: CAPPluginCall) throws -> String {
        guard let path = call.getString("path") else {
            throw CustomError.pathMissing
        }
        return path
    }
}
