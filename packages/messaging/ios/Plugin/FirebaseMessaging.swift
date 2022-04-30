import Foundation

import FirebaseCore

@objc public class FirebaseMessaging: NSObject {

    override init() {
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }
}
