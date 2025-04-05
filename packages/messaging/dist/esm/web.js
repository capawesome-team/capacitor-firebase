import { WebPlugin } from '@capacitor/core';
import { deleteToken, getMessaging, getToken, isSupported as isSupportedInWeb, onMessage, } from 'firebase/messaging';
export class FirebaseMessagingWeb extends WebPlugin {
    constructor() {
        super();
        isSupportedInWeb().then(supported => {
            if (!supported) {
                return;
            }
            const messaging = getMessaging();
            onMessage(messaging, payload => this.handleNotificationReceived(payload));
        });
    }
    async checkPermissions() {
        const isSupported = await isSupportedInWeb();
        if (!isSupported) {
            return {
                receive: 'denied',
            };
        }
        const receive = this.convertNotificationPermissionToPermissionState(Notification.permission);
        return {
            receive,
        };
    }
    async requestPermissions() {
        const isSupported = await isSupportedInWeb();
        if (!isSupported) {
            return {
                receive: 'denied',
            };
        }
        const notificationPermission = await Notification.requestPermission();
        const receive = this.convertNotificationPermissionToPermissionState(notificationPermission);
        return {
            receive,
        };
    }
    async isSupported() {
        const isSupported = await isSupportedInWeb();
        return {
            isSupported,
        };
    }
    async getToken(options) {
        const messaging = getMessaging();
        const token = await getToken(messaging, {
            vapidKey: options.vapidKey,
            serviceWorkerRegistration: options.serviceWorkerRegistration,
        });
        return {
            token,
        };
    }
    async deleteToken() {
        const messaging = getMessaging();
        await deleteToken(messaging);
    }
    async getDeliveredNotifications() {
        this.throwUnavailableError();
    }
    async removeDeliveredNotifications(_options) {
        this.throwUnavailableError();
    }
    async removeAllDeliveredNotifications() {
        this.throwUnavailableError();
    }
    async subscribeToTopic(_options) {
        this.throwUnavailableError();
    }
    async unsubscribeFromTopic(_options) {
        this.throwUnavailableError();
    }
    async createChannel(_options) {
        this.throwUnavailableError();
    }
    async deleteChannel(_options) {
        this.throwUnavailableError();
    }
    async listChannels() {
        this.throwUnavailableError();
    }
    handleNotificationReceived(messagePayload) {
        const notification = this.createNotificationResult(messagePayload);
        const event = {
            notification,
        };
        this.notifyListeners(FirebaseMessagingWeb.notificationReceivedEvent, event);
    }
    createNotificationResult(messagePayload) {
        var _a, _b, _c;
        const notification = {
            body: (_a = messagePayload.notification) === null || _a === void 0 ? void 0 : _a.body,
            data: messagePayload.data,
            id: messagePayload.messageId,
            image: (_b = messagePayload.notification) === null || _b === void 0 ? void 0 : _b.image,
            title: (_c = messagePayload.notification) === null || _c === void 0 ? void 0 : _c.title,
        };
        return notification;
    }
    convertNotificationPermissionToPermissionState(permission) {
        let state = 'prompt';
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
    throwUnavailableError() {
        throw this.unavailable('Not available on web.');
    }
}
FirebaseMessagingWeb.notificationReceivedEvent = 'notificationReceived';
//# sourceMappingURL=web.js.map