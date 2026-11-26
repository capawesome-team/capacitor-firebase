import Foundation
import Capacitor

@objc public class GetDocumentOptions: NSObject {
    let appName: String?
    let reference: String

    init(_ call: CAPPluginCall) throws {
        self.reference = try GetDocumentOptions.getReferenceFromCall(call)
        self.appName = GetDocumentOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }

    private static func getReferenceFromCall(_ call: CAPPluginCall) throws -> String {
        guard let reference = call.getString("reference") else {
            throw CustomError.referenceMissing
        }
        return reference
    }
}
