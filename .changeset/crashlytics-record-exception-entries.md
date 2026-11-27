---
'@capacitor-firebase/crashlytics': patch
---

fix: `recordException` now skips an invalid `keysAndValues` entry and records the rest
