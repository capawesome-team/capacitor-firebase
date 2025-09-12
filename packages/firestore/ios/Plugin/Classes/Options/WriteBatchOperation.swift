import Foundation
import Capacitor

@objc public class SetOptions: NSObject {
    private var merge: Bool

    init(_ options: JSObject) {
        self.merge = options["merge"] as? Bool ?? false
    }

    public func isMerge() -> Bool {
        return self.merge
    }
}

@objc public class WriteBatchOperation: NSObject {
    private var type: String
    private var reference: String
    private var data: [String: Any]
    private var options: SetOptions?

    init(_ operation: JSObject) {
        self.type = operation["type"] as? String ?? ""
        self.reference = operation["reference"] as? String ?? ""
        self.data = FirebaseFirestoreHelper.createHashMapFromJSObject(operation["data"] as? JSObject ?? [:])
        if let optsObj = operation["options"] as? JSObject {
            self.options = SetOptions(optsObj)
        } else {
            self.options = nil
        }
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

    public func getOptions() -> SetOptions? {
        return self.options
    }
}
