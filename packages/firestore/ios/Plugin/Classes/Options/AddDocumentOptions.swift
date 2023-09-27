import Foundation
import Capacitor

@objc public class AddDocumentOptions: NSObject {
    private var reference: String
    private var data: [String: Any]

    init(reference: String, data: JSObject) {
        self.reference = reference
        self.data = FirebaseFirestoreHelper.createHashMapFromJSObject(data)
    }

    func getReference() -> String {
        return reference
    }

    func getData() -> [String: Any] {
        return data
    }
}
