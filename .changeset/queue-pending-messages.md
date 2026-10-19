---
'@capacitor-firebase/messaging': patch
---

fix(android): deliver every data message received before the plugin was loaded, not just the last one
