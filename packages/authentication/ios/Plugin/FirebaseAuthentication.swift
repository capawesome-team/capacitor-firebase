import Foundation
import Capacitor
import FirebaseCore
import FirebaseAuth
import AppTrackingTransparency

public typealias AuthStateChangedObserver = () -> Void

// swiftlint:disable type_body_length
@objc public class FirebaseAuthentication: NSObject {
    private var appleAuthProviderHandler: AppleAuthProviderHandler?
    private let config: FirebaseAuthenticationConfig
    private var facebookAuthProviderHandler: FacebookAuthProviderHandler?
    private var gameCenterAuthProviderHandler: GameCenterAuthProviderHandler?
    private var googleAuthProviderHandler: GoogleAuthProviderHandler?
    private var oAuthProviderHandler: OAuthProviderHandler?
    private var phoneAuthProviderHandler: PhoneAuthProviderHandler?
    private let plugin: FirebaseAuthenticationPlugin
    private var savedCall: CAPPluginCall?

    init(plugin: FirebaseAuthenticationPlugin, config: FirebaseAuthenticationConfig) {
        self.plugin = plugin
        self.config = config
        super.init()
        if FirebaseApp.app() == nil {
            guard FirebaseOptions.defaultOptions() != nil else {
                CAPLog.print("[FirebaseAuthentication] Firebase was not configured: GoogleService-Info.plist is missing from the app bundle.")
                return
            }
            FirebaseApp.configure()
        }
        self.initAuthProviderHandlers(config: config)
        _ = Auth.auth().addStateDidChangeListener {_, _ in
            self.handleAuthStateChange()
        }
        _ = Auth.auth().addIDTokenDidChangeListener {_, _ in
            self.handleIdTokenChange()
        }
        if let authDomain = config.authDomain {
            Auth.auth().customAuthDomain = authDomain
        }
    }

    @objc public func applyActionCode(_ options: ApplyActionCodeOptions, completion: @escaping (Error?) -> Void) {
        let oobCode = options.oobCode

        return Auth.auth().applyActionCode(oobCode, completion: { error in
            completion(error)
        })
    }

    @objc public func checkAppTrackingTransparencyPermission(completion: @escaping (Result?, Error?) -> Void) {
        completion(CheckAppTrackingTransparencyPermissionResult(ATTrackingManager.trackingAuthorizationStatus), nil)
    }

    @objc public func confirmPasswordReset(_ options: ConfirmPasswordResetOptions, completion: @escaping (Error?) -> Void) {
        let oobCode = options.oobCode
        let newPassword = options.newPassword

        return Auth.auth().confirmPasswordReset(withCode: oobCode, newPassword: newPassword, completion: { error in
            completion(error)
        })
    }

    @objc public func createUserWithEmailAndPassword(_ options: CreateUserWithEmailAndPasswordOptions, completion: @escaping (Result?, Error?) -> Void) {
        let email = options.email
        let password = options.password

        return Auth.auth().createUser(withEmail: email, password: password) { _, error in
            if let error = error {
                completion(nil, error)
                return
            }
            let user = Auth.auth().currentUser
            let result = SignInResult(user: user, credential: nil, additionalUserInfo: nil)
            completion(result, nil)
        }
    }

    @objc public func deleteUser(completion: @escaping (Error?) -> Void) {
        guard let user = Auth.auth().currentUser else {
            completion(CustomError.noUserSignedIn)
            return
        }
        user.delete { error in
            completion(error)
        }
    }

    @objc public func fetchSignInMethodsForEmail(_ options: FetchSignInMethodsForEmailOptions, completion: @escaping (Result?, Error?) -> Void) {
        let email = options.email

        Auth.auth().fetchSignInMethods(forEmail: email) { signInMethods, error in
            if let error = error {
                completion(nil, error)
                return
            }
            let result = FetchSignInMethodsForEmailResult(signInMethods ?? [])
            completion(result, nil)
        }
    }

