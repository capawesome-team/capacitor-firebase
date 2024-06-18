import Foundation

@objc public class Functions: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
