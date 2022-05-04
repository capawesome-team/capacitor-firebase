import Foundation
import Capacitor
import FirebaseCore

public class FirebaseMessagingHelper {
    public static func createNotificationResult(notification: UNNotification) -> JSObject {
        var result = JSObject()
        result["id"] = notification.request.identifier
        result["title"] = notification.request.content.title
        result["body"] = notification.request.content.body
        result["data"] = JSTypes.coerceDictionaryToJSObject(notification.request.content.userInfo) ?? [:]
        return result
    }
}
