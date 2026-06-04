import Foundation

@objc public class AddDocumentSnapshotListenerOptions: NSObject {
    private var reference: String
    private var includeMetadataChanges: Bool
    private var serverTimestampBehavior: String?
    private var callbackId: String

    init(reference: String, includeMetadataChanges: Bool, serverTimestampBehavior: String?, callbackId: String) {
        self.reference = reference
        self.includeMetadataChanges = includeMetadataChanges
        self.serverTimestampBehavior = serverTimestampBehavior
        self.callbackId = callbackId
    }

    func getReference() -> String {
        return reference
    }

    func getIncludeMetadataChanges() -> Bool {
        return includeMetadataChanges
    }

    func getServerTimestampBehavior() -> String? {
        return serverTimestampBehavior
    }

    func getCallbackId() -> String {
        return callbackId
    }
}
