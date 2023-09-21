import Foundation

@objc public class AddCollectionSnapshotListenerOptions: NSObject {
    private var reference: String
    private var callbackId: String

    init(reference: String, callbackId: String) {
        self.reference = reference
        self.callbackId = callbackId
    }

    func getReference() -> String {
        return reference
    }

    func getCallbackId() -> String {
        return callbackId
    }
}
