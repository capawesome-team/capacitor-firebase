---
"@capacitor-firebase/authentication": patch
---

fix(android): create the Facebook auth provider handler on first use so the Facebook SDK no longer blocks the main thread while the plugin loads
