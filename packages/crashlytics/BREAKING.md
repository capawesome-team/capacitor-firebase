# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases of Capacitor Firebase Crashlytics plugin.

## Versions

- [Version 6.x.x](#version-6xx)
- [Version 5.x.x](#version-5xx)
- [Version 1.x.x](#version-1xx)

## Version 6.x.x

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
