# @capacitor-firebase/messaging

⚡️ Capacitor plugin for Firebase Cloud Messaging (FCM).

## Installation

```bash
npm install @capacitor-firebase/messaging firebase
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://firebase.google.com/docs/android/setup) / [iOS](https://firebase.google.com/docs/ios/setup) / [Web](https://firebase.google.com/docs/web/setup)).

### Android

**Attention**: Please make sure that you are using **Java 11**.

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$firebaseMessagingVersion` version of `com.google.firebase:firebase-messaging` (default: `20.0.6`)

#### Push Notification Icon

The Push Notification icon with the appropriate name should be added to the `android/app/src/main/AndroidManifest.xml` file:

```xml
<meta-data android:name="com.google.firebase.messaging.default_notification_icon" android:resource="@mipmap/push_icon_name" />
```

If no icon is specified, Android uses the application icon, but the push icon should be white pixels on a transparent background. Since the application icon does not usually look like this, it shows a white square or circle. Therefore, it is recommended to provide a separate icon for push notifications.

#### Prevent auto initialization

When a registration token is generated, the library uploads the identifier and configuration data to Firebase.
If you prefer to prevent token autogeneration, disable Analytics collection and FCM auto initialization by adding these metadata values to the `android/app/src/main/AndroidManifest.xml` file:

```xml
<meta-data
    android:name="firebase_messaging_auto_init_enabled"
    android:value="false" />
<meta-data
    android:name="firebase_analytics_collection_enabled"
    android:value="false" />
```

### iOS

