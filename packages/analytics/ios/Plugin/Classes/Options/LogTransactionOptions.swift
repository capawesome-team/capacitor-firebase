import Foundation
import Capacitor

@objc public class LogTransactionOptions: NSObject {
    let transactionId: UInt64

    init(_ call: CAPPluginCall) throws {
        self.transactionId = try LogTransactionOptions.getTransactionIdFromCall(call)
    }

    private static func getTransactionIdFromCall(_ call: CAPPluginCall) throws -> UInt64 {
        guard let transactionId = call.getString("transactionId") else {
            throw CustomError.transactionIdMissing
        }
        guard let numericTransactionId = UInt64(transactionId) else {
            throw CustomError.transactionIdInvalid
        }
        return numericTransactionId
    }
}
