import Foundation
import Capacitor

@objc public class UpdateDocumentOptions: NSObject {
    let data: JSObject
    let reference: String

    init(_ call: CAPPluginCall) throws {
        self.reference = try UpdateDocumentOptions.getReferenceFromCall(call)
        self.data = try UpdateDocumentOptions.getDataFromCall(call)
    }

    private static func getDataFromCall(_ call: CAPPluginCall) throws -> JSObject {
        guard let data = call.getObject("data") else {
            throw CustomError.dataMissing
        }
        return data
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
