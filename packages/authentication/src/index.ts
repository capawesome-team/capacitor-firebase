import { registerPlugin } from '@capacitor/core';

import type { FirebaseAuthenticationPlugin } from './definitions';

const FirebaseAuthentication = registerPlugin<FirebaseAuthenticationPlugin>(
  'FirebaseAuthentication',
  {
    web: () => import('./web').then(m => new m.FirebaseAuthenticationWeb()),
  },
);

export * from './definitions';
export { FirebaseAuthentication };
