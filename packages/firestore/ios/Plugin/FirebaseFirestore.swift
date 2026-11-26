import Foundation
import Capacitor
import FirebaseCore
import FirebaseFirestore

private actor ListenerRegistrationMap {
    private var listenerRegistrationMap: [String: ListenerRegistration] = [:]

    func addRegistration(_ listenerRegistration: ListenerRegistration, listenerId: String) async {
        listenerRegistrationMap[listenerId] = listenerRegistration
    }

    func removeAll() async {
        listenerRegistrationMap.forEach { _, value in
            value.remove()
        }
        listenerRegistrationMap.removeAll()
    }

    func removeRegistration(listenerId: String) async {
        if let listenerRegistration = listenerRegistrationMap[listenerId] {
            listenerRegistration.remove()
        }
        listenerRegistrationMap.removeValue(forKey: listenerId)
    }
}

@objc public class FirebaseFirestore: NSObject {
    private let config: FirebaseFirestoreConfig
    private var listenerRegistrationMap = ListenerRegistrationMap()
    private let plugin: FirebaseFirestorePlugin

    init(plugin: FirebaseFirestorePlugin, config: FirebaseFirestoreConfig) {
        self.plugin = plugin
        self.config = config
        super.init()
        if FirebaseApp.app() == nil {
            guard FirebaseOptions.defaultOptions() != nil else {
                CAPLog.print("[FirebaseFirestore] Firebase was not configured: GoogleService-Info.plist is missing from the app bundle.")
                return
            }
            FirebaseApp.configure()
        }
    }

    @objc public func addCollectionGroupSnapshotListener(_ options: AddCollectionGroupSnapshotListenerOptions, completion: @escaping (Result?, Error?) -> Void) throws {
        let firestore = try getFirestoreInstance(appName: options.appName)
        let reference = options.reference
        let compositeFilter = FirebaseFirestoreHelper.createQueryCompositeFilterConstraintFromJSObject(options.compositeFilter, firestore: firestore)
        let queryConstraints = FirebaseFirestoreHelper.createQueryNonFilterConstraintArrayFromJSArray(options.queryConstraints)
        let includeMetadataChanges = options.includeMetadataChanges
        let serverTimestampBehavior = FirebaseFirestoreHelper.createServerTimestampBehavior(options.serverTimestampBehavior)
        let callbackId = options.callbackId

        Task {
            do {
                let collectionReference = firestore.collectionGroup(reference)
                var query = collectionReference as Query
                if let compositeFilter = compositeFilter {
                    if let filter = try compositeFilter.toFilter() {
                        query = query.whereFilter(filter)
                    }
                }
                if !queryConstraints.isEmpty {
                    for queryConstraint in queryConstraints {
                        query = try await queryConstraint.toQuery(query: query, firestore: firestore)
                    }
                }

                let listenerRegistration = query.addSnapshotListener(includeMetadataChanges: includeMetadataChanges) { querySnapshot, error in
                    if let error = error {
                        completion(nil, error)
                    } else {
                        let result = GetCollectionGroupResult(querySnapshot!, serverTimestampBehavior)
                        completion(result, nil)
                    }
                }
                await listenerRegistrationMap.addRegistration(listenerRegistration, listenerId: callbackId)
            } catch {
                completion(nil, error)
            }
        }
    }

    @objc public func addCollectionSnapshotListener(_ options: AddCollectionSnapshotListenerOptions, completion: @escaping (Result?, Error?) -> Void) throws {
        let firestore = try getFirestoreInstance(appName: options.appName)
        let reference = options.reference
        let compositeFilter = FirebaseFirestoreHelper.createQueryCompositeFilterConstraintFromJSObject(options.compositeFilter, firestore: firestore)
        let queryConstraints = FirebaseFirestoreHelper.createQueryNonFilterConstraintArrayFromJSArray(options.queryConstraints)
        let includeMetadataChanges = options.includeMetadataChanges
        let serverTimestampBehavior = FirebaseFirestoreHelper.createServerTimestampBehavior(options.serverTimestampBehavior)
        let callbackId = options.callbackId

        Task {
            do {
                let collectionReference = firestore.collection(reference)
                var query = collectionReference as Query
                if let compositeFilter = compositeFilter {
                    if let filter = try compositeFilter.toFilter() {
                        query = query.whereFilter(filter)
                    }
                }
                if !queryConstraints.isEmpty {
                    for queryConstraint in queryConstraints {
                        query = try await queryConstraint.toQuery(query: query, firestore: firestore)
                    }
                }

                let listenerRegistration = query.addSnapshotListener(includeMetadataChanges: includeMetadataChanges) { querySnapshot, error in
                    if let error = error {
                        completion(nil, error)
                    } else {
                        let result = GetCollectionResult(querySnapshot!, serverTimestampBehavior)
                        completion(result, nil)
                    }
                }
                await listenerRegistrationMap.addRegistration(listenerRegistration, listenerId: callbackId)
            } catch {
                completion(nil, error)
            }
        }
    }

