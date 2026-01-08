import Foundation
import Capacitor
import FirebaseCore
import FirebaseAuth

public struct ProviderId {
    static let apple = "apple.com"
    static let gameCenter = "gc.apple.com"
    static let facebook = "facebook.com"
    static let gitHub = "github.com"
    static let google = "google.com"
    static let microsoft = "microsoft.com"
    static let playGames = "playgames.google.com"
    static let twitter = "twitter.com"
    static let yahoo = "yahoo.com"
    static let password = "password"
    static let phone = "phone"
}

public class FirebaseAuthenticationHelper {
    public static func createActionCodeSettings(_ settings: JSObject?) -> ActionCodeSettings? {
        guard let settings = settings else {
            return nil
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
        if let linkDomain = settings["linkDomain"] as? String {
            actionCodeSettings.linkDomain = linkDomain
        }
        return actionCodeSettings
    }

    public static func createErrorCode(error: Error?) -> String? {
        if let error = error as NSError? {
            if let errorCode = convertErrorCodeToString(errorCode: error.code) {
                let prefixedErrorCode = "auth/" + errorCode
                return prefixedErrorCode
            } else {
                return nil
            }
        }
        return nil
    }

    public static func createSignInResult(credential: AuthCredential?, user: User?, idToken: String?, nonce: String?, accessToken: String?, additionalUserInfo: AdditionalUserInfo?) -> JSObject {
        return createSignInResult(credential: credential, user: user, idToken: idToken, nonce: nonce, accessToken: accessToken, additionalUserInfo: additionalUserInfo, displayName: nil)
    }

    public static func createSignInResult(credential: AuthCredential?, user: User?, idToken: String?, nonce: String?, accessToken: String?, additionalUserInfo: AdditionalUserInfo?, displayName: String?) -> JSObject {
        return createSignInResult(credential: credential, user: user, idToken: idToken, nonce: nonce, accessToken: accessToken, additionalUserInfo: additionalUserInfo, displayName: nil,
                                  authorizationCode: nil)
    }

    public static func createSignInResult(credential: AuthCredential?, user: User?, idToken: String?, nonce: String?, accessToken: String?, additionalUserInfo: AdditionalUserInfo?, displayName: String?, authorizationCode: String?) -> JSObject {
        return createSignInResult(credential: credential, user: user, idToken: idToken, nonce: nonce, accessToken: accessToken,
                                  serverAuthCode: nil, additionalUserInfo: additionalUserInfo, displayName: nil, authorizationCode: nil)
    }

    // swiftlint:disable function_parameter_count
    public static func createSignInResult(credential: AuthCredential?, user: User?, idToken: String?, nonce: String?, accessToken: String?, serverAuthCode: String?, additionalUserInfo: AdditionalUserInfo?, displayName: String?, authorizationCode: String?) -> JSObject {
        let userResult = self.createUserResult(user, displayName: displayName)
        let credentialResult = self.createCredentialResult(credential, idToken: idToken, nonce: nonce, accessToken: accessToken, authorizationCode: authorizationCode, serverAuthCode: serverAuthCode)
        let additionalUserInfoResult = self.createAdditionalUserInfoResult(additionalUserInfo)
        var result = JSObject()
        result["user"] = userResult ?? NSNull()
        result["credential"] = credentialResult ?? NSNull()
        result["additionalUserInfo"] = additionalUserInfoResult ?? NSNull()
        return result
    }

    public static func createUserResult(_ user: User?) -> JSObject? {
        return createUserResult(user, displayName: nil)
    }

    public static func createUserResult(_ user: User?, displayName: String?) -> JSObject? {
        guard let user = user else {
            if let displayName = displayName {
                var result = JSObject()
                result["displayName"] = displayName
                return result
            } else {
                return nil
            }
        }
        var result = JSObject()
        result["displayName"] = displayName ?? user.displayName ?? NSNull()
        result["email"] = user.email ?? NSNull()
        result["emailVerified"] = user.isEmailVerified
        result["isAnonymous"] = user.isAnonymous
        result["metadata"] = self.createUserMetadataResult(user.metadata)
        result["phoneNumber"] = user.phoneNumber ?? NSNull()
        result["photoUrl"] = user.photoURL?.absoluteString ?? NSNull()
        result["providerData"] = self.createUserProviderDataResult(user.providerData)
        result["providerId"] = user.providerID
        result["tenantId"] = user.tenantID ?? NSNull()
        result["uid"] = user.uid
        return result
    }

    public static func createCredentialResult(_ credential: AuthCredential?, idToken: String?, nonce: String?, accessToken: String?, authorizationCode: String?, serverAuthCode: String?) -> JSObject? {
        if credential == nil && idToken == nil && nonce == nil && accessToken == nil && authorizationCode == nil {
            return nil
        }
        var result = JSObject()
        if let credential = credential {
            result["providerId"] = credential.provider
            if let oAuthCredential = credential as? OAuthCredential {
                let oAuthAccessToken = oAuthCredential.accessToken
                if oAuthAccessToken != nil {
                    result["accessToken"] = oAuthAccessToken
                }
                let oAuthIdToken = oAuthCredential.idToken
                if oAuthIdToken != nil {
                    result["idToken"] = oAuthIdToken
                }
                let oAuthSecret = oAuthCredential.secret
                if oAuthSecret != nil {
                    result["secret"] = oAuthSecret
                }
            }
        }
        if let idToken = idToken {
            result["idToken"] = idToken
        }
        if let nonce = nonce {
            result["nonce"] = nonce
        }
        if let accessToken = accessToken {
            result["accessToken"] = accessToken
        }
        if let authorizationCode = authorizationCode {
            result["authorizationCode"] = authorizationCode
        }
        if let serverAuthCode = serverAuthCode {
            result["serverAuthCode"] = serverAuthCode
        }
        return result
    }

    public static func createAdditionalUserInfoResult(_ additionalUserInfo: AdditionalUserInfo?) -> JSObject? {
        guard let additionalUserInfo = additionalUserInfo else {
            return nil
        }
        var result = JSObject()
        result["isNewUser"] = additionalUserInfo.isNewUser
        if let profile = additionalUserInfo.profile {
            result["profile"] = JSTypes.coerceDictionaryToJSObject(profile) ?? [:]
        }
        result["providerId"] = additionalUserInfo.providerID
        if let username = additionalUserInfo.username {
            result["username"] = username
        }
        return result
    }

    private static func createUserMetadataResult(_ metadata: UserMetadata) -> JSObject {
        var result = JSObject()
        if let creationDate = metadata.creationDate?.timeIntervalSince1970 {
            result["creationTime"] = creationDate * 1000
        }
        if let lastSignInDate = metadata.lastSignInDate?.timeIntervalSince1970 {
            result["lastSignInTime"] = lastSignInDate * 1000
        }
        return result
    }

    private static func createUserProviderDataResult(_ providerData: [UserInfo]) -> JSArray {
        var result = JSArray()
        for userInfo in providerData {
            var userInfoResult = JSObject()
            userInfoResult["displayName"] = userInfo.displayName ?? NSNull()
            userInfoResult["email"] = userInfo.email ?? NSNull()
            userInfoResult["phoneNumber"] = userInfo.phoneNumber ?? NSNull()
            userInfoResult["photoUrl"] = userInfo.photoURL?.absoluteString ?? NSNull()
            userInfoResult["providerId"] = userInfo.providerID
            userInfoResult["uid"] = userInfo.uid
            result.append(userInfoResult)
        }
        return result
    }

    private static func convertErrorCodeToString(errorCode: Int) -> String? {
        let errorCodes: [Int: String] = [
            AuthErrorCode.invalidCustomToken.rawValue: "invalid-custom-token",
            AuthErrorCode.customTokenMismatch.rawValue: "custom-token-mismatch",
            AuthErrorCode.invalidCredential.rawValue: "invalid-credential",
            AuthErrorCode.userDisabled.rawValue: "user-disabled",
            AuthErrorCode.operationNotAllowed.rawValue: "operation-not-allowed",
            AuthErrorCode.emailAlreadyInUse.rawValue: "email-already-in-use",
            AuthErrorCode.invalidEmail.rawValue: "invalid-email",
            AuthErrorCode.wrongPassword.rawValue: "wrong-password",
            AuthErrorCode.tooManyRequests.rawValue: "too-many-requests",
            AuthErrorCode.userNotFound.rawValue: "user-not-found",
            AuthErrorCode.accountExistsWithDifferentCredential.rawValue: "account-exists-with-different-credential",
            AuthErrorCode.requiresRecentLogin.rawValue: "requires-recent-login",
            AuthErrorCode.providerAlreadyLinked.rawValue: "provider-already-linked",
            AuthErrorCode.noSuchProvider.rawValue: "no-such-provider",
            AuthErrorCode.invalidUserToken.rawValue: "invalid-user-token",
            AuthErrorCode.networkError.rawValue: "network-request-failed",
            AuthErrorCode.userTokenExpired.rawValue: "user-token-expired",
            AuthErrorCode.invalidAPIKey.rawValue: "invalid-api-key",
            AuthErrorCode.userMismatch.rawValue: "user-mismatch",
            AuthErrorCode.credentialAlreadyInUse.rawValue: "credential-already-in-use",
            AuthErrorCode.weakPassword.rawValue: "weak-password",
            AuthErrorCode.appNotAuthorized.rawValue: "app-not-authorized",
            AuthErrorCode.expiredActionCode.rawValue: "expired-action-code",
            AuthErrorCode.invalidActionCode.rawValue: "invalid-action-code",
            AuthErrorCode.invalidMessagePayload.rawValue: "invalid-message-payload",
            AuthErrorCode.invalidSender.rawValue: "invalid-sender",
            AuthErrorCode.invalidRecipientEmail.rawValue: "invalid-recipient-email",
            AuthErrorCode.missingEmail.rawValue: "invalid-email",
            AuthErrorCode.missingIosBundleID.rawValue: "missing-ios-bundle-id",
            AuthErrorCode.missingAndroidPackageName.rawValue: "missing-android-pkg-name",
            AuthErrorCode.unauthorizedDomain.rawValue: "unauthorized-domain",
            AuthErrorCode.invalidContinueURI.rawValue: "invalid-continue-uri",
            AuthErrorCode.missingContinueURI.rawValue: "missing-continue-uri",
            AuthErrorCode.missingPhoneNumber.rawValue: "missing-phone-number",
            AuthErrorCode.invalidPhoneNumber.rawValue: "invalid-phone-number",
            AuthErrorCode.missingVerificationCode.rawValue: "missing-verification-code",
            AuthErrorCode.invalidVerificationCode.rawValue: "invalid-verification-code",
            AuthErrorCode.missingVerificationID.rawValue: "missing-verification-id",
            AuthErrorCode.invalidVerificationID.rawValue: "invalid-verification-id",
            AuthErrorCode.missingAppCredential.rawValue: "missing-app-credential",
            AuthErrorCode.invalidAppCredential.rawValue: "invalid-app-credential",
            AuthErrorCode.sessionExpired.rawValue: "code-expired",
            AuthErrorCode.quotaExceeded.rawValue: "quota-exceeded",
            AuthErrorCode.missingAppToken.rawValue: "missing-apns-token",
            AuthErrorCode.notificationNotForwarded.rawValue: "notification-not-forwarded",
            AuthErrorCode.appNotVerified.rawValue: "app-not-verified",
            AuthErrorCode.captchaCheckFailed.rawValue: "captcha-check-failed",
            AuthErrorCode.webContextAlreadyPresented.rawValue: "cancelled-popup-request",
            AuthErrorCode.webContextCancelled.rawValue: "popup-closed-by-user",
            AuthErrorCode.appVerificationUserInteractionFailure.rawValue: "app-verification-user-interaction-failure",
            AuthErrorCode.invalidClientID.rawValue: "invalid-oauth-client-id",
            AuthErrorCode.webNetworkRequestFailed.rawValue: "network-request-failed",
            AuthErrorCode.webInternalError.rawValue: "internal-error",
            AuthErrorCode.nullUser.rawValue: "null-user",
            AuthErrorCode.keychainError.rawValue: "keychain-error",
            AuthErrorCode.internalError.rawValue: "internal-error",
            AuthErrorCode.malformedJWT.rawValue: "malformed-jwt"
        ]

        return errorCodes[errorCode]
    }
}
