# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 9.x.x](#version-9xx)
- [Version 8.x.x](#version-8xx)
- [Version 7.x.x](#version-7xx)
- [Version 6.x.x](#version-6xx)
- [Version 5.x.x](#version-5xx)
- [Version 1.x.x](#version-1xx)

## Version 9.x.x

### Values of custom keys

On Android and iOS, `setCustomKey` now checks the `value` in the same way:

- A `value` that is missing or `null` is rejected with `value must be provided.`
- A `value` that does not match the `type` is rejected with `value is invalid.`

Before, some of these calls resolved and the value was not stored.

```typescript
await FirebaseCrashlytics.setCustomKey({ key: 'count', type: 'int', value: 'ten' });
// Before: resolves on iOS, but the value is not stored
// Now: rejects with "value is invalid."

await FirebaseCrashlytics.setCustomKey({ key: 'count', type: 'string', value: 10 });
// Before: resolves, but the value is not stored
// Now: rejects with "value is invalid."
```

On iOS, the check is now as strict as on Android. These values were stored before and are now rejected with `value is invalid.`:

- a boolean for the `type` `long`, `int`, `float` or `double`
- the numbers `0` and `1` for the `type` `boolean`
- a whole number outside the 32 bit range for the `type` `int`

On Android, a `value` of `null` is now always rejected with `value must be provided.`, as on iOS.

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **24** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

### Variables

- On Android, the `firebaseCrashlyticsVersion` variable has been updated to `20.0.3`.

## Version 7.x.x

### Dependencies

- The Firebase Javascript SDK has been updated to `11.2.0`.

### Variables

- On Android, the `firebaseCrashlyticsVersion` variable has been updated to `19.4.0`.

## Version 6.x.x

### Dependencies

- The Firebase Javascript SDK has been updated to `10.9.0`.

### Variables

- On Android, the `firebaseCrashlyticsVersion` variable has been updated to `18.6.2`.

## Version 5.x.x

### Capacitor 5

This plugin now supports Capacitor 5 only. Please run `npx cap sync` after updating this package.

If you want to use this plugin with Capacitor 4, please install version `1.4.0`:

```
npm i @capacitor-firebase/crashlytics@1.4.0
```

### Crashlytics Gradle plugin

Update the Crashlytics Gradle plugin in your root-level (project-level) Gradle file (usually `android/build.gradle`) to version `2.9.5`:

```diff
- classpath 'com.google.firebase:firebase-crashlytics-gradle:2.8.1'
+ classpath 'com.google.firebase:firebase-crashlytics-gradle:2.9.5'
```

### Localized error messages

On Android, error messages were previously generated with `getLocalizedMessage`. They are no longer localized and are generated with `getMessage` instead.

You should therefore check your error handling.

## Version 1.x.x

### Capacitor 4

This plugin now supports Capacitor 4 only. Please run `npx cap sync` after updating this package.

If you want to use this plugin with Capacitor 3, please install version `0.5.1`:

```
npm i @capacitor-firebase/crashlytics@0.5.1
```
