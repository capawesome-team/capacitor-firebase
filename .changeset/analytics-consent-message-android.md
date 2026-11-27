---
'@capacitor-firebase/analytics': patch
---

fix(android): `setConsent` now rejects with `consentType must be provided.` or `consentStatus must be provided.` when `type` or `status` is missing
