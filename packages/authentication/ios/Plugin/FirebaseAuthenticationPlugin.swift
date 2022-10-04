import Foundation
import Capacitor
import FirebaseCore
import FirebaseAuth

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebaseAuthenticationPlugin)
public class FirebaseAuthenticationPlugin: CAPPlugin {
    public let errorProviderIdMissing = "providerId must be provided."
    public let errorNoUserSignedIn = "No user is signed in."
    public let errorOobCodeMissing = "oobCode must be provided."
    public let errorTenantIdMissing = "tenantId must be provided."
    public let errorEmailMissing = "email must be provided."
    public let errorNewEmailMissing = "newEmail must be provided."
    public let errorEmailLinkMissing = "emailLink must be provided."
    public let errorActionCodeSettingsMissing = "actionCodeSettings must be provided."
    public let errorPasswordMissing = "password must be provided."
    public let errorNewPasswordMissing = "newPassword must be provided."
    public let errorPhoneNumberVerificationIdCodeMissing = "phoneNumber or verificationId and verificationCode must be provided."
    public let errorHostMissing = "host must be provided."
    public let errorSignInFailed = "signIn failed."
    public let errorCreateUserWithEmailAndPasswordFailed = "createUserWithEmailAndPassword failed."
    public let errorCustomTokenSkipNativeAuth =
        "signInWithCustomToken cannot be used in combination with skipNativeAuth."
    public let errorEmailLinkSignInSkipNativeAuth =
        "signInWithEmailLink cannot be used in combination with skipNativeAuth."
    public let errorEmailSignInSkipNativeAuth =
        "createUserWithEmailAndPassword and signInWithEmailAndPassword cannot be used in combination with skipNativeAuth."
    public let errorSignInAnonymouslySkipNativeAuth =
        "signInAnonymously cannot be used in combination with skipNativeAuth."
    public let errorDeviceUnsupported = "Device is not supported. At least iOS 13 is required."
    public let authStateChangeEvent = "authStateChange"
    private var implementation: FirebaseAuthentication?

    override public func load() {
        self.implementation = FirebaseAuthentication(plugin: self, config: firebaseAuthenticationConfig())
        self.implementation?.authStateObserver = { [weak self] in
            self?.handleAuthStateChange()
        }
    }

