---
'@capacitor-firebase/authentication': patch
---

fix(android): start Facebook sign-in via `LoginManager` so it no longer hangs once an access token is cached and no longer blocks the main thread at plugin load
