import Foundation

@objc public class FirebaseFirestore: NSObject {
    private let plugin: FirebaseAuthenticationPlugin

    init(plugin: FirebaseAuthenticationPlugin) {
        self.plugin = plugin
        super.init()
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }

    @objc public func addDocument(_ options: AddDocumentOptions, completion: @escaping (Result?, Error?) -> Void) {
        
    }

    @objc public func setDocument(_ options: SetDocumentOptions, completion: @escaping (Error?) -> Void) {
        
    }

    @objc public func getDocument(_ options: GetDocumentOptions, completion: @escaping (Result?, Error?) -> Void) {
        
    }

    @objc public func updateDocument(_ options: UpdateDocumentOptions, completion: @escaping (Error?) -> Void) {
        
    }

    @objc public func deleteDocument(_ options: DeleteDocumentOptions, completion: @escaping (Error?) -> Void) {
        
    }

    @objc public func getCollection(_ options: GetCollectionOptions, completion: @escaping (Result?, Error?) -> Void) {
        
    }

    @objc public func getCollectionGroup(_ options: GetCollectionGroupOptions, completion: @escaping (Result?, Error?) -> Void) {
        
    }

    @objc public func enableNetwork() {
        
    }

    @objc public func disableNetwork() {
        
    }

    @objc public func addDocumentSnapshotListener(_ options: AddDocumentSnapshotListenerOptions, completion: @escaping (Result?, Error?) -> Void) {
        
    }

    @objc public func addCollectionSnapshotListener(_ options: AddCollectionSnapshotListenerOptions, completion: @escaping (Result?, Error?) -> Void) {
        
    }

    @objc public func removeSnapshotListener(_ options: RemoveSnapshotListenerOptions) {
        
    }

    @objc public func removeAllListeners() {
        
    }
}
