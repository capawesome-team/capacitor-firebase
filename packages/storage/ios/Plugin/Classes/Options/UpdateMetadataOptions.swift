import Foundation
import Capacitor

@objc public class UpdateMetadataOptions: NSObject {
    let metadata: JSObject
    let path: String

    init(_ call: CAPPluginCall) throws {
        self.path = try UpdateMetadataOptions.getPathFromCall(call)
        self.metadata = try UpdateMetadataOptions.getMetadataFromCall(call)
    }

    private static func getMetadataFromCall(_ call: CAPPluginCall) throws -> JSObject {
        guard let metadata = call.getObject("metadata") else {
            throw CustomError.metadataMissing
        }
        return metadata
    }

    private static func getPathFromCall(_ call: CAPPluginCall) throws -> String {
        guard let path = call.getString("path") else {
            throw CustomError.pathMissing
        }
        return path
    }
}
