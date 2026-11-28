import Foundation
import Capacitor
import UserNotifications

@objc public class NotificationActionPerformedEvent: NSObject, Result {
    let response: UNNotificationResponse

    init(response: UNNotificationResponse) {
        self.response = response
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["notification"] = FirebaseMessagingHelper.createNotificationResult(notification: response.notification)
        if response.actionIdentifier == UNNotificationDefaultActionIdentifier {
            result["actionId"] = "tap"
        } else if response.actionIdentifier == UNNotificationDismissActionIdentifier {
            result["actionId"] = "dismiss"
        } else {
            result["actionId"] = response.actionIdentifier
        }
        if let inputType = response as? UNTextInputNotificationResponse {
            result["inputValue"] = inputType.userText
        }
        return result as AnyObject
    }
}
