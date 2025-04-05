import { registerPlugin } from '@capacitor/core';
const FirebaseMessaging = registerPlugin('FirebaseMessaging', {
    web: () => import('./web').then(m => new m.FirebaseMessagingWeb()),
});
export * from './definitions';
export { FirebaseMessaging };
//# sourceMappingURL=index.js.map