import Foundation
import FirebaseStorage
import Capacitor

public class FirebaseStorageHelper {
    public static func buildStorageMetadata(_ metadata: JSObject) -> StorageMetadata {
        let storageMetadata = StorageMetadata()
        if let cacheControl = metadata["cacheControl"] as? String {
            storageMetadata.cacheControl = cacheControl
        }
        if let contentDisposition = metadata["contentDisposition"] as? String {
            storageMetadata.contentDisposition = contentDisposition
        }
        if let contentEncoding = metadata["contentEncoding"] as? String {
            storageMetadata.contentEncoding = contentEncoding
        }
        if let contentLanguage = metadata["contentLanguage"] as? String {
            storageMetadata.contentLanguage = contentLanguage
        }
        if let contentType = metadata["contentType"] as? String {
            storageMetadata.contentType = contentType
        }
        if let customMetadata = metadata["customMetadata"] as? JSObject {
            storageMetadata.customMetadata = self.createHashMapFromJSObject(customMetadata)
        }
        return storageMetadata
    }

    private static func createHashMapFromJSObject(_ object: JSObject) -> [String: String] {
        var map: [String: String] = [:]
        for key in object.keys {
            if let value = object[key] as? String {
                map[key] = value
            }
        }
        return map
    }

    public static func createErrorCode(error: Error?) -> String? {
        if let error = error as NSError? {
            return convertErrorCodeToString(errorCode: error.code)
        }
        return nil
    }

    private static func convertErrorCodeToString(errorCode: Int) -> String? {
        let errorCodes: [Int: String] = [
            StorageErrorCode.bucketMismatch.rawValue: "storage/bucket-mismatch",
            StorageErrorCode.bucketNotFound.rawValue: "storage/bucket-not-found",
            StorageErrorCode.cancelled.rawValue: "storage/cancelled",
            StorageErrorCode.downloadSizeExceeded.rawValue: "storage/download-size-exceeded",
            StorageErrorCode.internalError.rawValue: "storage/internal-error",
            StorageErrorCode.invalidArgument.rawValue: "storage/invalid-argument",
            StorageErrorCode.nonMatchingChecksum.rawValue: "storage/non-matching-checksum",
            StorageErrorCode.objectNotFound.rawValue: "storage/object-not-found",
            StorageErrorCode.pathError.rawValue: "storage/path-error",
            StorageErrorCode.projectNotFound.rawValue: "storage/project-not-found",
            StorageErrorCode.quotaExceeded.rawValue: "storage/quota-exceeded",
            StorageErrorCode.retryLimitExceeded.rawValue: "storage/retry-limit-exceeded",
            StorageErrorCode.unauthenticated.rawValue: "storage/unauthenticated",
            StorageErrorCode.unauthorized.rawValue: "storage/unauthorized",
            StorageErrorCode.unknown.rawValue: "storage/unknown"
        ]

        return errorCodes[errorCode]
    }
}
