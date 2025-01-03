import Foundation
import FirebaseCore
import FirebaseFirestore

private actor ListenerRegistrationMap {
    private var listenerRegistrationMap: [String: ListenerRegistration] = [:]

    func addRegistration(_ listenerRegistration: ListenerRegistration, listenerId: String) async {
        listenerRegistrationMap[listenerId] = listenerRegistration
    }

    func removeRegistration(listenerId: String) async {
        if let listenerRegistration = listenerRegistrationMap[listenerId] {
            listenerRegistration.remove()
        }
        listenerRegistrationMap.removeValue(forKey: listenerId)
    }

    func removeAll() async {
        listenerRegistrationMap.forEach { _, value in
            value.remove()
        }
        listenerRegistrationMap.removeAll()
    }
}

@objc public class FirebaseFirestore: NSObject {
    private let plugin: FirebaseFirestorePlugin
    private var listenerRegistrationMap = ListenerRegistrationMap()

    init(plugin: FirebaseFirestorePlugin) {
        self.plugin = plugin
        super.init()
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }

    @objc public func addDocument(_ options: AddDocumentOptions, completion: @escaping (Result?, Error?) -> Void) {
        let reference = options.getReference()
        let data = options.getData()

        var documentReference: DocumentReference?
        documentReference = Firestore.firestore().collection(reference).addDocument(data: data) { error in
            if let error = error {
                completion(nil, error)
            } else {
                let result = AddDocumentResult(documentReference!)
                completion(result, nil)
            }
        }
    }

    @objc public func setDocument(_ options: SetDocumentOptions, completion: @escaping (Error?) -> Void) {
        let reference = options.getReference()
        let data = options.getData()
        let merge = options.getMerge()

        Firestore.firestore().document(reference).setData(data, merge: merge) { error in
            if let error = error {
                completion(error)
            } else {
                completion(nil)
            }
        }
    }

    @objc public func getDocument(_ options: GetDocumentOptions, completion: @escaping (Result?, Error?) -> Void) {
        let reference = options.getReference()

        Firestore.firestore().document(reference).getDocument { documentSnapshot, error in
            if let error = error {
                completion(nil, error)
            } else {
                let result = GetDocumentResult(documentSnapshot!)
                completion(result, nil)
            }
        }
    }

    @objc public func updateDocument(_ options: UpdateDocumentOptions, completion: @escaping (Error?) -> Void) {
        let reference = options.getReference()
        let data = options.getData()

        Firestore.firestore().document(reference).updateData(data) { error in
            if let error = error {
                completion(error)
            } else {
                completion(nil)
            }
        }
    }

    @objc public func deleteDocument(_ options: DeleteDocumentOptions, completion: @escaping (Error?) -> Void) {
        let reference = options.getReference()

        Firestore.firestore().document(reference).delete { error in
            if let error = error {
                completion(error)
            } else {
                completion(nil)
            }
        }
    }

    @objc public func writeBatch(_ options: WriteBatchOptions, completion: @escaping (Error?) -> Void) {
        let operations = options.getOperations()

        let batch = Firestore.firestore().batch()
        for operation in operations {
            let type = operation.getType()
            let reference = operation.getReference()
            let data = operation.getData()

            let documentReference = Firestore.firestore().document(reference)
            switch type {
            case "set":
                batch.setData(data, forDocument: documentReference)
            case "update":
                batch.updateData(data, forDocument: documentReference)
            case "delete":
                batch.deleteDocument(documentReference)
            default:
                break
            }
        }

        batch.commit { error in
            if let error = error {
                completion(error)
            } else {
                completion(nil)
            }
        }
    }

    @objc public func getCollection(_ options: GetCollectionOptions, completion: @escaping (Result?, Error?) -> Void) {
        let reference = options.getReference()
        let compositeFilter = options.getCompositeFilter()
        let queryConstraints = options.getQueryConstraints()

        Task {
            do {
                let collectionReference = Firestore.firestore().collection(reference)
                var query = collectionReference as Query
                if let compositeFilter = compositeFilter {
                    if let filter = compositeFilter.toFilter() {
                        query = query.whereFilter(filter)
                    }
                }
                if !queryConstraints.isEmpty {
                    for queryConstraint in queryConstraints {
                        query = try await queryConstraint.toQuery(query: query)
                    }
                }

                let querySnapshot = try await query.getDocuments()
                let result = GetCollectionResult(querySnapshot)
                completion(result, nil)
            } catch {
                completion(nil, error)
            }
        }
    }

    @objc public func getCollectionGroup(_ options: GetCollectionGroupOptions, completion: @escaping (Result?, Error?) -> Void) {
        let reference = options.getReference()
        let compositeFilter = options.getCompositeFilter()
        let queryConstraints = options.getQueryConstraints()

        Task {
            do {
                let collectionReference = Firestore.firestore().collectionGroup(reference)
                var query = collectionReference as Query
                if let compositeFilter = compositeFilter {
                    if let filter = compositeFilter.toFilter() {
                        query = query.whereFilter(filter)
                    }
                }
                if !queryConstraints.isEmpty {
                    for queryConstraint in queryConstraints {
                        query = try await queryConstraint.toQuery(query: query)
                    }
                }

                let querySnapshot = try await query.getDocuments()
                let result = GetCollectionResult(querySnapshot)
                completion(result, nil)
            } catch {
                completion(nil, error)
            }
        }
    }

