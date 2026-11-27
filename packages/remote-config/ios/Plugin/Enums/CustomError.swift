import Foundation

enum CustomError: Error {
    case callbackIdMissing
    case customSignalsMissing
    case defaultsMissing
    case fetchAndActivateFailed
    case idMissing
    case keyMissing
    case notAvailable

    var code: String? {
        switch self {
        case .callbackIdMissing,
             .customSignalsMissing,
             .defaultsMissing,
             .fetchAndActivateFailed,
             .idMissing,
             .keyMissing,
             .notAvailable:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .callbackIdMissing:
            return NSLocalizedString("callbackId must be provided.", comment: "callbackIdMissing")
        case .customSignalsMissing:
            return NSLocalizedString("customSignals must be provided.", comment: "customSignalsMissing")
        case .defaultsMissing:
            return NSLocalizedString("defaults must be provided.", comment: "defaultsMissing")
        case .fetchAndActivateFailed:
            return NSLocalizedString("fetchAndActivate failed.", comment: "fetchAndActivateFailed")
        case .idMissing:
            return NSLocalizedString("id must be provided.", comment: "idMissing")
        case .keyMissing:
            return NSLocalizedString("key must be provided.", comment: "keyMissing")
        case .notAvailable:
            return NSLocalizedString("Not available on iOS.", comment: "notAvailable")
        }
    }
}
