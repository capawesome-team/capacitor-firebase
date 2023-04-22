import Foundation

@objc class SignInOptions: NSObject {
    private var skipNativeAuth: Bool

    init(skipNativeAuth: Bool) {
        self.skipNativeAuth = skipNativeAuth
    }

    func getSkipNativeAuth() -> Bool {
        return skipNativeAuth
    }
}
