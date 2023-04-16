import Foundation
import FirebaseAuth
import Capacitor

@objc class SignInResult: NSObject, Result {
    let user: User?
    let credential: AuthCredential?
    let additionalUserInfo: AdditionalUserInfo?
    
    init(user: User?, credential: AuthCredential?, additionalUserInfo: AdditionalUserInfo?) {
        self.user = user
        self.credential = credential
        self.additionalUserInfo = additionalUserInfo
    }

    func toJSObject() -> AnyObject {
        return FirebaseAuthenticationHelper.createSignInResult(credential: credential, user: user, idToken: nil, nonce: nil, accessToken: nil, additionalUserInfo: additionalUserInfo) as AnyObject
    }
}
