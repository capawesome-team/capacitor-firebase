import Foundation
import Capacitor
import UserNotifications

@objc public class NotificationReceivedEvent: NSObject, Result {
    let notification: JSObject

    init(notification: UNNotification) {
        self.notification = FirebaseMessagingHelper.createNotificationResult(notification: notification)
    }

    init(notification: NSNotification) {
        self.notification = FirebaseMessagingHelper.createNotificationResult(notification: notification)
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["notification"] = notification
        return result as AnyObject
    }
}
