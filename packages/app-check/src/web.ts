import { WebPlugin } from '@capacitor/core';
import { getApp } from 'firebase/app';
import type {
  AppCheck,
  AppCheckTokenResult,
  Unsubscribe,
} from 'firebase/app-check';
import {
  getToken,
  initializeAppCheck,
  onTokenChanged,
  ReCaptchaV3Provider,
  setTokenAutoRefreshEnabled,
} from 'firebase/app-check';

import type {
  FirebaseAppCheckPlugin,
  GetTokenOptions,
  GetTokenResult,
  InitializeOptions,
  SetTokenAutoRefreshEnabledOptions,
  TokenChangedEvent,
} from './definitions';

declare global {
  interface Window {
    FIREBASE_APPCHECK_DEBUG_TOKEN: boolean | string;
  }
}

export class FirebaseAppCheckWeb
  extends WebPlugin
  implements FirebaseAppCheckPlugin
{
  public static readonly tokenChangedEvent = 'tokenChanged';
  public static readonly errorNotInitialized =
    'AppCheck has not been initialized.';
  public static readonly errorSiteKeyMissing = 'siteKey must be provided.';

  private _appCheckInstance: AppCheck | undefined;
  get appCheckInstance(): AppCheck | undefined {
    return this._appCheckInstance;
  }
  set appCheckInstance(value: AppCheck | undefined) {
    this._appCheckInstance = value;
    if (value) {
      this.registerOnTokenChangedListener();
    } else {
      this.unregisterOnTokenChangedListener();
    }
  }
  private onTokenChangedListenerUnsubscribe: Unsubscribe | undefined;

  public async getToken(options?: GetTokenOptions): Promise<GetTokenResult> {
    if (!this.appCheckInstance) {
      throw new Error(FirebaseAppCheckWeb.errorNotInitialized);
    }
    const result = await getToken(this.appCheckInstance, options?.forceRefresh);
    return {
      token: result.token,
    };
  }

  public async initialize(options?: InitializeOptions): Promise<void> {
    if (!options || !options.siteKey) {
      throw new Error(FirebaseAppCheckWeb.errorSiteKeyMissing);
    }
    if (options.debugToken) {
      self.FIREBASE_APPCHECK_DEBUG_TOKEN = options.debugToken;
    } else if (options.debug) {
      self.FIREBASE_APPCHECK_DEBUG_TOKEN = true;
    }
    const app = getApp();
    this.appCheckInstance = initializeAppCheck(app, {
      provider: new ReCaptchaV3Provider(options.siteKey),
      isTokenAutoRefreshEnabled: options.isTokenAutoRefreshEnabled,
    });
  }

  public async setTokenAutoRefreshEnabled(
    options: SetTokenAutoRefreshEnabledOptions,
  ): Promise<void> {
    if (!this.appCheckInstance) {
      throw new Error(FirebaseAppCheckWeb.errorNotInitialized);
    }
    setTokenAutoRefreshEnabled(this.appCheckInstance, options.enabled);
  }

  private registerOnTokenChangedListener(): void {
    if (!this.appCheckInstance) {
      return;
    }
    this.onTokenChangedListenerUnsubscribe = onTokenChanged(
      this.appCheckInstance,
      (tokenResult: AppCheckTokenResult) =>
        this.handleTokenChanged(tokenResult.token),
    );
  }

  private unregisterOnTokenChangedListener(): void {
    if (this.onTokenChangedListenerUnsubscribe) {
      this.onTokenChangedListenerUnsubscribe();
    }
  }

  private handleTokenChanged(token: string): void {
    const event: TokenChangedEvent = {
      token,
    };
    this.notifyListeners(FirebaseAppCheckWeb.tokenChangedEvent, event);
  }
}
