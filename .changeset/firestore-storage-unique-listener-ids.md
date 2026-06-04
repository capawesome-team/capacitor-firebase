---
'@capacitor-firebase/firestore': patch
'@capacitor-firebase/storage': patch
---

fix: generate unique listener/callback IDs on web so listeners created in the same millisecond do not overwrite each other
