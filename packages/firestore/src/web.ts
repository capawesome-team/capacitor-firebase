import { WebPlugin } from '@capacitor/core';

import type { FirebaseFirestorePlugin } from './definitions';

export class FirebaseFirestoreWeb
  extends WebPlugin
  implements FirebaseFirestorePlugin
{
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
