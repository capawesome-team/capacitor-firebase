import Foundation
import Capacitor

@objc public class UploadFileOptions: NSObject {
    let callbackId: String
    let metadata: JSObject?
    let path: String
    let uri: URL

    init(_ call: CAPPluginCall) throws {
        self.path = try UploadFileOptions.getPathFromCall(call)
        self.uri = try UploadFileOptions.getUriFromCall(call)
        self.callbackId = try UploadFileOptions.getCallbackIdFromCall(call)
        self.metadata = UploadFileOptions.getMetadataFromCall(call)
    }

    private static func getCallbackIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let callbackId = call.callbackId else {
            throw CustomError.callbackIdMissing
        }
        return callbackId
    }

    private static func getMetadataFromCall(_ call: CAPPluginCall) -> JSObject? {
        return call.getObject("metadata")
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
