import { registerPlugin } from '@capacitor/core';

import type { FirebaseAppCheckPlugin } from './definitions';

const FirebaseAppCheck = registerPlugin<FirebaseAppCheckPlugin>(
  'FirebaseAppCheck',
  {
    web: () => import('./web').then(m => new m.FirebaseAppCheckWeb()),
  },
);

export * from './definitions';
export { FirebaseAppCheck };
