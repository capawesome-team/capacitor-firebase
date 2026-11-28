import Foundation
import Capacitor
import UserNotifications

import FirebaseMessaging
import FirebaseCore

@objc public class FirebaseMessaging: NSObject, NotificationHandlerProtocol {
    public var isFirebaseConfigured: Bool {
        return FirebaseApp.app() != nil
    }
    private let config: FirebaseMessagingConfig
    private let plugin: FirebaseMessagingPlugin

    init(plugin: FirebaseMessagingPlugin, config: FirebaseMessagingConfig) {
        self.plugin = plugin
        self.config = config
        super.init()
        if FirebaseApp.app() == nil {
            guard FirebaseOptions.defaultOptions() != nil else {
                CAPLog.print("[FirebaseMessaging] Firebase was not configured: GoogleService-Info.plist is missing from the app bundle.")
                return
            }
            FirebaseApp.configure()
        }
        UIApplication.shared.registerForRemoteNotifications()
        Messaging.messaging().delegate = self
        self.plugin.bridge?.notificationRouter.pushNotificationHandler = self
    }

    @objc public func checkPermissions(completion: @escaping (_ result: PermissionStatusResult?, _ error: Error?) -> Void) {
        UNUserNotificationCenter.current().getNotificationSettings { settings in
            let receive: String

            switch settings.authorizationStatus {
            case .authorized, .ephemeral, .provisional:
                receive = "granted"
            case .denied:
                receive = "denied"
            case .notDetermined:
                receive = "prompt"
            @unknown default:
                receive = "prompt"
            }

            let result = PermissionStatusResult(receive: receive)
            completion(result, nil)
        }
    }

    @objc public func deleteToken(completion: @escaping (_ error: Error?) -> Void) {
        Messaging.messaging().deleteToken(completion: { error in
            if let error = error {
                completion(error)
                return
            }
            completion(nil)
        })
    }

    @objc public func getDeliveredNotifications(completion: @escaping (_ result: GetDeliveredNotificationsResult?, _ error: Error?) -> Void) {
        UNUserNotificationCenter.current().getDeliveredNotifications { notifications in
            let result = GetDeliveredNotificationsResult(notifications: notifications)
            completion(result, nil)
        }
    }

    @objc public func getToken(completion: @escaping (_ result: GetTokenResult?, _ error: Error?) -> Void) {
        Messaging.messaging().isAutoInitEnabled = true
        Messaging.messaging().token(completion: { token, error in
            if let error = error {
                completion(nil, error)
                return
            }
            let result = GetTokenResult(token: token)
            completion(result, nil)
        })
    }

    public func handleRemoteNotificationReceived(notification: NSNotification) {
        guard isFirebaseConfigured else {
            return
        }
        if let userInfo = notification.userInfo {
            Messaging.messaging().appDidReceiveMessage(userInfo)
        }
        self.plugin.notifyNotificationReceivedListeners(NotificationReceivedEvent(notification: notification))
    }

    @objc public func isSupported(completion: @escaping (_ result: IsSupportedResult?, _ error: Error?) -> Void) {
        let result = IsSupportedResult(isSupported: true)
        completion(result, nil)
    }

    @objc public func removeAllDeliveredNotifications(completion: @escaping (_ error: Error?) -> Void) {
        UNUserNotificationCenter.current().removeAllDeliveredNotifications()
        completion(nil)
    }

    @objc public func removeDeliveredNotifications(_ options: RemoveDeliveredNotificationsOptions, completion: @escaping (_ error: Error?) -> Void) {
        UNUserNotificationCenter.current().removeDeliveredNotifications(withIdentifiers: options.ids)
        completion(nil)
    }

    @objc public func requestPermissions(completion: @escaping (_ result: PermissionStatusResult?, _ error: Error?) -> Void) {
        var options = UNAuthorizationOptions()
        options.insert(.alert)
        options.insert(.badge)
        options.insert(.sound)
        if self.config.presentationOptions.contains("criticalAlert") {
            options.insert(.criticalAlert)
        }
        UNUserNotificationCenter.current().requestAuthorization(options: options) { granted, error in
            if let error = error {
                completion(nil, error)
                return
            }
            let result = PermissionStatusResult(receive: granted ? "granted" : "denied")
            completion(result, nil)
        }
    }

    public func setApnsToken(_ token: Data) {
        guard isFirebaseConfigured else {
            return
        }
        Messaging.messaging().apnsToken = token
    }

    @objc public func subscribeToTopic(_ options: SubscribeToTopicOptions, completion: @escaping (_ error: Error?) -> Void) {
        Messaging.messaging().subscribe(toTopic: options.topic) { error in
            if let error = error {
                completion(error)
                return
            }
            completion(nil)
        }
    }

    @objc public func unsubscribeFromTopic(_ options: UnsubscribeFromTopicOptions, completion: @escaping (_ error: Error?) -> Void) {
        Messaging.messaging().unsubscribe(fromTopic: options.topic, completion: { error in
            if let error = error {
                completion(error)
                return
            }
            completion(nil)
        })
    }

    private func handleNotificationActionPerformed(response: UNNotificationResponse) {
        let userInfo = response.notification.request.content.userInfo
        Messaging.messaging().appDidReceiveMessage(userInfo)
        self.plugin.notifyNotificationActionPerformedListeners(NotificationActionPerformedEvent(response: response))
    }

    private func handleNotificationReceived(notification: UNNotification) {
        let userInfo = notification.request.content.userInfo
        Messaging.messaging().appDidReceiveMessage(userInfo)
        self.plugin.notifyNotificationReceivedListeners(NotificationReceivedEvent(notification: notification))
    }
}

extension FirebaseMessaging: MessagingDelegate {

    public func didReceive(response: UNNotificationResponse) {
        self.handleNotificationActionPerformed(response: response)
    }

    public func messaging(_ messaging: Messaging, didReceiveRegistrationToken fcmToken: String?) {
        guard let fcmToken = fcmToken else {
            return
        }
        self.plugin.notifyTokenReceivedListeners(TokenReceivedEvent(token: fcmToken))
    }

    public func willPresent(notification: UNNotification) -> UNNotificationPresentationOptions {
        self.handleNotificationReceived(notification: notification)

        var presentationOptions = UNNotificationPresentationOptions.init()
        self.config.presentationOptions.forEach { option in
            switch option {
            case "alert":
                presentationOptions.insert(.banner)
                presentationOptions.insert(.list)
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
}
