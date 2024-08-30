import { WebPlugin } from '@capacitor/core';
import type { FirebaseApp } from 'firebase/app';
import { getApp, getApps, initializeApp } from 'firebase/app';

import type {
  FirebaseAppInitializedResult,
  FirebaseAppName,
  FirebaseAppPlugin,
  FirebaseConfigOptions,
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

  /**
   * Initialize a new Firebase App with the provided name and Firebase project configuration
   * @param options
   */
  public async initializeAppWithConfig(
    options: FirebaseConfigOptions,
  ): Promise<void> {
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

  /**
   * Check if a Firebase App with the provided name is initialized
   * @param options
   * @returns Promise object with property boolean "result", true if initialized
   */
  public async appIsInitialized(
    options: FirebaseAppName,
  ): Promise<FirebaseAppInitializedResult> {
    const apps = getApps();
    if (apps.some((app: FirebaseApp) => app.name == options.name)) {
      return new Promise(resolve => resolve({ result: true }));
    }
    return new Promise(resolve => resolve({ result: false }));
  }
}
