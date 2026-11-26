import Foundation
import Capacitor

@objc public class DeleteDocumentOptions: NSObject {
    let appName: String?
    let reference: String

    init(_ call: CAPPluginCall) throws {
        self.reference = try DeleteDocumentOptions.getReferenceFromCall(call)
        self.appName = DeleteDocumentOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }

    private static func getReferenceFromCall(_ call: CAPPluginCall) throws -> String {
        guard let reference = call.getString("reference") else {
            throw CustomError.referenceMissing
        }
        guard FirebaseFirestoreHelper.isValidDocumentPath(reference) else {
            throw CustomError.referenceInvalid
        }
        return reference
    }
}
