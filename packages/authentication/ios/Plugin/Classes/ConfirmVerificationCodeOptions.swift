import Foundation

@objc class ConfirmVerificationCodeOptions: NSObject {
    private var verificationId: String
    private var verificationCode: String

    init(verificationId: String, verificationCode: String) {
        self.verificationId = verificationId
        self.verificationCode = verificationCode
    }

    func getVerificationId() -> String {
        return verificationId
    }

    func getVerificationCode() -> String {
        return verificationCode
    }
}
