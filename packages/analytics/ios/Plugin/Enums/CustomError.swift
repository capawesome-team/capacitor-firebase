import Foundation

enum CustomError: Error {
    case consentStatusMissing
    case consentTypeMissing
    case emailAddressInvalid
    case emailAddressMissing
    case emailAddressToHashMissing
    case enabledMissing
    case keyMissing
    case nameMissing
    case phoneNumberInvalid
    case phoneNumberMissing
    case phoneNumberToHashMissing
    case transactionIdInvalid
    case transactionIdMissing
    case transactionNotFound

    var code: String? {
        switch self {
        case .consentStatusMissing,
             .consentTypeMissing,
             .emailAddressInvalid,
             .emailAddressMissing,
             .emailAddressToHashMissing,
             .enabledMissing,
             .keyMissing,
             .nameMissing,
             .phoneNumberInvalid,
             .phoneNumberMissing,
             .phoneNumberToHashMissing,
             .transactionIdInvalid,
             .transactionIdMissing,
             .transactionNotFound:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .consentStatusMissing:
            return NSLocalizedString("consentStatus must be provided.", comment: "consentStatusMissing")
        case .consentTypeMissing:
            return NSLocalizedString("consentType must be provided.", comment: "consentTypeMissing")
        case .emailAddressInvalid:
            return NSLocalizedString("Invalid email format. Please provide a valid email address.", comment: "emailAddressInvalid")
        case .emailAddressMissing:
            return NSLocalizedString("emailAddress must be provided.", comment: "emailAddressMissing")
        case .emailAddressToHashMissing:
            return NSLocalizedString("emailAddressToHash must be provided.", comment: "emailAddressToHashMissing")
        case .enabledMissing:
            return NSLocalizedString("enabled must be provided.", comment: "enabledMissing")
        case .keyMissing:
            return NSLocalizedString("key must be provided.", comment: "keyMissing")
        case .nameMissing:
            return NSLocalizedString("name must be provided.", comment: "nameMissing")
        case .phoneNumberInvalid:
            return NSLocalizedString(
                "Invalid phone number format. Please provide a valid E.164 formatted phone number.",
                comment: "phoneNumberInvalid"
            )
        case .phoneNumberMissing:
            return NSLocalizedString("phoneNumber must be provided.", comment: "phoneNumberMissing")
        case .phoneNumberToHashMissing:
            return NSLocalizedString("phoneNumberToHash must be provided.", comment: "phoneNumberToHashMissing")
        case .transactionIdInvalid:
            return NSLocalizedString("transactionId is not a valid numeric identifier.", comment: "transactionIdInvalid")
        case .transactionIdMissing:
            return NSLocalizedString("transactionId must be provided.", comment: "transactionIdMissing")
        case .transactionNotFound:
            return NSLocalizedString("Transaction not found.", comment: "transactionNotFound")
        }
    }
}
