import { registerPlugin } from '@capacitor/core';
const FirebaseFirestore = registerPlugin('FirebaseFirestore', {
    web: () => import('./web').then(m => new m.FirebaseFirestoreWeb()),
});
export * from './definitions';
export { FirebaseFirestore };
//# sourceMappingURL=index.js.map