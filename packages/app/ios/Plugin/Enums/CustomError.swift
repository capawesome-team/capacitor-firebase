import Foundation

enum CustomError: Error {
    case apiKeyInvalid
    case apiKeyMissing
    case appAlreadyExists
    case applicationIdInvalid
    case applicationIdMissing
    case defaultAppNotFound
    case gcmSenderIdMissing
    case nameInvalid
    case nameMissing
    case optionsMissing
    case projectIdMissing

    var code: String? {
        return nil
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .apiKeyInvalid:
            return NSLocalizedString("options.apiKey must be a valid API key.", comment: "apiKeyInvalid")
        case .apiKeyMissing:
            return NSLocalizedString("options.apiKey must be provided.", comment: "apiKeyMissing")
        case .appAlreadyExists:
            return NSLocalizedString("An app with this name already exists.", comment: "appAlreadyExists")
        case .applicationIdInvalid:
            return NSLocalizedString("options.applicationId must be an iOS app ID.", comment: "applicationIdInvalid")
        case .applicationIdMissing:
            return NSLocalizedString("options.applicationId must be provided.", comment: "applicationIdMissing")
        case .defaultAppNotFound:
            return NSLocalizedString("The default app has not been initialized.", comment: "defaultAppNotFound")
        case .gcmSenderIdMissing:
            return NSLocalizedString("options.gcmSenderId must be provided.", comment: "gcmSenderIdMissing")
        case .nameInvalid:
            return NSLocalizedString("name must only contain letters, numbers, hyphens and underscores.", comment: "nameInvalid")
        case .nameMissing:
            return NSLocalizedString("name must be provided.", comment: "nameMissing")
        case .optionsMissing:
            return NSLocalizedString("options must be provided.", comment: "optionsMissing")
        case .projectIdMissing:
            return NSLocalizedString("options.projectId must be provided.", comment: "projectIdMissing")
        }
    }
}
