import Foundation
import Capacitor

@objc public class RemoveDeliveredNotificationsOptions: NSObject {
    let ids: [String]

    init(_ call: CAPPluginCall) throws {
        let notifications = try RemoveDeliveredNotificationsOptions.getNotificationsFromCall(call)
        self.ids = try RemoveDeliveredNotificationsOptions.getIdsFromNotifications(notifications)
    }

    private static func getIdsFromNotifications(_ notifications: [JSObject]) throws -> [String] {
        var ids = [String]()
        for notification in notifications {
            guard let id = notification["id"] as? String else {
                throw CustomError.notificationsInvalid
            }
            ids.append(id)
        }
        return ids
    }

    private static func getNotificationsFromCall(_ call: CAPPluginCall) throws -> [JSObject] {
        guard let notifications = call.getArray("notifications", JSObject.self) else {
            throw CustomError.notificationsMissing
        }
        return notifications
    }
}
