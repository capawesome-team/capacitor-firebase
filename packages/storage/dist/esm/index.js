import { registerPlugin } from '@capacitor/core';
const FirebaseStorage = registerPlugin('FirebaseStorage', {
    web: () => import('./web').then(m => new m.FirebaseStorageWeb()),
});
export * from './definitions';
export { FirebaseStorage };
//# sourceMappingURL=index.js.map