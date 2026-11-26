import Foundation
import Capacitor

@objc public class WriteBatchOptions: NSObject {
    let operations: [WriteBatchOperation]

    init(_ call: CAPPluginCall) throws {
        self.operations = try WriteBatchOptions.getOperationsFromCall(call)
    }

    private static func createWriteBatchOperationArrayFromJSArray(_ operations: [JSObject]) throws -> [WriteBatchOperation] {
        var writeBatchOperations: [WriteBatchOperation] = []
        for item in operations {
            writeBatchOperations.append(try WriteBatchOperation(item))
        }
        return writeBatchOperations
    }

    private static func getOperationsFromCall(_ call: CAPPluginCall) throws -> [WriteBatchOperation] {
        guard let operations = call.getArray("operations", JSObject.self) else {
            throw CustomError.operationsMissing
        }
        return try WriteBatchOptions.createWriteBatchOperationArrayFromJSArray(operations)
    }
}
