import Foundation
import Capacitor

@objc public class UpdateProfileOptions: NSObject {
    let displayName: String?
    let photoUrl: String?

    init(_ call: CAPPluginCall) {
        self.displayName = UpdateProfileOptions.getDisplayNameFromCall(call)
        self.photoUrl = UpdateProfileOptions.getPhotoUrlFromCall(call)
    }

    private static func getDisplayNameFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("displayName")
    }

    private static func getPhotoUrlFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("photoUrl")
    }
}
