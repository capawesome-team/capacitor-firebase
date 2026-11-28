import Foundation
import Capacitor

import FirebaseMessaging
import FirebaseInstallations
import FirebaseCore

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FirebaseMessagingPlugin)
public class FirebaseMessagingPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "FirebaseMessagingPlugin"
    public let jsName = "FirebaseMessaging"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "checkPermissions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "createChannel", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "deleteChannel", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "deleteToken", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getDeliveredNotifications", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getToken", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isSupported", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "listChannels", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "removeAllDeliveredNotifications", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "removeDeliveredNotifications", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "requestPermissions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "subscribeToTopic", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "unsubscribeFromTopic", returnType: CAPPluginReturnPromise)
    ]
    public let tag = "FirebaseMessaging"
    static let eventApnsTokenReceived = "apnsTokenReceived"
    static let eventNotificationActionPerformed = "notificationActionPerformed"
    static let eventNotificationReceived = "notificationReceived"
    static let eventTokenReceived = "tokenReceived"
    private var implementation: FirebaseMessaging?
    private var isFirebaseConfigured: Bool {
        return implementation?.isFirebaseConfigured == true
    }

    @objc override public func checkPermissions(_ call: CAPPluginCall) {
        implementation?.checkPermissions(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
    }

    override public func load() {
        implementation = FirebaseMessaging(plugin: self, config: firebaseMessagingConfig())
        NotificationCenter.default.addObserver(self, selector: #selector(self.didRegisterForRemoteNotifications(notification:)), name: .capacitorDidRegisterForRemoteNotifications, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(self.didReceiveRemoteNotification(notification:)), name: Notification.Name.init("didReceiveRemoteNotification"), object: nil)
    }

    @objc public func notifyApnsTokenReceivedListeners(_ event: ApnsTokenReceivedEvent) {
        self.notifyListeners(Self.eventApnsTokenReceived, data: event.toJSObject() as? [String: Any], retainUntilConsumed: true)
    }

    @objc public func notifyNotificationActionPerformedListeners(_ event: NotificationActionPerformedEvent) {
        self.notifyListeners(Self.eventNotificationActionPerformed, data: event.toJSObject() as? [String: Any], retainUntilConsumed: true)
    }

    @objc public func notifyNotificationReceivedListeners(_ event: NotificationReceivedEvent) {
        self.notifyListeners(Self.eventNotificationReceived, data: event.toJSObject() as? [String: Any], retainUntilConsumed: true)
    }

    @objc public func notifyTokenReceivedListeners(_ event: TokenReceivedEvent) {
        self.notifyListeners(Self.eventTokenReceived, data: event.toJSObject() as? [String: Any], retainUntilConsumed: true)
    }

    @objc override public func requestPermissions(_ call: CAPPluginCall) {
        implementation?.requestPermissions(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func createChannel(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func deleteChannel(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func deleteToken(_ call: CAPPluginCall) {
        guard isFirebaseConfigured else {
            rejectCallAsUnavailable(call)
            return
        }

        implementation?.deleteToken(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func getDeliveredNotifications(_ call: CAPPluginCall) {
        implementation?.getDeliveredNotifications(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func getToken(_ call: CAPPluginCall) {
        guard isFirebaseConfigured else {
            rejectCallAsUnavailable(call)
            return
        }

        implementation?.getToken(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func isSupported(_ call: CAPPluginCall) {
        implementation?.isSupported(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func listChannels(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func removeAllDeliveredNotifications(_ call: CAPPluginCall) {
        implementation?.removeAllDeliveredNotifications(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func removeDeliveredNotifications(_ call: CAPPluginCall) {
        do {
            let options = try RemoveDeliveredNotificationsOptions(call)

            implementation?.removeDeliveredNotifications(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func subscribeToTopic(_ call: CAPPluginCall) {
        guard isFirebaseConfigured else {
            rejectCallAsUnavailable(call)
            return
        }

        do {
            let options = try SubscribeToTopicOptions(call)

            implementation?.subscribeToTopic(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func unsubscribeFromTopic(_ call: CAPPluginCall) {
        guard isFirebaseConfigured else {
            rejectCallAsUnavailable(call)
            return
        }

        do {
            let options = try UnsubscribeFromTopicOptions(call)

            implementation?.unsubscribeFromTopic(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc private func didReceiveRemoteNotification(notification: NSNotification) {
        implementation?.handleRemoteNotificationReceived(notification: notification)
    }

    @objc private func didRegisterForRemoteNotifications(notification: NSNotification) {
        guard let deviceToken = notification.object as? Data else {
            return
        }
        implementation?.setApnsToken(deviceToken)
        notifyApnsTokenReceivedListeners(ApnsTokenReceivedEvent(deviceToken: deviceToken))
    }

    private func firebaseMessagingConfig() -> FirebaseMessagingConfig {
        var config = FirebaseMessagingConfig()

        if let presentationOptions = getConfig().getArray("presentationOptions") as? [String] {
            config.presentationOptions = presentationOptions
        }

        return config
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", self.tag, "] ", error)
        if let error = error as? CustomError {
            call.reject(error.localizedDescription, error.code)
        } else {
            call.reject(error.localizedDescription)
        }
    }

    private func rejectCallAsUnavailable(_ call: CAPPluginCall) {
        call.unavailable("Firebase is not configured: GoogleService-Info.plist is missing from the app bundle.")
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
