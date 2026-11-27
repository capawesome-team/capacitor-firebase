import Foundation
import Capacitor

@objc public class InitiateOnDeviceConversionMeasurementWithPhoneNumberOptions: NSObject {
    let phoneNumber: String

    init(_ call: CAPPluginCall) throws {
        self.phoneNumber = try InitiateOnDeviceConversionMeasurementWithPhoneNumberOptions.getPhoneNumberFromCall(call)
    }

    private static func getPhoneNumberFromCall(_ call: CAPPluginCall) throws -> String {
        guard let phoneNumber = call.getString("phoneNumber") else {
            throw CustomError.phoneNumberMissing
        }
        if !FirebaseAnalyticsHelper.isValidE164PhoneNumber(phoneNumber) {
            throw CustomError.phoneNumberInvalid
        }
        return phoneNumber
    }
}
