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

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

</docgen-api>

## Changelog

See [CHANGELOG.md](./CHANGELOG.md).

## License

See [LICENSE](./LICENSE).

## Credits

This plugin is based on the [Capacitor Firebase Push plugin](https://github.com/EinfachHans/capacitor-firebase-push).
Thanks to everyone who contributed to the project!