    @objc public func addDocument(_ options: AddDocumentOptions, completion: @escaping (Result?, Error?) -> Void) throws {
        let firestore = try getFirestoreInstance(appName: options.appName)
        let reference = options.reference
        let data = try FirebaseFirestoreHelper.createHashMapFromJSObject(options.data, firestore: firestore)

        var documentReference: DocumentReference?
        documentReference = firestore.collection(reference).addDocument(data: data) { error in
            if let error = error {
                completion(nil, error)
            } else {
                let result = AddDocumentResult(documentReference!)
                completion(result, nil)
            }
        }
    }

    @objc public func addDocumentSnapshotListener(_ options: AddDocumentSnapshotListenerOptions, completion: @escaping (Result?, Error?) -> Void) throws {
        let reference = options.reference
        let includeMetadataChanges = options.includeMetadataChanges
        let serverTimestampBehavior = FirebaseFirestoreHelper.createServerTimestampBehavior(options.serverTimestampBehavior)
        let callbackId = options.callbackId

        let firestore = try getFirestoreInstance(appName: options.appName)
        let listenerRegistration = firestore.document(reference).addSnapshotListener(includeMetadataChanges: includeMetadataChanges) { documentSnapshot, error in
            if let error = error {
                completion(nil, error)
            } else {
                let result = GetDocumentResult(documentSnapshot!, serverTimestampBehavior)
                completion(result, nil)
            }
        }
        Task {
            await self.listenerRegistrationMap.addRegistration(listenerRegistration, listenerId: callbackId)
        }
    }

    @objc public func clearPersistence(_ options: ClearPersistenceOptions, completion: @escaping (Error?) -> Void) throws {
        try getFirestoreInstance(appName: options.appName).clearPersistence { error in
            completion(error)
        }
    }

    @objc public func deleteDocument(_ options: DeleteDocumentOptions, completion: @escaping (Error?) -> Void) throws {
        let reference = options.reference

        try getFirestoreInstance(appName: options.appName).document(reference).delete { error in
            completion(error)
        }
    }

    @objc public func disableNetwork(_ options: DisableNetworkOptions, completion: @escaping (Error?) -> Void) throws {
        try getFirestoreInstance(appName: options.appName).disableNetwork { error in
            completion(error)
        }
    }

    @objc public func disablePersistence(_ options: DisablePersistenceOptions, completion: @escaping (Error?) -> Void) throws {
        let firestore = try getFirestoreInstance(appName: options.appName)
        let settings = firestore.settings
        settings.cacheSettings = MemoryCacheSettings()
        firestore.settings = settings
        completion(nil)
    }

    @objc public func enableNetwork(_ options: EnableNetworkOptions, completion: @escaping (Error?) -> Void) throws {
        try getFirestoreInstance(appName: options.appName).enableNetwork { error in
            completion(error)
        }
    }

    @objc public func enablePersistence(_ options: EnablePersistenceOptions, completion: @escaping (Error?) -> Void) throws {
        let firestore = try getFirestoreInstance(appName: options.appName)
        let settings = firestore.settings
        if let cacheSizeBytes = options.cacheSizeBytes {
            settings.cacheSettings = PersistentCacheSettings(sizeBytes: cacheSizeBytes)
        } else {
            settings.cacheSettings = PersistentCacheSettings()
        }
        firestore.settings = settings
        completion(nil)
    }

