import Foundation
import Capacitor
import FirebaseStorage

@objc public class UploadFileCallbackEvent: NSObject, Result {
    private var snapshot: StorageTaskSnapshot

    init(snapshot: StorageTaskSnapshot) {
        self.snapshot = snapshot
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["progress"] = 100.0 * Double(snapshot.progress!.completedUnitCount)
            / Double(snapshot.progress!.totalUnitCount)
        result["completed"] = snapshot.status == .success ? true : false
        return result as AnyObject
    }
}