    @objc public func getCurrentUser(completion: @escaping (Result?, Error?) -> Void) {
        let user = Auth.auth().currentUser
        let result = GetCurrentUserResult(user: user)
        completion(result, nil)
    }

    @objc public func getIdToken(_ options: GetIdTokenOptions, completion: @escaping (Result?, Error?) -> Void) {
        let forceRefresh = options.forceRefresh

        getIdToken(forceRefresh, completion: completion)
    }

    @objc public func getIdTokenResult(_ options: GetIdTokenResultOptions, completion: @escaping (Result?, Error?) -> Void) {
        let forceRefresh = options.forceRefresh

        guard let user = Auth.auth().currentUser else {
            let error = CustomError.noUserSignedIn
            completion(nil, error)
            return
        }
        user.getIDTokenResult(forcingRefresh: forceRefresh) { tokenResult, error in
            if let error = error {
                CAPLog.print("[", self.plugin.tag, "] ", error)
                completion(nil, error)
                return
            }
            let idTokenResult = GetIdTokenResultResult(tokenResult: tokenResult)
            completion(idTokenResult, nil)
        }
    }

    @objc public func getTenantId(completion: @escaping (Result?, Error?) -> Void) {
        let tenantId = Auth.auth().tenantID
        let result = GetTenantIdResult(tenantId: tenantId)
        completion(result, nil)
    }

    @objc public func isSignInWithEmailLink(_ options: IsSignInWithEmailLinkOptions, completion: @escaping (Result?, Error?) -> Void) {
        let emailLink = options.emailLink

        let isSignInWithEmailLink = Auth.auth().isSignIn(withEmailLink: emailLink)
        let result = IsSignInWithEmailLinkResult(isSignInWithEmailLink: isSignInWithEmailLink)
        completion(result, nil)
    }

    @objc public func linkWithEmailAndPassword(_ options: LinkWithEmailAndPasswordOptions, completion: @escaping (Result?, Error?) -> Void) {
        let skipNativeAuth = options.getSkipNativeAuth()
        if skipNativeAuth == true {
            completion(nil, CustomError.emailSignInSkipNativeAuth)
            return
        }
        guard let user = Auth.auth().currentUser else {
            completion(nil, CustomError.noUserSignedIn)
            return
        }

        let email = options.email
        let password = options.password

        let credential = EmailAuthProvider.credential(withEmail: email, password: password)

        user.link(with: credential) { authResult, error in
            if let error = error {
                completion(nil, error)
                return
            }
            let result = SignInResult(user: authResult?.user, credential: authResult?.credential, additionalUserInfo: authResult?.additionalUserInfo)
            completion(result, nil)
        }
    }

    @objc public func linkWithEmailLink(_ options: LinkWithEmailLinkOptions, completion: @escaping (Result?, Error?) -> Void) {
        guard let user = Auth.auth().currentUser else {
            completion(nil, CustomError.noUserSignedIn)
            return
        }

        let email = options.email
        let emailLink = options.emailLink

        let credential = EmailAuthProvider.credential(withEmail: email, link: emailLink)

        user.link(with: credential) { authResult, error in
            if let error = error {
                completion(nil, error)
                return
            }
            let result = SignInResult(user: authResult?.user, credential: authResult?.credential, additionalUserInfo: authResult?.additionalUserInfo)
            completion(result, nil)
        }
    }

    @objc public func reload(completion: @escaping (Error?) -> Void) {
        guard let user = Auth.auth().currentUser else {
            completion(CustomError.noUserSignedIn)
            return
        }
        user.reload { error in
            completion(error)
        }
    }

    @objc public func requestAppTrackingTransparencyPermission(completion: @escaping (Result?, Error?) -> Void) {
        ATTrackingManager.requestTrackingAuthorization { result in
            completion(CheckAppTrackingTransparencyPermissionResult(result), nil)
        }
    }

    @objc public func revokeAccessToken(_ options: RevokeAccessTokenOptions, completion: @escaping (Error?) -> Void) {
        let token = options.token

        Auth.auth().revokeToken(withAuthorizationCode: token) { error in
            completion(error)
        }
    }

