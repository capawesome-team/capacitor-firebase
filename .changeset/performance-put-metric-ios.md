---
'@capacitor-firebase/performance': patch
---

fix(ios): reject instead of crashing for a `num` beyond the Int64 range in `putMetric`
