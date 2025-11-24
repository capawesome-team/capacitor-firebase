import { registerPlugin } from '@capacitor/core';

import type { FirebaseAnalyticsPlugin } from './definitions';

const FirebaseAnalytics = registerPlugin<FirebaseAnalyticsPlugin>('FirebaseAnalytics', {
  web: () => import('./web').then((m) => new m.FirebaseAnalyticsWeb()),
});

export * from './definitions';
export { FirebaseAnalytics };
