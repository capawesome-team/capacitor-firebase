import Foundation

@objc class FetchSignInMethodsForEmailOptions: NSObject {
    private var email: String

    init(email: String) {
        self.email = email
    }

    func getEmail() -> String {
        return email
    }
}
