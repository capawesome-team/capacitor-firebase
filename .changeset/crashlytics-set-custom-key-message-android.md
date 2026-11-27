---
'@capacitor-firebase/crashlytics': patch
---

fix(android): `setCustomKey` now rejects with `value is invalid.` when the value does not fit a number or boolean `type`
