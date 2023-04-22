import Foundation

@objc class RuntimeError: NSObject, Error {
    let localizedDescription: String

    init(_ description: String) {
        self.localizedDescription = description
    }
}
