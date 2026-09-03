---
"@capacitor-firebase/authentication": patch
---

fix(android): sign in with and link Facebook via `LoginManager` instead of a hidden `LoginButton`, whose click listener took a logout path and left the call unresolved whenever an access token was already cached
