import { registerPlugin } from '@capacitor/core';

import type { FunctionsPlugin } from './definitions';

const Functions = registerPlugin<FunctionsPlugin>('Functions', {
  web: () => import('./web').then(m => new m.FunctionsWeb()),
});

export * from './definitions';
export { Functions };
