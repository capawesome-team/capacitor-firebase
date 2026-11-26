# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 9.x.x](#version-9xx)
- [Version 8.x.x](#version-8xx)
- [Version 7.x.x](#version-7xx)
- [Version 6.x.x](#version-6xx)

## Version 9.x.x

### Whole numbers on iOS

On iOS, a whole number such as `5` is now stored as an integer. Before, it was stored as a double. Android and Web have always stored an integer. The same applies to the values of filters and to the items of `arrayUnion` and `arrayRemove`.

Documents that were written on iOS before this version keep their doubles until they are written again. Firestore treats an integer and a double of the same value as equal in most places, but not in all:

- `==`, `!=`, `<`, `<=`, `>`, `>=`, `not-in`, `orderBy`, `arrayUnion` and `arrayRemove` treat `5` and `5.0` as the same value.
- `in`, `array-contains` and `array-contains-any` only return documents whose stored number type equals the type of the filter value. After the update, such a query no longer returns documents that hold the number as a double. Before the update, the same query on iOS did not return documents that were written on Android or Web.

If your app uses one of these three operators on a number field, write the affected documents again with this version, so that the field holds an integer.

Security rules that check the number type are affected as well: `is int` is now true and `is float` is now false for a whole number that is written on iOS.

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
