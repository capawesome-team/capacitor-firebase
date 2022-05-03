import type { PermissionState } from '@capacitor/core';
import { WebPlugin } from '@capacitor/core';
import type { MessagePayload } from 'firebase/messaging';
import {
  deleteToken,
  getMessaging,
  getToken,
  onMessage,
} from 'firebase/messaging';

import type {
  FirebaseMessagingPlugin,
  GetDeliveredNotificationsResult,
  NotificationReceivedEvent,
  PermissionStatus,
  RemoveDeliveredNotificationsOptions,
  GetTokenOptions,
  GetTokenResult,
} from './definitions';
import { Notification } from './definitions';

export class FirebaseMessagingWeb
  extends WebPlugin
  implements FirebaseMessagingPlugin {
  public static readonly notificationReceivedEvent = 'notificationReceived';

  constructor() {
    super();
    const messaging = getMessaging();
    onMessage(messaging, payload => this.handleNotificationReceived(payload));
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

  public async getToken(options: GetTokenOptions): Promise<GetTokenResult> {
    const messaging = getMessaging();
    const token = await getToken(messaging, {
      vapidKey: options.vapidKey,
    });
    return {
      token,
    };
  }

  public async deleteToken(): Promise<void> {
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
