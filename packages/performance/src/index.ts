import { registerPlugin } from '@capacitor/core';

import type { FirebasePerformancePlugin } from './definitions';

const FirebasePerformance = registerPlugin<FirebasePerformancePlugin>('FirebasePerformance', {
  web: () => import('./web').then((m) => new m.FirebasePerformanceWeb()),
});

export * from './definitions';
export { FirebasePerformance };
