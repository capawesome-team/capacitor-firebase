---
'@capacitor-firebase/messaging': patch
---

fix(messaging): avoid configuring Firebase on iOS when `GoogleService-Info.plist` is missing and report native configuration availability from `isSupported()`
