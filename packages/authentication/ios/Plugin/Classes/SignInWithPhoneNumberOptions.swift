import Foundation

@objc class SignInWithPhoneNumberOptions: SignInOptions {
    private var phoneNumber: String

    init(skipNativeAuth: Bool, phoneNumber: String) {
        self.phoneNumber = phoneNumber
        super.init(skipNativeAuth: skipNativeAuth)
    }

    func getPhoneNumber() -> String {
        return phoneNumber
    }
}
