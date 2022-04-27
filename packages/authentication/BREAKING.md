# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases of Capacitor Firebase Authentication plugin.

## Versions

- [Version 0.3.0](#version-03x)

## Version 0.3.0

### Type definitions

The custom `User` interface is replaced by the official Firebase [`User` interface](https://firebase.google.com/docs/reference/js/auth.user).

```diff
+ import { User } from 'firebase/auth';
- import { User } from '@capacitor-firebase/authentication';
```
