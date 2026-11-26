import Foundation
import Capacitor

@objc public class GetDocumentOptions: NSObject {
    let reference: String

    init(_ call: CAPPluginCall) throws {
        self.reference = try GetDocumentOptions.getReferenceFromCall(call)
    }

    private static func getReferenceFromCall(_ call: CAPPluginCall) throws -> String {
        guard let reference = call.getString("reference") else {
            throw CustomError.referenceMissing
        }
        return reference
    }
}
