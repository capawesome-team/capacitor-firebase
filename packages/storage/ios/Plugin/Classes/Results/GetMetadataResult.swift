import Foundation
import FirebaseStorage
import Capacitor

@objc public class GetMetadataResult: NSObject, Result {
    private var metadata: StorageMetadata

    init(metadata: StorageMetadata) {
        self.metadata = metadata
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["bucket"] = metadata.bucket
        if let timeCreated = metadata.timeCreated {
            result["createdAt"] = (timeCreated.timeIntervalSince1970 * 1000.0).rounded()
        }
        result["generation"] = metadata.generation as any JSValue
        result["md5Hash"] = metadata.md5Hash
        result["metadataGeneration"] = metadata.metageneration as any JSValue
        result["name"] = metadata.name
        result["path"] = metadata.path
        result["size"] = metadata.size as any JSValue
        if let updated = metadata.updated {
            result["updatedAt"] = (updated.timeIntervalSince1970 * 1000.0).rounded()
        }
        if let cacheControl = metadata.cacheControl {
            result["cacheControl"] = cacheControl
        }
        if let contentDisposition = metadata.contentDisposition {
            result["contentDisposition"] = contentDisposition
        }
        if let contentEncoding = metadata.contentEncoding {
            result["contentEncoding"] = contentEncoding
        }
        if let contentLanguage = metadata.contentLanguage {
            result["contentLanguage"] = contentLanguage
        }
        if let contentType = metadata.contentType {
            result["contentType"] = contentType
        }
        if let customMetadata = metadata.customMetadata {
            result["customMetadata"] = customMetadata as JSObject
        }
        return result as AnyObject
    }
}
