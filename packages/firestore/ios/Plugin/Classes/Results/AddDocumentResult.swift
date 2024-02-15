import Foundation
import FirebaseFirestore
import Capacitor

@objc public class AddDocumentResult: NSObject, Result {
    let documentReference: DocumentReference

    init(_ documentReference: DocumentReference) {
        self.documentReference = documentReference
    }

    public func toJSObject() -> AnyObject {
        var referenceResult = JSObject()
        referenceResult["id"] = self.documentReference.documentID
        referenceResult["path"] = self.documentReference.path

        var result = JSObject()
        result["reference"] = referenceResult
        return result as AnyObject
    }
}
