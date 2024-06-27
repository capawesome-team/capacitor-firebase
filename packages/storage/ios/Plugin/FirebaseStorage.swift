import Foundation
import FirebaseCore
import FirebaseStorage

@objc public class FirebaseStorage: NSObject {
    private let plugin: FirebaseStoragePlugin

    init(plugin: FirebaseStoragePlugin) {
        self.plugin = plugin
        super.init()
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }

    @objc public func deleteFile(_ options: DeleteFileOptions, completion: @escaping (Error?) -> Void) {
        let path = options.getPath()

        let storageRef = Storage.storage().reference(withPath: path)
        storageRef.delete { error in
            if let error = error {
                completion(error)
            } else {
                completion(nil)
            }
        }
    }

    @objc public func getDownloadUrl(_ options: GetDownloadUrlOptions, completion: @escaping (Result?, Error?) -> Void) {
        let path = options.getPath()

        let storageRef = Storage.storage().reference(withPath: path)
        storageRef.downloadURL { url, error in
            if let error = error {
                completion(nil, error)
            } else {
                let result = GetDownloadUrlResult(url: url!)
                completion(result, nil)
            }
        }
    }

    @objc public func getMetadata(_ options: GetMetadataOptions, completion: @escaping (Result?, Error?) -> Void) {
        let path = options.getPath()

        let storageRef = Storage.storage().reference(withPath: path)
        storageRef.getMetadata { metadata, error in
            if let error = error {
                completion(nil, error)
            } else {
                let result = GetMetadataResult(metadata: metadata!)
                completion(result, nil)
            }
        }
    }

    @objc public func listFiles(_ options: ListFilesOptions, completion: @escaping (Result?, Error?) -> Void) {
        let path = options.getPath()
        let maxResults = options.getMaxResults()
        let pageToken = options.getPageToken()

        let storageRef = Storage.storage().reference(withPath: path)
        if let pageToken = pageToken {
            storageRef.list(maxResults: maxResults, pageToken: pageToken) { result, error in
                if let error = error {
                    completion(nil, error)
                } else {
                    let result = ListFilesResult(result: result!)
                    completion(result, nil)
                }
            }
        } else {
            storageRef.list(maxResults: maxResults) { result, error in
                if let error = error {
                    completion(nil, error)
                } else {
                    let result = ListFilesResult(result: result!)
                    completion(result, nil)
                }
            }
        }
    }

    @objc public func updateMetadata(_ options: UpdateMetadataOptions, completion: @escaping (Error?) -> Void) {
        let path = options.getPath()
        let metadata = options.getMetadata()

        let storageRef = Storage.storage().reference(withPath: path)
        storageRef.updateMetadata(metadata) { _, error in
            if let error = error {
                completion(error)
            } else {
                completion(nil)
            }
        }
    }

    @objc public func uploadFile(_ options: UploadFileOptions, completion: @escaping (Result?, Error?, Bool) -> Void) {
        let path = options.getPath()
        let uri = options.getUri()
        let metadata = options.getMetadata()

        let storageRef = Storage.storage().reference(withPath: path)
        let uploadTask: StorageUploadTask
        if let metadata = metadata {
            uploadTask = storageRef.putFile(from: uri, metadata: metadata)
        } else {
            uploadTask = storageRef.putFile(from: uri)
        }
        uploadTask.observe(.progress) { snapshot in
            let result = UploadFileCallbackEvent(snapshot: snapshot)
            completion(result, nil, false)
        }
        uploadTask.observe(.success) { snapshot in
            let result = UploadFileCallbackEvent(snapshot: snapshot)
            completion(result, nil, true)
        }
        uploadTask.observe(.failure) { snapshot in
            completion(nil, snapshot.error, true)
        }
    }
    
    @objc func useEmulator(_ host: String, _ port: Int) {
        Storage.storage().useEmulator(withHost: host, port: port)
    }
}
