import { WebPlugin } from '@capacitor/core';
import { getApp } from 'firebase/app';
import type {
  AppCheck,
  AppCheckOptions,
  AppCheckTokenResult,
  CustomProvider as CustomProviderType,
  Unsubscribe,
} from 'firebase/app-check';
import {
  getToken,
  initializeAppCheck,
  onTokenChanged,
  setTokenAutoRefreshEnabled,
} from 'firebase/app-check';

import { Provider } from './definitions';
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
    FIREBASE_APPCHECK_DEBUG_TOKEN: boolean;
  }
}

const isCustomProvider = (_providerClass: unknown, _providerOption: InitializeOptions['provider']): _providerClass is typeof CustomProviderType => {
  return !!_providerClass && _providerOption === Provider.CustomProvider;
}

export class FirebaseAppCheckWeb
  extends WebPlugin
  implements FirebaseAppCheckPlugin
{
  public static readonly tokenChangedEvent = 'tokenChanged';
  public static readonly errorNotInitialized =
    'AppCheck has not been initialized.';
  public static readonly errorSiteKeyMissing = 'siteKey must be provided.';
  public static readonly errorProviderMissing = 'AppCheck Provider missing.';
  public static readonly errorCustomProviderOptionsMissing =
    'customProviderOptions must be provided when using CustomProvider option.';

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

  private async getProvider(providerName: InitializeOptions['provider'] = Provider.ReCaptchaV3Provider) {
    switch (providerName) {
      case Provider.ReCaptchaV3Provider: {
        const { ReCaptchaV3Provider } = await import('firebase/app-check')
        return ReCaptchaV3Provider
      }
      case Provider.ReCaptchaEnterpriseProvider: {
        const { ReCaptchaEnterpriseProvider } = await import('firebase/app-check')
        return ReCaptchaEnterpriseProvider
      }
      case Provider.CustomProvider: {
        const { CustomProvider } = await import('firebase/app-check')
        return CustomProvider
      }
    }
  }

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
    if (options?.debug) {
      self.FIREBASE_APPCHECK_DEBUG_TOKEN = true;
    }
    const ProviderClass = await this.getProvider(options?.provider);
    if (!ProviderClass) {
      throw new Error(FirebaseAppCheckWeb.errorProviderMissing);
    }
    let provider: AppCheckOptions['provider'];
    if (isCustomProvider(ProviderClass, options?.provider)) {
      if (
        !options?.customProviderOptions ||
        typeof options?.customProviderOptions.getToken !== 'function'
      ) {
        throw new Error(FirebaseAppCheckWeb.errorCustomProviderOptionsMissing);
      }
      provider = new ProviderClass(options.customProviderOptions);
    } else {
      if (!options?.siteKey) {
        throw new Error(FirebaseAppCheckWeb.errorSiteKeyMissing);
      }
      provider = new ProviderClass(options.siteKey);
    }
    const app = getApp();
    this.appCheckInstance = initializeAppCheck(app, {
      provider,
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
