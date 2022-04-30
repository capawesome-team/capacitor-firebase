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
   *
   * @since 0.2.2
   */
  register(): Promise<void>;
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
  getDeliveredNotifications(): Promise<NotificationsResult>;
  /**
   * Remove specific notifications from the notifications screen.
   *
   * @since 0.2.2
   */
  removeDeliveredNotifications(options: NotificationsIds): Promise<void>;
  /**
   * Remove all notifications from the notifications screen.
   *
   * @since 0.2.2
   */
  removeAllDeliveredNotifications(): Promise<void>;
  /**
   * Called when a new FCM token is created.
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

/**
 * Callback to receive the token event.
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
export interface TokenResult {
  /**
   * @since 0.2.2
   */
  token: string;
}

/**
 * @since 0.2.2
 */
export interface NotificationsResult {
  /**
   * @since 0.2.2
   */
  notifications: Notification[];
}

/**
 * @since 0.2.2
 */
export interface NotificationsIds {
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
   * The notification title.
   *
   * @since 0.2.2
   */
  title?: string;
  /**
   * The notification subtitle.
   *
   * @since 0.2.2
   */
  subtitle?: string;
  /**
   * The notification payload.
   *
   * @since 0.2.2
   */
  body?: string;
  /**
   * The notification identifier.
   *
   * @since 0.2.2
   */
  id: string;
  /**
   * Any additional data that was included in the push notification payload.
   *
   * @since 0.2.2
   */
  data: any;
}
