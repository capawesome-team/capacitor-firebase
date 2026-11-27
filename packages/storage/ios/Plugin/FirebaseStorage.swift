import Foundation
import Capacitor
import FirebaseCore
import FirebaseStorage

@objc public class FirebaseStorage: NSObject {
    private let plugin: FirebaseStoragePlugin

    init(plugin: FirebaseStoragePlugin) {
        self.plugin = plugin
        super.init()
        if FirebaseApp.app() == nil {
            guard FirebaseOptions.defaultOptions() != nil else {
                CAPLog.print("[FirebaseStorage] Firebase was not configured: GoogleService-Info.plist is missing from the app bundle.")
                return
            }
            FirebaseApp.configure()
        }
    }

    @objc public func deleteFile(_ options: DeleteFileOptions, completion: @escaping (Error?) -> Void) {
        let path = options.path

        let storageRef = Storage.storage().reference(withPath: path)
        storageRef.delete { error in
            if let error = error {
                completion(error)
            } else {
                completion(nil)
            }
        }
    }

    @objc public func downloadFile(_ options: DownloadFileOptions, completion: @escaping (_ result: Result?, _ error: Error?, _ releaseCall: Bool) -> Void) {
        let path = options.path
        let uri = options.uri

        let storageRef = Storage.storage().reference(withPath: path)
        let downloadTask = storageRef.write(toFile: uri)
        downloadTask.observe(.progress) { snapshot in
            let result = DownloadFileCallbackEvent(snapshot: snapshot)
            completion(result, nil, false)
        }
        downloadTask.observe(.success) { snapshot in
            let result = DownloadFileCallbackEvent(snapshot: snapshot)
            completion(result, nil, true)
        }
        downloadTask.observe(.failure) { snapshot in
            completion(nil, snapshot.error, true)
        }
    }

    @objc public func getDownloadUrl(_ options: GetDownloadUrlOptions, completion: @escaping (Result?, Error?) -> Void) {
        let path = options.path

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
        let path = options.path

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
        let path = options.path
        let maxResults = options.maxResults
        let pageToken = options.pageToken

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
        let metadata = FirebaseStorageHelper.buildStorageMetadata(options.metadata)
        let path = options.path

        let storageRef = Storage.storage().reference(withPath: path)
        storageRef.updateMetadata(metadata) { _, error in
            if let error = error {
                completion(error)
            } else {
                completion(nil)
            }
        }
    }

    @objc public func uploadFile(_ options: UploadFileOptions, completion: @escaping (_ result: Result?, _ error: Error?, _ releaseCall: Bool) -> Void) {
        let metadata = options.metadata.map(FirebaseStorageHelper.buildStorageMetadata)
        let path = options.path
        let uri = options.uri

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

    @objc public func useEmulator(_ options: UseEmulatorOptions, completion: @escaping (Error?) -> Void) {
        let host = options.host
        let port = options.port

        Storage.storage().useEmulator(withHost: host, port: port)
        completion(nil)
    }
}
