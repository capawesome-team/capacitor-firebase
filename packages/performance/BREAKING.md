# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases of Capacitor Firebase Performance plugin.

## Versions

- [Version 7.x.x](#version-7xx)
- [Version 6.x.x](#version-6xx)
- [Version 5.x.x](#version-5xx)
- [Version 1.x.x](#version-1xx)

## Version 7.x.x

### Dependencies

- The Firebase Javascript SDK has been updated to `11.2.0`.

### Variables

- On Android, the `firebasePerfVersion` variable has been updated to `21.0.4`.

### Error Handling

The web implementation now throws an error when a `trace` is not found for a given `traceName`. This behavior was already in place on iOS and Android in earlier versions.

## Version 6.x.x

### Dependencies

- The Firebase Javascript SDK has been updated to `10.9.0`.

### Variables

- On Android, the `firebasePerfVersion` variable has been updated to `20.5.2`.

## Version 5.x.x

### Capacitor 5

This plugin now supports Capacitor 5 only. Please run `npx cap sync` after updating this package.

If you want to use this plugin with Capacitor 4, please install version `1.4.0`:

```
npm i @capacitor-firebase/performance@1.4.0
```

### Performance Monitoring Gradle plugin

Update the Performance Monitoring Gradle plugin in your root-level (project-level) Gradle file (usually `android/build.gradle`) to version `1.4.2`:

```diff
- classpath 'com.google.firebase:perf-plugin::1.4.1'
+ classpath 'com.google.firebase:perf-plugin::1.4.2'
```

### Localized error messages

On Android, error messages were previously generated with `getLocalizedMessage`. They are no longer localized and are generated with `getMessage` instead.

You should therefore check your error handling.

## Version 1.x.x

### Capacitor 4

This plugin now supports Capacitor 4 only. Please run `npx cap sync` after updating this package.

If you want to use this plugin with Capacitor 3, please install version `0.5.1`:

```
npm i @capacitor-firebase/performance@0.5.1
```

### Rename methods and interfaces

Method `setPerformanceCollectionEnabled` was renamed to `setEnabled`.  
Interface `SetPerformanceCollectionEnabledOptions` was renamed to `SetEnabledOptions`.

Method `isPerformanceCollectionEnabled` was renamed to `isEnabled`.  
Interface `IsPerformanceCollectionEnabledResult` was renamed to `IsEnabledResult`.
