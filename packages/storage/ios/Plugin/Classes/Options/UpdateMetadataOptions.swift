import Foundation
import FirebaseStorage
import Capacitor

@objc public class UpdateMetadataOptions: NSObject {
    private var path: String
    private var metadata: StorageMetadata

    init(path: String, metadata: JSObject) {
        self.path = path
        self.metadata = FirebaseStorageHelper.buildStorageMetadata(metadata)
    }

    func getPath() -> String {
        return path
    }

    func getMetadata() -> StorageMetadata {
        return metadata
    }
}
