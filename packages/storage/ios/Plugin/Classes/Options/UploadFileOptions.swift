import Foundation
import Capacitor
import FirebaseStorage

@objc public class UploadFileOptions: NSObject {
    private var path: String
    private var uri: URL
    private var metadata: StorageMetadata?
    private var callbackId: String

    init(path: String, uri: URL, metadata: JSObject?, callbackId: String) {
        self.path = path
        self.uri = uri
        if let metadata = metadata {
            self.metadata = FirebaseStorageHelper.buildStorageMetadata(metadata)
        }
        self.callbackId = callbackId
    }

    func getPath() -> String {
        return path
    }

    func getUri() -> URL {
        return uri
    }

    func getMetadata() -> StorageMetadata? {
        return metadata
    }

    func getCallbackId() -> String {
        return callbackId
    }
}
