import type { PluginListenerHandle } from '@capacitor/core';

export interface FirebaseAppCheckPlugin {
  /**
   * Get the current App Check token.
   *
   * @since 1.3.0
   */
  getToken(options?: GetTokenOptions): Promise<GetTokenResult>;
  /**
   * Activate App Check for the given app.
   * Can be called only once per app.
   *
   * @since 1.3.0
   */
  initialize(options?: InitializeOptions): Promise<void>;
  /**
   * Set whether the App Check token should be refreshed automatically or not.
   *
   * @since 1.3.0
   */
  setTokenAutoRefreshEnabled(
    options: SetTokenAutoRefreshEnabledOptions,
  ): Promise<void>;
  /**
   * Called when the App Check token changed.
   *
   * Only available for Web.
   *
   * @since 1.3.0
   */
  addListener(
    eventName: 'tokenChanged',
    listenerFunc: TokenChangedListener,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   *
   * Only available for Web.
   *
   * @since 1.3.0
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 1.3.0
 */
export interface GetTokenOptions {
  /**
   * If `true`, will always try to fetch a fresh token.
   * If `false`, will use a cached token if found in storage.
   *
   * @since 1.3.0
   * @default false
   */
  forceRefresh?: boolean;
}

/**
 * @since 1.3.0
 */
export interface GetTokenResult {
  /**
   * The App Check token in JWT format.
   *
   * @since 1.3.0
   */
  token: string;
  /**
   * The timestamp after which the token will expire in milliseconds since epoch.
   *
   * Only available for Android and iOS.
   *
   * @since 1.3.0
   */
  expireTimeMillis?: number;
}

/**
 * @since 1.3.0
 */
export interface InitializeOptions {
  /**
   * If `true`, the debug provider is used.
   *
   * ⚠️ **Attention**: The debug provider allows access to your Firebase resources from unverified devices.
   * Don't use the debug provider in production builds of your app, and don't share your debug builds with untrusted parties.
   *
   * ⚠️ **Deprecated**: Use `debugToken` instead. This option will be removed in the next major version.
   *
   * Read more: https://firebase.google.com/docs/app-check/web/debug-provider
   *
   * @since 1.3.0
   * @deprecated Use `debugToken` instead. This option will be removed in the next major version.
   * @default false
   */
  debug?: boolean;
  /**
   * If `true`, the debug provider is used.
   *
   * On **Web**, you can also set a predefined debug token string instead of `true`. On Android and iOS, you have to use environment variables for this.
   *
   * ⚠️ **Attention**: The debug provider allows access to your Firebase resources from unverified devices.
   * Don't use the debug provider in production builds of your app, and don't share your debug builds with untrusted parties.
   *
   * @since 7.1.0
   * @default false
   * @see https://firebase.google.com/docs/app-check/android/debug-provider#ci
   * @see https://firebase.google.com/docs/app-check/ios/debug-provider#ci
   * @see https://firebase.google.com/docs/app-check/web/debug-provider
   */
  debugToken?: boolean | string;
  /**
   * If `true`, the SDK automatically refreshes App Check tokens as needed.
   *
   * @since 1.3.0
   * @default false
   */
  isTokenAutoRefreshEnabled?: boolean;
  /**
   * The reCAPTCHA v3 site key (public key).
   *
   * Only available for Web.
   *
   * @since 1.3.0
   */
  siteKey?: string;
}

/**
 * @since 1.3.0
 */
export interface SetTokenAutoRefreshEnabledOptions {
  /**
   * If `true`, the SDK automatically refreshes App Check tokens as needed.
   * This overrides any value set during initializeAppCheck().
   *
   * @since 1.3.0
   */
  enabled: boolean;
}

/**
 * Callback to receive the token change event.
 *
 * @since 1.3.0
 */
export type TokenChangedListener = (event: TokenChangedEvent) => void;

/**
 * @since 1.3.0
 */
export interface TokenChangedEvent {
  /**
   * The App Check token in JWT format.
   *
   * @since 1.3.0
   */
  token: string;
}
