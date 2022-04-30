# @capacitor-firebase/messaging

⚡️ Capacitor plugin for Firebase Cloud Messaging (FCM).

## Installation

```bash
npm install @capacitor-firebase/messaging firebase
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://firebase.google.com/docs/android/setup) / [iOS](https://firebase.google.com/docs/ios/setup) / [Web](https://firebase.google.com/docs/web/setup)).

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$firebaseMessagingVersion` version of `com.google.firebase:firebase-messaging` (default: `20.0.6`)

#### Push Notification Icon

The Push Notification icon with the appropriate name should be added to the `android/app/src/main/AndroidManifest.xml` file:

```xml
<meta-data android:name="com.google.firebase.messaging.default_notification_icon" android:resource="@mipmap/push_icon_name" />
```

If no icon is specified, Android uses the application icon, but the push icon should be white pixels on a transparent background. Since the application icon does not usually look like this, it shows a white square or circle. Therefore, it is recommended to provide a separate icon for push notifications.

### iOS

You need to add the following to your `ios/App/App/AppDelegate.swift` file:

1. First, add an import at the top of the file:  
    ```swift
    import Firebase
    ```
1. Then you need to add the following two methods to correctly handle the push registration events:
    ```swift
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        Messaging.messaging().apnsToken = deviceToken
        Messaging.messaging().token(completion: { (token, error) in
            if let error = error {
                NotificationCenter.default.post(name: .capacitorDidFailToRegisterForRemoteNotifications, object: error)
            } else if let token = token {
                NotificationCenter.default.post(name: .capacitorDidRegisterForRemoteNotifications, object: token)
            }
        })
    }

    func application(_ application: UIApplication, didReceiveRemoteNotification userInfo: [AnyHashable : Any], fetchCompletionHandler completionHandler: @escaping (UIBackgroundFetchResult) -> Void) {
        NotificationCenter.default.post(name: Notification.Name.init("didReceiveRemoteNotification"), object: completionHandler, userInfo: userInfo)
    }
    ```
1. Finally edit your `ios/App/App/Info.plist` and add `FirebaseAppDelegateProxyEnabled` key to `NO`.


## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-firebase-plugin-demo](https://github.com/robingenz/capacitor-firebase-plugin-demo)

## Usage

```typescript
import { FirebaseMessaging } from '@capacitor-firebase/messaging';

const echo = async () => {
  await FirebaseMessaging.echo();
};
```

## API

<docgen-index>

* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions()`](#requestpermissions)
* [`register()`](#register)
* [`unregister()`](#unregister)
* [`getDeliveredNotifications()`](#getdeliverednotifications)
* [`removeDeliveredNotifications(...)`](#removedeliverednotifications)
* [`removeAllDeliveredNotifications()`](#removealldeliverednotifications)
* [`addListener('tokenReceived', ...)`](#addlistenertokenreceived)
* [`addListener('notificationReceived', ...)`](#addlistenernotificationreceived)
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


### register()

```typescript
register() => Promise<void>
```

Register the app to receive push notifications.

**Since:** 0.2.2

--------------------


### unregister()

```typescript
unregister() => Promise<void>
```

Unregister the app to stop receiving push notifications.
Can be called, for example, when a user signs out.

**Since:** 0.2.2

--------------------


### getDeliveredNotifications()

```typescript
getDeliveredNotifications() => Promise<NotificationsResult>
```

Get a list of notifications that are visible on the notifications screen.

**Returns:** <code>Promise&lt;<a href="#notificationsresult">NotificationsResult</a>&gt;</code>

**Since:** 0.2.2

--------------------


### removeDeliveredNotifications(...)

```typescript
removeDeliveredNotifications(options: NotificationsIds) => Promise<void>
```

Remove specific notifications from the notifications screen.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#notificationsids">NotificationsIds</a></code> |

**Since:** 0.2.2

--------------------


### removeAllDeliveredNotifications()

```typescript
removeAllDeliveredNotifications() => Promise<void>
```

Remove all notifications from the notifications screen.

**Since:** 0.2.2

--------------------


### addListener('tokenReceived', ...)

```typescript
addListener(eventName: 'tokenReceived', listenerFunc: TokenReceivedListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

Called when a new FCM token is created.

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

| Param              | Type                                                                                  |
| ------------------ | ------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'notificationReceived'</code>                                                   |
| **`listenerFunc`** | <code><a href="#notificationreceivedlistener">NotificationReceivedListener</a></code> |

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


#### NotificationsResult

| Prop                | Type                        | Since |
| ------------------- | --------------------------- | ----- |
| **`notifications`** | <code>Notification[]</code> | 0.2.2 |


#### Notification

| Prop           | Type                | Description                                                             | Since |
| -------------- | ------------------- | ----------------------------------------------------------------------- | ----- |
| **`title`**    | <code>string</code> | The notification title.                                                 | 0.2.2 |
| **`subtitle`** | <code>string</code> | The notification subtitle.                                              | 0.2.2 |
| **`body`**     | <code>string</code> | The notification payload.                                               | 0.2.2 |
| **`id`**       | <code>string</code> | The notification identifier.                                            | 0.2.2 |
| **`data`**     | <code>any</code>    | Any additional data that was included in the push notification payload. | 0.2.2 |


#### NotificationsIds

| Prop      | Type                  | Since |
| --------- | --------------------- | ----- |
| **`ids`** | <code>string[]</code> | 0.2.2 |


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


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### TokenReceivedListener

Callback to receive the token event.

<code>(event: <a href="#tokenreceivedevent">TokenReceivedEvent</a>): void</code>


#### NotificationReceivedListener

Callback to receive the push notification event.

<code>(event: <a href="#notificationreceivedevent">NotificationReceivedEvent</a>): void</code>

</docgen-api>

## Changelog

See [CHANGELOG.md](./CHANGELOG.md).

## License

See [LICENSE](./LICENSE).

## Credits

This plugin is based on the [Capacitor Firebase Push plugin](https://github.com/EinfachHans/capacitor-firebase-push).
Thanks to everyone who contributed to the project!
