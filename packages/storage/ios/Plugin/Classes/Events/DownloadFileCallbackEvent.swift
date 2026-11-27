import Foundation
import Capacitor
import FirebaseStorage

@objc public class DownloadFileCallbackEvent: NSObject, Result {
    private var snapshot: StorageTaskSnapshot

    init(snapshot: StorageTaskSnapshot) {
        self.snapshot = snapshot
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["progress"] = getProgress()
        result["completed"] = snapshot.status == .success ? true : false
        return result as AnyObject
    }

    private func getProgress() -> Double {
        let progress = snapshot.progress!
        if progress.totalUnitCount == 0 {
            // The size is not known yet or the file is empty. NEVER divide by zero here, the result is no JSON number.
            return snapshot.status == .success ? 1 : 0
        }
        return Double(progress.completedUnitCount) / Double(progress.totalUnitCount)
    }
}
