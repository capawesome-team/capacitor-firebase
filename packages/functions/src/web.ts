import { WebPlugin } from '@capacitor/core';

import type { FunctionsPlugin } from './definitions';

export class FunctionsWeb extends WebPlugin implements FunctionsPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
