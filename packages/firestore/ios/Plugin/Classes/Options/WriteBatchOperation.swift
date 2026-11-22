import Foundation
import Capacitor

@objc public class SetOptions: NSObject {
    let merge: Bool

    init(_ options: JSObject) {
        self.merge = options["merge"] as? Bool ?? false
    }
}

@objc public class WriteBatchOperation: NSObject {
    let data: JSObject
    let options: SetOptions?
    let reference: String
    let type: String

    init(_ operation: JSObject) {
        self.type = operation["type"] as? String ?? ""
        self.reference = operation["reference"] as? String ?? ""
        self.data = operation["data"] as? JSObject ?? [:]
        if let optsObj = operation["options"] as? JSObject {
            self.options = SetOptions(optsObj)
        } else {
            self.options = nil
        }
    }
}
