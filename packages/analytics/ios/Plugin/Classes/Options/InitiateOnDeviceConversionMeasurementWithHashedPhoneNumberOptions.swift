import Foundation
import Capacitor

@objc public class InitiateOnDeviceConversionMeasurementWithHashedPhoneNumberOptions: NSObject {
    let phoneNumberToHash: String

    init(_ call: CAPPluginCall) throws {
        self.phoneNumberToHash = try InitiateOnDeviceConversionMeasurementWithHashedPhoneNumberOptions.getPhoneNumberToHashFromCall(call)
    }

    private static func getPhoneNumberToHashFromCall(_ call: CAPPluginCall) throws -> String {
        guard let phoneNumberToHash = call.getString("phoneNumberToHash") else {
            throw CustomError.phoneNumberToHashMissing
        }
        if !FirebaseAnalyticsHelper.isValidE164PhoneNumber(phoneNumberToHash) {
            throw CustomError.phoneNumberInvalid
        }
        return phoneNumberToHash
    }
}
