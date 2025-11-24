import { registerPlugin } from '@capacitor/core';

import type { FirebaseStoragePlugin } from './definitions';

const FirebaseStorage = registerPlugin<FirebaseStoragePlugin>('FirebaseStorage', {
  web: () => import('./web').then((m) => new m.FirebaseStorageWeb()),
});

export * from './definitions';
export { FirebaseStorage };
