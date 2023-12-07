import Foundation
import FirebaseFirestore
import Capacitor

@objc public class GetCollectionResult: NSObject, Result {
    let querySnapshot: QuerySnapshot

    init(_ querySnapshot: QuerySnapshot) {
        self.querySnapshot = querySnapshot
    }

    public func toJSObject() -> AnyObject {
        var snapshotsResult = JSArray()
        for documentSnapshot in querySnapshot.documents {
            let snapshotDataResult = FirebaseFirestoreHelper.createJSObjectFromHashMap(documentSnapshot.data())

            var snapshotResult = JSObject()
            snapshotResult["id"] = documentSnapshot.documentID
            snapshotResult["path"] = documentSnapshot.reference.path
            if let snapshotDataResult = snapshotDataResult {
                snapshotResult["data"] = snapshotDataResult
            } else {
                snapshotResult["data"] = NSNull()
            }
            snapshotsResult.append(snapshotResult)
        }

        var result = JSObject()
        result["snapshots"] = snapshotsResult
        return result as AnyObject
    }
}
