import Foundation
import Capacitor
import FirebaseFirestore

@objc public class SetDocumentOptions: NSObject {
    private var reference: String
    private var data: [String: Any]
    private var merge: Bool

    init(reference: String, data: JSObject, merge: Bool, firestore: Firestore) {
        self.reference = reference
        self.data = FirebaseFirestoreHelper.createHashMapFromJSObject(data, firestore: firestore)
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
