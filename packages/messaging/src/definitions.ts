import type { PluginListenerHandle } from '@capacitor/core';

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
   * Register the app to receive push notifications.
   * This method will trigger the `registration` event with the FCM token.
   *
   * @since 0.2.2
   */
  register(options: RegisterOptions): Promise<void>;
  /**
   * Unregister the app to stop receiving push notifications.
   * Can be called, for example, when a user signs out.
   *
   * @since 0.2.2
   */
  unregister(): Promise<void>;
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
   * Called when the push notification registration is completed without problems.
   *
   * @since 0.2.2
   */
  addListener(
    eventName: 'registration',
    listenerFunc: RegistrationListener,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
  /**
   * Called when the push notification registration is completed with problems.
   *
   * @since 0.2.2
   */
  addListener(
    eventName: 'registrationError',
    listenerFunc: RegistrationErrorListener,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
  /**
   * Called when a new push notification is received.
   *
   * @since 0.2.2
   */
  addListener(
    eventName: 'notificationReceived',
    listenerFunc: NotificationReceivedListener,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
  /**
   * Remove all native listeners for this plugin.
   *
   * @since 0.2.2
   */
  removeAllListeners(): Promise<void>;
}

export interface RegisterOptions {
  /**
   * Your VAPID public key, which is required to retrieve the current registration token on the web.
   *
   * Only available for Web.
   */
  vapidKey?: string;
}

/**
 * Callback to receive the token event.
 *
 * @since 0.2.2
 */
export type RegistrationListener = (event: RegistrationEvent) => void;

/**
 * Callback to receive the token event.
 *
 * @since 0.2.2
 */
export type RegistrationErrorListener = (event: RegistrationErrorEvent) => void;

/**
 * Callback to receive the push notification event.
 *
 * @since 0.2.2
 */
export type NotificationReceivedListener = (
  event: NotificationReceivedEvent,
) => void;

/**
 * @since 0.2.2
 */
export interface RegistrationEvent {
  /**
   * @since 0.2.2
   */
  token: string;
}

/**
 * @since 0.2.2
 */
export interface RegistrationErrorEvent {
  /**
   * @since 0.2.2
   */
  message: string;
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
export interface TokenResult {
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
   * @since 0.2.2
   */
  ids: string[];
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
export interface Notification {
  /**
   * The notification payload.
   *
   * @since 0.2.2
   */
  body?: string;
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
  id: string;
  /**
   * The notification title.
   *
   * @since 0.2.2
   */
  title?: string;
}
