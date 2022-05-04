import Foundation
import Capacitor
import UserNotifications

import FirebaseMessaging
import FirebaseCore

@objc public class FirebaseMessaging: NSObject, MessagingDelegate, NotificationHandlerProtocol {
    private let plugin: FirebaseMessagingPlugin
    private let config: FirebaseMessagingConfig

    init(plugin: FirebaseMessagingPlugin, config: FirebaseMessagingConfig) {
        self.plugin = plugin
        self.config = config
        super.init()
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
        Messaging.messaging().delegate = self
        self.plugin.bridge?.notificationRouter.pushNotificationHandler = self
        DispatchQueue.main.async {
            UIApplication.shared.registerForRemoteNotifications()
        }
    }

    public func requestPermissions(completion: @escaping (_ granted: Bool, _ error: Error?) -> Void) {
        UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .sound, .badge]) { granted, error in
            completion(granted, error)
        }
    }

    public func checkPermissions(completion: @escaping (_ status: String) -> Void) {
        UNUserNotificationCenter.current().getNotificationSettings { settings in
            let permission: String

            switch settings.authorizationStatus {
            case .authorized, .ephemeral, .provisional:
                permission = "granted"
            case .denied:
                permission = "denied"
            case .notDetermined:
                permission = "prompt"
            @unknown default:
                permission = "prompt"
            }

            completion(permission)
        }
    }

    public func getToken(completion: @escaping (String?, Error?) -> Void) {
        Messaging.messaging().isAutoInitEnabled = true
        Messaging.messaging().token(completion: { result, error in
            if let error = error {
                completion(nil, error)
                return
            }
            completion(result, nil)
        })
    }

    public func deleteToken(completion: @escaping (Error?) -> Void) {
        Messaging.messaging().deleteToken(completion: { error in
            if let error = error {
                completion(error)
                return
            }
            completion(nil)
        })
    }

    public func getDeliveredNotifications(completion: @escaping ([UNNotification]) -> Void) {
        UNUserNotificationCenter.current().getDeliveredNotifications { notifications in
            completion(notifications)
        }
    }

    public func removeDeliveredNotifications(ids: [String]) {
        UNUserNotificationCenter.current().removeDeliveredNotifications(withIdentifiers: ids)
    }

    public func removeAllDeliveredNotifications() {
        UNUserNotificationCenter.current().removeAllDeliveredNotifications()
    }

    public func subscribeToTopic(topic: String, completion: @escaping (Error?) -> Void) {
        Messaging.messaging().subscribe(toTopic: topic) { error in
            if let error = error {
                completion(error)
                return
            }
            completion(nil)
        }
    }

    public func unsubscribeFromTopic(topic: String, completion: @escaping (Error?) -> Void) {
        Messaging.messaging().unsubscribe(fromTopic: topic, completion: { error in
            if let error = error {
                completion(error)
                return
            }
            completion(nil)
        })
    }

    public func willPresent(notification: UNNotification) -> UNNotificationPresentationOptions {
        self.plugin.handleNotificationReceived(notification: notification)

        var presentationOptions = UNNotificationPresentationOptions.init()
        self.config.presentationOptions.forEach { option in
            switch option {
            case "alert":
                presentationOptions.insert(.alert)
            case "badge":
                presentationOptions.insert(.badge)
            case "sound":
                presentationOptions.insert(.sound)
            default:
                print("Unrecogizned presentation option: \(option)")
            }
        }

        return presentationOptions
    }

    public func didReceive(response: UNNotificationResponse) {
        self.plugin.handleNotificationActionPerformed(response: response)
    }
}