    @objc public func sendEmailVerification(_ options: SendEmailVerificationOptions, completion: @escaping (Error?) -> Void) {
        let actionCodeSettings = FirebaseAuthenticationHelper.createActionCodeSettings(options.actionCodeSettings)

        guard let user = Auth.auth().currentUser else {
            completion(CustomError.noUserSignedIn)
            return
        }

        let completion: (Error?) -> Void = { error in
            completion(error)
        }
        if let actionCodeSettings = actionCodeSettings {
            user.sendEmailVerification(with: actionCodeSettings, completion: completion)
        } else {
            user.sendEmailVerification(completion: completion)
        }
    }

    @objc public func sendPasswordResetEmail(_ options: SendPasswordResetEmailOptions, completion: @escaping (Error?) -> Void) {
        let email = options.email
        let actionCodeSettings = FirebaseAuthenticationHelper.createActionCodeSettings(options.actionCodeSettings)

        let completion: (Error?) -> Void = { error in
            completion(error)
        }
        if let actionCodeSettings = actionCodeSettings {
            Auth.auth().sendPasswordReset(withEmail: email, actionCodeSettings: actionCodeSettings, completion: completion)
        } else {
            Auth.auth().sendPasswordReset(withEmail: email, completion: completion)
        }
    }

    @objc public func sendSignInLinkToEmail(_ options: SendSignInLinkToEmailOptions, completion: @escaping (Error?) -> Void) {
        let email = options.email
        guard let actionCodeSettings = FirebaseAuthenticationHelper.createActionCodeSettings(options.actionCodeSettings) else {
            completion(CustomError.actionCodeSettingsMissing)
            return
        }

        return Auth.auth().sendSignInLink(toEmail: email, actionCodeSettings: actionCodeSettings) { error in
            completion(error)
        }
    }

    @objc public func setLanguageCode(_ options: SetLanguageCodeOptions, completion: @escaping (Error?) -> Void) {
        let languageCode = options.languageCode

        Auth.auth().languageCode = languageCode
        completion(nil)
    }

    @objc public func setTenantId(_ options: SetTenantIdOptions, completion: @escaping (Error?) -> Void) {
        let tenantId = options.tenantId

        Auth.auth().tenantID = tenantId
        completion(nil)
    }

    @objc public func signInAnonymously(completion: @escaping (Result?, Error?) -> Void) {
        let skipNativeAuth = config.skipNativeAuth
        if skipNativeAuth == true {
            completion(nil, CustomError.signInAnonymouslySkipNativeAuth)
            return
        }
        Auth.auth().signInAnonymously { authResult, error in
            if let error = error {
                completion(nil, error)
                return
            }
            let result = SignInResult(user: authResult?.user, credential: authResult?.credential, additionalUserInfo: authResult?.additionalUserInfo)
            completion(result, nil)
        }
    }

    @objc public func signInWithCustomToken(_ options: SignInWithCustomTokenOptions, completion: @escaping (Result?, Error?) -> Void) {
        let skipNativeAuth = options.getSkipNativeAuth()
        if skipNativeAuth == true {
            completion(nil, CustomError.customTokenSkipNativeAuth)
            return
        }

        let token = options.token

        Auth.auth().signIn(withCustomToken: token) { _, error in
            if let error = error {
                completion(nil, error)
                return
            }
            let user = Auth.auth().currentUser
            let result = SignInResult(user: user, credential: nil, additionalUserInfo: nil)
            completion(result, nil)
        }
    }

    @objc public func signInWithEmailAndPassword(_ options: SignInWithEmailAndPasswordOptions, completion: @escaping (Result?, Error?) -> Void) {
        let skipNativeAuth = config.skipNativeAuth
        if skipNativeAuth == true {
            completion(nil, CustomError.emailSignInSkipNativeAuth)
            return
        }

        let email = options.email
        let password = options.password

        Auth.auth().signIn(withEmail: email, password: password) { _, error in
            if let error = error {
                completion(nil, error)
                return
            }
            let user = Auth.auth().currentUser
            let result = SignInResult(user: user, credential: nil, additionalUserInfo: nil)
            completion(result, nil)
        }
    }

