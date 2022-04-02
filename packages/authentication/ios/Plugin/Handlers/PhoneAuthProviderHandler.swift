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
            verifyPhoneNumber(call, phoneNumber)
        } else {
            handleVerificationCode(call, verificationId, verificationCode)
        }
    }

    private func verifyPhoneNumber(_ call: CAPPluginCall, _ phoneNumber: String?) {
        guard let phoneNumber = phoneNumber else {
            return
        }
        PhoneAuthProvider.provider()
            .verifyPhoneNumber(phoneNumber, uiDelegate: nil) { verificationID, error in
                if let error = error {
                    self.pluginImplementation.handleFailedSignIn(message: nil, error: error)
                    return
                }

                var result = FirebaseAuthenticationHelper.createSignInResult(credential: nil, user: nil, idToken: nil, nonce: nil, accessToken: nil)
                result["verificationId"] = verificationID
                call.resolve(result)
            }
    }

    private func handleVerificationCode(_ call: CAPPluginCall, _ verificationID: String?, _ verificationCode: String?) {
        guard let verificationID = verificationID, let verificationCode = verificationCode else {
            return
        }
        let credential = PhoneAuthProvider.provider().credential(
            withVerificationID: verificationID,
            verificationCode: verificationCode
        )
        self.pluginImplementation.handleSuccessfulSignIn(credential: credential, idToken: nil, nonce: nil, accessToken: nil)
    }
}
