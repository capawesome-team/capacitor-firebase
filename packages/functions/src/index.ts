import { registerPlugin } from '@capacitor/core';

import type { FirebaseFunctionsPlugin } from './definitions';

const FirebaseFunctions = registerPlugin<FirebaseFunctionsPlugin>('FirebaseFunctions', {
  web: () => import('./web').then((m) => new m.FirebaseFunctionsWeb()),
});

export * from './definitions';
export { FirebaseFunctions };