    @objc public func signInWithEmailLink(_ options: SignInWithEmailLinkOptions, completion: @escaping (Result?, Error?) -> Void) {
        let skipNativeAuth = config.skipNativeAuth
        if skipNativeAuth == true {
            completion(nil, CustomError.emailLinkSignInSkipNativeAuth)
            return
        }

        let email = options.email
        let emailLink = options.emailLink

        Auth.auth().signIn(withEmail: email, link: emailLink) { authResult, error in
            if let error = error {
                completion(nil, error)
                return
            }
            let result = SignInResult(user: authResult?.user, credential: authResult?.credential, additionalUserInfo: authResult?.additionalUserInfo)
            completion(result, nil)
        }
    }

    @objc public func signOut(completion: @escaping (Error?) -> Void) {
        do {
            try Auth.auth().signOut()
            googleAuthProviderHandler?.signOut()
            facebookAuthProviderHandler?.signOut()
            completion(nil)
        } catch {
            completion(error)
        }
    }

    @objc public func unlink(_ options: UnlinkOptions, completion: @escaping (Result?, Error?) -> Void) {
        let providerId = options.providerId

        guard let user = Auth.auth().currentUser else {
            completion(nil, CustomError.noUserSignedIn)
            return
        }
        user.unlink(fromProvider: providerId) { user, error in
            if let error = error {
                completion(nil, error)
                return
            }
            let result = UnlinkResult(user: user)
            completion(result, nil)
        }
    }

    @objc public func updateEmail(_ options: UpdateEmailOptions, completion: @escaping (Error?) -> Void) {
        let newEmail = options.newEmail

        guard let user = Auth.auth().currentUser else {
            completion(CustomError.noUserSignedIn)
            return
        }
        user.updateEmail(to: newEmail) { error in
            completion(error)
        }
    }

    @objc public func updatePassword(_ options: UpdatePasswordOptions, completion: @escaping (Error?) -> Void) {
        let newPassword = options.newPassword

        guard let user = Auth.auth().currentUser else {
            completion(CustomError.noUserSignedIn)
            return
        }
        user.updatePassword(to: newPassword) { error in
            completion(error)
        }
    }

    @objc public func updateProfile(_ options: UpdateProfileOptions, completion: @escaping (Error?) -> Void) {
        let displayName = options.displayName
        let photoUrl = options.photoUrl

        guard let user = Auth.auth().currentUser else {
            completion(CustomError.noUserSignedIn)
            return
        }
        let changeRequest = user.createProfileChangeRequest()

        if displayName != nil {
            changeRequest.displayName = displayName
        }
        if photoUrl != nil {
            changeRequest.photoURL = URL(string: photoUrl!)
        }

        changeRequest.commitChanges { error in
            completion(error)
        }
    }

    @objc public func useAppLanguage(completion: @escaping (Error?) -> Void) {
        Auth.auth().useAppLanguage()
        completion(nil)
    }

    @objc public func useEmulator(_ options: UseEmulatorOptions, completion: @escaping (Error?) -> Void) {
        let host = options.host
        let port = options.port

        Auth.auth().useEmulator(withHost: host, port: port)
        completion(nil)
    }

    @objc public func verifyBeforeUpdateEmail(_ options: VerifyBeforeUpdateEmailOptions, completion: @escaping (Error?) -> Void) {
        let newEmail = options.newEmail
        let actionCodeSettings = FirebaseAuthenticationHelper.createActionCodeSettings(options.actionCodeSettings)

        guard let user = Auth.auth().currentUser else {
            completion(CustomError.noUserSignedIn)
            return
        }

        let completion: (Error?) -> Void = { error in
            completion(error)
        }

        if let actionCodeSettings = actionCodeSettings {
            user.sendEmailVerification(beforeUpdatingEmail: newEmail, actionCodeSettings: actionCodeSettings, completion: completion)
        } else {
            user.sendEmailVerification(beforeUpdatingEmail: newEmail, completion: completion)
        }
    }

