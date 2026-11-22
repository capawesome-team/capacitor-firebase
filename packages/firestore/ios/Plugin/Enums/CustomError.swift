import Foundation

enum CustomError: Error {
    case callbackIdMissing
    case dataMissing
    case hostMissing
    case operationsMissing
    case referenceMissing

    var code: String? {
        switch self {
        case .callbackIdMissing, .dataMissing, .hostMissing, .operationsMissing, .referenceMissing:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .callbackIdMissing:
            return NSLocalizedString("callbackId must be provided.", comment: "callbackIdMissing")
        case .dataMissing:
            return NSLocalizedString("data must be provided.", comment: "dataMissing")
        case .hostMissing:
            return NSLocalizedString("host must be provided.", comment: "hostMissing")
        case .operationsMissing:
            return NSLocalizedString("operations must be provided.", comment: "operationsMissing")
        case .referenceMissing:
            return NSLocalizedString("reference must be provided.", comment: "referenceMissing")
        }
    }
}
