# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases of Capacitor Firebase Cloud Messaging plugin.

## Versions

- [Version 1.x.x](#version-1xx)
- [Version 0.4.x](#version-04x)
- [Version 0.3.x](#version-03x)

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
