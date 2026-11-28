import Foundation
import Capacitor
import UserNotifications

@objc public class GetDeliveredNotificationsResult: NSObject, Result {
    let notifications: [UNNotification]

    init(notifications: [UNNotification]) {
        self.notifications = notifications
    }

    @objc public func toJSObject() -> AnyObject {
        let notificationResults = notifications.map({ notification in
            return FirebaseMessagingHelper.createNotificationResult(notification: notification)
        })
        var result = JSObject()
        result["notifications"] = notificationResults
        return result as AnyObject
    }
}
