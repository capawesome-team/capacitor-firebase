import { registerPlugin } from '@capacitor/core';

import type { FirebaseMessagingPlugin } from './definitions';

const FirebaseMessaging = registerPlugin<FirebaseMessagingPlugin>(
  'FirebaseMessaging',
  {
    web: () => import('./web').then(m => new m.FirebaseMessagingWeb()),
  },
);

export * from './definitions';
export { FirebaseMessaging };
