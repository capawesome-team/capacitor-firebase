import Foundation

@objc public class AddDocumentSnapshotListenerOptions: NSObject {
    private var reference: String
    private var includeMetadataChanges: Bool
    private var callbackId: String

    init(reference: String, includeMetadataChanges: Bool, callbackId: String) {
        self.reference = reference
        self.includeMetadataChanges = includeMetadataChanges
        self.callbackId = callbackId
    }

    func getReference() -> String {
        return reference
    }

    func getIncludeMetadataChanges() -> Bool {
        return includeMetadataChanges
    }

    func getCallbackId() -> String {
        return callbackId
    }
}
