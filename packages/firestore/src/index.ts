import { registerPlugin } from '@capacitor/core';

import { FirebaseFirestoreClient } from './client';
import type { FirebaseFirestorePlugin } from './definitions';

const plugin = registerPlugin<FirebaseFirestorePlugin>('FirebaseFirestore', {
  web: () => import('./web').then(m => new m.FirebaseFirestoreWeb()),
});

const FirebaseFirestore = new FirebaseFirestoreClient(
  plugin,
) as unknown as FirebaseFirestorePlugin;

export * from './definitions';
export { FirebaseFirestore };
export { FieldValue } from './field-value';
export { GeoPoint } from './geopoint';
export { Timestamp } from './timestamp';
