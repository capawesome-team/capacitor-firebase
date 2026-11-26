import Foundation
import Capacitor

@objc public class GetCountFromServerOptions: NSObject {
    let appName: String?
    let compositeFilter: JSObject?
    let queryConstraints: [JSObject]?
    let reference: String

    init(_ call: CAPPluginCall) throws {
        self.reference = try GetCountFromServerOptions.getReferenceFromCall(call)
        self.compositeFilter = GetCountFromServerOptions.getCompositeFilterFromCall(call)
        self.queryConstraints = GetCountFromServerOptions.getQueryConstraintsFromCall(call)
        self.appName = GetCountFromServerOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
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
        return reference
    }
}
