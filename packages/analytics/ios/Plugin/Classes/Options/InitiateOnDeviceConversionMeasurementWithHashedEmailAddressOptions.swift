import Foundation
import Capacitor

@objc public class InitiateOnDeviceConversionMeasurementWithHashedEmailAddressOptions: NSObject {
    let emailAddressToHash: String

    init(_ call: CAPPluginCall) throws {
        self.emailAddressToHash = try InitiateOnDeviceConversionMeasurementWithHashedEmailAddressOptions.getEmailAddressToHashFromCall(call)
    }

    private static func getEmailAddressToHashFromCall(_ call: CAPPluginCall) throws -> String {
        guard let emailAddressToHash = call.getString("emailAddressToHash") else {
            throw CustomError.emailAddressToHashMissing
        }
        if !FirebaseAnalyticsHelper.isValidEmail(emailAddressToHash) {
            throw CustomError.emailAddressInvalid
        }
        return emailAddressToHash
    }
}
