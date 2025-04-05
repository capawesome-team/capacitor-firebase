import { registerPlugin } from '@capacitor/core';
const FirebaseApp = registerPlugin('FirebaseApp', {
    web: () => import('./web').then(m => new m.FirebaseAppWeb()),
});
export * from './definitions';
export { FirebaseApp };
//# sourceMappingURL=index.js.map