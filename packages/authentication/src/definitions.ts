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
       * This configuration option has no effect on Firebase account linking.
       *
       * **Note that the plugin may behave differently across the platforms.**
       *
       * Only available for Android and iOS.
       *
       * @default false
       * @example false
       * @since 0.1.0
       */
      skipNativeAuth?: boolean;
      /**
       * Configure the providers that should be loaded by the plugin.
       *
       * Possible values: `["apple.com", "facebook.com", "github.com", "google.com", "microsoft.com", "playgames.google.com", "twitter.com", "yahoo.com", "phone"]`
       *
       * Only available for Android and iOS.
       *
       * @default []
       * @example ["apple.com", "facebook.com"]
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
   * Get the tenant id.
   *
   * @since 1.1.0
   */
  getTenantId(): Promise<GetTenantIdResult>;
  /**
   * Checks if an incoming link is a sign-in with email link suitable for `signInWithEmailLink`.
   *
   * @since 1.1.0
   */
  isSignInWithEmailLink(
    options: IsSignInWithEmailLinkOptions,
  ): Promise<IsSignInWithEmailLinkResult>;
  /**
   * Links the user account with Apple authentication provider.
   *
   * The user must be logged in on the native layer.
   * The `skipNativeAuth` configuration option has no effect here.
   *
   * @since 1.1.0
   */
  linkWithApple(options?: LinkWithOAuthOptions): Promise<LinkResult>;
  /**
   * Links the user account with Email authentication provider.
   *
   * The user must be logged in on the native layer.
   * The `skipNativeAuth` configuration option has no effect here.
   *
   * @since 1.1.0
   */
  linkWithEmailAndPassword(
    options: LinkWithEmailAndPasswordOptions,
  ): Promise<LinkResult>;
  /**
   * Links the user account with Email authentication provider.
   *
   * The user must be logged in on the native layer.
   * The `skipNativeAuth` configuration option has no effect here.
   *
   * @since 1.1.0
   */
  linkWithEmailLink(options: LinkWithEmailLinkOptions): Promise<LinkResult>;
  /**
   * Links the user account with Facebook authentication provider.
   *
   * The user must be logged in on the native layer.
   * The `skipNativeAuth` configuration option has no effect here.
   *
   * @since 1.1.0
   */
  linkWithFacebook(options?: LinkWithOAuthOptions): Promise<LinkResult>;
  /**
   * Links the user account with GitHub authentication provider.
   *
   * The user must be logged in on the native layer.
   * The `skipNativeAuth` configuration option has no effect here.
   *
   * @since 1.1.0
   */
  linkWithGithub(options?: LinkWithOAuthOptions): Promise<LinkResult>;
  /**
   * Links the user account with Google authentication provider.
   *
   * The user must be logged in on the native layer.
   * The `skipNativeAuth` configuration option has no effect here.
   *
   * @since 1.1.0
   */
  linkWithGoogle(options?: LinkWithOAuthOptions): Promise<LinkResult>;
  /**
   * Links the user account with Microsoft authentication provider.
   *
   * The user must be logged in on the native layer.
   * The `skipNativeAuth` configuration option has no effect here.
   *
   * @since 1.1.0
   */
  linkWithMicrosoft(options?: LinkWithOAuthOptions): Promise<LinkResult>;
  /**
   * Links the user account with Phone Number authentication provider.
   *
   * The user must be logged in on the native layer.
   * The `skipNativeAuth` configuration option has no effect here.
   *
   * @since 1.1.0
   */
  linkWithPhoneNumber(options: LinkWithPhoneNumberOptions): Promise<LinkResult>;
  /**
   * Links the user account with Play Games authentication provider.
   *
   * The user must be logged in on the native layer.
   * The `skipNativeAuth` configuration option has no effect here.
   *
   * @since 1.1.0
   */
  linkWithPlayGames(options?: LinkWithOAuthOptions): Promise<LinkResult>;
  /**
   * Links the user account with Twitter authentication provider.
   *
   * The user must be logged in on the native layer.
   * The `skipNativeAuth` configuration option has no effect here.
   *
   * @since 1.1.0
   */
  linkWithTwitter(options?: LinkWithOAuthOptions): Promise<LinkResult>;
  /**
   * Links the user account with Yahoo authentication provider.
   *
   * The user must be logged in on the native layer.
   * The `skipNativeAuth` configuration option has no effect here.
   *
   * @since 1.1.0
   */
  linkWithYahoo(options?: LinkWithOAuthOptions): Promise<LinkResult>;
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
   * Sends a sign-in email link to the user with the specified email.
   *
   * To complete sign in with the email link, call `signInWithEmailLink` with the email address and the email link supplied in the email sent to the user.
   *
   * @since 1.1.0
   */
  sendSignInLinkToEmail(options: SendSignInLinkToEmailOptions): Promise<void>;
  /**
   * Sets the user-facing language code for auth operations.
   *
   * @since 0.1.0
   */
  setLanguageCode(options: SetLanguageCodeOptions): Promise<void>;
  /**
   * Sets the tenant id.
   *
   * @since 1.1.0
   */
  setTenantId(options: SetTenantIdOptions): Promise<void>;
  /**
   * Signs in as an anonymous user.
   *
   * @since 1.1.0
   */
  signInAnonymously(): Promise<SignInResult>;
  /**
   * Starts the Apple sign-in flow.
   *
   * @since 0.1.0
   */
  signInWithApple(
    options?: SignInOptions | SignInWithOAuthOptions,
  ): Promise<SignInResult>;
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
   * Signs in using an email and sign-in email link.
   *
   * @since 1.1.0
   */
  signInWithEmailLink(
    options: SignInWithEmailLinkOptions,
  ): Promise<SignInResult>;
  /**
   * Starts the Facebook sign-in flow.
   *
   * @since 0.1.0
   */
  signInWithFacebook(
    options?: SignInOptions | SignInWithOAuthOptions,
  ): Promise<SignInResult>;
  /**
   * Starts the GitHub sign-in flow.
   *
   * @since 0.1.0
   */
  signInWithGithub(
    options?: SignInOptions | SignInWithOAuthOptions,
  ): Promise<SignInResult>;
  /**
   * Starts the Google sign-in flow.
   *
   * @since 0.1.0
   */
  signInWithGoogle(
    options?: SignInOptions | SignInWithOAuthOptions,
  ): Promise<SignInResult>;
  /**
   * Starts the Microsoft sign-in flow.
   *
   * @since 0.1.0
   */
  signInWithMicrosoft(
    options?: SignInOptions | SignInWithOAuthOptions,
  ): Promise<SignInResult>;
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
  signInWithPlayGames(
    options?: SignInOptions | SignInWithOAuthOptions,
  ): Promise<SignInResult>;
  /**
   * Starts the Twitter sign-in flow.
   *
   * @since 0.1.0
   */
  signInWithTwitter(
    options?: SignInOptions | SignInWithOAuthOptions,
  ): Promise<SignInResult>;
  /**
   * Starts the Yahoo sign-in flow.
   *
   * @since 0.1.0
   */
  signInWithYahoo(
    options?: SignInOptions | SignInWithOAuthOptions,
  ): Promise<SignInResult>;
  /**
   * Starts the sign-out flow.
   *
   * @since 0.1.0
   */
  signOut(): Promise<void>;
  /**
   * Unlinks a provider from a user account.
   *
   * @since 1.1.0
   */
  unlink(options: UnlinkOptions): Promise<UnlinkResult>;
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
 * @since 1.1.0
 */