    @objc func confirmVerificationCode(_ options: ConfirmVerificationCodeOptions, completion: @escaping (Result?, Error?) -> Void) {
        guard let phoneAuthProviderHandler = self.phoneAuthProviderHandler else {
            completion(nil, RuntimeError(createProviderNotEnabledErrorMessage("Phone")))
            return
        }
        phoneAuthProviderHandler.confirmVerificationCode(options, completion: completion)
    }

    func getConfig() -> FirebaseAuthenticationConfig {
        return self.config
    }

    func getPlugin() -> FirebaseAuthenticationPlugin {
        return self.plugin
    }

    func handleFailedLink(message: String?, error: Error?) {
        guard let savedCall = self.savedCall else {
            return
        }
        rejectCall(savedCall, message: message, error: error)
    }

    func handleFailedSignIn(message: String?, error: Error?) {
        guard let savedCall = self.savedCall else {
            return
        }
        rejectCall(savedCall, message: message, error: error)
    }

    func handlePhoneCodeSent(_ verificationId: String) {
        plugin.handlePhoneCodeSent(verificationId)
    }

    func handlePhoneVerificationFailed(_ error: Error) {
        plugin.handlePhoneVerificationFailed(error)
    }

    func handleSuccessfulLink(credential: AuthCredential, idToken: String?, nonce: String?, accessToken: String?, serverAuthCode: String?, displayName: String?, authorizationCode: String?) {
        guard let user = Auth.auth().currentUser else {
            self.handleFailedLink(message: plugin.errorNoUserSignedIn, error: nil)
            return
        }
        user.link(with: credential) { (authResult, error) in
            if let error = error {
                self.handleFailedLink(message: nil, error: error)
                return
            }
            guard let savedCall = self.savedCall else {
                return
            }
            let result = FirebaseAuthenticationHelper.createSignInResult(credential: authResult?.credential, user: authResult?.user, idToken: idToken,
                                                                         nonce: nonce, accessToken: accessToken,
                                                                         serverAuthCode: serverAuthCode, additionalUserInfo: authResult?.additionalUserInfo,
                                                                         displayName: displayName, authorizationCode: authorizationCode)
            savedCall.resolve(result)
        }
    }

    func handleSuccessfulSignIn(credential: AuthCredential, idToken: String?, nonce: String?, accessToken: String?, displayName: String?, authorizationCode: String?, serverAuthCode: String?) {
        guard let savedCall = self.savedCall else {
            return
        }
        let skipNativeAuth = savedCall.getBool("skipNativeAuth", config.skipNativeAuth)
        if skipNativeAuth == true {
            let result = FirebaseAuthenticationHelper.createSignInResult(credential: credential, user: nil, idToken: idToken,
                                                                         nonce: nonce, accessToken: accessToken, serverAuthCode: serverAuthCode,
                                                                         additionalUserInfo: nil, displayName: displayName,
                                                                         authorizationCode: authorizationCode)
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
            let result = FirebaseAuthenticationHelper.createSignInResult(credential: authResult?.credential, user: authResult?.user, idToken: idToken,
                                                                         nonce: nonce, accessToken: accessToken, serverAuthCode: serverAuthCode,
                                                                         additionalUserInfo: authResult?.additionalUserInfo, displayName: displayName,
                                                                         authorizationCode: authorizationCode)
            savedCall.resolve(result)
        }
    }

    @objc func linkWithApple(_ call: CAPPluginCall) {
        guard let appleAuthProviderHandler = self.appleAuthProviderHandler else {
            call.reject(createProviderNotEnabledErrorMessage("Apple"))
            return
        }
        self.savedCall = call
        appleAuthProviderHandler.link(call: call)
    }

