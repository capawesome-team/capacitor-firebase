import Foundation
import Capacitor
import FirebaseCore
import FirebaseAuth

public typealias AuthStateChangedObserver = () -> Void

@objc public class FirebaseAuthentication: NSObject {
    public var authStateObserver: AuthStateChangedObserver?
    private let plugin: FirebaseAuthenticationPlugin
    private let config: FirebaseAuthenticationConfig
    private var appleAuthProviderHandler: AppleAuthProviderHandler?
    private var facebookAuthProviderHandler: FacebookAuthProviderHandler?
    private var googleAuthProviderHandler: GoogleAuthProviderHandler?
    private var oAuthProviderHandler: OAuthProviderHandler?
    private var phoneAuthProviderHandler: PhoneAuthProviderHandler?
    private var savedCall: CAPPluginCall?
    private var resolver: MultiFactorResolver?

    init(plugin: FirebaseAuthenticationPlugin, config: FirebaseAuthenticationConfig) {
        self.plugin = plugin
        self.config = config
        super.init()
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
        self.initAuthProviderHandlers(config: config)
        Auth.auth().addStateDidChangeListener {_, _ in
            self.authStateObserver?()
        }
    }

    @objc func applyActionCode(oobCode: String, completion: @escaping (Error?) -> Void) {
        return Auth.auth().applyActionCode(oobCode, completion: { error in
            completion(error)
        })
    }

    @objc func createUserWithEmailAndPassword(_ call: CAPPluginCall) {
        if config.skipNativeAuth == true {
            call.reject(plugin.errorEmailSignInSkipNativeAuth)
            return
        }

        guard let email = call.getString("email") else {
            call.reject(plugin.errorEmailMissing)
            return
        }
        guard let password = call.getString("password") else {
            call.reject(plugin.errorPasswordMissing)
            return
        }

        self.savedCall = call
        return Auth.auth().createUser(withEmail: email, password: password) { _, error in
            if let error = error {
                self.handleFailedSignIn(message: nil, error: error)
                return
            }
            guard let savedCall = self.savedCall else {
                return
            }
            let user = self.getCurrentUser()
            let result = FirebaseAuthenticationHelper.createSignInResult(credential: nil, user: user, idToken: nil, nonce: nil, accessToken: nil)
            savedCall.resolve(result)
        }
    }

    @objc func confirmPasswordReset(oobCode: String, newPassword: String, completion: @escaping (Error?) -> Void) {
        return Auth.auth().confirmPasswordReset(withCode: oobCode, newPassword: newPassword, completion: { error in
            completion(error)
        })
    }

    @objc func getCurrentUser() -> User? {
        return Auth.auth().currentUser
    }

    @objc func getIdToken(_ forceRefresh: Bool, completion: @escaping (String?, String?) -> Void) {
        guard let user = self.getCurrentUser() else {
            completion(nil, self.plugin.errorNoUserSignedIn)
            return
        }
        user.getIDTokenResult(forcingRefresh: forceRefresh, completion: { result, error in
            if let error = error {
                completion(nil, error.localizedDescription)
                return
            }
            completion(result?.token, nil)
        })
    }

    @objc func sendEmailVerification(user: User, completion: @escaping (Error?) -> Void) {
        user.sendEmailVerification(completion: { error in
            completion(error)
        })
    }

    @objc func sendPasswordResetEmail(email: String, completion: @escaping (Error?) -> Void) {
        return Auth.auth().sendPasswordReset(withEmail: email) { error in
            completion(error)
        }
    }

    @objc func setLanguageCode(_ languageCode: String) {
        Auth.auth().languageCode = languageCode
    }

    @objc func signInWithApple(_ call: CAPPluginCall) {
        self.savedCall = call
        self.appleAuthProviderHandler?.signIn(call: call)
    }

    @objc func signInWithCustomToken(_ call: CAPPluginCall) {
        if config.skipNativeAuth == true {
            call.reject(plugin.errorCustomTokenSkipNativeAuth)
            return
        }

        let token = call.getString("token", "")

        self.savedCall = call
        Auth.auth().signIn(withCustomToken: token) { _, error in
            if let error = error {
                self.handleFailedSignIn(message: nil, error: error)
                return
            }
            guard let savedCall = self.savedCall else {
                return
            }
            let user = self.getCurrentUser()
            let result = FirebaseAuthenticationHelper.createSignInResult(credential: nil, user: user, idToken: nil, nonce: nil, accessToken: nil)
            savedCall.resolve(result)
        }
    }

    @objc func signInWithEmailAndPassword(_ call: CAPPluginCall) {
        if config.skipNativeAuth == true {
            call.reject(plugin.errorEmailSignInSkipNativeAuth)
            return
        }

        let email = call.getString("email", "")
        let password = call.getString("password", "")

        self.savedCall = call
        
        Auth.auth().signIn(withEmail: email,
                           password: password) { (result, error) in
          let authError = error as NSError?
          if (authError == nil || authError!.code != AuthErrorCode.secondFactorRequired.rawValue) {
            // User is not enrolled with a second factor and is successfully signed in.
            print("User is signed in!", authError)
            let user = self.getCurrentUser()
            let result = FirebaseAuthenticationHelper.createSignInResult(credential: nil, user: user, idToken: nil, nonce: nil, accessToken: nil)
            savedCall.resolve(result)
          } else {
              // Second factor is required, or some other issue
            let resolver = authError!.userInfo[AuthErrorUserInfoMultiFactorResolverKey] as! MultiFactorResolver
            self.resolver = resolver
            call.resolve()
          }
        }
    }

