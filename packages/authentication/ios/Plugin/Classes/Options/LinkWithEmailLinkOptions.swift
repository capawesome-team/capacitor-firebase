import Foundation
import Capacitor

@objc public class LinkWithEmailLinkOptions: NSObject {
    let email: String
    let emailLink: String

    init(_ call: CAPPluginCall) {
        self.email = LinkWithEmailLinkOptions.getEmailFromCall(call)
        self.emailLink = LinkWithEmailLinkOptions.getEmailLinkFromCall(call)
    }

    private static func getEmailFromCall(_ call: CAPPluginCall) -> String {
        return call.getString("email", "")
    }

    private static func getEmailLinkFromCall(_ call: CAPPluginCall) -> String {
        return call.getString("emailLink", "")
    }
}
