import Foundation
import FirebaseFirestore
import Capacitor

@objc public class GetDocumentResult: NSObject, Result {
    let documentSnapshot: DocumentSnapshot
    let serverTimestampBehavior: ServerTimestampBehavior

    init(_ documentSnapshot: DocumentSnapshot, _ serverTimestampBehavior: ServerTimestampBehavior = .none) {
        self.documentSnapshot = documentSnapshot
        self.serverTimestampBehavior = serverTimestampBehavior
    }

    public func toJSObject() -> AnyObject {
        let snapshotDataResult = FirebaseFirestoreHelper.createJSObjectFromHashMap(documentSnapshot.data(with: serverTimestampBehavior))

        var snapshotResult = JSObject()
        snapshotResult["id"] = documentSnapshot.documentID
        snapshotResult["path"] = documentSnapshot.reference.path
        if let snapshotDataResult = snapshotDataResult {
            snapshotResult["data"] = snapshotDataResult
        } else {
            snapshotResult["data"] = NSNull()
        }

        var metadata = JSObject()
        metadata["fromCache"] = documentSnapshot.metadata.isFromCache
        metadata["hasPendingWrites"] = documentSnapshot.metadata.hasPendingWrites
        snapshotResult["metadata"] = metadata

        var result = JSObject()
        result["snapshot"] = snapshotResult
        return result as AnyObject
    }
}
