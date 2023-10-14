import { registerPlugin } from '@capacitor/core';

import type { FirebaseFirestorePlugin } from './definitions';

const FirebaseFirestore = registerPlugin<FirebaseFirestorePlugin>(
  'FirebaseFirestore',
  {
    web: () => import('./web').then(m => new m.FirebaseFirestoreWeb()),
  },
);

export * from './definitions';
export { FirebaseFirestore };
