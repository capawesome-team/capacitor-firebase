import { WebPlugin } from '@capacitor/core';

import type { FirebaseStoragePlugin } from './definitions';

export class FirebaseStorageWeb
  extends WebPlugin
  implements FirebaseStoragePlugin
{
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
