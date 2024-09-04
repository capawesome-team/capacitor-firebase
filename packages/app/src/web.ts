import { WebPlugin } from '@capacitor/core';
import { getApp, getApps, initializeApp } from 'firebase/app';

import type {
  FirebaseAppPlugin,
  GetNameResult,
  GetAppsResult,
  GetOptionsResult,
  InitializeAppOptions,
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

  public async initializeApp(options: InitializeAppOptions): Promise<void> {
    const app = initializeApp(
      {
        apiKey: options.config.apiKey,
        appId: options.config.appId,
        projectId: options.config.projectId,
        databaseURL: options.config.databaseURL,
        storageBucket: options.config.storageBucket,
        messagingSenderId: options.config.messagingSenderId,
      },
      options.name,
    );

    if (app) {
      return;
    } else {
      throw new Error('Could not initialize app with provided firebase config');
    }
  }

  public async getApps(): Promise<GetAppsResult> {
    const apps = getApps();
    return new Promise(resolve => resolve({ apps: apps.map(app => app.name) }));
  }
}