    @objc public func clearPersistence(completion: @escaping (Error?) -> Void) {
        Firestore.firestore().clearPersistence { error in
            completion(error)
        }
    }

    @objc public func enableNetwork(completion: @escaping (Error?) -> Void) {
        Firestore.firestore().enableNetwork { error in
            completion(error)
        }
    }

    @objc public func disableNetwork(completion: @escaping (Error?) -> Void) {
        Firestore.firestore().disableNetwork { error in
            completion(error)
        }
    }

    @objc func useEmulator(_ host: String, _ port: Int) {
        let settings = Firestore.firestore().settings
        settings.host = "\(host):\(port)"
        settings.isSSLEnabled = false
        Firestore.firestore().settings = settings
    }

    @objc public func addDocumentSnapshotListener(_ options: AddDocumentSnapshotListenerOptions, completion: @escaping (Result?, Error?) -> Void) {
        let reference = options.getReference()
        let includeMetadataChanges = options.getIncludeMetadataChanges()
        let callbackId = options.getCallbackId()

        let listenerRegistration = Firestore.firestore().document(reference).addSnapshotListener(includeMetadataChanges: includeMetadataChanges) { documentSnapshot, error in
            if let error = error {
                completion(nil, error)
            } else {
                let result = GetDocumentResult(documentSnapshot!)
                completion(result, nil)
            }
        }
        Task {
            await self.listenerRegistrationMap.addRegistration(listenerRegistration, listenerId: callbackId)
        }
    }

    @objc public func addCollectionSnapshotListener(_ options: AddCollectionSnapshotListenerOptions, completion: @escaping (Result?, Error?) -> Void) {
        let reference = options.getReference()
        let compositeFilter = options.getCompositeFilter()
        let queryConstraints = options.getQueryConstraints()
        let includeMetadataChanges = options.getIncludeMetadataChanges()
        let callbackId = options.getCallbackId()

        Task {
            do {
                let collectionReference = Firestore.firestore().collection(reference)
                var query = collectionReference as Query
                if let compositeFilter = compositeFilter {
                    if let filter = compositeFilter.toFilter() {
                        query = query.whereFilter(filter)
                    }
                }
                if !queryConstraints.isEmpty {
                    for queryConstraint in queryConstraints {
                        query = try await queryConstraint.toQuery(query: query)
                    }
                }

                let listenerRegistration = query.addSnapshotListener(includeMetadataChanges: includeMetadataChanges) { querySnapshot, error in
                    if let error = error {
                        completion(nil, error)
                    } else {
                        let result = GetCollectionResult(querySnapshot!)
                        completion(result, nil)
                    }
                }
                await listenerRegistrationMap.addRegistration(listenerRegistration, listenerId: callbackId)
            } catch {
                completion(nil, error)
            }
        }
    }

    @objc public func addCollectionGroupSnapshotListener(_ options: AddCollectionGroupSnapshotListenerOptions, completion: @escaping (Result?, Error?) -> Void) {
        let reference = options.getReference()
        let compositeFilter = options.getCompositeFilter()
        let queryConstraints = options.getQueryConstraints()
        let includeMetadataChanges = options.getIncludeMetadataChanges()
        let callbackId = options.getCallbackId()

        Task {
            do {
                let collectionReference = Firestore.firestore().collectionGroup(reference)
                var query = collectionReference as Query
                if let compositeFilter = compositeFilter {
                    if let filter = compositeFilter.toFilter() {
                        query = query.whereFilter(filter)
                    }
                }
                if !queryConstraints.isEmpty {
                    for queryConstraint in queryConstraints {
                        query = try await queryConstraint.toQuery(query: query)
                    }
                }

                let listenerRegistration = query.addSnapshotListener(includeMetadataChanges: includeMetadataChanges) { querySnapshot, error in
                    if let error = error {
                        completion(nil, error)
                    } else {
                        let result = GetCollectionGroupResult(querySnapshot!)
                        completion(result, nil)
                    }
                }
                await listenerRegistrationMap.addRegistration(listenerRegistration, listenerId: callbackId)
            } catch {
                completion(nil, error)
            }
        }
    }

    @objc public func removeSnapshotListener(_ options: RemoveSnapshotListenerOptions, completion: @escaping () -> Void) {
        let callbackId = options.getCallbackId()
        Task {
            await listenerRegistrationMap.removeRegistration(listenerId: callbackId)
            completion()
        }
    }

    @objc public func removeAllListeners(completion: @escaping () -> Void) {
        Task {
            await listenerRegistrationMap.removeAll()
            completion()
        }
    }
}
