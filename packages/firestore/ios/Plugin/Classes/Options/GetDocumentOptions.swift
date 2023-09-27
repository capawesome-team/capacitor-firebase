import Foundation

@objc public class GetDocumentOptions: NSObject {
    private var reference: String

    init(reference: String) {
        self.reference = reference
    }

    func getReference() -> String {
        return reference
    }
}
