import Foundation
import Capacitor
import FirebaseCore
import FirebaseAuth

class PhoneAuthProviderHandler: NSObject {
    var pluginImplementation: FirebaseAuthentication

    init(_ pluginImplementation: FirebaseAuthentication) {
        self.pluginImplementation = pluginImplementation
        super.init()
    }

    func signIn(call: CAPPluginCall) {
        let phoneNumber = call.getString("phoneNumber")
        let verificationId = call.getString("verificationId")
        let verificationCode = call.getString("verificationCode")

        if verificationCode == nil {
            verifyPhoneNumber(call, phoneNumber, isLink: false)
        } else {
            handleVerificationCode(call, verificationId, verificationCode, isLink: false)
        }
    }

    func link(call: CAPPluginCall) {
        let phoneNumber = call.getString("phoneNumber")
        let verificationId = call.getString("verificationId")
        let verificationCode = call.getString("verificationCode")

        if verificationCode == nil {
            verifyPhoneNumber(call, phoneNumber, isLink: true)
        } else {
            handleVerificationCode(call, verificationId, verificationCode, isLink: true)
        }
    }

    private func verifyPhoneNumber(_ call: CAPPluginCall, _ phoneNumber: String?, isLink: Bool) {
        guard let phoneNumber = phoneNumber else {
            return
        }
        PhoneAuthProvider.provider()
            .verifyPhoneNumber(phoneNumber, uiDelegate: nil) { verificationID, error in
                /**
                 * @deprecated This code was replaced by event listener.
                 *
                 * Caution: The call must be resolved earlier.
                 */
                if let error = error {
                    self.pluginImplementation.handlePhoneVerificationFailed(error)
                    if isLink == true {
                        self.pluginImplementation.handleFailedLink(message: nil, error: error)
                    } else {
                        self.pluginImplementation.handleFailedSignIn(message: nil, error: error)
                    }
                    return
                }

                self.pluginImplementation.handlePhoneCodeSent(verificationID ?? "")
                var result = FirebaseAuthenticationHelper.createSignInResult(credential: nil, user: nil, idToken: nil, nonce: nil, accessToken: nil, additionalUserInfo: nil)
                result["verificationId"] = verificationID
                call.resolve(result)
            }
    }

    private func handleVerificationCode(_ call: CAPPluginCall, _ verificationID: String?, _ verificationCode: String?, isLink: Bool) {
        guard let verificationID = verificationID, let verificationCode = verificationCode else {
            return
        }
        let credential = PhoneAuthProvider.provider().credential(
            withVerificationID: verificationID,
            verificationCode: verificationCode
        )
        if isLink == true {
            self.pluginImplementation.handleSuccessfulLink(credential: credential, idToken: nil, nonce: nil, accessToken: nil)
        } else {
            self.pluginImplementation.handleSuccessfulSignIn(credential: credential, idToken: nil, nonce: nil, accessToken: nil)
        }
    }
}
