# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases of Capacitor Firebase Firestore plugin.

## Versions

- [Version 7.x.x](#version-7xx)
- [Version 6.x.x](#version-6xx)

## Version 7.x.x

### Error codes

Error codes are now prefixed with `firestore/` to be consistent with the Firebase Web SDK.

## Version 6.x.x

### Dependencies

- The Firebase Javascript SDK has been updated to `10.9.0`.

### `QueryDocumentSnapshot` interface

The `QueryDocumentSnapshot` interface has been replaced with the `DocumentSnapshot` interface.

### Variables

- On Android, the `firebaseFirestoreVersion` variable has been updated to `24.10.3`.
