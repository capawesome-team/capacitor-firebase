---
'@capacitor-firebase/crashlytics': patch
---

fix(android): `setCustomKey` and `recordException` now handle a `long` value above the int range, and `setCustomKey` also a `float` or `double` value
