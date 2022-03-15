import { registerPlugin } from '@capacitor/core';

import type { FirebaseAppPlugin } from './definitions';

const FirebaseApp = registerPlugin<FirebaseAppPlugin>('FirebaseApp', {
  web: () => import('./web').then(m => new m.FirebaseAppWeb()),
});

export * from './definitions';
export { FirebaseApp };
