# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases of Capacitor Firebase Cloud Messaging plugin.

## Versions

- [Version 0.4.x](#version-04x)
- [Version 0.3.x](#version-03x)

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
