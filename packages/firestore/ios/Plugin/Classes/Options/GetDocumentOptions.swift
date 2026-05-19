import Foundation

@objc public class GetDocumentOptions: NSObject {
    private var reference: String
    private var serverTimestampBehavior: String?

    init(reference: String, serverTimestampBehavior: String? = nil) {
        self.reference = reference
        self.serverTimestampBehavior = serverTimestampBehavior
    }

    func getReference() -> String {
        return reference
    }

    func getServerTimestampBehavior() -> String? {
        return serverTimestampBehavior
    }
}
