import Foundation
import Capacitor
import FirebaseAuth

public enum AuthType {
    case signIn
    case link
}

@objc public class FirebaseAuthenticationHandler: NSObject {
    static func success(_ call: CAPPluginCall, _ authType: AuthType, _ implementation: FirebaseAuthentication, credential: AuthCredential, idToken: String?, nonce: String?, accessToken: String?) {
        self.success(call, authType, implementation, credential: credential, idToken: idToken, nonce: nonce, accessToken: accessToken, displayName: nil)
    }

    static func success(_ call: CAPPluginCall, _ authType: AuthType, _ implementation: FirebaseAuthentication, credential: AuthCredential, idToken: String?, nonce: String?, accessToken: String?, displayName: String?) {
        if implementation.getConfig().skipNativeAuth == true {
            let result = FirebaseAuthenticationHelper.createSignInResult(credential: credential, user: nil, idToken: idToken, nonce: nonce,
                                                                         accessToken: accessToken, additionalUserInfo: nil, displayName: displayName)
            call.resolve(result)
            return
        }
        (
            (authType == AuthType.link)
                ? implementation.getCurrentUser()!.link
                : Auth.auth().signIn
        )(credential) { (authResult, error) in
            if let error = error {
                call.reject(error.localizedDescription)
                return
            }
            let result = FirebaseAuthenticationHelper.createSignInResult(credential: authResult?.credential, user: authResult?.user,
                                                                         idToken: idToken, nonce: nonce, accessToken: accessToken,
                                                                         additionalUserInfo: authResult?.additionalUserInfo, displayName: displayName)
            call.resolve(result)
        }
    }

    static func failure(_ call: CAPPluginCall, message: String?, error: Error?) {
        let errorMessage = message ?? error?.localizedDescription ?? ""
        call.reject(errorMessage, nil, error)
    }
}
