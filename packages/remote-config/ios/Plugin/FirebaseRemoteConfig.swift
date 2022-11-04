import Foundation

import FirebaseCore
import FirebaseRemoteConfig

@objc public class FirebaseRemoteConfig: NSObject {
    override init() {
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }

    @objc public func echo(_ traceName: String) {
    }
}
