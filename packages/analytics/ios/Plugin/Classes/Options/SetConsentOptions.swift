import Foundation
import Capacitor
import FirebaseAnalytics

@objc public class SetConsentOptions: NSObject {
    let status: ConsentStatus
    let type: ConsentType

    init(_ call: CAPPluginCall) throws {
        // ALWAYS read the type before the status, so a missing type is reported first.
        self.type = try SetConsentOptions.getTypeFromCall(call)
        self.status = try SetConsentOptions.getStatusFromCall(call)
    }

    private static func getStatusFromCall(_ call: CAPPluginCall) throws -> ConsentStatus {
        guard let status = FirebaseAnalyticsHelper.mapStringToConsentStatus(call.getString("status")) else {
            throw CustomError.consentStatusMissing
        }
        return status
    }

    private static func getTypeFromCall(_ call: CAPPluginCall) throws -> ConsentType {
        guard let type = FirebaseAnalyticsHelper.mapStringToConsentType(call.getString("type")) else {
            throw CustomError.consentTypeMissing
        }
        return type
    }
}
