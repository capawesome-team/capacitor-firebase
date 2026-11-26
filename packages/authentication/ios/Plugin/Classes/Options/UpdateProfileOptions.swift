import Foundation
import Capacitor

@objc public class UpdateProfileOptions: NSObject {
    let appName: String?
    let displayName: String?
    let photoUrl: String?

    init(_ call: CAPPluginCall) {
        self.displayName = UpdateProfileOptions.getDisplayNameFromCall(call)
        self.photoUrl = UpdateProfileOptions.getPhotoUrlFromCall(call)
        self.appName = UpdateProfileOptions.getAppNameFromCall(call)
    }

    private static func getAppNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("appName")
    }

    private static func getDisplayNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("displayName")
    }

    private static func getPhotoUrlFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("photoUrl")
    }
}