See [Prerequisites](https://capacitorjs.com/docs/guides/push-notifications-firebase#prerequisites) and complete the prerequisites first.

See [Upload the APNS Certificate or Key to Firebase](https://capacitorjs.com/docs/guides/push-notifications-firebase#upload-the-apns-certificate-or-key-to-firebase) and follow the instructions to upload the APNS Certificate or APNS Auth Key to Firebase.

> If you have difficulties with the instructions, you can also look at the corresponding sections of [this guide](https://www.raywenderlich.com/20201639-firebase-cloud-messaging-for-ios-push-notifications). 
The steps are explained there in a quite understandable way.
But be aware that this guide is more detailed and covers more than you need. 
Use it only for assistance.

Add the following to your app's `AppDelegate.swift`:

```swift
func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
  NotificationCenter.default.post(name: .capacitorDidRegisterForRemoteNotifications, object: deviceToken)
}

func application(_ application: UIApplication, didFailToRegisterForRemoteNotificationsWithError error: Error) {
  NotificationCenter.default.post(name: .capacitorDidFailToRegisterForRemoteNotifications, object: error)
}
    
func application(_ application: UIApplication, didReceiveRemoteNotification userInfo: [AnyHashable : Any], fetchCompletionHandler completionHandler: @escaping (UIBackgroundFetchResult) -> Void) {
    NotificationCenter.default.post(name: Notification.Name.init("didReceiveRemoteNotification"), object: completionHandler, userInfo: userInfo)
}
```

Finally edit your `ios/App/App/Info.plist` and set `FirebaseAppDelegateProxyEnabled` key to `NO`.

**Attention**: If you use this plugin in combination with `@capacitor-firebase/authentication`, then add the following to your app's `AppDelegate.swift`:

```diff
+ import FirebaseAuth

func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
+    if Auth.auth().canHandle(url) {
+      return true
+    }
    return ApplicationDelegateProxy.shared.application(app, open: url, options: options)
}
```

#### Prevent auto initialization

When a registration token is generated, the library uploads the identifier and configuration data to Firebase.
If you prefer to prevent token autogeneration, disable FCM auto initialization by editing your `ios/App/App/Info.plist` and set `FirebaseMessagingAutoInitEnabled` key to `NO`.

### Web

1. See [Configure Web Credentials with FCM](https://firebase.google.com/docs/cloud-messaging/js/client#configure_web_credentials_with) and follow the instructions to configure your web credentials correctly.
1. Add a `firebase-messaging-sw.js` file to the root of your domain. This file can be empty if you do not want to receive push notifications in the background.
   See [Setting notification options in the service worker](https://firebase.google.com/docs/cloud-messaging/js/receive#setting_notification_options_in_the_service_worker) for more information.

## Configuration

<docgen-config>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

On iOS you can configure the way the push notifications are displayed when the app is in foreground.

| Prop                      | Type                              | Description                                                                                                                                                                                                                                                                                                                                                                                 | Default                                  | Since |
| ------------------------- | --------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------- | ----- |
| **`presentationOptions`** | <code>PresentationOption[]</code> | This is an array of strings you can combine. Possible values in the array are: - `badge`: badge count on the app icon is updated (default value) - `sound`: the device will ring/vibrate when the push notification is received - `alert`: the push notification is displayed in a native dialog An empty array can be provided if none of the options are desired. Only available for iOS. | <code>["badge", "sound", "alert"]</code> | 0.2.2 |

### Examples

In `capacitor.config.json`:

```json
{
  "plugins": {
    "PushNotifications": {
      "presentationOptions": ["badge", "sound", "alert"]
    }
  }
}
```

In `capacitor.config.ts`:

```ts
/// <reference types="@capacitor/push-notifications" />

import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    PushNotifications: {
      presentationOptions: ["badge", "sound", "alert"],
    },
  },
};

export default config;
```

</docgen-config>

## Demo

A working example can be found here: [robingenz/capacitor-firebase-plugin-demo](https://github.com/robingenz/capacitor-firebase-plugin-demo)

## Usage

```typescript
import { FirebaseMessaging } from '@capacitor-firebase/messaging';

const checkPermissions = async () => {
  const result = await FirebaseMessaging.checkPermissions();
  return result.receive;
};

const requestPermissions = async () => {
  const result = await FirebaseMessaging.requestPermissions();
  return result.receive;
};

const getToken = async () => {
  const result = await FirebaseMessaging.getToken();
  return result.token;
};

const deleteToken = async () => {
  await FirebaseMessaging.deleteToken();
};

const getDeliveredNotifications = async () => {
  const result = await FirebaseMessaging.getDeliveredNotifications();
  return result.notifications;
};

const removeDeliveredNotifications = async () => {
  await FirebaseMessaging.removeDeliveredNotifications({
    ids: ['798dfhliblqew89pzads'],
  });
};

const removeAllDeliveredNotifications = async () => {
  await FirebaseMessaging.removeAllDeliveredNotifications();
};

const subscribeToTopic = async () => {
  await FirebaseMessaging.subscribeToTopic({ topic: 'news' });
};

const unsubscribeFromTopic = async () => {
  await FirebaseMessaging.unsubscribeFromTopic({ topic: 'news' });
};

const addTokenReceivedListener = async () => {
  await FirebaseMessaging.addListener('tokenReceived', event => {
    console.log('tokenReceived', { event });
  });
};

const addNotificationReceivedListener = async () => {
  await FirebaseMessaging.addListener('notificationReceived', event => {
    console.log('notificationReceived', { event });
  });
};

const addNotificationActionPerformedListener = async () => {
  await FirebaseMessaging.addListener('notificationActionPerformed', event => {
    console.log('notificationActionPerformed', { event });
  });
};

const removeAllListeners = async () => {
  await FirebaseMessaging.removeAllListeners();
};
```

## API

<docgen-index>

* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions()`](#requestpermissions)
* [`isSupported()`](#issupported)
* [`getToken(...)`](#gettoken)
* [`deleteToken()`](#deletetoken)
* [`getDeliveredNotifications()`](#getdeliverednotifications)
* [`removeDeliveredNotifications(...)`](#removedeliverednotifications)
* [`removeAllDeliveredNotifications()`](#removealldeliverednotifications)
* [`subscribeToTopic(...)`](#subscribetotopic)
* [`unsubscribeFromTopic(...)`](#unsubscribefromtopic)
* [`addListener('tokenReceived', ...)`](#addlistenertokenreceived)
* [`addListener('notificationReceived', ...)`](#addlistenernotificationreceived)
* [`addListener('notificationActionPerformed', ...)`](#addlistenernotificationactionperformed)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check permission to receive push notifications.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.2.2

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request permission to receive push notifications.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.2.2

--------------------


### isSupported()

```typescript
isSupported() => Promise<IsSupportedResult>
```

Checks if all required APIs exist.

Always returns `true` on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#issupportedresult">IsSupportedResult</a>&gt;</code>

**Since:** 0.3.1

--------------------


### getToken(...)

```typescript
getToken(options?: GetTokenOptions | undefined) => Promise<GetTokenResult>
```

Register the app to receive push notifications.
Returns a FCM token that can be used to send push messages to that Messaging instance.

This method also re-enables FCM auto-init.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#gettokenoptions">GetTokenOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#gettokenresult">GetTokenResult</a>&gt;</code>

**Since:** 0.2.2

--------------------


### deleteToken()

```typescript
deleteToken() => Promise<void>
```

Delete the FCM token and unregister the app to stop receiving push notifications.
Can be called, for example, when a user signs out.

**Since:** 0.2.2

--------------------


### getDeliveredNotifications()

```typescript
getDeliveredNotifications() => Promise<GetDeliveredNotificationsResult>
```

Get a list of notifications that are visible on the notifications screen.

**Returns:** <code>Promise&lt;<a href="#getdeliverednotificationsresult">GetDeliveredNotificationsResult</a>&gt;</code>

**Since:** 0.2.2

--------------------


### removeDeliveredNotifications(...)

```typescript
removeDeliveredNotifications(options: RemoveDeliveredNotificationsOptions) => Promise<void>
```

Remove specific notifications from the notifications screen.

| Param         | Type                                                                                                |
| ------------- | --------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#removedeliverednotificationsoptions">RemoveDeliveredNotificationsOptions</a></code> |

**Since:** 0.2.2

--------------------


### removeAllDeliveredNotifications()

```typescript
removeAllDeliveredNotifications() => Promise<void>
```

Remove all notifications from the notifications screen.

**Since:** 0.2.2

--------------------


### subscribeToTopic(...)

```typescript
subscribeToTopic(options: SubscribeToTopicOptions) => Promise<void>
```

Subscribes to topic in the background.

Only available for Android and iOS.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#subscribetotopicoptions">SubscribeToTopicOptions</a></code> |

**Since:** 0.2.2

--------------------


### unsubscribeFromTopic(...)

```typescript
unsubscribeFromTopic(options: UnsubscribeFromTopicOptions) => Promise<void>
```

Unsubscribes from topic in the background.

Only available for Android and iOS.

| Param         | Type                                                                                |
| ------------- | ----------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#unsubscribefromtopicoptions">UnsubscribeFromTopicOptions</a></code> |

**Since:** 0.2.2

--------------------


### addListener('tokenReceived', ...)

```typescript
addListener(eventName: 'tokenReceived', listenerFunc: TokenReceivedListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

Called when a new FCM token is received.

| Param              | Type                                                                    |
| ------------------ | ----------------------------------------------------------------------- |
| **`eventName`**    | <code>'tokenReceived'</code>                                            |
| **`listenerFunc`** | <code><a href="#tokenreceivedlistener">TokenReceivedListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

**Since:** 0.2.2

--------------------


### addListener('notificationReceived', ...)

```typescript
addListener(eventName: 'notificationReceived', listenerFunc: NotificationReceivedListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

Called when a new push notification is received.

On **Android**, this listener is called for every push notification if the app is in the _foreground_.
If the app is in the _background_, then this listener is only called on data push notifications.
See https://firebase.google.com/docs/cloud-messaging/android/receive#handling_messages for more information.

On **iOS**, this listener is called for every push notification if the app is in the _foreground_.
If the app is in the _background_, then this listener is only called for silent push notifications (messages with the `content-available` key).
See https://developer.apple.com/library/archive/documentation/NetworkingInternet/Conceptual/RemoteNotificationsPG/PayloadKeyReference.html for more information.

| Param              | Type                                                                                  |
| ------------------ | ------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'notificationReceived'</code>                                                   |
| **`listenerFunc`** | <code><a href="#notificationreceivedlistener">NotificationReceivedListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

**Since:** 0.2.2

--------------------


### addListener('notificationActionPerformed', ...)

```typescript
addListener(eventName: 'notificationActionPerformed', listenerFunc: NotificationActionPerformedListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

Called when a new push notification action is performed.

Only available on Android and iOS.

| Param              | Type                                                                                                |
| ------------------ | --------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'notificationActionPerformed'</code>                                                          |
| **`listenerFunc`** | <code><a href="#notificationactionperformedlistener">NotificationActionPerformedListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

**Since:** 0.2.2

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all native listeners for this plugin.

**Since:** 0.2.2

--------------------


### Interfaces


#### PermissionStatus

| Prop          | Type                                                        | Since |
| ------------- | ----------------------------------------------------------- | ----- |
| **`receive`** | <code><a href="#permissionstate">PermissionState</a></code> | 0.2.2 |


#### IsSupportedResult

| Prop              | Type                 | Since |
| ----------------- | -------------------- | ----- |
| **`isSupported`** | <code>boolean</code> | 0.3.1 |


#### GetTokenResult

| Prop        | Type                | Since |
| ----------- | ------------------- | ----- |
| **`token`** | <code>string</code> | 0.2.2 |


#### GetTokenOptions

| Prop                            | Type                                   | Description                                                                                                                                                                                                |
| ------------------------------- | -------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`vapidKey`**                  | <code>string</code>                    | Your VAPID public key, which is required to retrieve the current registration token on the web. Only available for Web.                                                                                    |
| **`serviceWorkerRegistration`** | <code>ServiceWorkerRegistration</code> | The service worker registration for receiving push messaging. If the registration is not provided explicitly, you need to have a `firebase-messaging-sw.js` at your root location. Only available for Web. |


#### GetDeliveredNotificationsResult

| Prop                | Type                        | Since |
| ------------------- | --------------------------- | ----- |
| **`notifications`** | <code>Notification[]</code> | 0.2.2 |


#### Notification

| Prop              | Type                 | Description                                                                                                    | Since |
| ----------------- | -------------------- | -------------------------------------------------------------------------------------------------------------- | ----- |
| **`body`**        | <code>string</code>  | The notification payload.                                                                                      | 0.2.2 |
| **`clickAction`** | <code>string</code>  | The action to be performed on the user opening the notification. Only available on Android.                    | 0.2.2 |
| **`data`**        | <code>unknown</code> | Any additional data that was included in the push notification payload.                                        | 0.2.2 |
| **`id`**          | <code>string</code>  | The notification identifier.                                                                                   | 0.2.2 |
| **`image`**       | <code>string</code>  | The URL of an image that is downloaded on the device and displayed in the notification. Only available on Web. | 0.2.2 |
| **`link`**        | <code>string</code>  | Deep link from the notification. Only available on Android.                                                    | 0.2.2 |
| **`subtitle`**    | <code>string</code>  | The notification subtitle. Only available on iOS.                                                              | 0.2.2 |
| **`tag`**         | <code>string</code>  | The notification string identifier. Only available on Android.                                                 | 0.4.0 |
| **`title`**       | <code>string</code>  | The notification title.                                                                                        | 0.2.2 |


#### RemoveDeliveredNotificationsOptions

| Prop                | Type                        | Since |
| ------------------- | --------------------------- | ----- |
| **`notifications`** | <code>Notification[]</code> | 0.4.0 |


#### SubscribeToTopicOptions

| Prop        | Type                | Description                         | Since |
| ----------- | ------------------- | ----------------------------------- | ----- |
| **`topic`** | <code>string</code> | The name of the topic to subscribe. | 0.2.2 |


#### UnsubscribeFromTopicOptions

| Prop        | Type                | Description                                | Since |
| ----------- | ------------------- | ------------------------------------------ | ----- |
| **`topic`** | <code>string</code> | The name of the topic to unsubscribe from. | 0.2.2 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### TokenReceivedEvent

| Prop        | Type                | Since |
| ----------- | ------------------- | ----- |
| **`token`** | <code>string</code> | 0.2.2 |


#### NotificationReceivedEvent

| Prop               | Type                                                  | Since |
| ------------------ | ----------------------------------------------------- | ----- |
| **`notification`** | <code><a href="#notification">Notification</a></code> | 0.2.2 |


#### NotificationActionPerformedEvent

| Prop               | Type                                                  | Description                                                     | Since |
| ------------------ | ----------------------------------------------------- | --------------------------------------------------------------- | ----- |
| **`actionId`**     | <code>string</code>                                   | The action performed on the notification.                       | 0.2.2 |
| **`inputValue`**   | <code>string</code>                                   | Text entered on the notification action. Only available on iOS. | 0.2.2 |
| **`notification`** | <code><a href="#notification">Notification</a></code> | The notification in which the action was performed.             | 0.2.2 |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### TokenReceivedListener

Callback to receive the push notification event.

<code>(event: <a href="#tokenreceivedevent">TokenReceivedEvent</a>): void</code>


#### NotificationReceivedListener

Callback to receive the push notification event.

<code>(event: <a href="#notificationreceivedevent">NotificationReceivedEvent</a>): void</code>


#### NotificationActionPerformedListener

Callback to receive the push notification event.

<code>(event: <a href="#notificationactionperformedevent">NotificationActionPerformedEvent</a>): void</code>

</docgen-api>

## Changelog

See [CHANGELOG.md](/packages/messaging/CHANGELOG.md).

## License

See [LICENSE](/packages/messaging/LICENSE).
