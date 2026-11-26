import { WebPlugin } from '@capacitor/core';
import { getApp, getApps, initializeApp } from 'firebase/app';

import type {
  FirebaseAppPlugin,
  GetAppsResult,
  GetNameResult,
  GetOptionsResult,
  InitializeAppOptions,
} from './definitions';

export class FirebaseAppWeb extends WebPlugin implements FirebaseAppPlugin {
  private static errorApiKeyMissing = 'options.apiKey must be provided.';
  private static errorAppAlreadyExists =
    'An app with this name already exists.';
  private static errorApplicationIdMissing =
    'options.applicationId must be provided.';
  private static errorGcmSenderIdMissing =
    'options.gcmSenderId must be provided.';
  private static errorNameInvalid =
    'name must only contain letters, numbers, hyphens and underscores.';
  private static errorNameMissing = 'name must be provided.';
  private static errorOptionsMissing = 'options must be provided.';
  private static errorProjectIdMissing = 'options.projectId must be provided.';

  async getApps(): Promise<GetAppsResult> {
    const apps = getApps();
    return {
      apps: apps.map(app => ({ name: app.name })),
    };
  }

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

  async initializeApp(options: InitializeAppOptions): Promise<void> {
    this.validateInitializeAppOptions(options);
    const isAppInitialized = getApps().some(app => app.name === options.name);
    if (isAppInitialized) {
      throw new Error(FirebaseAppWeb.errorAppAlreadyExists);
    }
    initializeApp(
      {
        apiKey: options.options.apiKey,
        appId: options.options.applicationId,
        databaseURL: options.options.databaseUrl,
        messagingSenderId: options.options.gcmSenderId,
        projectId: options.options.projectId,
        storageBucket: options.options.storageBucket,
      },
      options.name,
    );
  }

  private validateInitializeAppOptions(options: InitializeAppOptions): void {
    const { name, options: firebaseOptions } = options;
    if (typeof name !== 'string') {
      throw new Error(FirebaseAppWeb.errorNameMissing);
    }
    if (!/^[A-Za-z0-9_-]+$/.test(name)) {
      throw new Error(FirebaseAppWeb.errorNameInvalid);
    }
    if (!firebaseOptions) {
      throw new Error(FirebaseAppWeb.errorOptionsMissing);
    }
    if (typeof firebaseOptions.apiKey !== 'string') {
      throw new Error(FirebaseAppWeb.errorApiKeyMissing);
    }
    if (typeof firebaseOptions.applicationId !== 'string') {
      throw new Error(FirebaseAppWeb.errorApplicationIdMissing);
    }
    if (typeof firebaseOptions.gcmSenderId !== 'string') {
      throw new Error(FirebaseAppWeb.errorGcmSenderIdMissing);
    }
    if (typeof firebaseOptions.projectId !== 'string') {
      throw new Error(FirebaseAppWeb.errorProjectIdMissing);
    }
  }
}
