import Foundation

enum CustomError: Error {
    case appNotFound
    case callbackIdMissing
    case cursorDocumentFieldMissing
    case cursorDocumentNotFound
    case dataFieldPathInvalid
    case dataMissing
    case documentReferencePathInvalid
    case fieldNameEmpty
    case fieldPathInvalid
    case fieldPathMissing
    case hostMissing
    case limitInvalid
    case limitMissing
    case operationsMissing
    case referenceInvalid
    case referenceMissing
    case valueInvalid

    var code: String? {
        switch self {
        case .appNotFound,
             .callbackIdMissing,
             .cursorDocumentFieldMissing,
             .cursorDocumentNotFound,
             .dataFieldPathInvalid,
             .dataMissing,
             .documentReferencePathInvalid,
             .fieldNameEmpty,
             .fieldPathInvalid,
             .fieldPathMissing,
             .hostMissing,
             .limitInvalid,
             .limitMissing,
             .operationsMissing,
             .referenceInvalid,
             .referenceMissing,
             .valueInvalid:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .appNotFound:
            return NSLocalizedString("No app with the given appName has been initialized.", comment: "appNotFound")
        case .callbackIdMissing:
            return NSLocalizedString("callbackId must be provided.", comment: "callbackIdMissing")
        case .cursorDocumentFieldMissing:
            return NSLocalizedString("The document of the query cursor has no value for an orderBy field.", comment: "cursorDocumentFieldMissing")
        case .cursorDocumentNotFound:
            return NSLocalizedString("The document of the query cursor does not exist.", comment: "cursorDocumentNotFound")
        case .dataFieldPathInvalid:
            return NSLocalizedString("A field path in data is invalid.", comment: "dataFieldPathInvalid")
        case .dataMissing:
            return NSLocalizedString("data must be provided.", comment: "dataMissing")
        case .documentReferencePathInvalid:
            return NSLocalizedString("The path of a document reference is invalid.", comment: "documentReferencePathInvalid")
        case .fieldNameEmpty:
            return NSLocalizedString("A field name must not be empty.", comment: "fieldNameEmpty")
        case .fieldPathInvalid:
            return NSLocalizedString("fieldPath is invalid.", comment: "fieldPathInvalid")
        case .fieldPathMissing:
            return NSLocalizedString("fieldPath must be provided.", comment: "fieldPathMissing")
        case .hostMissing:
            return NSLocalizedString("host must be provided.", comment: "hostMissing")
        case .limitInvalid:
            return NSLocalizedString("limit is invalid.", comment: "limitInvalid")
        case .limitMissing:
            return NSLocalizedString("limit must be provided.", comment: "limitMissing")
        case .operationsMissing:
            return NSLocalizedString("operations must be provided.", comment: "operationsMissing")
        case .referenceInvalid:
            return NSLocalizedString("reference is invalid.", comment: "referenceInvalid")
        case .referenceMissing:
            return NSLocalizedString("reference must be provided.", comment: "referenceMissing")
        case .valueInvalid:
            return NSLocalizedString("value is invalid.", comment: "valueInvalid")
        }
    }
}
