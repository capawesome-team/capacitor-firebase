# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases of Capacitor Firebase App Check plugin.

## Versions

- [Version 6.x.x](#version-6xx)
- [Version 5.x.x](#version-5xx)

## Version 6.x.x

### Variables

- `firebaseAppCheckPlayIntegrityVersion` variable has been updated to `17.1.2`.
- `firebaseAppCheckDebugVersion` variable has been updated to `17.1.2`.

## Version 5.x.x

### Capacitor 5

This plugin now supports Capacitor 5 only. Please run `npx cap sync` after updating this package.

If you want to use this plugin with Capacitor 4, please install version `1.4.0`:

```
npm i @capacitor-firebase/app-check@1.4.0
```

### Localized error messages

On Android, error messages were previously generated with `getLocalizedMessage`. They are no longer localized and are generated with `getMessage` instead.

You should therefore check your error handling.
