import Foundation

import FirebaseCore
import FirebaseAppCheck

@objc public class FirebaseAppCheck: NSObject {
    override init() {
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }

    @objc public func echo(_ traceName: String) {
    }
}
