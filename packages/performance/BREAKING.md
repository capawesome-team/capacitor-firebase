# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases of Capacitor Firebase Performance plugin.

## Versions

- [Version 1.x.x](#version-1xx)

## Version 1.x.x

### Capacitor 4

This plugin now supports Capacitor 4 only. Please run `npx cap sync` after updating this package.

If you want to use this plugin with Capacitor 3, please install version `0.5.1`:

```
npm i @capacitor-firebase/performance@0.5.1
```

### Renamed methods and interfaces

Method `setPerformanceCollectionEnabled` was renamed to `setEnabled`.  
Interface `SetPerformanceCollectionEnabledOptions` was renamed to `SetEnabledOptions`.  

Method `isPerformanceCollectionEnabled` was renamed to `isEnabled`.  
Interface `IsPerformanceCollectionEnabledResult` was renamed to `IsEnabledResult`.  
