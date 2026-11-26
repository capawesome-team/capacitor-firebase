import Foundation

enum CustomError: Error {
    case hostMissing
    case nameMissing
    case urlInvalid
    case urlMissing

    var code: String? {
        switch self {
        case .hostMissing,
             .nameMissing,
             .urlInvalid,
             .urlMissing:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .hostMissing:
            return NSLocalizedString("host must be provided.", comment: "hostMissing")
        case .nameMissing:
            return NSLocalizedString("name must be provided.", comment: "nameMissing")
        case .urlInvalid:
            return NSLocalizedString("url is invalid.", comment: "urlInvalid")
        case .urlMissing:
            return NSLocalizedString("url must be provided.", comment: "urlMissing")
        }
    }
}
