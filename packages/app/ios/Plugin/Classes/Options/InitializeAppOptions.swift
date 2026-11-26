import Foundation
import Capacitor
import FirebaseCore

@objc public class InitializeAppOptions: NSObject {
    let name: String
    let options: FirebaseOptions

    init(_ call: CAPPluginCall) throws {
        self.name = try InitializeAppOptions.getNameFromCall(call)
        self.options = try InitializeAppOptions.getOptionsFromCall(call)
    }

    private static func getNameFromCall(_ call: CAPPluginCall) throws -> String {
        guard let name = call.getString("name") else {
            throw CustomError.nameMissing
        }
        // NEVER allow other characters. The SDK raises an Objective-C exception for them, which Swift cannot catch.
        guard NSPredicate(format: "SELF MATCHES %@", "^[A-Za-z0-9_-]+$").evaluate(with: name) else {
            throw CustomError.nameInvalid
        }
        return name
    }

    private static func getOptionsFromCall(_ call: CAPPluginCall) throws -> FirebaseOptions {
        guard let options = call.getObject("options") else {
            throw CustomError.optionsMissing
        }
        guard let apiKey = options["apiKey"] as? String else {
            throw CustomError.apiKeyMissing
        }
        // NEVER remove this check. Firebase Installations raises an Objective-C exception for any other API key, which Swift cannot catch.
        guard NSPredicate(format: "SELF MATCHES %@", "^A[A-Za-z0-9_-]{38}$").evaluate(with: apiKey) else {
            throw CustomError.apiKeyInvalid
        }
        guard let applicationId = options["applicationId"] as? String else {
            throw CustomError.applicationIdMissing
        }
        // NEVER remove this check. The SDK raises an Objective-C exception for an app ID that is not an iOS app ID, which Swift cannot catch.
        guard NSPredicate(format: "SELF MATCHES %@", "^[0-9]+:[0-9]+:ios:[0-9a-fA-F]+$").evaluate(with: applicationId) else {
            throw CustomError.applicationIdInvalid
        }
        guard let gcmSenderId = options["gcmSenderId"] as? String else {
            throw CustomError.gcmSenderIdMissing
        }
        // NEVER allow an empty value. Firebase Installations raises an Objective-C exception for it, which Swift cannot catch.
        guard let projectId = options["projectId"] as? String, !projectId.isEmpty else {
            throw CustomError.projectIdMissing
        }
        let firebaseOptions = FirebaseOptions(googleAppID: applicationId, gcmSenderID: gcmSenderId)
        firebaseOptions.apiKey = apiKey
        firebaseOptions.databaseURL = options["databaseUrl"] as? String
        firebaseOptions.projectID = projectId
        firebaseOptions.storageBucket = options["storageBucket"] as? String
        return firebaseOptions
    }
}
