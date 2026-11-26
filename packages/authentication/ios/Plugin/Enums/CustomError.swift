import Foundation

enum CustomError: Error {
    case actionCodeSettingsMissing
    case customTokenSkipNativeAuth
    case emailLinkMissing
    case emailLinkSignInSkipNativeAuth
    case emailMissing
    case emailSignInSkipNativeAuth
    case hostMissing
    case newEmailMissing
    case newPasswordMissing
    case noUserSignedIn
    case oobCodeMissing
    case passwordMissing
    case providerIdMissing
    case signInAnonymouslySkipNativeAuth
    case tenantIdMissing
    case tokenMissing

    var code: String? {
        switch self {
        case .actionCodeSettingsMissing, .customTokenSkipNativeAuth, .emailLinkMissing, .emailLinkSignInSkipNativeAuth, .emailMissing,
             .emailSignInSkipNativeAuth, .hostMissing, .newEmailMissing, .newPasswordMissing, .noUserSignedIn, .oobCodeMissing,
             .passwordMissing, .providerIdMissing, .signInAnonymouslySkipNativeAuth, .tenantIdMissing, .tokenMissing:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .actionCodeSettingsMissing:
            return NSLocalizedString("actionCodeSettings must be provided.", comment: "actionCodeSettingsMissing")
        case .customTokenSkipNativeAuth:
            return NSLocalizedString(
                "signInWithCustomToken cannot be used in combination with skipNativeAuth.",
                comment: "customTokenSkipNativeAuth"
            )
        case .emailLinkMissing:
            return NSLocalizedString("emailLink must be provided.", comment: "emailLinkMissing")
        case .emailLinkSignInSkipNativeAuth:
            return NSLocalizedString(
                "signInWithEmailLink cannot be used in combination with skipNativeAuth.",
                comment: "emailLinkSignInSkipNativeAuth"
            )
        case .emailMissing:
            return NSLocalizedString("email must be provided.", comment: "emailMissing")
        case .emailSignInSkipNativeAuth:
            return NSLocalizedString(
                "createUserWithEmailAndPassword and signInWithEmailAndPassword cannot be used in combination with skipNativeAuth.",
                comment: "emailSignInSkipNativeAuth"
            )
        case .hostMissing:
            return NSLocalizedString("host must be provided.", comment: "hostMissing")
        case .newEmailMissing:
            return NSLocalizedString("newEmail must be provided.", comment: "newEmailMissing")
        case .newPasswordMissing:
            return NSLocalizedString("newPassword must be provided.", comment: "newPasswordMissing")
        case .noUserSignedIn:
            return NSLocalizedString("No user is signed in.", comment: "noUserSignedIn")
        case .oobCodeMissing:
            return NSLocalizedString("oobCode must be provided.", comment: "oobCodeMissing")
        case .passwordMissing:
            return NSLocalizedString("password must be provided.", comment: "passwordMissing")
        case .providerIdMissing:
            return NSLocalizedString("providerId must be provided.", comment: "providerIdMissing")
        case .signInAnonymouslySkipNativeAuth:
            return NSLocalizedString(
                "signInAnonymously cannot be used in combination with skipNativeAuth.",
                comment: "signInAnonymouslySkipNativeAuth"
            )
        case .tenantIdMissing:
            return NSLocalizedString("tenantId must be provided.", comment: "tenantIdMissing")
        case .tokenMissing:
            return NSLocalizedString("token must be provided.", comment: "tokenMissing")
        }
    }
}
