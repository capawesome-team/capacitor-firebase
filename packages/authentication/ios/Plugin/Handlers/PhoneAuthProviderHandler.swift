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

    func link(call: CAPPluginCall) {
        dispatch(call, success: self.pluginImplementation.handleSuccessfulLink, failure: self.pluginImplementation.handleFailedLink)
    }

    func signIn(call: CAPPluginCall) {
        dispatch(call, success: self.pluginImplementation.handleSuccessfulSignIn, failure: self.pluginImplementation.handleFailedSignIn)
    }

    private func dispatch(_ call: CAPPluginCall, success: @escaping AuthSuccessHandler, failure: @escaping AuthFailureHandler) {
        let phoneNumber = call.getString("phoneNumber")
        let verificationId = call.getString("verificationId")
        let verificationCode = call.getString("verificationCode")

        if verificationCode == nil {
            verifyPhoneNumber(call, phoneNumber, failure)
        } else {
            handleVerificationCode(call, verificationId, verificationCode, success)
        }
    }

    private func verifyPhoneNumber(_ call: CAPPluginCall, _ phoneNumber: String?, _ failure: @escaping AuthFailureHandler) {
        guard let phoneNumber = phoneNumber else {
            return
        }
        PhoneAuthProvider.provider()
            .verifyPhoneNumber(phoneNumber, uiDelegate: nil) { verificationID, error in
                if let error = error {
                    failure((message: nil, error: error))
                    return
                }

                var result = FirebaseAuthenticationHelper.createSignInResult(credential: nil, user: nil, idToken: nil, nonce: nil, accessToken: nil, additionalUserInfo: nil)
                result["verificationId"] = verificationID
                call.resolve(result)
            }
    }

    private func handleVerificationCode(_ call: CAPPluginCall, _ verificationID: String?, _ verificationCode: String?, _ success: @escaping AuthSuccessHandler) {
        guard let verificationID = verificationID, let verificationCode = verificationCode else {
            return
        }
        let credential = PhoneAuthProvider.provider().credential(
            withVerificationID: verificationID,
            verificationCode: verificationCode
        )
        success((credential: credential, idToken: nil, nonce: nil, accessToken: nil))
    }
}
