# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 8.x.x](#version-8xx)
- [Version 7.x.x](#version-7xx)
- [Version 6.x.x](#version-6xx)

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **36** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

## Version 7.x.x

### Error codes

Error codes are now prefixed with `firestore/` to be consistent with the Firebase Web SDK.

### Dependencies

- The Firebase Javascript SDK has been updated to `11.2.0`.

### Variables

- On Android, the `firebaseFirestoreVersion` variable has been updated to `25.1.1`.

## Version 6.x.x

### Dependencies

- The Firebase Javascript SDK has been updated to `10.9.0`.

### `QueryDocumentSnapshot` interface

The `QueryDocumentSnapshot` interface has been replaced with the `DocumentSnapshot` interface.

### Variables

- On Android, the `firebaseFirestoreVersion` variable has been updated to `24.10.3`.
