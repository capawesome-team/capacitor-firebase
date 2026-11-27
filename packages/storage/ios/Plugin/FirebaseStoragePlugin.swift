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
        CAPPluginMethod(name: "downloadFile", returnType: CAPPluginReturnCallback),
        CAPPluginMethod(name: "getDownloadUrl", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getMetadata", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "listFiles", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "updateMetadata", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "uploadFile", returnType: CAPPluginReturnCallback),
        CAPPluginMethod(name: "useEmulator", returnType: CAPPluginReturnPromise)
    ]
    public let tag = "FirebaseStorage"
    private var implementation: FirebaseStorage?

    override public func load() {
        self.implementation = FirebaseStorage(plugin: self)
    }

    @objc func deleteFile(_ call: CAPPluginCall) {
        do {
            let options = try DeleteFileOptions(call)

            implementation?.deleteFile(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func downloadFile(_ call: CAPPluginCall) {
        call.keepAlive = true

        do {
            let options = try DownloadFileOptions(call)

            implementation?.downloadFile(options, completion: { result, error, releaseCall in
                if let error = error {
                    self.rejectCall(call, error)
                } else if let result = result {
                    self.resolveCall(call, result)
                }
                if releaseCall {
                    self.bridge?.releaseCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getDownloadUrl(_ call: CAPPluginCall) {
        do {
            let options = try GetDownloadUrlOptions(call)

            implementation?.getDownloadUrl(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getMetadata(_ call: CAPPluginCall) {
        do {
            let options = try GetMetadataOptions(call)

            implementation?.getMetadata(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func listFiles(_ call: CAPPluginCall) {
        do {
            let options = try ListFilesOptions(call)

            implementation?.listFiles(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func updateMetadata(_ call: CAPPluginCall) {
        do {
            let options = try UpdateMetadataOptions(call)

            implementation?.updateMetadata(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func uploadFile(_ call: CAPPluginCall) {
        call.keepAlive = true

        do {
            let options = try UploadFileOptions(call)

            implementation?.uploadFile(options, completion: { result, error, releaseCall in
                if let error = error {
                    self.rejectCall(call, error)
                } else if let result = result {
                    self.resolveCall(call, result)
                }
                if releaseCall {
                    self.bridge?.releaseCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func useEmulator(_ call: CAPPluginCall) {
        do {
            let options = try UseEmulatorOptions(call)

            implementation?.useEmulator(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", self.tag, "] ", error)
        if let error = error as? CustomError {
            call.reject(error.localizedDescription, error.code)
        } else {
            call.reject(error.localizedDescription, FirebaseStorageHelper.createErrorCode(error: error))
        }
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
