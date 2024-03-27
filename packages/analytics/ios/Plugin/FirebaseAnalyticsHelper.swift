import Foundation
import FirebaseAnalytics

public class FirebaseAnalyticsHelper {
    public static func mapStringToConsentType(_ value: String?) -> ConsentType? {
        switch value {
        case "AD_PERSONALIZATION":
            return ConsentType.adPersonalization
        case "AD_STORAGE":
            return ConsentType.adStorage
        case "AD_USER_DATA":
            return ConsentType.adUserData
        case "ANALYTICS_STORAGE":
            return ConsentType.analyticsStorage
        default:
            return nil
        }
    }

    public static func mapStringToConsentStatus(_ value: String?) -> ConsentStatus? {
        switch value {
        case "DENIED":
            return ConsentStatus.denied
        case "GRANTED":
            return ConsentStatus.granted
        default:
            return nil
        }
    }
}
