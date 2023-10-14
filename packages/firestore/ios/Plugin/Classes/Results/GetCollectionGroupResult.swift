import Foundation
import FirebaseFirestore
import Capacitor

@objc public class GetCollectionGroupResult: NSObject, Result {
    let querySnapshot: QuerySnapshot

    init(_ querySnapshot: QuerySnapshot) {
        self.querySnapshot = querySnapshot
    }

    public func toJSObject() -> AnyObject {
        var snapshotsResult = JSArray()
        for documentSnapshot in querySnapshot.documents {
            let documentSnapshotData = documentSnapshot.data()
            var snapshotResult = JSObject()
            snapshotResult["id"] = documentSnapshot.documentID
            snapshotResult["path"] = documentSnapshot.reference.path
            snapshotResult["data"] = FirebaseFirestoreHelper.createJSObjectFromHashMap(documentSnapshotData)
            snapshotsResult.append(snapshotResult)
        }

        var result = JSObject()
        result["snapshots"] = snapshotsResult
        return result as AnyObject
    }
}