    @objc func signInWithFacebook(_ call: CAPPluginCall) {
        self.savedCall = call
        self.facebookAuthProviderHandler?.signIn(call: call)
    }

    @objc func signInWithGithub(_ call: CAPPluginCall) {
        self.savedCall = call
        self.oAuthProviderHandler?.signIn(call: call, providerId: "github.com")
    }

    @objc func signInWithGoogle(_ call: CAPPluginCall) {
        self.savedCall = call
        self.googleAuthProviderHandler?.signIn(call: call)
    }

    @objc func signInWithMicrosoft(_ call: CAPPluginCall) {
        self.savedCall = call
        self.oAuthProviderHandler?.signIn(call: call, providerId: "microsoft.com")
    }

    @objc func signInWithPhoneNumber(_ call: CAPPluginCall) {
        self.savedCall = call
        self.phoneAuthProviderHandler?.signIn(call: call)
    }

    @objc func signInWithTwitter(_ call: CAPPluginCall) {
        self.savedCall = call
        self.oAuthProviderHandler?.signIn(call: call, providerId: "twitter.com")
    }

    @objc func signInWithYahoo(_ call: CAPPluginCall) {
        self.savedCall = call
        self.oAuthProviderHandler?.signIn(call: call, providerId: "yahoo.com")
    }

    @objc func signOut(_ call: CAPPluginCall) {
        do {
            try Auth.auth().signOut()
            googleAuthProviderHandler?.signOut()
            facebookAuthProviderHandler?.signOut()
            call.resolve()
        } catch let signOutError as NSError {
            call.reject("Error signing out: \(signOutError)")
        }
    }

    @objc func updateEmail(user: User, newEmail: String, completion: @escaping (Error?) -> Void) {
        user.updateEmail(to: newEmail) { error in
            completion(error)
        }
    }

    @objc func updatePassword(user: User, newPassword: String, completion: @escaping (Error?) -> Void) {
        user.updatePassword(to: newPassword) { error in
            completion(error)
        }
    }

    @objc func useAppLanguage() {
        Auth.auth().useAppLanguage()
    }

    @objc func useEmulator(_ host: String, _ port: Int) {
        Auth.auth().useEmulator(withHost: host, port: port)
    }
    
    @objc func verifyPhoneNumber(_ call: CAPPluginCall) {
        self.savedCall = call
        
        let phoneNumber = call.getString("phoneNumber", "")
        print("Phone number: ", phoneNumber)
        if (phoneNumber != "") {
            print(self.getCurrentUser())
            self.getCurrentUser()!.multiFactor.getSessionWithCompletion({ (session, error) in
                PhoneAuthProvider.provider().verifyPhoneNumber(
                  phoneNumber,
                  uiDelegate: nil,
                  multiFactorSession: session) { (verificationId, error) in
                    if error != nil {
                        // Failed to verify phone number.
                        print("Error sending sms to enroll: ", error)
                        call.reject()
                    }
                      var result = JSObject()
                      result["verificationId"] = verificationId
                      call.resolve(result)
                }
            })
        } else {
            let hint = self.resolver?.hints[0]
            if hint == nil {
                call.reject("No hint for sending sms. Please call signInWithEmailAndPassword first")
                return;
            }
            PhoneAuthProvider.provider().verifyPhoneNumber(
                with: hint as! PhoneMultiFactorInfo,
              uiDelegate: nil,
              multiFactorSession: resolver?.session) { (verificationId, error) in
                if error != nil {
                    // Failed to verify phone number.
                    print("Error sending sms to sign in: ", error)
                    call.reject()
                }
                  var result = JSObject()
                  result["verificationId"] = verificationId
                  call.resolve(result)
            }
        }
    }

    func handleSuccessfulSignIn(credential: AuthCredential, idToken: String?, nonce: String?, accessToken: String?) {
        if config.skipNativeAuth == true {
            guard let savedCall = self.savedCall else {
                return
            }
            let result = FirebaseAuthenticationHelper.createSignInResult(credential: credential, user: nil, idToken: idToken, nonce: nonce, accessToken: accessToken)
            savedCall.resolve(result)
            return
        }
        Auth.auth().signIn(with: credential) { (authResult, error) in
            if let error = error {
                self.handleFailedSignIn(message: nil, error: error)
                return
            }
            guard let savedCall = self.savedCall else {
                return
            }
            let user = self.getCurrentUser()
            let result = FirebaseAuthenticationHelper.createSignInResult(credential: authResult?.credential, user: user, idToken: idToken, nonce: nonce, accessToken: accessToken)
            savedCall.resolve(result)
        }
    }

    func handleFailedSignIn(message: String?, error: Error?) {
        guard let savedCall = self.savedCall else {
            return
        }
        let errorMessage = message ?? error?.localizedDescription ?? ""
        savedCall.reject(errorMessage, nil, error)
    }

    func getPlugin() -> FirebaseAuthenticationPlugin {
        return self.plugin
    }

    private func initAuthProviderHandlers(config: FirebaseAuthenticationConfig) {
        if config.providers.contains("apple.com") {
            self.appleAuthProviderHandler = AppleAuthProviderHandler(self)
        }
        if config.providers.contains("facebook.com") {
            self.facebookAuthProviderHandler = FacebookAuthProviderHandler(self)
        }
        if config.providers.contains("google.com") {
            self.googleAuthProviderHandler = GoogleAuthProviderHandler(self)
        }
        if config.providers.contains("phone") {
            self.phoneAuthProviderHandler = PhoneAuthProviderHandler(self)
        }
        self.oAuthProviderHandler = OAuthProviderHandler(self)
    }
}
