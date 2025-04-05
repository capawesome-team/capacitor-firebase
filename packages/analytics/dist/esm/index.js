import { registerPlugin } from '@capacitor/core';
const FirebaseAnalytics = registerPlugin('FirebaseAnalytics', {
    web: () => import('./web').then(m => new m.FirebaseAnalyticsWeb()),
});
export * from './definitions';
export { FirebaseAnalytics };
//# sourceMappingURL=index.js.map