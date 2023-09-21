import Foundation

@objc public class DeleteDocumentOptions: NSObject {
    private var reference: String

    init(reference: String) {
        self.reference = reference
    }

    func getReference() -> String {
        return reference
    }
}
