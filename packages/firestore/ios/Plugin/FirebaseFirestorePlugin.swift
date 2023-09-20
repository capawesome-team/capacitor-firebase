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
    public let errorCallbackIdMissing = "callbackId must be provided."
    private var implementation: FirebaseFirestore?

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

        let options = SetDocumentOptions(reference: reference, data: data)

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

    @objc func getCollection(_ call: CAPPluginCall) {
        guard let reference = call.getString("reference") else {
            call.reject(errorReferenceMissing)
            return
        }
        let compositeFilter = call.getObject("compositeFilter")
        let queryConstraints = call.getArray("queryConstraints", JSObject.self)

        let options = GetCollectionOptions(reference: reference)

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
        guard let collectionId = call.getString("collectionId") else {
            call.reject("collectionId must be provided.")
            return
        }
        let compositeFilter = call.getObject("compositeFilter")
        let queryConstraints = call.getArray("queryConstraints", JSObject.self)

        let options = GetCollectionGroupOptions(collectionId: collectionId)

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

    @objc func addDocumentSnapshotListener(_ call: CAPPluginCall) {
        guard let reference = call.getString("reference") else {
            call.reject(errorReferenceMissing)
            return
        }

        let options = AddDocumentSnapshotListenerOptions(reference: reference)

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
        guard let reference = call.getString("reference") else {
            call.reject(errorReferenceMissing)
            return
        }

        let options = AddCollectionSnapshotListenerOptions(reference: reference)

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
    }

    @objc func removeSnapshotListener(_ call: CAPPluginCall) {
        guard let callbackId = call.getString("callbackId") else {
            call.reject(errorCallbackIdMissing)
            return
        }

        let options = RemoveSnapshotListenerOptions(listenerId: listenerId)

        implementation?.removeSnapshotListener(options, completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
            }
            call.resolve()
        })
    }

    @objc func removeAllListeners(_ call: CAPPluginCall) {
        implementation?.removeAllListeners(completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
            }
            call.resolve()
        })
    }
}
