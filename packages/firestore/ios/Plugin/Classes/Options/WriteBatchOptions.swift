import Foundation
import Capacitor
import FirebaseFirestore

@objc public class WriteBatchOptions: NSObject {
    private var operations: [WriteBatchOperation]

    init(operations: [JSObject], firestore: Firestore) {
        self.operations = WriteBatchOptions.createWriteBatchOperationArrayFromJSArray(operations, firestore: firestore)
    }

    public func getOperations() -> [WriteBatchOperation] {
        return self.operations
    }

    private static func createWriteBatchOperationArrayFromJSArray(_ data: [JSObject], firestore: Firestore) -> [WriteBatchOperation] {
        var operations: [WriteBatchOperation] = []
        for item in data {
            operations.append(WriteBatchOperation(item, firestore: firestore))
        }
        return operations
    }
}
