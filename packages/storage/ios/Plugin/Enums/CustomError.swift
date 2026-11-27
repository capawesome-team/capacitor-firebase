import Foundation

enum CustomError: Error {
    case callbackIdMissing
    case fileNotExist
    case hostMissing
    case metadataMissing
    case pathMissing
    case uriMissing

    var code: String? {
        switch self {
        case .callbackIdMissing,
             .fileNotExist,
             .hostMissing,
             .metadataMissing,
             .pathMissing,
             .uriMissing:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .callbackIdMissing:
            return NSLocalizedString("callbackId must be provided.", comment: "callbackIdMissing")
        case .fileNotExist:
            return NSLocalizedString("File does not exist.", comment: "fileNotExist")
        case .hostMissing:
            return NSLocalizedString("host must be provided.", comment: "hostMissing")
        case .metadataMissing:
            return NSLocalizedString("metadata must be provided.", comment: "metadataMissing")
        case .pathMissing:
            return NSLocalizedString("path must be provided.", comment: "pathMissing")
        case .uriMissing:
            return NSLocalizedString("uri must be provided.", comment: "uriMissing")
        }
    }
}
