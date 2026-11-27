import Foundation

enum CustomError: Error {
    case attributeMissing
    case enabledMissing
    case metricNameMissing
    case numInvalid
    case numMissing
    case traceNameAlreadyAssigned
    case traceNameInvalid
    case traceNameMissing
    case traceNotFound
    case valueMissing

    var code: String? {
        switch self {
        case .attributeMissing,
             .enabledMissing,
             .metricNameMissing,
             .numInvalid,
             .numMissing,
             .traceNameAlreadyAssigned,
             .traceNameInvalid,
             .traceNameMissing,
             .traceNotFound,
             .valueMissing:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .attributeMissing:
            return NSLocalizedString("attribute must be provided.", comment: "attributeMissing")
        case .enabledMissing:
            return NSLocalizedString("enabled must be provided.", comment: "enabledMissing")
        case .metricNameMissing:
            return NSLocalizedString("metricName must be provided.", comment: "metricNameMissing")
        case .numInvalid:
            return NSLocalizedString("num is invalid.", comment: "numInvalid")
        case .numMissing:
            return NSLocalizedString("num must be provided.", comment: "numMissing")
        case .traceNameAlreadyAssigned:
            return NSLocalizedString("traceName already assigned.", comment: "traceNameAlreadyAssigned")
        case .traceNameInvalid:
            return NSLocalizedString("traceName is invalid.", comment: "traceNameInvalid")
        case .traceNameMissing:
            return NSLocalizedString("traceName must be provided.", comment: "traceNameMissing")
        case .traceNotFound:
            return NSLocalizedString("No trace was found with the provided traceName.", comment: "traceNotFound")
        case .valueMissing:
            return NSLocalizedString("value must be provided.", comment: "valueMissing")
        }
    }
}
