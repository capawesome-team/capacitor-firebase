/// <reference types="@capacitor/cli" />

import type { PluginListenerHandle } from '@capacitor/core';

declare module '@capacitor/cli' {
  export interface PluginsConfig {
    /**
     * These configuration values are available:
     */
    FirebaseAuthentication?: {
      /**
       * Configure whether the plugin should skip the native authentication.
       * Only needed if you want to use the Firebase JavaScript SDK.
       *
       * Only available for Android and iOS.
       *
       * @default false
       * @example false
       */
      skipNativeAuth?: boolean;
      /**
       * Configure which providers you want to use so that only the providers you need are fully initialized.
       * If you do not configure any providers, they will be all initialized.
       * Please note that this does not prevent the automatic initialization of third-party SDKs.
       *
       * Only available for Android and iOS.
       *
       * @default ["apple.com", "facebook.com", "github.com", "google.com", "microsoft.com", "playgames.google.com", "twitter.com", "yahoo.com", "phone"]
       * @example ["apple.com", "google.com"]
       */
      providers?: string[];
    };
  }
}

export interface FirebaseAuthenticationPlugin {
  /**
   * Fetches the currently signed-in user.
   */
  getCurrentUser(): Promise<GetCurrentUserResult>;
  /**
   * Fetches the Firebase Auth ID Token for the currently signed-in user.
   */
  getIdToken(options?: GetIdTokenOptions): Promise<GetIdTokenResult>;
  /**
   * Sets the user-facing language code for auth operations.
   */
  setLanguageCode(options: SetLanguageCodeOptions): Promise<void>;
  /**
   * Starts the Apple sign-in flow.
   */
  signInWithApple(options?: SignInOptions): Promise<SignInResult>;
  /**
   * Starts the Facebook sign-in flow.
   */
  signInWithFacebook(options?: SignInOptions): Promise<SignInResult>;
  /**
   * Starts the GitHub sign-in flow.
   */
  signInWithGithub(options?: SignInOptions): Promise<SignInResult>;
  /**
   * Starts the Google sign-in flow.
   */
  signInWithGoogle(options?: SignInOptions): Promise<SignInResult>;
  /**
   * Starts the Microsoft sign-in flow.
   */
  signInWithMicrosoft(options?: SignInOptions): Promise<SignInResult>;
  /**
   * Starts the Play Games sign-in flow.
   */
  signInWithPlayGames(options?: SignInOptions): Promise<SignInResult>;
  /**
   * Starts the Twitter sign-in flow.
   */
  signInWithTwitter(options?: SignInOptions): Promise<SignInResult>;
  /**
   * Starts the Yahoo sign-in flow.
   */
  signInWithYahoo(options?: SignInOptions): Promise<SignInResult>;
  /**
   * Starts the sign-in flow using a phone number.
   *
   * Either the phone number or the verification code and verification ID must be provided.
   *
   * Only available for Android and iOS.
   */
  signInWithPhoneNumber(
    options: SignInWithPhoneNumberOptions,
  ): Promise<SignInWithPhoneNumberResult>;
  /**
   * Starts the Custom Token sign-in flow.
   *
   * This method cannot be used in combination with `skipNativeAuth` on Android and iOS.
   * In this case you have to use the `signInWithCustomToken` interface of the Firebase JS SDK directly.
   */
  signInWithCustomToken(
    options: SignInWithCustomTokenOptions,
  ): Promise<SignInResult>;
  /**
   * Starts the sign-out flow.
   */
  signOut(): Promise<void>;
  /**
   * Sets the user-facing language code to be the default app language.
   */
  useAppLanguage(): Promise<void>;
  /**
   * Listen for the user's sign-in state changes.
   */
  addListener(
    eventName: 'authStateChange',
    listenerFunc: AuthStateChangeListener,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
  /**
   * Remove all listeners for this plugin.
   */
  removeAllListeners(): Promise<void>;
}

export interface GetCurrentUserResult {
  /**
   * The currently signed-in user, or null if there isn't any.
   */
  user: User | null;
}

export interface GetIdTokenOptions {
  /**
   * Force refresh regardless of token expiration.
   */
  forceRefresh: boolean;
}

export interface GetIdTokenResult {
  /**
   * The Firebase Auth ID token JWT string.
   */
  token: string;
}

export interface SetLanguageCodeOptions {
  /**
   * BCP 47 language code.
   *
   * Example: `en-US`.
   */
  languageCode: string;
}

export interface SignInOptions {
  /**
   * Configures custom parameters to be passed to the identity provider during the OAuth sign-in flow.
   */
  customParameters?: SignInCustomParameter[];
}

export interface SignInCustomParameter {
  /**
   * The custom parameter key (e.g. `login_hint`).
   */
  key: string;
  /**
   * The custom parameter value (e.g. `user@firstadd.onmicrosoft.com`).
   */
  value: string;
}

export interface SignInWithPhoneNumberOptions {
  /**
   * The phone number to be verified.
   */
  phoneNumber?: string;
  /**
   * The verification ID which will be returned when `signInWithPhoneNumber` is called for the first time.
   * The `verificationCode` must also be provided.
   */
  verificationId?: string;
  /**
   * The verification code from the SMS message.
   * The `verificationId` must also be provided.
   */
  verificationCode?: string;
}

export interface SignInWithCustomTokenOptions {
  /**
   * The custom token to sign in with.
   */
  token: string;
}

export interface SignInResult {
  /**
   * The currently signed-in user, or null if there isn't any.
   */
  user: User | null;
  /**
   * Credentials returned by an auth provider.
   */
  credential: AuthCredential | null;
}

export interface SignInWithPhoneNumberResult extends SignInResult {
  /**
   * The verification ID, which is needed to identify the verification code.
   */
  verificationId?: string;
}

export interface User {
  displayName: string | null;
  email: string | null;
  emailVerified: boolean;
  isAnonymous: boolean;
  phoneNumber: string | null;
  photoUrl: string | null;
  providerId: string;
  tenantId: string | null;
  uid: string;
}

export interface AuthCredential {
  /**
   * The authentication provider ID for the credential.
   *
   * Example: `google.com`.
   */
  providerId: string;
  /**
   * The OAuth access token associated with the credential if it belongs to an OAuth provider.
   */
  accessToken?: string;
  /**
   * The OAuth ID token associated with the credential if it belongs to an OIDC provider.
   */
  idToken?: string;
  /**
   * The OAuth access token secret associated with the credential if it belongs to an OAuth 1.0 provider.
   */
  secret?: string;
  /**
   * The random string used to make sure that the ID token you get was granted specifically in response to your app's authentication request.
   */
  nonce?: string;
}

/**
 * Callback to receive the user's sign-in state change notifications.
 */
export type AuthStateChangeListener = (change: AuthStateChange) => void;

export interface AuthStateChange {
  /**
   * The currently signed-in user, or null if there isn't any.
   */
  user: User | null;
}
