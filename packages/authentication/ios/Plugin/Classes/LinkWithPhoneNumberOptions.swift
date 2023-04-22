import Foundation

@objc class LinkWithPhoneNumberOptions: SignInWithPhoneNumberOptions {
    init(phoneNumber: String) {
        super.init(skipNativeAuth: false, phoneNumber: phoneNumber)
    }
}