    @objc func applyActionCode(_ call: CAPPluginCall) {
        guard let oobCode = call.getString("oobCode") else {
            call.reject(errorOobCodeMissing)
            return
        }

        implementation?.applyActionCode(oobCode: oobCode, completion: { error in
            if let error = error {
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func createUserWithEmailAndPassword(_ call: CAPPluginCall) {
        implementation?.createUserWithEmailAndPassword(call)
    }

    @objc func confirmPasswordReset(_ call: CAPPluginCall) {
        guard let oobCode = call.getString("oobCode") else {
            call.reject(errorOobCodeMissing)
            return
        }
        guard let newPassword = call.getString("newPassword") else {
            call.reject(errorNewPasswordMissing)
            return
        }

        implementation?.confirmPasswordReset(oobCode: oobCode, newPassword: newPassword, completion: { error in
            if let error = error {
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func getCurrentUser(_ call: CAPPluginCall) {
        let user = implementation?.getCurrentUser()
        let userResult = FirebaseAuthenticationHelper.createUserResult(user)
        var result = JSObject()
        result["user"] = userResult
        call.resolve(result)
    }

    @objc func getIdToken(_ call: CAPPluginCall) {
        let forceRefresh = call.getBool("forceRefresh", false)

        implementation?.getIdToken(forceRefresh, completion: { token, errorMessage in
            if let errorMessage = errorMessage {
                call.reject(errorMessage)
                return
            }
            var result = JSObject()
            result["token"] = token
            call.resolve(result)
        })
    }

    @objc func getTenantId(_ call: CAPPluginCall) {
        var result = JSObject()
        result["tenantId"] = implementation?.getTenantId()
        call.resolve(result)
    }

    @objc func isSignInWithEmailLink(_ call: CAPPluginCall) {
        guard let emailLink = call.getString("emailLink") else {
            call.reject(errorEmailLinkMissing)
            return
        }

        var result = JSObject()
        result["isSignInWithEmailLink"] = implementation?.isSignInWithEmailLink(link: emailLink)
        call.resolve(result)
    }

    @objc func linkWithApple(_ call: CAPPluginCall) {
        implementation?.linkWithApple(call)
    }

    @objc func linkWithEmailAndPassword(_ call: CAPPluginCall) {
        implementation?.linkWithEmailAndPassword(call)
    }

    @objc func linkWithEmailLink(_ call: CAPPluginCall) {
        implementation?.linkWithEmailLink(call)
    }

    @objc func linkWithFacebook(_ call: CAPPluginCall) {
        implementation?.linkWithFacebook(call)
    }

    @objc func linkWithGithub(_ call: CAPPluginCall) {
        implementation?.linkWithGithub(call)
    }

    @objc func linkWithGoogle(_ call: CAPPluginCall) {
        implementation?.linkWithGoogle(call)
    }

    @objc func linkWithMicrosoft(_ call: CAPPluginCall) {
        implementation?.linkWithMicrosoft(call)
    }

    @objc func linkWithPhoneNumber(_ call: CAPPluginCall) {
        let phoneNumber = call.getString("phoneNumber")
        let verificationId = call.getString("verificationId")
        let verificationCode = call.getString("verificationCode")

        if phoneNumber == nil && (verificationId == nil || verificationCode == nil) {
            call.reject(errorPhoneNumberVerificationIdCodeMissing)
            return
        }

        implementation?.linkWithPhoneNumber(call)
    }

    @objc func linkWithPlayGames(_ call: CAPPluginCall) {
        call.reject("Not available on iOS.")
    }

    @objc func linkWithTwitter(_ call: CAPPluginCall) {
        implementation?.linkWithTwitter(call)
    }

    @objc func linkWithYahoo(_ call: CAPPluginCall) {
        implementation?.linkWithYahoo(call)
    }

    @objc func sendEmailVerification(_ call: CAPPluginCall) {
        guard let user = implementation?.getCurrentUser() else {
            call.reject(errorNoUserSignedIn)
            return
        }

        implementation?.sendEmailVerification(user: user, completion: { error in
            if let error = error {
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func sendPasswordResetEmail(_ call: CAPPluginCall) {
        guard let email = call.getString("email") else {
            call.reject(errorEmailMissing)
            return
        }

        implementation?.sendPasswordResetEmail(email: email, completion: { error in
            if let error = error {
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func sendSignInLinkToEmail(_ call: CAPPluginCall) {
        guard let email = call.getString("email") else {
            call.reject(errorEmailMissing)
            return
        }
        guard let settings = call.getObject("actionCodeSettings") else {
            call.reject(errorActionCodeSettingsMissing)
            return
        }

        let actionCodeSettings = ActionCodeSettings()
        if let url = settings["url"] as? String {
            actionCodeSettings.url = URL(string: url)
        }

        if let handleCodeInApp = settings["handleCodeInApp"] as? Bool {
            actionCodeSettings.handleCodeInApp = handleCodeInApp
        }

        if let iOS = settings["iOS"] as? JSObject {
            if let bundleId = iOS["bundleId"] as? String {
                actionCodeSettings.setIOSBundleID(bundleId)
            }
        }

        if let android = settings["android"] as? JSObject {
            if let packageName = android["packageName"] as? String {
                actionCodeSettings.setAndroidPackageName(
                    packageName,
                    installIfNotAvailable: android["installApp"] as? Bool ?? false,
                    minimumVersion: android["minimumVersion"] as? String
                )
            }
        }

        if let dynamicLinkDomain = settings["dynamicLinkDomain"] as? String {
            actionCodeSettings.dynamicLinkDomain = dynamicLinkDomain
        }

        implementation?.sendSignInLinkToEmail(email: email, actionCodeSettings: actionCodeSettings, completion: { error in
            if let error = error {
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func setLanguageCode(_ call: CAPPluginCall) {
        let languageCode = call.getString("languageCode", "")

        implementation?.setLanguageCode(languageCode)
        call.resolve()
    }

    @objc func setTenantId(_ call: CAPPluginCall) {
        guard let tenantId = call.getString("tenantId") else {
            call.reject(errorTenantIdMissing)
            return
        }

        implementation?.setTenantId(tenantId: tenantId)
        call.resolve()
    }

    @objc func signInAnonymously(_ call: CAPPluginCall) {
        implementation?.signInAnonymously(call)
    }

    @objc func signInWithApple(_ call: CAPPluginCall) {
        implementation?.signInWithApple(call)
    }

    @objc func signInWithCustomToken(_ call: CAPPluginCall) {
        implementation?.signInWithCustomToken(call)
    }

    @objc func signInWithEmailAndPassword(_ call: CAPPluginCall) {
        implementation?.signInWithEmailAndPassword(call)
    }

    @objc func signInWithEmailLink(_ call: CAPPluginCall) {
        implementation?.signInWithEmailLink(call)
    }

    @objc func signInWithFacebook(_ call: CAPPluginCall) {
        implementation?.signInWithFacebook(call)
    }

    @objc func signInWithGithub(_ call: CAPPluginCall) {
        implementation?.signInWithGithub(call)
    }

    @objc func signInWithGoogle(_ call: CAPPluginCall) {
        implementation?.signInWithGoogle(call)
    }

    @objc func signInWithMicrosoft(_ call: CAPPluginCall) {
        implementation?.signInWithMicrosoft(call)
    }

    @objc func signInWithPhoneNumber(_ call: CAPPluginCall) {
        let phoneNumber = call.getString("phoneNumber")
        let verificationId = call.getString("verificationId")
        let verificationCode = call.getString("verificationCode")

        if phoneNumber == nil && (verificationId == nil || verificationCode == nil) {
            call.reject(errorPhoneNumberVerificationIdCodeMissing)
            return
        }

        implementation?.signInWithPhoneNumber(call)
    }

    @objc func signInWithPlayGames(_ call: CAPPluginCall) {
        call.reject("Not available on iOS.")
    }

    @objc func signInWithTwitter(_ call: CAPPluginCall) {
        implementation?.signInWithTwitter(call)
    }

    @objc func signInWithYahoo(_ call: CAPPluginCall) {
        implementation?.signInWithYahoo(call)
    }

    @objc func signOut(_ call: CAPPluginCall) {
        implementation?.signOut(call)
    }

    @objc func unlink(_ call: CAPPluginCall) {
        guard let providerId = call.getString("providerId") else {
            call.reject(errorProviderIdMissing)
            return
        }
        guard let user = implementation?.getCurrentUser() else {
            call.reject(errorNoUserSignedIn)
            return
        }

        implementation?.unlink(user: user, providerId: providerId, completion: { user, error in
            if let error = error {
                call.reject(error.localizedDescription)
                return
            }
            let userResult = FirebaseAuthenticationHelper.createUserResult(user)
            var result = JSObject()
            result["user"] = userResult
            call.resolve(result)
        })
    }

    @objc func updateEmail(_ call: CAPPluginCall) {
        guard let newEmail = call.getString("newEmail") else {
            call.reject(errorNewEmailMissing)
            return
        }
        guard let user = implementation?.getCurrentUser() else {
            call.reject(errorNoUserSignedIn)
            return
        }

        implementation?.updateEmail(user: user, newEmail: newEmail, completion: { error in
            if let error = error {
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func updatePassword(_ call: CAPPluginCall) {
        guard let newPassword = call.getString("newPassword") else {
            call.reject(errorNewPasswordMissing)
            return
        }
        guard let user = implementation?.getCurrentUser() else {
            call.reject(errorNoUserSignedIn)
            return
        }

        implementation?.updatePassword(user: user, newPassword: newPassword, completion: { error in
            if let error = error {
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func useAppLanguage(_ call: CAPPluginCall) {
        implementation?.useAppLanguage()
        call.resolve()
    }

    @objc func useEmulator(_ call: CAPPluginCall) {
        guard let host = call.getString("host") else {
            call.reject(errorHostMissing)
            return
        }
        let port = call.getInt("port") ?? 9099

        implementation?.useEmulator(host, port)
        call.resolve()
    }

    @objc func handleAuthStateChange() {
        let user = implementation?.getCurrentUser()
        let userResult = FirebaseAuthenticationHelper.createUserResult(user)
        var result = JSObject()
        result["user"] = userResult
        notifyListeners(authStateChangeEvent, data: result, retainUntilConsumed: true)
    }

    private func firebaseAuthenticationConfig() -> FirebaseAuthenticationConfig {
        var config = FirebaseAuthenticationConfig()

        config.skipNativeAuth = getConfig().getBoolean("skipNativeAuth", config.skipNativeAuth)
        if let providers = getConfig().getArray("providers") as? [String] {
            config.providers = providers
        }

        return config
    }
}
