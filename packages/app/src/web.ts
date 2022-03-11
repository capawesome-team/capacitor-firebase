import { WebPlugin } from '@capacitor/core';
import { getApp } from 'firebase/app';

import type {
  FirebaseAppPlugin,
  GetNameResult,
  GetOptionsResult,
} from './definitions';

export class FirebaseAppWeb extends WebPlugin implements FirebaseAppPlugin {
  async getName(): Promise<GetNameResult> {
    const app = getApp();
    return {
      name: app.name,
    };
  }

  async getOptions(): Promise<GetOptionsResult> {
    const app = getApp();
    return {
      apiKey: app.options.apiKey || '',
      applicationId: app.options.appId || '',
      databaseUrl: app.options.databaseURL || '',
      gcmSenderId: app.options.messagingSenderId || '',
      projectId: app.options.projectId || '',
      storageBucket: app.options.storageBucket || '',
    };
  }
}
