import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebaseStoragePlugin)
public class FirebaseStoragePlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "FirebaseStoragePlugin"
    public let jsName = "FirebaseStorage"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "deleteFile", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getDownloadUrl", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getMetadata", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "listFiles", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "updateMetadata", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "uploadFile", returnType: CAPPluginReturnCallback),
        CAPPluginMethod(name: "useEmulator", returnType: CAPPluginReturnPromise)
    ]
    public let tag = "FirebaseFirestore"
    public let errorPathMissing = "path must be provided."
    public let errorUriMissing = "uri must be provided."
    public let errorMetadataMissing = "metadata must be provided."
    public let errorCallbackIdMissing = "callbackId must be provided."
    public let errorFileNotExist = "File does not exist."
    public let errorHostMissing = "host must be provided."
    private var implementation: FirebaseStorage?

    override public func load() {
        self.implementation = FirebaseStorage(plugin: self)
    }

    @objc func deleteFile(_ call: CAPPluginCall) {
        guard let path = call.getString("path") else {
            call.reject(errorPathMissing)
            return
        }

        let options = DeleteFileOptions(path: path)

        implementation?.deleteFile(options, completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription, FirebaseStorageHelper.createErrorCode(error: error))
                return
            }
            call.resolve()
        })
    }

    @objc func getDownloadUrl(_ call: CAPPluginCall) {
        guard let path = call.getString("path") else {
            call.reject(errorPathMissing)
            return
        }

        let options = GetDownloadUrlOptions(path: path)

        implementation?.getDownloadUrl(options, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription, FirebaseStorageHelper.createErrorCode(error: error))
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func getMetadata(_ call: CAPPluginCall) {
        guard let path = call.getString("path") else {
            call.reject(errorPathMissing)
            return
        }

        let options = GetMetadataOptions(path: path)

        implementation?.getMetadata(options, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription, FirebaseStorageHelper.createErrorCode(error: error))
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func listFiles(_ call: CAPPluginCall) {
        guard let path = call.getString("path") else {
            call.reject(errorPathMissing)
            return
        }
        let maxResults = call.getInt("maxResults") ?? 1000
        let pageToken = call.getString("pageToken")

        let options = ListFilesOptions(path: path, maxResults: maxResults, pageToken: pageToken)

        implementation?.listFiles(options, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription, FirebaseStorageHelper.createErrorCode(error: error))
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func updateMetadata(_ call: CAPPluginCall) {
        guard let path = call.getString("path") else {
            call.reject(errorPathMissing)
            return
        }
        guard let metadata = call.getObject("metadata") else {
            call.reject(errorMetadataMissing)
            return
        }

        let options = UpdateMetadataOptions(path: path, metadata: metadata)

        implementation?.updateMetadata(options, completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription, FirebaseStorageHelper.createErrorCode(error: error))
                return
            }
            call.resolve()
        })
    }

    @objc func uploadFile(_ call: CAPPluginCall) {
        call.keepAlive = true

        guard let path = call.getString("path") else {
            call.reject(errorPathMissing)
            return
        }
        guard let uri = call.getString("uri") else {
            call.reject(errorUriMissing)
            return
        }
        guard let url = URL(string: uri) else {
            call.reject(errorFileNotExist)
            return
        }
        guard let callbackId = call.callbackId else {
            call.reject(errorCallbackIdMissing)
            return
        }
        let metadata = call.getObject("metadata")

        let options = UploadFileOptions(path: path, uri: url, metadata: metadata, callbackId: callbackId)

        implementation?.uploadFile(options, completion: { result, error, releaseCall in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription, FirebaseStorageHelper.createErrorCode(error: error))
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
            if releaseCall == true {
                self.bridge?.releaseCall(call)
            }
        })
    }

    @objc func useEmulator(_ call: CAPPluginCall) {
        guard let host = call.getString("host") else {
            call.reject(errorHostMissing)
            return
        }
        let port = call.getInt("port") ?? 9199

        implementation?.useEmulator(host, port)
        call.resolve()
    }
}
