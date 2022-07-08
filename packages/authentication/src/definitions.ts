/// <reference types="@capacitor/cli" />

import type { PluginListenerHandle } from '@capacitor/core';

declare module '@capacitor/cli' {
  export interface PluginsConfig {
    /**
     * These configuration values are available:
     *
     * @since 0.1.0
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
       * @since 0.1.0
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
       * @since 0.1.0
       */
      providers?: string[];
    };
  }
}

export interface FirebaseAuthenticationPlugin {
  /**
   * Applies a verification code sent to the user by email.
   *
   * @since 0.2.2
   */
  applyActionCode(options: ApplyActionCodeOptions): Promise<void>;
  /**
   * Creates a new user account with email and password.
   * If the new account was created, the user is signed in automatically.
   *
   * @since 0.2.2
   */
  createUserWithEmailAndPassword(
    options: CreateUserWithEmailAndPasswordOptions,
  ): Promise<SignInResult>;
  /**
   * Completes the password reset process.
   *
   * @since 0.2.2
   */
  confirmPasswordReset(options: ConfirmPasswordResetOptions): Promise<void>;
  /**
   * Fetches the currently signed-in user.
   *
   * @since 0.1.0
   */
  getCurrentUser(): Promise<GetCurrentUserResult>;
  /**
   * Fetches the Firebase Auth ID Token for the currently signed-in user.
   *
   * @since 0.1.0
   */
  getIdToken(options?: GetIdTokenOptions): Promise<GetIdTokenResult>;
  /**
   * Sends a verification email to the currently signed in user.
   *
   * @since 0.2.2
   */
  sendEmailVerification(): Promise<void>;
  /**
   * Sends a password reset email.
   *
   * @since 0.2.2
   */
  sendPasswordResetEmail(options: SendPasswordResetEmailOptions): Promise<void>;
  /**
   * Sets the user-facing language code for auth operations.
   *
   * @since 0.1.0
   */
  setLanguageCode(options: SetLanguageCodeOptions): Promise<void>;
  /**
   * Starts the Apple sign-in flow.
   *
   * @since 0.1.0
   */
  signInWithApple(options?: SignInOptions): Promise<SignInResult>;
  /**
   * Starts the Custom Token sign-in flow.
   *
   * This method cannot be used in combination with `skipNativeAuth` on Android and iOS.
   * In this case you have to use the `signInWithCustomToken` interface of the Firebase JS SDK directly.
   *
   * @since 0.1.0
   */
  signInWithCustomToken(
    options: SignInWithCustomTokenOptions,
  ): Promise<SignInResult>;
  /**
   * Starts the sign-in flow using an email and password.
   *
   * @since 0.2.2
   */
  signInWithEmailAndPassword(
    options: SignInWithEmailAndPasswordOptions,
  ): Promise<SignInResult>;
  /**
   * Starts the Facebook sign-in flow.
   *
   * @since 0.1.0
   */
  signInWithFacebook(options?: SignInOptions): Promise<SignInResult>;
  /**
   * Starts the GitHub sign-in flow.
   *
   * @since 0.1.0
   */
  signInWithGithub(options?: SignInOptions): Promise<SignInResult>;
  /**
   * Starts the Google sign-in flow.
   *
   * @since 0.1.0
   */
  signInWithGoogle(options?: SignInOptions): Promise<SignInResult>;
  /**
   * Starts the Microsoft sign-in flow.
   *
   * @since 0.1.0
   */
  signInWithMicrosoft(options?: SignInOptions): Promise<SignInResult>;
  /**
   * Starts the sign-in flow using a phone number.
   *
   * Either the phone number or the verification code and verification ID must be provided.
   *
   * Only available for Android and iOS.
   *
   * @since 0.1.0
   */
  signInWithPhoneNumber(
    options: SignInWithPhoneNumberOptions,
  ): Promise<SignInWithPhoneNumberResult>;
  /**
   * Starts the Play Games sign-in flow.
   *
   * @since 0.1.0
   */
  signInWithPlayGames(options?: SignInOptions): Promise<SignInResult>;
  /**
   * Starts the Twitter sign-in flow.
   *
   * @since 0.1.0
   */
  signInWithTwitter(options?: SignInOptions): Promise<SignInResult>;
  /**
   * Starts the Yahoo sign-in flow.
   *
   * @since 0.1.0
   */
  signInWithYahoo(options?: SignInOptions): Promise<SignInResult>;
  /**
   * Starts the sign-out flow.
   *
   * @since 0.1.0
   */
  signOut(): Promise<void>;
  /**
   * Updates the email address of the currently signed in user.
   *
   * @since 0.1.0
   */
  updateEmail(options: UpdateEmailOptions): Promise<void>;
  /**
   * Updates the password of the currently signed in user.
   *
   * @since 0.1.0
   */
  updatePassword(options: UpdatePasswordOptions): Promise<void>;
  /**
   * Sets the user-facing language code to be the default app language.
   *
   * @since 0.1.0
   */
  useAppLanguage(): Promise<void>;
  /**
   * Instrument your app to talk to the Authentication emulator.
   *
   * @since 0.2.0
   */
  useEmulator(options: UseEmulatorOptions): Promise<void>;
  /**
   * Listen for the user's sign-in state changes.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'authStateChange',
    listenerFunc: AuthStateChangeListener,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
  /**
   * Remove all listeners for this plugin.
   *
   * @since 0.1.0
   */
  removeAllListeners(): Promise<void>;

