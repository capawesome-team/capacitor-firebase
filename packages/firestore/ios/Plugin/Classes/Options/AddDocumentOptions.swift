import Foundation
import Capacitor
import FirebaseFirestore

@objc public class AddDocumentOptions: NSObject {
    private var reference: String
    private var data: [String: Any]

    init(reference: String, data: JSObject, firestore: Firestore) {
        self.reference = reference
        self.data = FirebaseFirestoreHelper.createHashMapFromJSObject(data, firestore: firestore)
    }

    func getReference() -> String {
        return reference
    }

    func getData() -> [String: Any] {
        return data
    }
}
