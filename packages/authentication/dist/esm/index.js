import { registerPlugin } from '@capacitor/core';
const FirebaseAuthentication = registerPlugin('FirebaseAuthentication', {
    web: () => import('./web').then(m => new m.FirebaseAuthenticationWeb()),
});
export * from './definitions';
export { FirebaseAuthentication };
//# sourceMappingURL=index.js.map