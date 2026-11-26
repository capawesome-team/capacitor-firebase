# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 9.x.x](#version-9xx)
- [Version 8.x.x](#version-8xx)
- [Version 7.x.x](#version-7xx)

## Version 9.x.x

### Null values on Android

On Android, a `null` in the result of a function is now returned. Before, it was missing: a function that returns `null` gave a result without `data`, and a key with the value `null` was missing in an object. iOS and Web have always returned it. This applies to `callByName` and `callByUrl`, and also to objects nested in objects or arrays. A `null` item in an array was already returned.

```typescript
// The function returns { name: 'Alice', nickname: null }
const { data } = await FirebaseFunctions.callByName({ name: 'getUser' });
// Before on Android: { name: 'Alice' }
// Now on all platforms: { name: 'Alice', nickname: null }
```

Check code that treats a missing value and a `null` value differently, for example `data.nickname === undefined`, a default value such as `const { data = [] } = result` or `{ ...defaults, ...data }`.

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **24** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

### Variables

- On Android, the `firebaseFunctionsVersion` variable has been updated to `22.1.0`.

### Peer Dependencies

The `firebase` peer dependency has been updated to version `^12.6.0`. Ensure your project uses a compatible version of `firebase` before upgrading.

## Version 7.x.x

### Dependencies

- The Firebase Javascript SDK has been updated to `11.2.0`.

### Variables

- On Android, the `firebaseFunctionsVersion` variable has been updated to `21.1.0`.
