import { registerPlugin } from '@capacitor/core';
const FirebaseCrashlytics = registerPlugin('FirebaseCrashlytics', {
    web: () => import('./web').then(m => new m.FirebaseCrashlyticsWeb()),
});
export * from './definitions';
export { FirebaseCrashlytics };
//# sourceMappingURL=index.js.map