/// <reference types="@capacitor/cli" />

import type { PermissionState, PluginListenerHandle } from '@capacitor/core';

export type PresentationOption = 'badge' | 'sound' | 'alert';

declare module '@capacitor/cli' {
  export interface PluginsConfig {
    /**
     * On iOS you can configure the way the push notifications are displayed when the app is in foreground.
     */
    FirebaseMessaging?: {
      /**
       * This is an array of strings you can combine. Possible values in the array are:
       *   - `badge`: badge count on the app icon is updated (default value)
       *   - `sound`: the device will ring/vibrate when the push notification is received
       *   - `alert`: the push notification is displayed in a native dialog
       *
       * An empty array can be provided if none of the options are desired.
       *
       * Only available for iOS.
       *
       * @example ["badge", "sound", "alert"]
       * @default ["badge", "sound", "alert"]
       * @since 0.2.2
       */
      presentationOptions: PresentationOption[];
    };
  }
}

export interface FirebaseMessagingPlugin {
  /**
   * Check permission to receive push notifications.
   *
   * @since 0.2.2
   */
  checkPermissions(): Promise<PermissionStatus>;
  /**
   * Request permission to receive push notifications.
   *
   * @since 0.2.2
   */
  requestPermissions(): Promise<PermissionStatus>;
  /**
   * Checks if all required APIs exist.
   *
   * Always returns `true` on Android and iOS.
   *
   * @since 0.3.1
   */
  isSupported(): Promise<IsSupportedResult>;
  /**
   * Register the app to receive push notifications.
   * Returns a FCM token that can be used to send push messages to that Messaging instance.
   *
   * This method also re-enables FCM auto-init.
   *
   * @since 0.2.2
   */
  getToken(options?: GetTokenOptions): Promise<GetTokenResult>;
  /**
   * Delete the FCM token and unregister the app to stop receiving push notifications.
   * Can be called, for example, when a user signs out.
   *
   * @since 0.2.2
   */
  deleteToken(): Promise<void>;
  /**
   * Get a list of notifications that are visible on the notifications screen.
   *
   * @since 0.2.2
   */
  getDeliveredNotifications(): Promise<GetDeliveredNotificationsResult>;
  /**
   * Remove specific notifications from the notifications screen.
   *
   * @since 0.2.2
   */
  removeDeliveredNotifications(
    options: RemoveDeliveredNotificationsOptions,
  ): Promise<void>;
  /**
   * Remove all notifications from the notifications screen.
   *
   * @since 0.2.2
   */
  removeAllDeliveredNotifications(): Promise<void>;
  /**
   * Subscribes to topic in the background.
   *
   * Only available for Android and iOS.
   *
   * @since 0.2.2
   */
  subscribeToTopic(options: SubscribeToTopicOptions): Promise<void>;
  /**
   * Unsubscribes from topic in the background.
   *
   * Only available for Android and iOS.
   *
   * @since 0.2.2
   */
  unsubscribeFromTopic(options: UnsubscribeFromTopicOptions): Promise<void>;
  /**
   * Called when a new FCM token is received.
   *
   * @since 0.2.2
   */
  addListener(
    eventName: 'tokenReceived',
    listenerFunc: TokenReceivedListener,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
  /**
   * Called when a new push notification is received.
   *
   * On **Android**, this listener is called for every push notification if the app is in the _foreground_.
   * If the app is in the _background_, then this listener is only called on data push notifications.
   * See https://firebase.google.com/docs/cloud-messaging/android/receive#handling_messages for more information.
   *
   * On **iOS**, this listener is called for every push notification if the app is in the _foreground_.
   * If the app is in the _background_, then this listener is only called for silent push notifications (messages with the `content-available` key).
   * See https://developer.apple.com/library/archive/documentation/NetworkingInternet/Conceptual/RemoteNotificationsPG/PayloadKeyReference.html for more information.
   *
   * @since 0.2.2
   */
  addListener(
    eventName: 'notificationReceived',
    listenerFunc: NotificationReceivedListener,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
  /**
   * Called when a new push notification action is performed.
   *
   * Only available on Android and iOS.
   *
   * @since 0.2.2
   */
  addListener(
    eventName: 'notificationActionPerformed',
    listenerFunc: NotificationActionPerformedListener,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
  /**
   * Remove all native listeners for this plugin.
   *
   * @since 0.2.2
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 0.2.2
 */
export interface PermissionStatus {
  /**
   * @since 0.2.2
   */
  receive: PermissionState;
}

/**
 * @since 0.2.2
 */
export interface GetTokenOptions {
  /**
   * Your VAPID public key, which is required to retrieve the current registration token on the web.
   *
   * Only available for Web.
   */
  vapidKey?: string;
  /**
   * The service worker registration for receiving push messaging.
   * If the registration is not provided explicitly, you need to have a `firebase-messaging-sw.js` at your root location.
   *
   * Only available for Web.
   */
  serviceWorkerRegistration?: ServiceWorkerRegistration;
}

/**
 * @since 0.3.1
 */
export interface IsSupportedResult {
  /**
   * @since 0.3.1
   */
  isSupported: boolean;
}

/**
 * @since 0.2.2
 */
export interface GetTokenResult {
  /**
   * @since 0.2.2
   */
  token: string;
}

/**
 * @since 0.2.2
 */
export interface GetDeliveredNotificationsResult {
  /**
   * @since 0.2.2
   */
  notifications: Notification[];
}

/**
 * @since 0.2.2
 */
export interface RemoveDeliveredNotificationsOptions {
  /**
   * @since 0.4.0
   */
  notifications: Notification[];
}

/**
 * @since 0.2.2
 */
export interface SubscribeToTopicOptions {
  /**
   * The name of the topic to subscribe.
   *
   * @since 0.2.2
   */
  topic: string;
}

/**
 * @since 0.2.2
 */
export interface UnsubscribeFromTopicOptions {
  /**
   * The name of the topic to unsubscribe from.
   *
   * @since 0.2.2
   */
  topic: string;
}

/**
 * Callback to receive the push notification event.
 *
 * @since 0.2.2
 */
export type TokenReceivedListener = (event: TokenReceivedEvent) => void;

/**
 * Callback to receive the push notification event.
 *
 * @since 0.2.2
 */
export type NotificationReceivedListener = (
  event: NotificationReceivedEvent,
) => void;

/**
 * Callback to receive the push notification event.
 *
 * @since 0.2.2
 */
export type NotificationActionPerformedListener = (
  event: NotificationActionPerformedEvent,
) => void;

/**
 * @since 0.2.2
 */
export interface TokenReceivedEvent {
  /**
   * @since 0.2.2
   */
  token: string;
}

/**
 * @since 0.2.2
 */
export interface NotificationReceivedEvent {
  /**
   * @since 0.2.2
   */
  notification: Notification;
}

/**
 * @since 0.2.2
 */
export interface NotificationActionPerformedEvent {
  /**
   * The action performed on the notification.
   *
   * @since 0.2.2
   */
  actionId: string;
  /**
   * Text entered on the notification action.
   *
   * Only available on iOS.
   *
   * @since 0.2.2
   */
  inputValue?: string;
  /**
   * The notification in which the action was performed.
   *
   * @since 0.2.2
   */
  notification: Notification;
}

/**
 * @since 0.2.2
 */
export interface Notification {
  /**
   * The notification payload.
   *
   * @since 0.2.2
   */
  body?: string;
  /**
   * The action to be performed on the user opening the notification.
   *
   * Only available on Android.
   *
   * @since 0.2.2
   */
  clickAction?: string;
  /**
   * Any additional data that was included in the push notification payload.
   *
   * @since 0.2.2
   */
  data?: any;
  /**
   * The notification identifier.
   *
   * @since 0.2.2
   */
  id?: string;
  /**
   * The URL of an image that is downloaded on the device and displayed in the notification.
   *
   * Only available on Web.
   *
   * @since 0.2.2
   */
  image?: string;
  /**
   * Deep link from the notification.
   *
   * Only available on Android.
   *
   * @since 0.2.2
   */
  link?: string;
  /**
   * The notification subtitle.
   *
   * Only available on iOS.
   *
   * @since 0.2.2
   */
  subtitle?: string;
  /**
   * The notification string identifier.
   *
   * Only available on Android.
   *
   * @since 0.4.0
   */
  tag?: string;
  /**
   * The notification title.
   *
   * @since 0.2.2
   */
  title?: string;
}
