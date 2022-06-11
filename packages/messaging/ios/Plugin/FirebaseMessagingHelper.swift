import Foundation
import Capacitor
import FirebaseCore

public class FirebaseMessagingHelper {
    public static func createNotificationResult(notification: UNNotification) -> JSObject {
        var result = JSObject()
        result["body"] = notification.request.content.body
        result["data"] = JSTypes.coerceDictionaryToJSObject(notification.request.content.userInfo) ?? [:]
        result["id"] = notification.request.identifier
        result["subtitle"] = notification.request.content.subtitle
        result["title"] = notification.request.content.title
        return result
    }

    public static func createNotificationResult(notification: NSNotification) -> JSObject {
        var result = JSObject()
        result["data"] = JSTypes.coerceDictionaryToJSObject(notification.userInfo) ?? [:]
        return result
    }
}
