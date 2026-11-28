import Foundation

enum CustomError: Error {
    case notificationsInvalid
    case notificationsMissing
    case topicMissing

    var code: String? {
        switch self {
        case .notificationsInvalid,
             .notificationsMissing,
             .topicMissing:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .notificationsInvalid:
            return NSLocalizedString("The provided notifications are invalid.", comment: "notificationsInvalid")
        case .notificationsMissing:
            return NSLocalizedString("notifications must be provided.", comment: "notificationsMissing")
        case .topicMissing:
            return NSLocalizedString("topic must be provided.", comment: "topicMissing")
        }
    }
}