  /**
   * Sends an SMS to the user. If no phoneNumber is provided, uses the first hint from the resolver session (must call signInWithEmailAndPassword first to grab the resolver session when 2nd factor is needed)
   * @param {string} [phoneNumber] 
   * phoneNumber to send the sms to
   * @since New forked Repo
   */
  verifyPhoneNumber(options?: {phoneNumber?: string}): Promise<{verificationId: string}>;
}

/**
 * @since 0.2.2
 */
export interface ApplyActionCodeOptions {
  /**
   * A verification code sent to the user.
   *
   * @since 0.2.2
   */
  oobCode: string;
}

/**
 * @since 0.2.2
 */
export interface ConfirmPasswordResetOptions {
  /**
   * A verification code sent to the user.
   *
   * @since 0.2.2
   */
  oobCode: string;
  /**
   * The new password.
   *
   * @since 0.2.2
   */
  newPassword: string;
}

/**
 * @since 0.2.2
 */
export interface CreateUserWithEmailAndPasswordOptions {
  /**
   * @since 0.2.2
   */
  email: string;
  /**
   * @since 0.2.2
   */
  password: string;
}

/**
 * @since 0.1.0
 */
export interface GetCurrentUserResult {
  /**
   * The currently signed-in user, or null if there isn't any.
   *
   * @since 0.1.0
   */
  user: User | null;
}

/**
 * @since 0.1.0
 */
export interface GetIdTokenOptions {
  /**
   * Force refresh regardless of token expiration.
   *
   * @since 0.1.0
   */
  forceRefresh: boolean;
}

/**
 * @since 0.1.0
 */
export interface GetIdTokenResult {
  /**
   * The Firebase Auth ID token JWT string.
   *
   * @since 0.1.0
   */
  token: string;
}

/**
 * @since 0.2.2
 */
export interface SendPasswordResetEmailOptions {
  /**
   * @since 0.2.2
   */
  email: string;
}

/**
 * @since 0.1.0
 */
export interface SetLanguageCodeOptions {
  /**
   * BCP 47 language code.
   *
   * @example "en-US"
   * @since 0.1.0
   */
  languageCode: string;
}

/**
 * @since 0.2.2
 */
export interface UpdateEmailOptions {
  /**
   * The new email address.
   *
   * @since 0.2.2
   */
  newEmail: string;
}

/**
 * @since 0.2.2
 */
export interface UpdatePasswordOptions {
  /**
   * The new password.
   *
   * @since 0.2.2
   */
  newPassword: string;
}

/**
 * @since 0.1.0
 */
export interface SignInOptions {
  /**
   * Configures custom parameters to be passed to the identity provider during the OAuth sign-in flow.
   *
   * @since 0.1.0
   */
  customParameters?: SignInCustomParameter[];
  /**
   * Scopes to request from provider.
   *
   * @since 0.3.1
   */
  scopes?: string[];
}

/**
 * @since 0.1.0
 */
