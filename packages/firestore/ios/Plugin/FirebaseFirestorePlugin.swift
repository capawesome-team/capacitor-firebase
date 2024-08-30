import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebaseFirestorePlugin)
public class FirebaseFirestorePlugin: CAPPlugin {
    public let tag = "FirebaseFirestore"
    public let errorReferenceMissing = "reference must be provided."
    public let errorDataMissing = "data must be provided."
    public let errorOperationsMissing = "operations must be provided."
    public let errorHostMissing = "host must be provided."
    public let errorCallbackIdMissing = "callbackId must be provided."
    private var implementation: FirebaseFirestore?
    private var pluginCallMap: [String: CAPPluginCall] = [:]

    override public func load() {
        self.implementation = FirebaseFirestore(plugin: self)
    }

    @objc func addDocument(_ call: CAPPluginCall) {
        guard let reference = call.getString("reference") else {
            call.reject(errorReferenceMissing)
            return
        }
        guard let data = call.getObject("data") else {
            call.reject(errorDataMissing)
            return
        }

        let options = AddDocumentOptions(reference: reference, data: data)

        implementation?.addDocument(options, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func setDocument(_ call: CAPPluginCall) {
        guard let reference = call.getString("reference") else {
            call.reject(errorReferenceMissing)
            return
        }
        guard let data = call.getObject("data") else {
            call.reject(errorDataMissing)
            return
        }
        let merge = call.getBool("merge", false)

        let options = SetDocumentOptions(reference: reference, data: data, merge: merge)

        implementation?.setDocument(options, completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func getDocument(_ call: CAPPluginCall) {
        guard let reference = call.getString("reference") else {
            call.reject(errorReferenceMissing)
            return
        }

        let options = GetDocumentOptions(reference: reference)

        implementation?.getDocument(options, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func updateDocument(_ call: CAPPluginCall) {
        guard let reference = call.getString("reference") else {
            call.reject(errorReferenceMissing)
            return
        }
        guard let data = call.getObject("data") else {
            call.reject(errorDataMissing)
            return
        }

        let options = UpdateDocumentOptions(reference: reference, data: data)

        implementation?.updateDocument(options, completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func deleteDocument(_ call: CAPPluginCall) {
        guard let reference = call.getString("reference") else {
            call.reject(errorReferenceMissing)
            return
        }

        let options = DeleteDocumentOptions(reference: reference)

        implementation?.deleteDocument(options, completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func writeBatch(_ call: CAPPluginCall) {
        guard let operations = call.getArray("operations", JSObject.self) else {
            call.reject(errorOperationsMissing)
            return
        }

        let options = WriteBatchOptions(operations: operations)

        implementation?.writeBatch(options, completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func getCollection(_ call: CAPPluginCall) {
        guard let reference = call.getString("reference") else {
            call.reject(errorReferenceMissing)
            return
        }
        let compositeFilter = call.getObject("compositeFilter")
        let queryConstraints = call.getArray("queryConstraints", JSObject.self)

        let options = GetCollectionOptions(reference: reference, compositeFilter: compositeFilter, queryConstraints: queryConstraints)

        implementation?.getCollection(options, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func getCollectionGroup(_ call: CAPPluginCall) {
        guard let reference = call.getString("reference") else {
            call.reject("collectionId must be provided.")
            return
        }
        let compositeFilter = call.getObject("compositeFilter")
        let queryConstraints = call.getArray("queryConstraints", JSObject.self)

        let options = GetCollectionGroupOptions(reference: reference, compositeFilter: compositeFilter, queryConstraints: queryConstraints)

        implementation?.getCollectionGroup(options, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func clearPersistence(_ call: CAPPluginCall) {
        implementation?.clearPersistence(completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func enableNetwork(_ call: CAPPluginCall) {
        implementation?.enableNetwork(completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func disableNetwork(_ call: CAPPluginCall) {
        implementation?.disableNetwork(completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func useEmulator(_ call: CAPPluginCall) {
        guard let host = call.getString("host") else {
            call.reject(errorHostMissing)
            return
        }
        let port = call.getInt("port") ?? 8080

        implementation?.useEmulator(host, port)
        call.resolve()
    }

    @objc func addDocumentSnapshotListener(_ call: CAPPluginCall) {
        call.keepAlive = true

        guard let reference = call.getString("reference") else {
            call.reject(errorReferenceMissing)
            return
        }
        let includeMetadataChanges = call.getBool("includeMetadataChanges", false)
        guard let callbackId = call.callbackId else {
            call.reject(errorCallbackIdMissing)
            return
        }

        self.pluginCallMap[callbackId] = call

        let options = AddDocumentSnapshotListenerOptions(reference: reference, includeMetadataChanges: includeMetadataChanges, callbackId: callbackId)

        implementation?.addDocumentSnapshotListener(options, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func addCollectionSnapshotListener(_ call: CAPPluginCall) {
        call.keepAlive = true

        guard let reference = call.getString("reference") else {
            call.reject(errorReferenceMissing)
            return
        }
        let compositeFilter = call.getObject("compositeFilter")
        let queryConstraints = call.getArray("queryConstraints", JSObject.self)
        let includeMetadataChanges = call.getBool("includeMetadataChanges", false)
        guard let callbackId = call.callbackId else {
            call.reject(errorCallbackIdMissing)
            return
        }

        self.pluginCallMap[callbackId] = call

        let options = AddCollectionSnapshotListenerOptions(reference: reference, compositeFilter: compositeFilter, queryConstraints: queryConstraints, includeMetadataChanges: includeMetadataChanges, callbackId: callbackId)

        do {
            implementation?.addCollectionSnapshotListener(options, completion: { result, error in
                if let error = error {
                    CAPLog.print("[", self.tag, "] ", error)
                    call.reject(error.localizedDescription)
                    return
                }
                if let result = result?.toJSObject() as? JSObject {
                    call.resolve(result)
                }
            })
        } catch {
            CAPLog.print("[", self.tag, "] ", error)
            call.reject(error.localizedDescription)
        }
    }

    @objc func addCollectionGroupSnapshotListener(_ call: CAPPluginCall) {
        call.keepAlive = true

        guard let reference = call.getString("reference") else {
            call.reject(errorReferenceMissing)
            return
        }
        let compositeFilter = call.getObject("compositeFilter")
        let queryConstraints = call.getArray("queryConstraints", JSObject.self)
        let includeMetadataChanges = call.getBool("includeMetadataChanges", false)
        guard let callbackId = call.callbackId else {
            call.reject(errorCallbackIdMissing)
            return
        }

        self.pluginCallMap[callbackId] = call

        let options = AddCollectionGroupSnapshotListenerOptions(reference: reference, compositeFilter: compositeFilter, queryConstraints: queryConstraints, includeMetadataChanges: includeMetadataChanges, callbackId: callbackId)

        do {
            implementation?.addCollectionGroupSnapshotListener(options, completion: { result, error in
                if let error = error {
                    CAPLog.print("[", self.tag, "] ", error)
                    call.reject(error.localizedDescription)
                    return
                }
                if let result = result?.toJSObject() as? JSObject {
                    call.resolve(result)
                }
            })
        } catch {
            CAPLog.print("[", self.tag, "] ", error)
            call.reject(error.localizedDescription)
        }
    }

    @objc func removeSnapshotListener(_ call: CAPPluginCall) {
        guard let callbackId = call.getString("callbackId") else {
            call.reject(errorCallbackIdMissing)
            return
        }

        let savedCall = self.pluginCallMap[callbackId]
        if let savedCall = savedCall {
            bridge?.releaseCall(savedCall)
        }
        self.pluginCallMap.removeValue(forKey: callbackId)

        let options = RemoveSnapshotListenerOptions(callbackId: callbackId)

        implementation?.removeSnapshotListener(options)
        call.resolve()
    }

    @objc override public func removeAllListeners(_ call: CAPPluginCall) {
        for (_, savedCall) in self.pluginCallMap {
            bridge?.releaseCall(savedCall)
        }
        self.pluginCallMap.removeAll()

        implementation?.removeAllListeners()
        super.removeAllListeners(call)
    }
}
