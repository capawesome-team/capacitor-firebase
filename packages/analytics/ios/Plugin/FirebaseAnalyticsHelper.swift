import Foundation
import FirebaseAnalytics
import CommonCrypto

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

    /**
     * Normalizes email addresses according to Google Analytics API requirements before SHA256 hashing.
     * See: https://firebase.google.com/docs/tutorials/ads-ios-on-device-measurement/step-3#use-hashed-credentials
     *
     * The normalization process:
     * 1. Converts email to lowercase
     * 2. Replaces @googlemail.com with @gmail.com
     * 3. For @gmail.com addresses:
     *    - Removes all periods from username
     *    - Replaces i, I, 1 with l
     *    - Replaces 0 with o
     *    - Replaces 2 with z
     *    - Replaces 5 with s
     *
     * Examples:
     * - an.email.user0125@googlemail.com -> anemalluserolzs@gmail.com
     * - CAPSUSER0125@provider.net -> capsuser0125@provider.net
     */
    public static func normalizeEmail(_ email: String) -> String {
        var normalized = email.lowercased()

        if normalized.hasSuffix("@googlemail.com") {
            normalized = normalized.replacingOccurrences(of: "@googlemail.com", with: "@gmail.com")
        }

        if normalized.hasSuffix("@gmail.com") {
            let components = normalized.components(separatedBy: "@")
            if components.count == 2 {
                var username = components[0]

                username = username.replacingOccurrences(of: ".", with: "")

                username = username.replacingOccurrences(of: "i", with: "l")
                username = username.replacingOccurrences(of: "I", with: "l")
                username = username.replacingOccurrences(of: "1", with: "l")
                username = username.replacingOccurrences(of: "0", with: "o")
                username = username.replacingOccurrences(of: "2", with: "z")
                username = username.replacingOccurrences(of: "5", with: "s")

                normalized = username + "@gmail.com"
            }
        }

        return normalized
    }

    public static func isValidEmail(_ email: String) -> Bool {
        let pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        let regex = try? NSRegularExpression(pattern: pattern)
        let range = NSRange(location: 0, length: email.utf16.count)
        return regex?.firstMatch(in: email, range: range) != nil
    }

    /**
     * See: https://firebase.google.com/docs/tutorials/ads-ios-on-device-measurement/step-3#use-hashed-credentials
     * Note: For phone numbers, they must be in E.164 format before hashing:
     * - Must start with +
     * - 1-3 digits for country code
     * - Max 12 digits for subscriber number
     */
    public static func isValidE164PhoneNumber(_ phoneNumber: String) -> Bool {
        let pattern = "^\\+[0-9]{1,3}[0-9]{1,12}$"
        let regex = try? NSRegularExpression(pattern: pattern)
        let range = NSRange(location: 0, length: phoneNumber.utf16.count)
        return regex?.firstMatch(in: phoneNumber, range: range) != nil
    }

    public static func sha256(_ string: String) -> Data {
        let data = string.data(using: .utf8)!
        var hash = [UInt8](repeating: 0, count: Int(CC_SHA256_DIGEST_LENGTH))
        data.withUnsafeBytes {
            _ = CC_SHA256($0.baseAddress, CC_LONG(data.count), &hash)
        }
        return Data(hash)
    }
}
