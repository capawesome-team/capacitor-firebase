import Foundation
import FirebaseFirestore
import Capacitor

@objc public class GetDocumentResult: NSObject, Result {
    let documentSnapshot: DocumentSnapshot

    init(_ documentSnapshot: DocumentSnapshot) {
        self.documentSnapshot = documentSnapshot
    }

    public func toJSObject() -> AnyObject {
        let snapshotDataResult = FirebaseFirestoreHelper.createJSObjectFromHashMap(documentSnapshot.data())
        
        var snapshotResult = JSObject()
        snapshotResult["id"] = documentSnapshot.documentID
        snapshotResult["path"] = documentSnapshot.reference.path
        if let snapshotDataResult = snapshotDataResult {
            snapshotResult["data"] = snapshotDataResult
        } else {
            snapshotResult["data"] = NSNull()
        }

        var result = JSObject()
        result["snapshot"] = snapshotResult
        return result as AnyObject
    }
}
