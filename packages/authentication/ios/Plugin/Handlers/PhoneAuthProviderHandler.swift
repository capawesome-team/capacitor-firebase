import Foundation
import Capacitor
import FirebaseCore
import FirebaseAuth

class PhoneAuthProviderHandler: NSObject {
    private var pluginImplementation: FirebaseAuthentication
    private var signInOnConfirm = true
    private var skipNativeAuthOnConfirm = false

    init(_ pluginImplementation: FirebaseAuthentication) {
        self.pluginImplementation = pluginImplementation
        super.init()
    }

    func signIn(_ options: SignInWithPhoneNumberOptions) {
        signInOnConfirm = true
        skipNativeAuthOnConfirm = options.getSkipNativeAuth()
        verifyPhoneNumber(options)
    }

    func link(_ options: LinkWithPhoneNumberOptions) {
        signInOnConfirm = false
        skipNativeAuthOnConfirm = options.getSkipNativeAuth()
        verifyPhoneNumber(options)
    }

    func confirmVerificationCode(_ options: ConfirmVerificationCodeOptions, completion: @escaping (Result?, Error?) -> Void) {
        let credential = PhoneAuthProvider.provider().credential(
            withVerificationID: options.getVerificationId(),
            verificationCode: options.getVerificationCode()
        )
        if self.signInOnConfirm {
            pluginImplementation.signInWithCredential(SignInOptions(skipNativeAuth: skipNativeAuthOnConfirm), credential: credential, completion: completion)
        } else {
            pluginImplementation.linkWithCredential(credential: credential, completion: completion)
        }
    }

    private func verifyPhoneNumber(_ options: SignInWithPhoneNumberOptions) {
        PhoneAuthProvider.provider()
            .verifyPhoneNumber(options.getPhoneNumber(), uiDelegate: nil) { verificationID, error in
                if error != nil {
                    self.pluginImplementation.handlePhoneVerificationFailed(error!)
                } else {
                    self.pluginImplementation.handlePhoneCodeSent(verificationID ?? "")
                }
            }
    }

}
