import { registerPlugin } from '@capacitor/core';
const FirebaseAppCheck = registerPlugin('FirebaseAppCheck', {
    web: () => import('./web').then(m => new m.FirebaseAppCheckWeb()),
});
export * from './definitions';
export { FirebaseAppCheck };
//# sourceMappingURL=index.js.map