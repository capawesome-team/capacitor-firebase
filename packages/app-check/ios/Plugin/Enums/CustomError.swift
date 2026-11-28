import Foundation

enum CustomError: Error {
    case enabledMissing
    case getTokenFailed

    var code: String? {
        switch self {
        case .enabledMissing,
             .getTokenFailed:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .enabledMissing:
            return NSLocalizedString("enabled must be provided.", comment: "enabledMissing")
        case .getTokenFailed:
            return NSLocalizedString("Failed to get token.", comment: "getTokenFailed")
        }
    }
}