    @objc public func getCollection(_ options: GetCollectionOptions, completion: @escaping (Result?, Error?) -> Void) throws {
        let firestore = try getFirestoreInstance(appName: options.appName)
        let reference = options.reference
        let compositeFilter = FirebaseFirestoreHelper.createQueryCompositeFilterConstraintFromJSObject(options.compositeFilter, firestore: firestore)
        let queryConstraints = FirebaseFirestoreHelper.createQueryNonFilterConstraintArrayFromJSArray(options.queryConstraints)

        Task {
            do {
                let collectionReference = firestore.collection(reference)
                var query = collectionReference as Query
                if let compositeFilter = compositeFilter {
                    if let filter = try compositeFilter.toFilter() {
                        query = query.whereFilter(filter)
                    }
                }
                if !queryConstraints.isEmpty {
                    for queryConstraint in queryConstraints {
                        query = try await queryConstraint.toQuery(query: query, firestore: firestore)
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

    @objc public func getCollectionGroup(_ options: GetCollectionGroupOptions, completion: @escaping (Result?, Error?) -> Void) throws {
        let firestore = try getFirestoreInstance(appName: options.appName)
        let reference = options.reference
        let compositeFilter = FirebaseFirestoreHelper.createQueryCompositeFilterConstraintFromJSObject(options.compositeFilter, firestore: firestore)
        let queryConstraints = FirebaseFirestoreHelper.createQueryNonFilterConstraintArrayFromJSArray(options.queryConstraints)

        Task {
            do {
                let collectionReference = firestore.collectionGroup(reference)
                var query = collectionReference as Query
                if let compositeFilter = compositeFilter {
                    if let filter = try compositeFilter.toFilter() {
                        query = query.whereFilter(filter)
                    }
                }
                if !queryConstraints.isEmpty {
                    for queryConstraint in queryConstraints {
                        query = try await queryConstraint.toQuery(query: query, firestore: firestore)
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

    @objc public func getCountFromServer(_ options: GetCountFromServerOptions, completion: @escaping (Result?, Error?) -> Void) throws {
        let firestore = try getFirestoreInstance(appName: options.appName)
        let reference = options.reference
        let compositeFilter = FirebaseFirestoreHelper.createQueryCompositeFilterConstraintFromJSObject(options.compositeFilter, firestore: firestore)
        let queryConstraints = FirebaseFirestoreHelper.createQueryNonFilterConstraintArrayFromJSArray(options.queryConstraints)

        Task {
            do {
                let collectionReference = firestore.collection(reference)
                var query = collectionReference as Query
                if let compositeFilter = compositeFilter {
                    if let filter = try compositeFilter.toFilter() {
                        query = query.whereFilter(filter)
                    }
                }
                if !queryConstraints.isEmpty {
                    for queryConstraint in queryConstraints {
                        query = try await queryConstraint.toQuery(query: query, firestore: firestore)
                    }
                }

                let snapshot = try await query.count.getAggregation(source: .server)
                let result = GetCountFromServerResult(snapshot.count.intValue)
                completion(result, nil)
            } catch {
                completion(nil, error)
            }
        }
    }

    @objc public func getDocument(_ options: GetDocumentOptions, completion: @escaping (Result?, Error?) -> Void) throws {
        let reference = options.reference

        try getFirestoreInstance(appName: options.appName).document(reference).getDocument { documentSnapshot, error in
            if let error = error {
                completion(nil, error)
            } else {
                let result = GetDocumentResult(documentSnapshot!)
                completion(result, nil)
            }
        }
    }

    @objc public func removeAllListeners(completion: @escaping () -> Void) {
        Task {
            await listenerRegistrationMap.removeAll()
            completion()
        }
    }

    @objc public func removeSnapshotListener(_ options: RemoveSnapshotListenerOptions, completion: @escaping () -> Void) {
        let callbackId = options.callbackId
        Task {
            await listenerRegistrationMap.removeRegistration(listenerId: callbackId)
            completion()
        }
    }

    @objc public func setDocument(_ options: SetDocumentOptions, completion: @escaping (Error?) -> Void) throws {
        let firestore = try getFirestoreInstance(appName: options.appName)
        let reference = options.reference
        let data = try FirebaseFirestoreHelper.createHashMapFromJSObject(options.data, firestore: firestore)
        let merge = options.merge

        firestore.document(reference).setData(data, merge: merge) { error in
            completion(error)
        }
    }

    @objc public func terminate(_ options: TerminateOptions, completion: @escaping (Error?) -> Void) throws {
        let firestore = try getFirestoreInstance(appName: options.appName)
        firestore.terminate { error in
            // The SDK drops its own reference on terminate. Keep the instance alive, or it can be
            // deallocated before the completion handler runs and the handler is never called.
            withExtendedLifetime(firestore) {
                completion(error)
            }
        }
    }

    @objc public func updateDocument(_ options: UpdateDocumentOptions, completion: @escaping (Error?) -> Void) throws {
        let firestore = try getFirestoreInstance(appName: options.appName)
        let reference = options.reference
        let data = try FirebaseFirestoreHelper.createHashMapFromJSObject(options.data, firestore: firestore)

        firestore.document(reference).updateData(data) { error in
            completion(error)
        }
    }

    @objc public func useEmulator(_ options: UseEmulatorOptions, completion: @escaping (Error?) -> Void) throws {
        let firestore = try getFirestoreInstance(appName: options.appName)
        let settings = firestore.settings
        settings.host = "\(options.host):\(options.port)"
        settings.isSSLEnabled = false
        firestore.settings = settings
        completion(nil)
    }

    @objc public func writeBatch(_ options: WriteBatchOptions, completion: @escaping (Error?) -> Void) throws {
        let firestore = try getFirestoreInstance(appName: options.appName)
        let operations = options.operations

        let batch = firestore.batch()
        for operation in operations {
            let type = operation.type
            let reference = operation.reference

            let documentReference = firestore.document(reference)
            switch type {
            case "set":
                let data = try FirebaseFirestoreHelper.createHashMapFromJSObject(operation.data, firestore: firestore)
                if let setOpts = operation.options, setOpts.merge {
                    batch.setData(
                        data,
                        forDocument: documentReference,
                        merge: true
                    )
                } else {
                    batch.setData(data, forDocument: documentReference)
                }
            case "update":
                let data = try FirebaseFirestoreHelper.createHashMapFromJSObject(operation.data, firestore: firestore)
                batch.updateData(data, forDocument: documentReference)
            case "delete":
                batch.deleteDocument(documentReference)
            default:
                break
            }
        }

        batch.commit { error in
            completion(error)
        }
    }

    private func getFirestoreInstance(appName: String?) throws -> Firestore {
        if let appName = appName {
            guard let app = FirebaseApp.app(name: appName) else {
                throw CustomError.appNotFound
            }
            return Firestore.firestore(app: app)
        }
        if let databaseId = config.databaseId {
            return Firestore.firestore(database: databaseId)
        }
        return Firestore.firestore()
    }
}
