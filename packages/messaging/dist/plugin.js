var capacitorFirebaseMessaging = (function (exports, core, messaging) {
    'use strict';

    /// <reference types="@capacitor/cli" />
    /**
     * The importance level.
     *
     * For more details, see the [Android Developer Docs](https://developer.android.com/reference/android/app/NotificationManager#IMPORTANCE_DEFAULT)
     *
     * @since 1.4.0
     */
    exports.Importance = void 0;
    (function (Importance) {
        /**
         * @since 1.4.0
         */
        Importance[Importance["Min"] = 1] = "Min";
        /**
         * @since 1.4.0
         */
        Importance[Importance["Low"] = 2] = "Low";
        /**
         * @since 1.4.0
         */
        Importance[Importance["Default"] = 3] = "Default";
        /**
         * @since 1.4.0
         */
        Importance[Importance["High"] = 4] = "High";
        /**
         * @since 1.4.0
         */
        Importance[Importance["Max"] = 5] = "Max";
    })(exports.Importance || (exports.Importance = {}));
    /**
     * The notification visibility.
     *
     * For more details, see the [Android Developer Docs](https://developer.android.com/reference/androidx/core/app/NotificationCompat#VISIBILITY_PRIVATE())
     *
     * @since 1.4.0
     */
    exports.Visibility = void 0;
    (function (Visibility) {
        /**
         * @since 1.4.0
         */
        Visibility[Visibility["Secret"] = -1] = "Secret";
        /**
         * @since 1.4.0
         */
        Visibility[Visibility["Private"] = 0] = "Private";
        /**
         * @since 1.4.0
         */
        Visibility[Visibility["Public"] = 1] = "Public";
    })(exports.Visibility || (exports.Visibility = {}));

    const FirebaseMessaging = core.registerPlugin('FirebaseMessaging', {
        web: () => Promise.resolve().then(function () { return web; }).then(m => new m.FirebaseMessagingWeb()),
    });

    class FirebaseMessagingWeb extends core.WebPlugin {
        constructor() {
            super();
            messaging.isSupported().then(supported => {
                if (!supported) {
                    return;
                }
                const messaging$1 = messaging.getMessaging();
                messaging.onMessage(messaging$1, payload => this.handleNotificationReceived(payload));
            });
        }
        async checkPermissions() {
            const isSupported = await messaging.isSupported();
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
            const isSupported = await messaging.isSupported();
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
            const isSupported = await messaging.isSupported();
            return {
                isSupported,
            };
        }
        async getToken(options) {
            const messaging$1 = messaging.getMessaging();
            const token = await messaging.getToken(messaging$1, {
                vapidKey: options.vapidKey,
                serviceWorkerRegistration: options.serviceWorkerRegistration,
            });
            return {
                token,
            };
        }
        async deleteToken() {
            const messaging$1 = messaging.getMessaging();
            await messaging.deleteToken(messaging$1);
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
                body: (_a = messagePayload.notification) === null || _a === undefined ? undefined : _a.body,
                data: messagePayload.data,
                id: messagePayload.messageId,
                image: (_b = messagePayload.notification) === null || _b === undefined ? undefined : _b.image,
                title: (_c = messagePayload.notification) === null || _c === undefined ? undefined : _c.title,
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

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        FirebaseMessagingWeb: FirebaseMessagingWeb
    });

    exports.FirebaseMessaging = FirebaseMessaging;

    return exports;

})({}, capacitorExports, firebaseMessagingExports);
//# sourceMappingURL=plugin.js.map
