import Foundation
import Capacitor

@objc public class AddDocumentOptions: NSObject {
    let appName: String?
    let data: JSObject
    let reference: String

    init(_ call: CAPPluginCall) throws {
        self.reference = try AddDocumentOptions.getReferenceFromCall(call)
        self.data = try AddDocumentOptions.getDataFromCall(call)
        self.appName = AddDocumentOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
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
        guard FirebaseFirestoreHelper.isValidCollectionPath(reference) else {
            throw CustomError.referenceInvalid
        }
        return reference
    }
}
