# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 8.x.x](#version-8xx)
- [Version 7.x.x](#version-7xx)
- [Version 6.x.x](#version-6xx)
- [Version 5.x.x](#version-5xx)
- [Version 1.x.x](#version-1xx)
- [Version 0.4.x](#version-04x)
- [Version 0.3.x](#version-03x)

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **36** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

### Variables

- On Android, the `firebaseMessagingVersion` variable has been updated to `25.0.1`.

### Error codes

On **Web**, the following methods now correctly return `UNIMPLEMENTED` instead of `UNAVAILABLE` when called:

- `getDeliveredNotifications()`
- `removeDeliveredNotifications(...)`
- `removeAllDeliveredNotifications()`
- `subscribeToTopic(...)`
- `unsubscribeFromTopic(...)`
- `createChannel(...)`
- `deleteChannel(...)`
- `listChannels()`

These are mobile-specific features that cannot be implemented on the web platform.

### Peer Dependencies

The `firebase` peer dependency has been updated to version `^12.6.0`. Ensure your project uses a compatible version of `firebase` before upgrading.

## Version 7.x.x

### Dependencies

- The Firebase Javascript SDK has been updated to `11.2.0`.

### Variables

- On Android, the `firebaseMessagingVersion` variable has been updated to `24.1.0`.

## Version 6.x.x

### Dependencies

- The Firebase Javascript SDK has been updated to `10.9.0`.

### Variables

- On Android, the `firebaseMessagingVersion` variable has been updated to `23.4.1`.

## Version 5.x.x

### Capacitor 5

This plugin now supports Capacitor 5 only. Please run `npx cap sync` after updating this package.

If you want to use this plugin with Capacitor 4, please install version `1.4.0`:

```
npm i @capacitor-firebase/messaging@1.4.0
```

### Localized error messages

On Android, error messages were previously generated with `getLocalizedMessage`. They are no longer localized and are generated with `getMessage` instead.

You should therefore check your error handling.

## Version 1.x.x

### Capacitor 4

This plugin now supports Capacitor 4 only. Please run `npx cap sync` after updating this package.

If you want to use this plugin with Capacitor 3, please install version `0.5.1`:

```
npm i @capacitor-firebase/messaging@0.5.1
```

### `tokenReceived` event

The `tokenReceived` event is no longer triggered by the `getToken` method.
If you have used these methods in combination so far, please check your implementation.

## Version 0.4.x

The `removeDeliveredNotifications` now requires a notifications array as parameter:

```diff
export interface RemoveDeliveredNotificationsOptions {
-  ids: string[];
+  notifications: Notification[];
}
```

## Version 0.3.x

The `Notification.id` property is now optional:

```diff
export interface Notification {
  /**
   * The notification identifier.
   *
   * @since 0.2.2
   */
-  id: string;
+  id?: string;
   ...
}
```
