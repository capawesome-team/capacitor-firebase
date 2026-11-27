# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 9.x.x](#version-9xx)
- [Version 8.x.x](#version-8xx)
- [Version 7.x.x](#version-7xx)
- [Version 6.x.x](#version-6xx)

## Version 9.x.x

### Generation numbers on iOS

On iOS, `getMetadata` now returns `generation` and `metadataGeneration` as strings. Before, both were numbers. Android and Web have always returned strings.

```typescript
const result = await FirebaseStorage.getMetadata({ path: 'images/mountains.png' });
// Before on iOS: { generation: 1700000000000000, metadataGeneration: 1, ... }
// Now on all platforms: { generation: '1700000000000000', metadataGeneration: '1', ... }
```

Check code that compares these values with a number or calculates with them, for example `result.metadataGeneration === 1`.

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **24** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

### Variables

- On Android, the `firebaseStorageVersion` variable has been updated to `22.0.1`.

### Peer Dependencies

The `firebase` peer dependency has been updated to version `^12.6.0`. Ensure your project uses a compatible version of `firebase` before upgrading.

## Version 7.x.x

### Dependencies

- The Firebase Javascript SDK has been updated to `11.2.0`.

### Variables

- On Android, the `firebaseStorageVersion` variable has been updated to `21.0.1`.

## Version 6.x.x

### Dependencies

- The Firebase Javascript SDK has been updated to `10.9.0`.

### Variables

- On Android, the `firebaseStorageVersion` variable has been updated to `20.3.0`.
