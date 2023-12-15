/// <reference types="@capacitor/cli" />

import type { PermissionState, PluginListenerHandle } from '@capacitor/core';

export type PresentationOption = 'badge' | 'sound' | 'alert' | 'criticalAlert';

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
       *   - `criticalAlert`: the push notification is displayed in a native dialog and bypasses the mute switch
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
   * On **Android**, this method only needs to be called on Android 13+.
   *
   * @since 0.2.2
   */
  checkPermissions(): Promise<PermissionStatus>;
  /**
   * Request permission to receive push notifications.
   *
   * On **Android**, this method only needs to be called on Android 13+.
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
   * Create a notification channel.
   *
   * Only available for Android (SDK 26+).
   *
   * @since 1.4.0
   */
  createChannel(options: CreateChannelOptions): Promise<void>;
  /**
   * Delete a notification channel.
   *
   * Only available for Android (SDK 26+).
   *
   * @since 1.4.0
   */
  deleteChannel(options: DeleteChannelOptions): Promise<void>;
  /**
   * List the available notification channels.
   *
   * Only available for Android (SDK 26+).
   *
   * @since 1.4.0
   */
  listChannels(): Promise<ListChannelsResult>;
  /**
   * Called when a new FCM token is received.
   *
   * Only available for Android and iOS.
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
   * Only available for Android and iOS.
   *
   * @since 0.2.2
   */
  addListener(
    eventName: 'notificationActionPerformed',
    listenerFunc: NotificationActionPerformedListener,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
  /**
   * Remove all listeners for this plugin.
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
 * @since 1.4.0
 */
export type CreateChannelOptions = Channel;

/**
 * @since 1.4.0
 */
export interface DeleteChannelOptions {
  /**
   * The channel identifier.
   *
   * @since 1.4.0
   */
  id: string;
}

/**
 * @since 1.4.0
 */
export interface ListChannelsResult {
  channels: Channel[];
}

/**
 * @since 1.4.0
 */
export interface Channel {
  /**
   * The description of this channel (presented to the user).
   *
   * @since 1.4.0
   */
  description?: string;
  /**
   * The channel identifier.
   *
   * @since 1.4.0
   */
  id: string;
  /**
   * The level of interruption for notifications posted to this channel.
   *
   * @since 1.4.0
   */
  importance?: Importance;
  /**
   * The light color for notifications posted to this channel.
   *
   * Only supported if lights are enabled on this channel and the device
   * supports it.
   *
   * Supported color formats are `#RRGGBB` and `#RRGGBBAA`.
   *
   * @since 1.4.0
   */
  lightColor?: string;
  /**
   * Whether notifications posted to this channel should display notification
   * lights, on devices that support it.
   *
   * @since 1.4.0
   */
  lights?: boolean;
  /**
   * The name of this channel (presented to the user).
   *
   * @since 1.4.0
   */
  name: string;
  /**
   * The sound that should be played for notifications posted to this channel.
   *
   * Notification channels with an importance of at least `3` should have a
   * sound.
   *
   * The file name of a sound file should be specified relative to the android
   * app `res/raw` directory.
   *
   * @since 1.4.0
   * @example "jingle.wav"
   */
  sound?: string;
  /**
   * Whether notifications posted to this channel should vibrate.
   *
   * @since 1.4.0
   */
  vibration?: boolean;
  /**
   * The visibility of notifications posted to this channel.
   *
   * This setting is for whether notifications posted to this channel appear on
   * the lockscreen or not, and if so, whether they appear in a redacted form.
   *
   * @since 1.4.0
   */
  visibility?: Visibility;
}

/**
 * The importance level.
 *
 * For more details, see the [Android Developer Docs](https://developer.android.com/reference/android/app/NotificationManager#IMPORTANCE_DEFAULT)
 *
 * @since 1.4.0
 */
export enum Importance {
  /**
   * @since 1.4.0
   */
  Min = 1,
  /**
   * @since 1.4.0
   */
  Low = 2,
  /**
   * @since 1.4.0
   */
  Default = 3,
  /**
   * @since 1.4.0
   */
  High = 4,
  /**
   * @since 1.4.0
   */
  Max = 5,
}

/**
 * The notification visibility.
 *
 * For more details, see the [Android Developer Docs](https://developer.android.com/reference/androidx/core/app/NotificationCompat#VISIBILITY_PRIVATE())
 *
 * @since 1.4.0
 */
export enum Visibility {
  /**
   * @since 1.4.0
   */
  Secret = -1,
  /**
   * @since 1.4.0
   */
  Private = 0,
  /**
   * @since 1.4.0
   */
  Public = 1,
}

/**
 * Callback to receive the token received event.
 *
 * @since 0.2.2
 */
export type TokenReceivedListener = (event: TokenReceivedEvent) => void;

/**
 * Callback to receive the notification received event.
 *
 * @since 0.2.2
 */
export type NotificationReceivedListener = (
  event: NotificationReceivedEvent,
) => void;

/**
 * Callback to receive the notification action performed event.
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
   * Only available for iOS.
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
   * Only available for Android.
   *
   * @since 0.2.2
   */
  clickAction?: string;
  /**
   * Any additional data that was included in the push notification payload.
   *
   * @since 0.2.2
   */
  data?: unknown;
  /**
   * The notification identifier.
   *
   * @since 0.2.2
   */
  id?: string;
  /**
   * The URL of an image that is downloaded on the device and displayed in the notification.
   *
   * Only available for Web.
   *
   * @since 0.2.2
   */
  image?: string;
  /**
   * Deep link from the notification.
   *
   * Only available for Android.
   *
   * @since 0.2.2
   */
  link?: string;
  /**
   * The notification subtitle.
   *
   * Only available for iOS.
   *
   * @since 0.2.2
   */
  subtitle?: string;
  /**
   * The notification string identifier.
   *
   * Only available for Android.
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
