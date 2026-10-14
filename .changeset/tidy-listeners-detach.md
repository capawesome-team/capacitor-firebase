---
'@capacitor-firebase/app-check': patch
'@capacitor-firebase/authentication': patch
---

fix(android): remove Firebase listeners when the bridge is destroyed so recreated activities no longer leak the previous Activity and WebView
