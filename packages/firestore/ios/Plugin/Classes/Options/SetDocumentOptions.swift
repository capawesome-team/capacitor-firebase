import Foundation
import Capacitor

@objc public class SetDocumentOptions: NSObject {
    let data: JSObject
    let merge: Bool
    let reference: String

    init(_ call: CAPPluginCall) throws {
        self.reference = try SetDocumentOptions.getReferenceFromCall(call)
        self.data = try SetDocumentOptions.getDataFromCall(call)
        self.merge = SetDocumentOptions.getMergeFromCall(call)
    }

    private static func getDataFromCall(_ call: CAPPluginCall) throws -> JSObject {
        guard let data = call.getObject("data") else {
            throw CustomError.dataMissing
        }
        return data
    }

    private static func getMergeFromCall(_ call: CAPPluginCall) -> Bool {
        return call.getBool("merge", false)
    }

    private static func getReferenceFromCall(_ call: CAPPluginCall) throws -> String {
        guard let reference = call.getString("reference") else {
            throw CustomError.referenceMissing
        }
        return reference
    }
}