    @objc func linkWithCredential(
        credential: AuthCredential,
        completion: @escaping (Result?, Error?) -> Void
    ) {
        guard let user = Auth.auth().currentUser else {
            completion(nil, RuntimeError(plugin.errorNoUserSignedIn))
            return
        }
        user.link(with: credential) { (authResult, error) in
            if let error = error {
                completion(nil, error)
                return
            }
            if let authResult = authResult {
                let result = SignInResult(authResult)
                completion(result, nil)
            }
        }
    }

    @objc func linkWithFacebook(_ call: CAPPluginCall) {
        guard let facebookAuthProviderHandler = self.facebookAuthProviderHandler else {
            call.reject(createProviderNotEnabledErrorMessage("Facebook"))
            return
        }
        self.savedCall = call
        facebookAuthProviderHandler.link(call: call)
    }

    @objc func linkWithGameCenter(_ call: CAPPluginCall) {
        guard let gameCenterAuthProviderHandler = self.gameCenterAuthProviderHandler else {
            call.reject(createProviderNotEnabledErrorMessage("Game Center"))
            return
        }
        self.savedCall = call
        gameCenterAuthProviderHandler.link(call: call)
    }

    @objc func linkWithGithub(_ call: CAPPluginCall) {
        self.savedCall = call
        self.oAuthProviderHandler?.link(call: call, providerId: ProviderId.gitHub)
    }

    @objc func linkWithGoogle(_ call: CAPPluginCall) {
        guard let googleAuthProviderHandler = self.googleAuthProviderHandler else {
            call.reject(createProviderNotEnabledErrorMessage("Google"))
            return
        }
        self.savedCall = call
        googleAuthProviderHandler.link(call: call)
    }

    @objc func linkWithMicrosoft(_ call: CAPPluginCall) {
        self.savedCall = call
        self.oAuthProviderHandler?.link(call: call, providerId: ProviderId.microsoft)
    }

    @objc func linkWithOpenIdConnect(_ call: CAPPluginCall, providerId: String) {
        self.savedCall = call
        self.oAuthProviderHandler?.link(call: call, providerId: providerId)
    }

    func linkWithPhoneNumber(_ options: LinkWithPhoneNumberOptions) throws {
        guard let phoneAuthProviderHandler = self.phoneAuthProviderHandler else {
            throw RuntimeError(createProviderNotEnabledErrorMessage("Phone"))
        }
        phoneAuthProviderHandler.link(options)
    }

    @objc func linkWithTwitter(_ call: CAPPluginCall) {
        self.savedCall = call
        self.oAuthProviderHandler?.link(call: call, providerId: ProviderId.twitter)
    }

    @objc func linkWithYahoo(_ call: CAPPluginCall) {
        self.savedCall = call
        self.oAuthProviderHandler?.link(call: call, providerId: ProviderId.yahoo)
    }

    @objc func signInWithApple(_ call: CAPPluginCall) {
        guard let appleAuthProviderHandler = self.appleAuthProviderHandler else {
            call.reject(createProviderNotEnabledErrorMessage("Apple"))
            return
        }
        self.savedCall = call
        appleAuthProviderHandler.signIn(call: call)
    }

    @objc func signInWithCredential(
        _ options: SignInOptions,
        credential: AuthCredential,
        completion: @escaping (Result?, Error?) -> Void
    ) {
        let skipNativeAuth = options.getSkipNativeAuth()
        if skipNativeAuth == true {
            let result = SignInResult(user: nil, credential: credential, additionalUserInfo: nil)
            completion(result, nil)
            return
        }
        Auth.auth().signIn(with: credential) { (authResult, error) in
            if let error = error {
                completion(nil, error)
                return
            }
            if let authResult = authResult {
                let result = SignInResult(authResult)
                completion(result, nil)
            }
        }
    }

    @objc func signInWithFacebook(_ call: CAPPluginCall) {
        guard let facebookAuthProviderHandler = self.facebookAuthProviderHandler else {
            call.reject(createProviderNotEnabledErrorMessage("Facebook"))
            return
        }
        self.savedCall = call
        facebookAuthProviderHandler.signIn(call: call)
    }

