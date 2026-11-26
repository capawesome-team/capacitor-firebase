# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 9.x.x](#version-9xx)
- [Version 8.x.x](#version-8xx)
- [Version 7.x.x](#version-7xx)
- [Version 6.x.x](#version-6xx)

## Version 9.x.x

### Null values on Android

On Android, a field with the value `null` is now part of the data when a document is read. Before, such a field was missing. iOS and Web have always returned it. This applies to `getDocument`, `getCollection`, `getCollectionGroup` and the snapshot listeners, and also to fields in nested maps.

```typescript
// Document in Firestore: { name: 'Alice', nickname: null }
const { snapshot } = await FirebaseFirestore.getDocument({ reference: 'users/1' });
// Before on Android: { name: 'Alice' }
// Now on all platforms: { name: 'Alice', nickname: null }
```

Check code that treats a missing field and a `null` field differently, for example `data.nickname === undefined`, `'nickname' in data` or `{ ...defaults, ...data }`.

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **24** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

### Variables

- On Android, the `firebaseFirestoreVersion` variable has been updated to `26.0.2`.

### Peer Dependencies

The `firebase` peer dependency has been updated to version `^12.6.0`. Ensure your project uses a compatible version of `firebase` before upgrading.

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
