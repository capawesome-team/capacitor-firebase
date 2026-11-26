import Foundation

@objc class RuntimeError: NSObject, LocalizedError {
    let errorDescription: String?

    init(_ description: String) {
        self.errorDescription = description
    }
}
