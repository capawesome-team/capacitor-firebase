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
        if self.pluginImplementation.getCurrentUser() == nil {
            call.reject(self.pluginImplementation.getPlugin().errorNoUserSignedIn)
            return
        }
        dispatch(call, AuthType.link)
    }

    func signIn(call: CAPPluginCall) {
        dispatch(call, AuthType.signIn)
    }

    private func dispatch(_ call: CAPPluginCall, _ authType: AuthType) {
        let phoneNumber = call.getString("phoneNumber")
        let verificationId = call.getString("verificationId")
        let verificationCode = call.getString("verificationCode")

        if verificationCode == nil {
            verifyPhoneNumber(call, authType, phoneNumber)
        } else {
            handleVerificationCode(call, authType, verificationId, verificationCode)
        }
    }

    private func verifyPhoneNumber(_ call: CAPPluginCall, _ authType: AuthType, _ phoneNumber: String?) {
        guard let phoneNumber = phoneNumber else {
            return
        }
        PhoneAuthProvider.provider()
            .verifyPhoneNumber(phoneNumber, uiDelegate: nil) { verificationID, error in
                if let error = error {
                    FirebaseAuthenticationHandler.failure(call, message: nil, error: error)
                    return
                }

                var result = FirebaseAuthenticationHelper.createSignInResult(credential: nil, user: nil, idToken: nil, nonce: nil, accessToken: nil, additionalUserInfo: nil)
                result["verificationId"] = verificationID
                call.resolve(result)
            }
    }

    private func handleVerificationCode(_ call: CAPPluginCall, _ authType: AuthType, _ verificationID: String?, _ verificationCode: String?) {
        guard let verificationID = verificationID, let verificationCode = verificationCode else {
            return
        }
        let credential = PhoneAuthProvider.provider().credential(
            withVerificationID: verificationID,
            verificationCode: verificationCode
        )
        FirebaseAuthenticationHandler.success(call, authType, self.pluginImplementation, credential: credential, idToken: nil, nonce: nil, accessToken: nil)
    }
}
