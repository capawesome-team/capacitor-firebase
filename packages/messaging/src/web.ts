import type { PermissionState } from '@capacitor/core';
import { WebPlugin } from '@capacitor/core';
import type { MessagePayload } from 'firebase/messaging';
import {
  deleteToken,
  getMessaging,
  getToken,
  onMessage,
} from 'firebase/messaging';

import { Notification } from './definitions';
import type {
  FirebaseMessagingPlugin,
  GetDeliveredNotificationsResult,
  NotificationReceivedEvent,
  PermissionStatus,
  RegistrationErrorEvent,
  RegistrationEvent,
  RemoveDeliveredNotificationsOptions,
} from './definitions';

export class FirebaseMessagingWeb
  extends WebPlugin
  implements FirebaseMessagingPlugin {
  public static readonly registrationEvent = 'registration';
  public static readonly registrationErrorEvent = 'registrationError';
  public static readonly notificationReceivedEvent = 'notificationReceived';

  constructor() {
    super();
    const messaging = getMessaging();
    onMessage(messaging, payload => this.handleNotificationReceived(payload));
  }

  public async register(): Promise<void> {
    const messaging = getMessaging();
    try {
      const token = await getToken(messaging, {
        vapidKey: '<YOUR_PUBLIC_VAPID_KEY_HERE>',
      });
      if (token) {
        this.handleRegistration(token);
      } else {
        this.handleRegistrationError(
          'No registration token available. Request permission to generate one.',
        );
      }
    } catch (error) {
      if (error instanceof Error) {
        this.handleRegistrationError(error.message);
      } else {
        this.handleRegistrationError('' + error);
      }
    }
  }

  public async unregister(): Promise<void> {
    const messaging = getMessaging();
    await deleteToken(messaging);
  }

  public async getDeliveredNotifications(): Promise<GetDeliveredNotificationsResult> {
    this.throwUnavailableError();
  }

  public async removeDeliveredNotifications(
    _options: RemoveDeliveredNotificationsOptions,
  ): Promise<void> {
    this.throwUnavailableError();
  }

  public async removeAllDeliveredNotifications(): Promise<void> {
    this.throwUnavailableError();
  }

  public async removeAllListeners(): Promise<void> {
    this.throwUnavailableError();
  }

  public async checkPermissions(): Promise<PermissionStatus> {
    const receive = this.convertNotificationPermissionToPermissionState(
      Notification.permission,
    );
    return {
      receive,
    };
  }

  public async requestPermissions(): Promise<PermissionStatus> {
    const notificationPermission = await Notification.requestPermission();
    const receive = this.convertNotificationPermissionToPermissionState(
      notificationPermission,
    );
    return {
      receive,
    };
  }

  private handleRegistration(token: string): void {
    const event: RegistrationEvent = {
      token,
    };
    this.notifyListeners(FirebaseMessagingWeb.registrationEvent, event);
  }

  private handleRegistrationError(message: string): void {
    const event: RegistrationErrorEvent = {
      message,
    };
    this.notifyListeners(FirebaseMessagingWeb.registrationErrorEvent, event);
  }

  private handleNotificationReceived(messagePayload: MessagePayload): void {
    const notification = this.createNotificationResult(messagePayload);
    const event: NotificationReceivedEvent = {
      notification,
    };
    this.notifyListeners(FirebaseMessagingWeb.notificationReceivedEvent, event);
  }

  private createNotificationResult(
    messagePayload: MessagePayload,
  ): Notification {
    const notification: Notification = {
      body: messagePayload.notification?.body,
      data: messagePayload.data,
      id: messagePayload.messageId,
      title: messagePayload.notification?.title,
    };
    return notification;
  }

  private convertNotificationPermissionToPermissionState(
    permission: NotificationPermission,
  ) {
    let state: PermissionState = 'prompt';
    switch (permission) {
      case 'granted':
        state = 'granted';
        break;
      case 'denied':
        state = 'denied';
        break;
    }
    return state;
  }

  private throwUnavailableError(): never {
    throw this.unavailable('Not available on web.');
  }
}