    @objc func signInWithGameCenter(_ call: CAPPluginCall) {
        guard let gameCenterAuthProviderHandler = self.gameCenterAuthProviderHandler else {
            call.reject(createProviderNotEnabledErrorMessage("Game Center"))
            return
        }
        self.savedCall = call
        gameCenterAuthProviderHandler.signIn(call: call)
    }

    @objc func signInWithGithub(_ call: CAPPluginCall) {
        self.savedCall = call
        self.oAuthProviderHandler?.signIn(call: call, providerId: ProviderId.gitHub)
    }

    @objc func signInWithGoogle(_ call: CAPPluginCall) {
        guard let googleAuthProviderHandler = self.googleAuthProviderHandler else {
            call.reject(createProviderNotEnabledErrorMessage("Google"))
            return
        }
        self.savedCall = call
        googleAuthProviderHandler.signIn(call: call)
    }

    @objc func signInWithMicrosoft(_ call: CAPPluginCall) {
        self.savedCall = call
        self.oAuthProviderHandler?.signIn(call: call, providerId: ProviderId.microsoft)
    }

    @objc func signInWithOpenIdConnect(_ call: CAPPluginCall, providerId: String) {
        self.savedCall = call
        self.oAuthProviderHandler?.signIn(call: call, providerId: providerId)
    }

    func signInWithPhoneNumber(_ options: SignInWithPhoneNumberOptions) throws {
        guard let phoneAuthProviderHandler = self.phoneAuthProviderHandler else {
            throw RuntimeError(createProviderNotEnabledErrorMessage("Phone"))
        }
        phoneAuthProviderHandler.signIn(options)
    }

    @objc func signInWithTwitter(_ call: CAPPluginCall) {
        self.savedCall = call
        self.oAuthProviderHandler?.signIn(call: call, providerId: ProviderId.twitter)
    }

    @objc func signInWithYahoo(_ call: CAPPluginCall) {
        self.savedCall = call
        self.oAuthProviderHandler?.signIn(call: call, providerId: ProviderId.yahoo)
    }

    private func createProviderNotEnabledErrorMessage(_ providerName: String) -> String {
        return "\(providerName) sign-in provider is not enabled. Make sure to add the provider to the 'providers' list in the Capacitor configuration."
    }

    private func getIdToken(_ forceRefresh: Bool, completion: @escaping (GetIdTokenResult?, Error?) -> Void) {
        guard let user = Auth.auth().currentUser else {
            let error = CustomError.noUserSignedIn
            completion(nil, error)
            return
        }
        user.getIDTokenResult(forcingRefresh: forceRefresh, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.plugin.tag, "] ", error)
                completion(nil, error)
                return
            }
            let result = GetIdTokenResult(token: result?.token ?? "")
            completion(result, nil)
        })
    }

    private func handleAuthStateChange() {
        let user = Auth.auth().currentUser
        plugin.notifyAuthStateChangeListeners(AuthStateChangeEvent(user: user))
    }

    private func handleIdTokenChange() {
        getIdToken(false, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.plugin.tag, "] ", error)
                return
            }
            if let result = result {
                self.plugin.notifyIdTokenChangeListeners(IdTokenChangeEvent(token: result.token))
            }
        })
    }

    private func initAuthProviderHandlers(config: FirebaseAuthenticationConfig) {
        if config.providers.contains(ProviderId.apple) {
            self.appleAuthProviderHandler = AppleAuthProviderHandler(self)
        }
        if config.providers.contains(ProviderId.gameCenter) {
            self.gameCenterAuthProviderHandler = GameCenterAuthProviderHandler(self)
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

    private func rejectCall(_ call: CAPPluginCall, message: String?, error: Error?) {
        let errorMessage = message ?? error?.localizedDescription ?? ""
        CAPLog.print("[", self.plugin.tag, "] ", errorMessage)
        let code = FirebaseAuthenticationHelper.createErrorCode(error: error)
        call.reject(errorMessage, code)
    }
}
