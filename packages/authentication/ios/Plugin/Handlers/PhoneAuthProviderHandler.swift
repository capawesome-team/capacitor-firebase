import Foundation
import Capacitor
import FirebaseCore
import FirebaseAuth

class PhoneAuthProviderHandler: NSObject {
    var pluginImplementation: FirebaseAuthentication
    fileprivate var isLink: Bool?

    init(_ pluginImplementation: FirebaseAuthentication) {
        self.pluginImplementation = pluginImplementation
        super.init()
    }

    func link(call: CAPPluginCall) {
        if self.pluginImplementation.getCurrentUser() == nil {
            call.reject(self.pluginImplementation.getPlugin().errorNoUserSignedIn)
            return
        }
        dispatch(call, true)
    }

    func signIn(call: CAPPluginCall) {
        dispatch(call, false)
    }

    private func dispatch(_ call: CAPPluginCall, _ isLink: Bool) {
        self.isLink = isLink
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
                guard let isLink = self.isLink else {
                    return
                }
                if let error = error {
                    isLink
                        ? self.pluginImplementation.handleFailedLink(message: nil, error: error)
                        : self.pluginImplementation.handleFailedSignIn(message: nil, error: error)
                    return
                }

                var result = FirebaseAuthenticationHelper.createSignInResult(credential: nil, user: nil, idToken: nil, nonce: nil, accessToken: nil, additionalUserInfo: nil)
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
        guard let isLink = self.isLink else {
            return
        }
        isLink
            ? self.pluginImplementation.handleSuccessfulLink(credential: credential, idToken: nil, nonce: nil, accessToken: nil)
            : self.pluginImplementation.handleSuccessfulSignIn(credential: credential, idToken: nil, nonce: nil, accessToken: nil)
    }
}
