import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebaseFirestorePlugin)
public class FirebaseFirestorePlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "FirebaseFirestorePlugin"
    public let jsName = "FirebaseFirestore"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "addCollectionGroupSnapshotListener", returnType: CAPPluginReturnCallback),
        CAPPluginMethod(name: "addCollectionSnapshotListener", returnType: CAPPluginReturnCallback),
        CAPPluginMethod(name: "addDocument", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "addDocumentSnapshotListener", returnType: CAPPluginReturnCallback),
        CAPPluginMethod(name: "clearPersistence", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "deleteDocument", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "disableNetwork", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "disablePersistence", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "enableNetwork", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "enablePersistence", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getCollection", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getCollectionGroup", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getCountFromServer", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getDocument", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "removeSnapshotListener", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setDocument", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "terminate", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "updateDocument", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "useEmulator", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "writeBatch", returnType: CAPPluginReturnPromise)
    ]
    public let tag = "FirebaseFirestore"
    private var implementation: FirebaseFirestore?
    private var pluginCallMap: [String: CAPPluginCall] = [:]

    override public func load() {
        self.implementation = FirebaseFirestore(plugin: self, config: firebaseFirestoreConfig())
    }

    @objc override public func removeAllListeners(_ call: CAPPluginCall) {
        for (_, savedCall) in self.pluginCallMap {
            bridge?.releaseCall(savedCall)
        }
        self.pluginCallMap.removeAll()
        self.eventListeners?.removeAllObjects()

        implementation?.removeAllListeners {
            self.resolveCall(call)
        }
    }

    @objc func addCollectionGroupSnapshotListener(_ call: CAPPluginCall) {
        call.keepAlive = true

        do {
            let options = try AddCollectionGroupSnapshotListenerOptions(call)

            self.pluginCallMap[options.callbackId] = call

            try implementation?.addCollectionGroupSnapshotListener(options, completion: { result, error in
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

    @objc func addCollectionSnapshotListener(_ call: CAPPluginCall) {
        call.keepAlive = true

        do {
            let options = try AddCollectionSnapshotListenerOptions(call)

            self.pluginCallMap[options.callbackId] = call

            try implementation?.addCollectionSnapshotListener(options, completion: { result, error in
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

    @objc func addDocument(_ call: CAPPluginCall) {
        do {
            let options = try AddDocumentOptions(call)

            try implementation?.addDocument(options, completion: { result, error in
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

    @objc func addDocumentSnapshotListener(_ call: CAPPluginCall) {
        call.keepAlive = true

        do {
            let options = try AddDocumentSnapshotListenerOptions(call)

            self.pluginCallMap[options.callbackId] = call

            try implementation?.addDocumentSnapshotListener(options, completion: { result, error in
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

    @objc func clearPersistence(_ call: CAPPluginCall) {
        do {
            let options = ClearPersistenceOptions(call)

            try implementation?.clearPersistence(options, completion: { error in
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

    @objc func deleteDocument(_ call: CAPPluginCall) {
        do {
            let options = try DeleteDocumentOptions(call)

            try implementation?.deleteDocument(options, completion: { error in
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

    @objc func disableNetwork(_ call: CAPPluginCall) {
        do {
            let options = DisableNetworkOptions(call)

            try implementation?.disableNetwork(options, completion: { error in
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

    @objc func disablePersistence(_ call: CAPPluginCall) {
        do {
            let options = DisablePersistenceOptions(call)

            try implementation?.disablePersistence(options, completion: { error in
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

    @objc func enableNetwork(_ call: CAPPluginCall) {
        do {
            let options = EnableNetworkOptions(call)

            try implementation?.enableNetwork(options, completion: { error in
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

    @objc func enablePersistence(_ call: CAPPluginCall) {
        do {
            let options = EnablePersistenceOptions(call)

            try implementation?.enablePersistence(options, completion: { error in
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

    @objc func getCollection(_ call: CAPPluginCall) {
        do {
            let options = try GetCollectionOptions(call)

            try implementation?.getCollection(options, completion: { result, error in
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

    @objc func getCollectionGroup(_ call: CAPPluginCall) {
        do {
            let options = try GetCollectionGroupOptions(call)

            try implementation?.getCollectionGroup(options, completion: { result, error in
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

    @objc func getCountFromServer(_ call: CAPPluginCall) {
        do {
            let options = try GetCountFromServerOptions(call)

            try implementation?.getCountFromServer(options, completion: { result, error in
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

    @objc func getDocument(_ call: CAPPluginCall) {
        do {
            let options = try GetDocumentOptions(call)

            try implementation?.getDocument(options, completion: { result, error in
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

    @objc func removeSnapshotListener(_ call: CAPPluginCall) {
        do {
            let options = try RemoveSnapshotListenerOptions(call)

            let savedCall = self.pluginCallMap[options.callbackId]
            if let savedCall = savedCall {
                bridge?.releaseCall(savedCall)
            }
            self.pluginCallMap.removeValue(forKey: options.callbackId)

            implementation?.removeSnapshotListener(options) {
                self.resolveCall(call)
            }
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setDocument(_ call: CAPPluginCall) {
        do {
            let options = try SetDocumentOptions(call)

            try implementation?.setDocument(options, completion: { error in
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

    @objc func terminate(_ call: CAPPluginCall) {
        do {
            let options = TerminateOptions(call)

            try implementation?.terminate(options, completion: { error in
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

    @objc func updateDocument(_ call: CAPPluginCall) {
        do {
            let options = try UpdateDocumentOptions(call)

            try implementation?.updateDocument(options, completion: { error in
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

    @objc func useEmulator(_ call: CAPPluginCall) {
        do {
            let options = try UseEmulatorOptions(call)

            try implementation?.useEmulator(options, completion: { error in
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

    @objc func writeBatch(_ call: CAPPluginCall) {
        do {
            let options = try WriteBatchOptions(call)

            try implementation?.writeBatch(options, completion: { error in
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

    private func firebaseFirestoreConfig() -> FirebaseFirestoreConfig {
        var config = FirebaseFirestoreConfig()

        config.databaseId = getConfig().getString("databaseId")

        return config
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", self.tag, "] ", error)
        if let error = error as? CustomError {
            call.reject(error.localizedDescription, error.code)
        } else {
            call.reject(error.localizedDescription, FirebaseFirestoreHelper.createErrorCode(error: error))
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
