import { Capacitor, registerPlugin } from '@capacitor/core';

import { FirebaseFirestoreNative } from "./native";

import type { FirebaseFirestorePlugin } from './definitions';


let FirebaseFirestore = registerPlugin<FirebaseFirestorePlugin>(
  'FirebaseFirestore',
  {
    web: () => import('./web').then(m => new m.FirebaseFirestoreWeb()),
  },
);

if (Capacitor.isNativePlatform() && Capacitor.getPlatform() == "ios") {
  FirebaseFirestore = new FirebaseFirestoreNative(FirebaseFirestore);
}

export * from './definitions';
export { FirebaseFirestore };
