---
'@capacitor-firebase/crashlytics': minor
---

- adds the possibility to add dedicated custom properties when recording a non-fatal exception as outlined here:
  - https://firebase.google.com/docs/crashlytics/customize-crash-reports?platform=android#log-excepts
  - https://firebase.google.com/docs/crashlytics/customize-crash-reports?platform=ios#log-excepts
> Note: on iOS this cannot be combined with `stacktrace` due to [limitations in the ios-sdk](https://github.com/firebase/firebase-ios-sdk/discussions/11452#discussioncomment-6277129).
