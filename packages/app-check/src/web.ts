import { WebPlugin } from '@capacitor/core';

import type { FirebaseAppCheckPlugin, AppCheckToken } from './definitions';

export class FirebaseAppCheckWeb extends WebPlugin implements FirebaseAppCheckPlugin {
  initialize(_options: { debug: boolean; }): Promise<{ success: boolean; }> {
    throw this.unimplemented('Not implemented on web.');
  }
  getToken(): Promise<AppCheckToken> {
    throw this.unimplemented('Not implemented on web.');
  }
}
