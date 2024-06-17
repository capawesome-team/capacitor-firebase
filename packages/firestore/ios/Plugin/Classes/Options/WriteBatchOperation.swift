import Foundation
import Capacitor

@objc public class WriteBatchOperation: NSObject {
    private var type: String
    private var reference: String
    private var data: [String: Any]

    init(_ operation: JSObject) {
        self.type = operation["type"] as? String ?? ""
        self.reference = operation["reference"] as? String ?? ""
        self.data = FirebaseFirestoreHelper.createHashMapFromJSObject(operation["data"] as? JSObject ?? [:])
    }

    public func getType() -> String {
        return self.type
    }

    public func getReference() -> String {
        return self.reference
    }

    public func getData() -> [String: Any] {
        return self.data
    }
}
