import Foundation

@objc public class GetCountFromServerOptions: NSObject {
    private let reference: String

    init(reference: String) {
        self.reference = reference
    }

    func getReference() -> String {
        return reference
    }
}
