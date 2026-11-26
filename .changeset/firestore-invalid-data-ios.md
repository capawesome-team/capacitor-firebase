---
'@capacitor-firebase/firestore': patch
---

fix(ios): reject instead of crashing for an invalid document reference value, an invalid key in update data, an empty field name or a cursor document without the `orderBy` field
