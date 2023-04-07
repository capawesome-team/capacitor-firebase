# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases of Capacitor Firebase Performance plugin.

## Versions

- [Version 5.x.x](#version-5xx)
- [Version 1.x.x](#version-1xx)

## Version 5.x.x

### Capacitor 5

This plugin now supports Capacitor 5 only. Please run `npx cap sync` after updating this package.

If you want to use this plugin with Capacitor 4, please install version `1.4.0`:

```
npm i @capacitor-firebase/performance@1.4.0
```

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
