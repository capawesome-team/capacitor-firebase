---
'@capacitor-firebase/messaging': major
---

feat(messaging): support Firebase Installation IDs

Add `register()`, `unregister()` and the `registered`/`unregistered` events, which identify the app instance by its Firebase Installation ID (FID). `getToken()`, `deleteToken()` and the `tokenReceived` event are deprecated.

Firebase SDK versions: JS `12.14.0`, iOS `12.18.0`, Android `firebase-messaging` `25.1.0`.

**BREAKING CHANGE**: the `firebase` peer dependency is now `^12.14.0`. See `BREAKING.md`.
