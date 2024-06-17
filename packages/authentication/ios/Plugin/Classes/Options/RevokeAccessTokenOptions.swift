import Foundation

@objc public class RevokeAccessTokenOptions: NSObject {
    private var token: String

    init(token: String) {
        self.token = token
    }

    func getToken() -> String {
        return token
    }
}
