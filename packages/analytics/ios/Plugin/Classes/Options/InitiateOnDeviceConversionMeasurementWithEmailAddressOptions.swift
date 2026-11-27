import Foundation
import Capacitor

@objc public class InitiateOnDeviceConversionMeasurementWithEmailAddressOptions: NSObject {
    let emailAddress: String

    init(_ call: CAPPluginCall) throws {
        self.emailAddress = try InitiateOnDeviceConversionMeasurementWithEmailAddressOptions.getEmailAddressFromCall(call)
    }

    private static func getEmailAddressFromCall(_ call: CAPPluginCall) throws -> String {
        guard let emailAddress = call.getString("emailAddress") else {
            throw CustomError.emailAddressMissing
        }
        if !FirebaseAnalyticsHelper.isValidEmail(emailAddress) {
            throw CustomError.emailAddressInvalid
        }
        return emailAddress
    }
}