export interface GetTenantIdResult {
  /**
   * The tenant id.
   * `null` if it has never been set.
   *
   * @since 1.1.0
   */
  tenantId: string | null;
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
 * @since 1.1.0
 */
export interface SetTenantIdOptions {
  /**
   * The tenant id.
   *
   * @since 1.1.0
   */
  tenantId: string;
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
 * @since 1.1.0
 */
export type LinkOptions = SignInOptions;

/**
 * @since 1.1.0
 */
export type LinkWithOAuthOptions = SignInWithOAuthOptions;

/**
 * @since 1.1.0
 */
export interface LinkWithEmailAndPasswordOptions {
  /**
   * The users email address.
   *
   * @since 1.1.0
   */
  email: string;
  /**
   * The users password.
   *
   * @since 1.1.0
   */
  password: string;
}

/**
 * @since 1.1.0
 */
export interface LinkWithEmailLinkOptions {
  /**
   * The user's email address.
   *
   * @since 1.1.0
   */
  email: string;
  /**
   * The link sent to the user's email address.
   *
   * @since 1.1.0
   */
  emailLink: string;
}

/**
 * @since 1.1.0
 */
export interface LinkWithPhoneNumberOptions {
  /**
   * The user's phone number in E.164 format.
   *
   * @example "+16505550101"
   * @since 1.1.0
   */
  phoneNumber: string;
}

/**
 * @since 1.1.0
 */
export type LinkWithCustomTokenOptions = SignInWithCustomTokenOptions;

/**
 * @since 1.1.0
 */
export type LinkResult = SignInResult;

/**
 * @since 0.1.0
 */
export interface SignInOptions {
  /**
   * Configures custom parameters to be passed to the identity provider during the OAuth sign-in flow.
   *
   * @since 0.1.0
   * @deprecated Use `SignInWithOAuthOptions` interface instead.
   */
  customParameters?: SignInCustomParameter[];
  /**
   * Scopes to request from provider.
   *
   * @since 0.3.1
   * @deprecated Use `SignInWithOAuthOptions` interface instead.
   */
  scopes?: string[];
  /**
   * Whether the plugin should skip the native authentication or not.
   * Only needed if you want to use the Firebase JavaScript SDK.
   * This value overwrites the configrations value of the `skipNativeAuth` option.
   * If no value is set, the configuration value is used.
   *
   * **Note that the plugin may behave differently across the platforms.**
   *
   * `skipNativeAuth` cannot be used in combination with `signInWithCustomToken`, `createUserWithEmailAndPassword` or `signInWithEmailAndPassword`.
   *
   * Only available for Android and iOS.
   *
   * @since 1.1.0
   */
  skipNativeAuth?: boolean;
}

/**
 * @since 1.1.0
 */
export interface SignInWithOAuthOptions extends SignInOptions {
  /**
   * Configures custom parameters to be passed to the identity provider during the OAuth sign-in flow.
   * 
   * Supports Apple, Facebook, GitHub, Google, Microsoft, Twitter and Yahoo on Web.
   * Supports Apple, GitHub, Microsoft, Twitter and Yahoo on Android.
   * Supports Facebook, GitHub, Microsoft, Twitter and Yahoo on iOS.
   *
   * @since 1.1.0
   */
  customParameters?: SignInCustomParameter[];
  /**
   * Scopes to request from provider.
   * 
   * Supports Apple, Facebook, GitHub, Google, Microsoft, Twitter and Yahoo on Web.
   * Supports Apple, GitHub, Microsoft, Twitter, Yahoo and Play Games on Android.
   * Supports Facebook, GitHub, Google, Microsoft, Twitter and Yahoo on iOS.
   *
   * @since 1.1.0
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
export interface SignInWithPhoneNumberOptions extends SignInOptions {
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
export interface SignInWithEmailAndPasswordOptions extends SignInOptions {
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
 * @since 1.1.0
 */
export interface SendSignInLinkToEmailOptions {
  /**
   * The user's email address.
   *
   * @since 1.1.0
   */
  email: string;
  /**
   * Structure that contains the required continue/state URL with optional Android and iOS bundle identifiers.
   *
   * @since 1.1.0
   */
  actionCodeSettings: ActionCodeSettings;
}

/**
 * @since 1.1.0
 */
export interface IsSignInWithEmailLinkOptions {
  /**
   * The link sent to the user's email address.
   *
   * @since 1.1.0
   */
  emailLink: string;
}
/**
 * @since 1.1.0
 */
export interface IsSignInWithEmailLinkResult {
  /**
   * Whether an incoming link is a signup with email link suitable for `signInWithEmailLink(...)`.
   */
  isSignInWithEmailLink: boolean;
}

/**
 * @since 1.1.0
 */
export interface SignInWithEmailLinkOptions extends SignInOptions {
  /**
   * The user's email address.
   *
   * @since 1.1.0
   */
  email: string;
  /**
   * The link sent to the user's email address.
   *
   * @since 1.1.0
   */
  emailLink: string;
}

/**
 * @since 0.1.0
 */
export interface SignInWithCustomTokenOptions extends SignInOptions {
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
  /**
   * Additional user information from a federated identity provider.
   *
   * @since 0.5.1
   */
  additionalUserInfo: AdditionalUserInfo | null;
}

/**
 * @since 1.1.0
 */
export interface UnlinkOptions {
  /**
   * The provider to unlink.
   *
   * @since 1.1.0
   */
  providerId: ProviderId;
}

/**
 * @since 1.1.0
 */
export interface UnlinkResult {
  /**
   * The currently signed-in user, or null if there isn't any.
   *
   * @since 1.1.0
   */
  user: User | null;
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
 * @since 0.5.1
 */
export interface AdditionalUserInfo {
  /**
   * Whether the user is new (sign-up) or existing (sign-in).
   *
   * @since 0.5.1
   */
  isNewUser: boolean;
  /**
   * Map containing IDP-specific user data.
   *
   * @since 0.5.1
   */
  profile?: { [key: string]: unknown };
  /**
   * Identifier for the provider used to authenticate this user.
   *
   * @since 0.5.1
   */
  providerId?: string;
  /**
   * The username if the provider is GitHub or Twitter.
   *
   * @since 0.5.1
   */
  username?: string;
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

/**
 * An interface that defines the required continue/state URL with optional Android and iOS
 * bundle identifiers.
 *
 * @since 1.1.0
 */
export interface ActionCodeSettings {
  /**
   * Sets the Android package name.
   */
  android?: {
    installApp?: boolean;
    minimumVersion?: string;
    packageName: string;
  };
  /**
   * When set to true, the action code link will be be sent as a Universal Link or Android App
   * Link and will be opened by the app if installed.
   */
  handleCodeInApp?: boolean;
  /**
   * Sets the iOS bundle ID.
   */
  iOS?: {
    bundleId: string;
  };
  /**
   * Sets the link continue/state URL.
   */
  url: string;
  /**
   * When multiple custom dynamic link domains are defined for a project, specify which one to use
   * when the link is to be opened via a specified mobile app (for example, `example.page.link`).
   */
  dynamicLinkDomain?: string;
}

export enum ProviderId {
  APPLE = 'apple.com',
  FACEBOOK = 'facebook.com',
  GITHUB = 'github.com',
  GOOGLE = 'google.com',
  MICROSOFT = 'microsoft.com',
  PLAY_GAMES = 'playgames.google.com',
  TWITTER = 'twitter.com',
  YAHOO = 'yahoo.com',
  PASSWORD = 'password',
  PHONE = 'phone',
}
