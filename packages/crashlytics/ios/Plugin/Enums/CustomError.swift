import Foundation

enum CustomError: Error {
    case enabledMissing
    case keyMissing
    case messageMissing
    case userIdMissing
    case valueInvalid
    case valueMissing

    var code: String? {
        switch self {
        case .enabledMissing,
             .keyMissing,
             .messageMissing,
             .userIdMissing,
             .valueInvalid,
             .valueMissing:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .enabledMissing:
            return NSLocalizedString("enabled must be provided.", comment: "enabledMissing")
        case .keyMissing:
            return NSLocalizedString("key must be provided.", comment: "keyMissing")
        case .messageMissing:
            return NSLocalizedString("message must be provided.", comment: "messageMissing")
        case .userIdMissing:
            return NSLocalizedString("userId must be provided.", comment: "userIdMissing")
        case .valueInvalid:
            return NSLocalizedString("value is invalid.", comment: "valueInvalid")
        case .valueMissing:
            return NSLocalizedString("value must be provided.", comment: "valueMissing")
        }
    }
}
