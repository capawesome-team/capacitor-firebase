import Foundation

@objc public class FirebaseFunctions: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
