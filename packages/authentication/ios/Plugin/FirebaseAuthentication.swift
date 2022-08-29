import Foundation
import Capacitor
import FirebaseCore
import FirebaseAuth

public typealias AuthStateChangedObserver = () -> Void

public typealias AuthSuccessHandler = ((credential: AuthCredential, idToken: String?, nonce: String?, accessToken: String?)) -> Void
public typealias AuthFailureHandler = ((message: String?, error: Error?)) -> Void

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
        let skipNativeAuth = call.getBool("skipNativeAuth", config.skipNativeAuth)
        if skipNativeAuth == true {
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
            guard let savedCall = self.savedCall else {
                return
            }
            if let error = error {
                FirebaseAuthenticationHandler.failure(savedCall, message: nil, error: error)
                return
            }
            let user = self.getCurrentUser()
            let result = FirebaseAuthenticationHelper.createSignInResult(credential: nil, user: user, idToken: nil, nonce: nil,
                                                                         accessToken: nil, additionalUserInfo: nil)
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

    @objc func getTenantId() -> String? {
        return Auth.auth().tenantID
    }

    @objc func isSignInWithEmailLink(link: String) -> Bool {
        return Auth.auth().isSignIn(withEmailLink: link)
    }

    @objc func linkWithApple(_ call: CAPPluginCall) {
        self.appleAuthProviderHandler?.link(call: call)
    }

    @objc func linkWithEmailAndPassword(_ call: CAPPluginCall) {
        if config.skipNativeAuth == true {
            call.reject(plugin.errorEmailSignInSkipNativeAuth)
            return
        }
        guard let user = self.getCurrentUser() else {
            call.reject(plugin.errorNoUserSignedIn)
            return
        }

        let email = call.getString("email", "")
        let password = call.getString("password", "")

        let credential = EmailAuthProvider.credential(withEmail: email, password: password)

        self.savedCall = call
        user.link(with: credential) { authResult, error in
            guard let savedCall = self.savedCall else {
                return
            }
            if let error = error {
                FirebaseAuthenticationHandler.failure(savedCall, message: nil, error: error)
                return
            }
            let result = FirebaseAuthenticationHelper.createSignInResult(credential: authResult?.credential, user: authResult?.user,
                                                                         idToken: nil, nonce: nil, accessToken: nil,
                                                                         additionalUserInfo: authResult?.additionalUserInfo)
            savedCall.resolve(result)
        }
    }

    @objc func linkWithEmailLink(_ call: CAPPluginCall) {
        guard let user = self.getCurrentUser() else {
            call.reject(plugin.errorNoUserSignedIn)
            return
        }

        let email = call.getString("email", "")
        let emailLink = call.getString("emailLink", "")

        let credential = EmailAuthProvider.credential(withEmail: email, link: emailLink)

        self.savedCall = call
        user.link(with: credential) { authResult, error in
            guard let savedCall = self.savedCall else {
                return
            }
            if let error = error {
                FirebaseAuthenticationHandler.failure(savedCall, message: nil, error: error)
                return
            }
            let result = FirebaseAuthenticationHelper.createSignInResult(credential: authResult?.credential, user: authResult?.user,
                                                                         idToken: nil, nonce: nil, accessToken: nil,
                                                                         additionalUserInfo: authResult?.additionalUserInfo)
            savedCall.resolve(result)
        }
    }

    @objc func linkWithFacebook(_ call: CAPPluginCall) {
        self.facebookAuthProviderHandler?.link(call: call)
    }

    @objc func linkWithGithub(_ call: CAPPluginCall) {
        self.oAuthProviderHandler?.link(call: call, providerId: ProviderId.gitHub)
    }

    @objc func linkWithGoogle(_ call: CAPPluginCall) {
        self.googleAuthProviderHandler?.link(call: call)
    }

    @objc func linkWithMicrosoft(_ call: CAPPluginCall) {
        self.oAuthProviderHandler?.link(call: call, providerId: ProviderId.microsoft)
    }

    @objc func linkWithPhoneNumber(_ call: CAPPluginCall) {
        self.phoneAuthProviderHandler?.link(call: call)
    }

    @objc func linkWithTwitter(_ call: CAPPluginCall) {
        self.oAuthProviderHandler?.link(call: call, providerId: ProviderId.twitter)
    }

    @objc func linkWithYahoo(_ call: CAPPluginCall) {
        self.oAuthProviderHandler?.link(call: call, providerId: ProviderId.yahoo)
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

    @objc func sendSignInLinkToEmail(email: String, actionCodeSettings: ActionCodeSettings, completion: @escaping (Error?) -> Void) {
        return Auth.auth().sendSignInLink(toEmail: email, actionCodeSettings: actionCodeSettings) { error in
            completion(error)
        }
    }

    @objc func setLanguageCode(_ languageCode: String) {
        Auth.auth().languageCode = languageCode
    }

    @objc func setTenantId(tenantId: String) {
        Auth.auth().tenantID = tenantId
    }

    @objc func signInAnonymously(_ call: CAPPluginCall) {
        self.savedCall = call
        Auth.auth().signInAnonymously { authResult, error in
            guard let savedCall = self.savedCall else {
                return
            }
            if let error = error {
                FirebaseAuthenticationHandler.failure(savedCall, message: nil, error: error)
                return
            }
            let result = FirebaseAuthenticationHelper.createSignInResult(credential: authResult?.credential, user: authResult?.user,
                                                                         idToken: nil, nonce: nil, accessToken: nil,
                                                                         additionalUserInfo: authResult?.additionalUserInfo)
            savedCall.resolve(result)
        }
    }

    @objc func signInWithApple(_ call: CAPPluginCall) {
        self.savedCall = call
        self.appleAuthProviderHandler?.signIn(call: call)
    }

    @objc func signInWithCustomToken(_ call: CAPPluginCall) {
        let skipNativeAuth = call.getBool("skipNativeAuth", config.skipNativeAuth)
        if skipNativeAuth == true {
            call.reject(plugin.errorCustomTokenSkipNativeAuth)
            return
        }

        let token = call.getString("token", "")

        self.savedCall = call
        Auth.auth().signIn(withCustomToken: token) { _, error in
            guard let savedCall = self.savedCall else {
                return
            }
            if let error = error {
                FirebaseAuthenticationHandler.failure(savedCall, message: nil, error: error)
                return
            }
            let user = self.getCurrentUser()
            let result = FirebaseAuthenticationHelper.createSignInResult(credential: nil, user: user, idToken: nil, nonce: nil,
                                                                         accessToken: nil, additionalUserInfo: nil)
            savedCall.resolve(result)
        }
    }

    @objc func signInWithEmailAndPassword(_ call: CAPPluginCall) {
        let skipNativeAuth = call.getBool("skipNativeAuth", config.skipNativeAuth)
        if skipNativeAuth == true {
            call.reject(plugin.errorEmailSignInSkipNativeAuth)
            return
        }

        let email = call.getString("email", "")
        let password = call.getString("password", "")

        self.savedCall = call
        Auth.auth().signIn(withEmail: email, password: password) { _, error in
            guard let savedCall = self.savedCall else {
                return
            }
            if let error = error {
                FirebaseAuthenticationHandler.failure(savedCall, message: nil, error: error)
                return
            }
            let user = self.getCurrentUser()
            let result = FirebaseAuthenticationHelper.createSignInResult(credential: nil, user: user, idToken: nil, nonce: nil,
                                                                         accessToken: nil, additionalUserInfo: nil)
            savedCall.resolve(result)
        }
    }

    @objc func signInWithEmailLink(_ call: CAPPluginCall) {
        let skipNativeAuth = call.getBool("skipNativeAuth", config.skipNativeAuth)
        if skipNativeAuth == true {
            call.reject(plugin.errorEmailLinkSignInSkipNativeAuth)
            return
        }

        let email = call.getString("email", "")
        let emailLink = call.getString("emailLink", "")

        self.savedCall = call
        Auth.auth().signIn(withEmail: email, link: emailLink) { authResult, error in
            guard let savedCall = self.savedCall else {
                return
            }
            if let error = error {
                FirebaseAuthenticationHandler.failure(savedCall, message: nil, error: error)
                return
            }
            let result = FirebaseAuthenticationHelper.createSignInResult(credential: authResult?.credential, user: authResult?.user,
                                                                         idToken: nil, nonce: nil, accessToken: nil,
                                                                         additionalUserInfo: authResult?.additionalUserInfo)
            savedCall.resolve(result)
        }
    }

    @objc func signInWithFacebook(_ call: CAPPluginCall) {
        self.savedCall = call
        self.facebookAuthProviderHandler?.signIn(call: call)
    }

    @objc func signInWithGithub(_ call: CAPPluginCall) {
        self.savedCall = call
        self.oAuthProviderHandler?.signIn(call: call, providerId: ProviderId.gitHub)
    }

    @objc func signInWithGoogle(_ call: CAPPluginCall) {
        self.savedCall = call
        self.googleAuthProviderHandler?.signIn(call: call)
    }

    @objc func signInWithMicrosoft(_ call: CAPPluginCall) {
        self.savedCall = call
        self.oAuthProviderHandler?.signIn(call: call, providerId: ProviderId.microsoft)
    }

    @objc func signInWithPhoneNumber(_ call: CAPPluginCall) {
        self.savedCall = call
        self.phoneAuthProviderHandler?.signIn(call: call)
    }

    @objc func signInWithTwitter(_ call: CAPPluginCall) {
        self.savedCall = call
        self.oAuthProviderHandler?.signIn(call: call, providerId: ProviderId.twitter)
    }

    @objc func signInWithYahoo(_ call: CAPPluginCall) {
        self.savedCall = call
        self.oAuthProviderHandler?.signIn(call: call, providerId: ProviderId.yahoo)
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

    @objc func unlink(user: User, providerId: String, completion: @escaping (User?, Error?) -> Void) {
        user.unlink(fromProvider: providerId) { user, error in
            completion(user, error)
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

    func getPlugin() -> FirebaseAuthenticationPlugin {
        return self.plugin
    }

    func getConfig() -> FirebaseAuthenticationConfig {
        return self.config
    }

    private func initAuthProviderHandlers(config: FirebaseAuthenticationConfig) {
        if config.providers.contains(ProviderId.apple) {
            self.appleAuthProviderHandler = AppleAuthProviderHandler(self)
        }
        if config.providers.contains(ProviderId.facebook) {
            self.facebookAuthProviderHandler = FacebookAuthProviderHandler(self)
        }
        if config.providers.contains(ProviderId.google) {
            self.googleAuthProviderHandler = GoogleAuthProviderHandler(self)
        }
        if config.providers.contains(ProviderId.phone) {
            self.phoneAuthProviderHandler = PhoneAuthProviderHandler(self)
        }
        self.oAuthProviderHandler = OAuthProviderHandler(self)
    }
}
