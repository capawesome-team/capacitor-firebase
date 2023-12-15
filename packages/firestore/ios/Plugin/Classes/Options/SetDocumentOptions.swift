import Foundation
import Capacitor

@objc public class SetDocumentOptions: NSObject {
    private var reference: String
    private var data: [String: Any]
    private var merge: Bool

    init(reference: String, data: JSObject, merge: Bool) {
        self.reference = reference
        self.data = FirebaseFirestoreHelper.createHashMapFromJSObject(data)
        self.merge = merge
    }

    func getReference() -> String {
        return reference
    }

    func getData() -> [String: Any] {
        return data
    }

    func getMerge() -> Bool {
        return merge
    }
}
