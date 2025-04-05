import { registerPlugin } from '@capacitor/core';
const FirebaseRemoteConfig = registerPlugin('FirebaseRemoteConfig', {
    web: () => import('./web').then(m => new m.FirebaseRemoteConfigWeb()),
});
export * from './definitions';
export { FirebaseRemoteConfig };
//# sourceMappingURL=index.js.map