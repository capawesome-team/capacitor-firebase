import { WebPlugin } from '@capacitor/core';
import type { Channel, DeleteChannelOptions, FirebaseMessagingPlugin, GetDeliveredNotificationsResult, GetTokenOptions, GetTokenResult, IsSupportedResult, ListChannelsResult, PermissionStatus, RemoveDeliveredNotificationsOptions, SubscribeToTopicOptions, UnsubscribeFromTopicOptions } from './definitions';
export declare class FirebaseMessagingWeb extends WebPlugin implements FirebaseMessagingPlugin {
    static readonly notificationReceivedEvent = "notificationReceived";
    constructor();
    checkPermissions(): Promise<PermissionStatus>;
    requestPermissions(): Promise<PermissionStatus>;
    isSupported(): Promise<IsSupportedResult>;
    getToken(options: GetTokenOptions): Promise<GetTokenResult>;
    deleteToken(): Promise<void>;
    getDeliveredNotifications(): Promise<GetDeliveredNotificationsResult>;
    removeDeliveredNotifications(_options: RemoveDeliveredNotificationsOptions): Promise<void>;
    removeAllDeliveredNotifications(): Promise<void>;
    subscribeToTopic(_options: SubscribeToTopicOptions): Promise<void>;
    unsubscribeFromTopic(_options: UnsubscribeFromTopicOptions): Promise<void>;
    createChannel(_options: Channel): Promise<void>;
    deleteChannel(_options: DeleteChannelOptions): Promise<void>;
    listChannels(): Promise<ListChannelsResult>;
    private handleNotificationReceived;
    private createNotificationResult;
    private convertNotificationPermissionToPermissionState;
    private throwUnavailableError;
}
