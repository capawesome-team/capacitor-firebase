import Foundation
import Capacitor

@objc public class GetCollectionGroupOptions: NSObject {
    let compositeFilter: JSObject?
    let queryConstraints: [JSObject]?
    let reference: String

    init(_ call: CAPPluginCall) throws {
        self.reference = try GetCollectionGroupOptions.getReferenceFromCall(call)
        self.compositeFilter = GetCollectionGroupOptions.getCompositeFilterFromCall(call)
        self.queryConstraints = GetCollectionGroupOptions.getQueryConstraintsFromCall(call)
    }

    private static func getCompositeFilterFromCall(_ call: CAPPluginCall) -> JSObject? {
        return call.getObject("compositeFilter")
    }

    private static func getQueryConstraintsFromCall(_ call: CAPPluginCall) -> [JSObject]? {
        return call.getArray("queryConstraints", JSObject.self)
    }

    private static func getReferenceFromCall(_ call: CAPPluginCall) throws -> String {
        guard let reference = call.getString("reference") else {
            throw CustomError.referenceMissing
        }
        guard FirebaseFirestoreHelper.isValidCollectionId(reference) else {
            throw CustomError.referenceInvalid
        }
        return reference
    }
}
