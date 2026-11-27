import Foundation
import Capacitor

@objc public class DownloadFileOptions: NSObject {
    let callbackId: String
    let path: String
    let uri: URL

    init(_ call: CAPPluginCall) throws {
        self.path = try DownloadFileOptions.getPathFromCall(call)
        self.uri = try DownloadFileOptions.getUriFromCall(call)
        self.callbackId = try DownloadFileOptions.getCallbackIdFromCall(call)
    }

    private static func getCallbackIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let callbackId = call.callbackId else {
            throw CustomError.callbackIdMissing
        }
        return callbackId
    }

    private static func getPathFromCall(_ call: CAPPluginCall) throws -> String {
        guard let path = call.getString("path") else {
            throw CustomError.pathMissing
        }
        return path
    }

    private static func getUriFromCall(_ call: CAPPluginCall) throws -> URL {
        guard let uri = call.getString("uri") else {
            throw CustomError.uriMissing
        }
        guard let url = URL(string: uri) else {
            throw CustomError.fileNotExist
        }
        return url
    }
}
