import Foundation
import FirebaseCore
import FirebaseFirestore

@objc public class FirebaseFirestore: NSObject {
    private let plugin: FirebaseFirestorePlugin
    private var listenerRegistrationMap: [String: ListenerRegistration] = [:]

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
                        query.whereFilter(filter)
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
                        query.whereFilter(filter)
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

    @objc public func addDocumentSnapshotListener(_ options: AddDocumentSnapshotListenerOptions, completion: @escaping (Result?, Error?) -> Void) {
        let reference = options.getReference()
        let callbackId = options.getCallbackId()

        let listenerRegistration = Firestore.firestore().document(reference).addSnapshotListener { documentSnapshot, error in
            if let error = error {
                completion(nil, error)
            } else {
                let result = GetDocumentResult(documentSnapshot!)
                completion(result, nil)
            }
        }
        self.listenerRegistrationMap[callbackId] = listenerRegistration
    }

    @objc public func addCollectionSnapshotListener(_ options: AddCollectionSnapshotListenerOptions, completion: @escaping (Result?, Error?) -> Void) {
        let reference = options.getReference()
        let compositeFilter = options.getCompositeFilter()
        let queryConstraints = options.getQueryConstraints()
        let callbackId = options.getCallbackId()

        Task {
            do {
                let collectionReference = Firestore.firestore().collection(reference)
                var query = collectionReference as Query
                if let compositeFilter = compositeFilter {
                    if let filter = compositeFilter.toFilter() {
                        query.whereFilter(filter)
                    }
                }
                if !queryConstraints.isEmpty {
                    for queryConstraint in queryConstraints {
                        query = try await queryConstraint.toQuery(query: query)
                    }
                }

                let listenerRegistration = query.addSnapshotListener { querySnapshot, error in
                    if let error = error {
                        completion(nil, error)
                    } else {
                        let result = GetCollectionResult(querySnapshot!)
                        completion(result, nil)
                    }
                }
                self.listenerRegistrationMap[callbackId] = listenerRegistration
            } catch {
                completion(nil, error)
            }
        }
    }

    @objc public func removeSnapshotListener(_ options: RemoveSnapshotListenerOptions) {
        let callbackId = options.getCallbackId()

        if let listenerRegistration = self.listenerRegistrationMap[callbackId] {
            listenerRegistration.remove()
        }
        self.listenerRegistrationMap.removeValue(forKey: callbackId)
    }

    @objc public func removeAllListeners() {
        for listenerRegistration in self.listenerRegistrationMap.values {
            listenerRegistration.remove()
        }
        self.listenerRegistrationMap.removeAll()
    }
}