export interface SignInCustomParameter {
  /**
   * The custom parameter key (e.g. `login_hint`).
   *
   * @since 0.1.0
   */
  key: string;
  /**
   * The custom parameter value (e.g. `user@firstadd.onmicrosoft.com`).
   *
   * @since 0.1.0
   */
  value: string;
}

/**
 * @since 0.1.0
 */
export interface SignInWithPhoneNumberOptions {
  /**
   * The phone number to be verified.
   *
   * @since 0.1.0
   */
  phoneNumber?: string;
  /**
   * The verification ID which will be returned when `signInWithPhoneNumber` is called for the first time.
   * The `verificationCode` must also be provided.
   *
   * @since 0.1.0
   */
  verificationId?: string;
  /**
   * The verification code from the SMS message.
   * The `verificationId` must also be provided.
   *
   * @since 0.1.0
   */
  verificationCode?: string;
}

/**
 * @since 0.2.2
 */
export interface SignInWithEmailAndPasswordOptions {
  /**
   * The users email address.
   *
   * @since 0.2.2
   */
  email: string;
  /**
   * The users password.
   *
   * @since 0.2.2
   */
  password: string;
}

/**
 * @since 0.1.0
 */
export interface SignInWithCustomTokenOptions {
  /**
   * The custom token to sign in with.
   *
   * @since 0.1.0
   */
  token: string;
}

/**
 * @since 0.1.0
 */
export interface SignInResult {
  /**
   * The currently signed-in user, or null if there isn't any.
   *
   * @since 0.1.0
   */
  user: User | null;
  /**
   * Credentials returned by an auth provider.
   *
   * @since 0.1.0
   */
  credential: AuthCredential | null;
}

/**
 * @since 0.2.0
 */
export interface UseEmulatorOptions {
  /**
   * The emulator host (e.g. `10.0.2.2`).
   *
   * @since 0.2.0
   */
  host: string;
  /**
   * The emulator port (e.g. `9099`).
   *
   * @default 9099
   * @since 0.2.0
   */
  port?: number;
}

/**
 * @since 0.1.0
 */
export interface SignInWithPhoneNumberResult extends SignInResult {
  /**
   * The verification ID, which is needed to identify the verification code.
   *
   * @since 0.1.0
   */
  verificationId?: string;
}

/**
 * @since 0.1.0
 */
export interface User {
  /**
   * @since 0.1.0
   */
  displayName: string | null;
  /**
   * @since 0.1.0
   */
  email: string | null;
  /**
   * @since 0.1.0
   */
  emailVerified: boolean;
  /**
   * @since 0.1.0
   */
  isAnonymous: boolean;
  /**
   * @since 0.1.0
   */
  phoneNumber: string | null;
  /**
   * @since 0.1.0
   */
  photoUrl: string | null;
  /**
   * @since 0.1.0
   */
  providerId: string;
  /**
   * @since 0.1.0
   */
  tenantId: string | null;
  /**
   * @since 0.1.0
   */
  uid: string;
}

/**
 * @since 0.1.0
 */
export interface AuthCredential {
  /**
   * The authentication provider ID for the credential.
   *
   * @example "google.com"
   * @since 0.1.0
   */
  providerId: string;
  /**
   * The OAuth access token associated with the credential if it belongs to an OAuth provider.
   *
   * @since 0.1.0
   */
  accessToken?: string;
  /**
   * The OAuth ID token associated with the credential if it belongs to an OIDC provider.
   *
   * @since 0.1.0
   */
  idToken?: string;
  /**
   * The OAuth access token secret associated with the credential if it belongs to an OAuth 1.0 provider.
   *
   * @since 0.1.0
   */
  secret?: string;
  /**
   * The random string used to make sure that the ID token you get was granted specifically in response to your app's authentication request.
   *
   * @since 0.1.0
   */
  nonce?: string;
}

/**
 * Callback to receive the user's sign-in state change notifications.
 *
 * @since 0.1.0
 */
export type AuthStateChangeListener = (change: AuthStateChange) => void;

/**
 * @since 0.1.0
 */
export interface AuthStateChange {
  /**
   * The currently signed-in user, or null if there isn't any.
   *
   * @since 0.1.0
   */
  user: User | null;
}
