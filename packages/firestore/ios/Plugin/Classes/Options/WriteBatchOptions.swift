import Foundation
import Capacitor

@objc public class WriteBatchOptions: NSObject {
    private var operations: [WriteBatchOperation]

    init(operations: [JSObject]) {
        self.operations = WriteBatchOptions.createWriteBatchOperationArrayFromJSArray(operations)
    }

    public func getOperations() -> [WriteBatchOperation] {
        return self.operations
    }

    private static func createWriteBatchOperationArrayFromJSArray(_ data: [JSObject]) -> [WriteBatchOperation] {
        var operations: [WriteBatchOperation] = []
        for item in data {
            operations.append(WriteBatchOperation(item))
        }
        return operations
    }
}
