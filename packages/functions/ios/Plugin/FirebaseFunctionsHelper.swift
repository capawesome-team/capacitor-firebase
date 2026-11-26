import Foundation
import FirebaseFunctions

public class FirebaseFunctionsHelper {
    public static func createErrorCode(error: Error?) -> String? {
        guard let error = error as NSError? else {
            return nil
        }
        if isFailedRequest(error: error) {
            // The Android and JavaScript SDKs report a failed request as `internal`. The iOS SDK passes the URL error on.
            return "functions/internal"
        }
        guard error.domain == FunctionsErrorDomain, let errorCode = convertErrorCodeToString(errorCode: error.code) else {
            return nil
        }
        return "functions/" + errorCode
    }

    private static func convertErrorCodeToString(errorCode: Int) -> String? {
        let errorCodes: [Int: String] = [
            FunctionsErrorCode.aborted.rawValue: "aborted",
            FunctionsErrorCode.alreadyExists.rawValue: "already-exists",
            FunctionsErrorCode.cancelled.rawValue: "cancelled",
            FunctionsErrorCode.dataLoss.rawValue: "data-loss",
            FunctionsErrorCode.deadlineExceeded.rawValue: "deadline-exceeded",
            FunctionsErrorCode.failedPrecondition.rawValue: "failed-precondition",
            FunctionsErrorCode.internal.rawValue: "internal",
            FunctionsErrorCode.invalidArgument.rawValue: "invalid-argument",
            FunctionsErrorCode.notFound.rawValue: "not-found",
            FunctionsErrorCode.OK.rawValue: "ok",
            FunctionsErrorCode.outOfRange.rawValue: "out-of-range",
            FunctionsErrorCode.permissionDenied.rawValue: "permission-denied",
            FunctionsErrorCode.resourceExhausted.rawValue: "resource-exhausted",
            FunctionsErrorCode.unauthenticated.rawValue: "unauthenticated",
            FunctionsErrorCode.unavailable.rawValue: "unavailable",
            FunctionsErrorCode.unimplemented.rawValue: "unimplemented",
            FunctionsErrorCode.unknown.rawValue: "unknown"
        ]

        return errorCodes[errorCode]
    }

    private static func isFailedRequest(error: NSError) -> Bool {
        // An invalid URL keeps no code because Android rejects it without one and Web reports `internal`.
        let invalidUrlErrorCodes = [NSURLErrorBadURL, NSURLErrorUnsupportedURL]
        return error.domain == NSURLErrorDomain && !invalidUrlErrorCodes.contains(error.code)
    }
}
