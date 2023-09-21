import Foundation
import FirebaseFirestore
import Capacitor

@objc public class GetDocumentResult: NSObject, Result {
    let documentSnapshot: DocumentSnapshot

    init(_ documentSnapshot: DocumentSnapshot) {
        self.documentSnapshot = documentSnapshot
    }
    
    public func toJSObject() -> AnyObject {
        let documentSnapshotData = documentSnapshot.data()
        var snapshotResult = JSObject()
        snapshotResult["id"] = documentSnapshot.documentID
        snapshotResult["path"] = documentSnapshot.reference.path
        snapshotResult["data"] = FirebaseFirestoreHelper.createJSObjectFromHashMap(documentSnapshotData)
        
        var result = JSObject()
        result["snapshot"] = snapshotResult
        return result as AnyObject
    }
}
