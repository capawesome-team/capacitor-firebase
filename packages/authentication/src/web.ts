import { WebPlugin } from '@capacitor/core';
import { initializeApp, getApp, getApps } from "firebase/app";
import type { FirebaseApp } from "firebase/app";
import type {
  ConfirmationResult,
  AuthCredential as FirebaseAuthCredential,
  AuthProvider as FirebaseAuthProvider,
  CustomParameters as FirebaseCustomParameters,
  User as FirebaseUser,
  UserCredential as FirebaseUserCredential,
  UserInfo as FirebaseUserInfo,
  UserMetadata as FirebaseUserMetadata,
} from 'firebase/auth';
import {
  EmailAuthProvider,
  FacebookAuthProvider,
  GithubAuthProvider,
  GoogleAuthProvider,
  OAuthCredential,
  OAuthProvider,
  RecaptchaVerifier,
  TwitterAuthProvider,
  applyActionCode,
  browserLocalPersistence,
  browserSessionPersistence,
  confirmPasswordReset,
  connectAuthEmulator,
  createUserWithEmailAndPassword,
  deleteUser,
  fetchSignInMethodsForEmail,
  getAdditionalUserInfo,
  getAuth,
  getRedirectResult,
  inMemoryPersistence,
  indexedDBLocalPersistence,
  isSignInWithEmailLink,
  linkWithCredential,
  linkWithPhoneNumber,
  linkWithPopup,
  linkWithRedirect,
  reload,
  revokeAccessToken,
  sendEmailVerification,
  sendPasswordResetEmail,
  sendSignInLinkToEmail,
  setPersistence,
  signInAnonymously,
  signInWithCustomToken,
  signInWithEmailAndPassword,
  signInWithEmailLink,
  signInWithPhoneNumber,
  signInWithPopup,
  signInWithRedirect,
  unlink,
  updateEmail,
  updatePassword,
  updateProfile,
} from 'firebase/auth';

import type {
  AdditionalUserInfo,
  ApplyActionCodeOptions,
  AuthCredential,
  AuthStateChange,
  ConfirmPasswordResetOptions,
  ConfirmVerificationCodeOptions,
  CreateUserWithEmailAndPasswordOptions,
  FetchSignInMethodsForEmailOptions,
  FetchSignInMethodsForEmailResult,
  FirebaseAppInitializedResult,
  FirebaseAppName,
  FirebaseAuthenticationPlugin,
  FirebaseConfigOptions,
  GetCurrentUserResult,
  GetIdTokenOptions,
  GetIdTokenResult,
  GetTenantIdResult,
  IsSignInWithEmailLinkOptions,
  IsSignInWithEmailLinkResult,
  LinkResult,
  LinkWithEmailAndPasswordOptions,
  LinkWithEmailLinkOptions,
  LinkWithOAuthOptions,
  LinkWithPhoneNumberOptions,
  PhoneCodeSentEvent,
  PhoneVerificationFailedEvent,
  RevokeAccessTokenOptions,
  SendEmailVerificationOptions,
  SendPasswordResetEmailOptions,
  SendSignInLinkToEmailOptions,
  SetLanguageCodeOptions,
  SetPersistenceOptions,
  SetTenantIdOptions,
  SignInResult,
  SignInWithCustomTokenOptions,
  SignInWithEmailAndPasswordOptions,
  SignInWithEmailLinkOptions,
  SignInWithOAuthOptions,
  SignInWithOpenIdConnectOptions,
  SignInWithPhoneNumberOptions,
  UnlinkOptions,
  UnlinkResult,
  UpdateEmailOptions,
  UpdatePasswordOptions,
  UpdateProfileOptions,
  UseEmulatorOptions,
  UseFirebaseAppOptions,
  User,
  UserInfo,
  UserMetadata,
} from './definitions';
import { Persistence, ProviderId } from './definitions';


export class FirebaseAuthenticationWeb
  extends WebPlugin
  implements FirebaseAuthenticationPlugin
{
  public static readonly AUTH_STATE_CHANGE_EVENT = 'authStateChange';
  public static readonly PHONE_CODE_SENT_EVENT = 'phoneCodeSent';
  public static readonly PHONE_VERIFICATION_FAILED_EVENT =
    'phoneVerificationFailed';
  public static readonly ERROR_NO_USER_SIGNED_IN = 'No user is signed in.';
  public static readonly ERROR_PHONE_NUMBER_MISSING =
    'phoneNumber must be provided.';
  public static readonly ERROR_RECAPTCHA_VERIFIER_MISSING =
    'recaptchaVerifier must be provided and must be an instance of RecaptchaVerifier.';
  public static readonly ERROR_CONFIRMATION_RESULT_MISSING =
    'No confirmation result with this verification id was found.';

  private lastConfirmationResult: Map<string, ConfirmationResult> = new Map();

  private readonly defaultAppName: string = "";
  private currentAppName = "";

  constructor() {
    super();
    const auth = getAuth();
    this.defaultAppName = auth.app.name;
    this.currentAppName = this.defaultAppName;

    auth.onAuthStateChanged(user => this.handleAuthStateChange(user));
  }

  /**
   * Initialize a new Firebase App with the provided name and Firebase project configuration
   * @param options
   */
  public async initWithFirebaseConfig(options: FirebaseConfigOptions): Promise<void> {
    const app = initializeApp({
      apiKey: options.config.apiKey,
      appId: options.config.appId,
      projectId: options.config.projectId,
      databaseURL: options.config.databaseURL,
      storageBucket: options.config.storageBucket,
      messagingSenderId: options.config.messagingSenderId,
    }, options.name);

    if (app) {
      return;
    } else {
      throw new Error("Could not initialize app with provided firebase config");
    }
  }

  /**
   * Check if a Firebase App with the provided name is initialized
   * @param options
   * @returns Promise object with property boolean "result", true if initialized
   */
  public async firebaseAppIsInitialized(options: FirebaseAppName): Promise<FirebaseAppInitializedResult> {
    const apps = getApps();
    if (apps.some((app: FirebaseApp) => app.name == options.name)) {
      return new Promise(resolve => resolve({ result: true }));
    }
    return new Promise(resolve => resolve({ result: false }));
  }

  /**
   * Changes Firebase Authentication to use the provided app name
   * Will throw an error if the app has not already been initialized
   * @param options
   */
  public async useFirebaseApp(options: UseFirebaseAppOptions): Promise<void> {
    if (options.name == "default") {
      options.name = this.defaultAppName;
    }
    if ((await this.firebaseAppIsInitialized({ name: options.name})).result == true) {
      this.currentAppName = options.name;
      return;
    } else {
      throw new Error("Firebase app does not exist")
    }
  }

  /**
   * Returns the name of the current Firebase App used by Firebase Authentication
   */
  public async currentFirebaseApp(): Promise<FirebaseAppName> {
    // TODO: Implement for web
    return { name: this.currentAppName };
  }

  public async applyActionCode(options: ApplyActionCodeOptions): Promise<void> {
    const auth = getAuth(getApp(this.currentAppName));
    return applyActionCode(auth, options.oobCode);
  }

  public async createUserWithEmailAndPassword(
    options: CreateUserWithEmailAndPasswordOptions,
  ): Promise<SignInResult> {
    const auth = getAuth(getApp(this.currentAppName));
    const userCredential = await createUserWithEmailAndPassword(
      auth,
      options.email,
      options.password,
    );
    return this.createSignInResult(userCredential, null);
  }

  public async confirmPasswordReset(
    options: ConfirmPasswordResetOptions,
  ): Promise<void> {
    const auth = getAuth(getApp(this.currentAppName));
    return confirmPasswordReset(auth, options.oobCode, options.newPassword);
  }

  public async confirmVerificationCode(
    options: ConfirmVerificationCodeOptions,
  ): Promise<SignInResult> {
    const { verificationCode, verificationId } = options;
    const confirmationResult = this.lastConfirmationResult.get(verificationId);
    if (!confirmationResult) {
      throw new Error(
        FirebaseAuthenticationWeb.ERROR_CONFIRMATION_RESULT_MISSING,
      );
    }
    const userCredential = await confirmationResult.confirm(verificationCode);
    return this.createSignInResult(userCredential, null);
  }

  public async deleteUser(): Promise<void> {
    const auth = getAuth(getApp(this.currentAppName));
    const currentUser = auth.currentUser;
    if (!currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    return deleteUser(currentUser);
  }

  public async fetchSignInMethodsForEmail(
    options: FetchSignInMethodsForEmailOptions,
  ): Promise<FetchSignInMethodsForEmailResult> {
    const auth = getAuth(getApp(this.currentAppName));
    const signInMethods = await fetchSignInMethodsForEmail(auth, options.email);
    return {
      signInMethods,
    };
  }

  public async getPendingAuthResult(): Promise<SignInResult> {
    this.throwNotAvailableError();
  }

  public async getCurrentUser(): Promise<GetCurrentUserResult> {
    const auth = getAuth(getApp(this.currentAppName));
    const userResult = this.createUserResult(auth.currentUser);
    const result: GetCurrentUserResult = {
      user: userResult,
    };
    return result;
  }

  public async getIdToken(
    options?: GetIdTokenOptions,
  ): Promise<GetIdTokenResult> {
    const auth = getAuth(getApp(this.currentAppName));
    if (!auth.currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    const idToken = await auth.currentUser.getIdToken(options?.forceRefresh);
    const result: GetIdTokenResult = {
      token: idToken || '',
    };
    return result;
  }

  public async getRedirectResult(): Promise<SignInResult> {
    const auth = getAuth(getApp(this.currentAppName));
    const userCredential = await getRedirectResult(auth);
    const authCredential = userCredential
      ? OAuthProvider.credentialFromResult(userCredential)
      : null;
    return this.createSignInResult(userCredential, authCredential);
  }

  public async getTenantId(): Promise<GetTenantIdResult> {
    const auth = getAuth(getApp(this.currentAppName));
    return {
      tenantId: auth.tenantId,
    };
  }

  public async isSignInWithEmailLink(
    options: IsSignInWithEmailLinkOptions,
  ): Promise<IsSignInWithEmailLinkResult> {
    const auth = getAuth(getApp(this.currentAppName));
    return {
      isSignInWithEmailLink: isSignInWithEmailLink(auth, options.emailLink),
    };
  }

  public async linkWithApple(
    options?: LinkWithOAuthOptions,
  ): Promise<LinkResult> {
    const provider = new OAuthProvider(ProviderId.APPLE);
    this.applySignInOptions(options || {}, provider);
    const userCredential = await this.linkCurrentUserWithPopupOrRedirect(
      provider,
      options?.mode,
    );
    const authCredential = OAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async linkWithEmailAndPassword(
    options: LinkWithEmailAndPasswordOptions,
  ): Promise<LinkResult> {
    const authCredential = EmailAuthProvider.credential(
      options.email,
      options.password,
    );
    const userCredential = await this.linkCurrentUserWithCredential(
      authCredential,
    );
    return this.createSignInResult(userCredential, authCredential);
  }

  public async linkWithEmailLink(
    options: LinkWithEmailLinkOptions,
  ): Promise<LinkResult> {
    const authCredential = EmailAuthProvider.credentialWithLink(
      options.email,
      options.emailLink,
    );
    const userCredential = await this.linkCurrentUserWithCredential(
      authCredential,
    );
    return this.createSignInResult(userCredential, authCredential);
  }

  public async linkWithFacebook(
    options?: LinkWithOAuthOptions,
  ): Promise<LinkResult> {
    const provider = new FacebookAuthProvider();
    this.applySignInOptions(options || {}, provider);
    const userCredential = await this.linkCurrentUserWithPopupOrRedirect(
      provider,
      options?.mode,
    );
    const authCredential =
      FacebookAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async linkWithGameCenter(): Promise<LinkResult> {
    this.throwNotAvailableError();
  }

  public async linkWithGithub(
    options?: LinkWithOAuthOptions,
  ): Promise<LinkResult> {
    const provider = new GithubAuthProvider();
    this.applySignInOptions(options || {}, provider);
    const userCredential = await this.linkCurrentUserWithPopupOrRedirect(
      provider,
      options?.mode,
    );
    const authCredential =
      GithubAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async linkWithGoogle(
    options?: LinkWithOAuthOptions,
  ): Promise<LinkResult> {
    const provider = new GoogleAuthProvider();
    this.applySignInOptions(options || {}, provider);
    const userCredential = await this.linkCurrentUserWithPopupOrRedirect(
      provider,
      options?.mode,
    );
    const authCredential =
      GoogleAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async linkWithMicrosoft(
    options?: LinkWithOAuthOptions,
  ): Promise<LinkResult> {
    const provider = new OAuthProvider(ProviderId.MICROSOFT);
    this.applySignInOptions(options || {}, provider);
    const userCredential = await this.linkCurrentUserWithPopupOrRedirect(
      provider,
      options?.mode,
    );
    const authCredential = OAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async linkWithOpenIdConnect(
    options: SignInWithOpenIdConnectOptions,
  ): Promise<SignInResult> {
    const provider = new OAuthProvider(options.providerId);
    this.applySignInOptions(options, provider);
    const userCredential = await this.linkCurrentUserWithPopupOrRedirect(
      provider,
      options.mode,
    );
    const authCredential = OAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async linkWithPhoneNumber(
    options: LinkWithPhoneNumberOptions,
  ): Promise<void> {
    const auth = getAuth(getApp(this.currentAppName));
    const currentUser = auth.currentUser;
    if (!currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    if (!options.phoneNumber) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_PHONE_NUMBER_MISSING);
    }
    if (
      !options.recaptchaVerifier ||
      !(options.recaptchaVerifier instanceof RecaptchaVerifier)
    ) {
      throw new Error(
        FirebaseAuthenticationWeb.ERROR_RECAPTCHA_VERIFIER_MISSING,
      );
    }
    try {
      const confirmationResult = await linkWithPhoneNumber(
        currentUser,
        options.phoneNumber,
        options.recaptchaVerifier,
      );
      const { verificationId } = confirmationResult;
      this.lastConfirmationResult.set(verificationId, confirmationResult);
      const event: PhoneCodeSentEvent = {
        verificationId,
      };
      this.notifyListeners(
        FirebaseAuthenticationWeb.PHONE_CODE_SENT_EVENT,
        event,
      );
    } catch (error) {
      const event: PhoneVerificationFailedEvent = {
        message: this.getErrorMessage(error),
      };
      this.notifyListeners(
        FirebaseAuthenticationWeb.PHONE_VERIFICATION_FAILED_EVENT,
        event,
      );
    }
  }

  public async linkWithPlayGames(): Promise<LinkResult> {
    this.throwNotAvailableError();
  }

  public async linkWithTwitter(
    options?: LinkWithOAuthOptions,
  ): Promise<LinkResult> {
    const provider = new TwitterAuthProvider();
    this.applySignInOptions(options || {}, provider);
    const userCredential = await this.linkCurrentUserWithPopupOrRedirect(
      provider,
      options?.mode,
    );
    const authCredential =
      TwitterAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async linkWithYahoo(
    options?: LinkWithOAuthOptions,
  ): Promise<LinkResult> {
    const provider = new OAuthProvider(ProviderId.YAHOO);
    this.applySignInOptions(options || {}, provider);
    const userCredential = await this.linkCurrentUserWithPopupOrRedirect(
      provider,
      options?.mode,
    );
    const authCredential = OAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async reload(): Promise<void> {
    const auth = getAuth(getApp(this.currentAppName));
    const currentUser = auth.currentUser;
    if (!currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    return reload(currentUser);
  }

  public async revokeAccessToken(
    options: RevokeAccessTokenOptions,
  ): Promise<void> {
    const auth = getAuth(getApp(this.currentAppName));
    return revokeAccessToken(auth, options.token);
  }

  public async sendEmailVerification(
    options: SendEmailVerificationOptions,
  ): Promise<void> {
    const auth = getAuth(getApp(this.currentAppName));
    const currentUser = auth.currentUser;
    if (!currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    return sendEmailVerification(currentUser, options?.actionCodeSettings);
  }

  public async sendPasswordResetEmail(
    options: SendPasswordResetEmailOptions,
  ): Promise<void> {
    const auth = getAuth(getApp(this.currentAppName));
    return sendPasswordResetEmail(
      auth,
      options.email,
      options.actionCodeSettings,
    );
  }

  public async sendSignInLinkToEmail(
    options: SendSignInLinkToEmailOptions,
  ): Promise<void> {
    const auth = getAuth(getApp(this.currentAppName));
    return sendSignInLinkToEmail(
      auth,
      options.email,
      options.actionCodeSettings,
    );
  }

  public async setLanguageCode(options: SetLanguageCodeOptions): Promise<void> {
    const auth = getAuth(getApp(this.currentAppName));
    auth.languageCode = options.languageCode;
  }

  public async setPersistence(options: SetPersistenceOptions): Promise<void> {
    const auth = getAuth(getApp(this.currentAppName));
    switch (options.persistence) {
      case Persistence.BrowserLocal:
        await setPersistence(auth, browserLocalPersistence);
        break;
      case Persistence.BrowserSession:
        await setPersistence(auth, browserSessionPersistence);
        break;
      case Persistence.IndexedDbLocal:
        await setPersistence(auth, indexedDBLocalPersistence);
        break;
      case Persistence.InMemory:
        await setPersistence(auth, inMemoryPersistence);
        break;
    }
  }

  public async setTenantId(options: SetTenantIdOptions): Promise<void> {
    const auth = getAuth(getApp(this.currentAppName));
    auth.tenantId = options.tenantId;
  }

  public async signInAnonymously(): Promise<SignInResult> {
    const auth = getAuth(getApp(this.currentAppName));
    const userCredential = await signInAnonymously(auth);
    return this.createSignInResult(userCredential, null);
  }

  public async signInWithApple(
    options?: SignInWithOAuthOptions,
  ): Promise<SignInResult> {
    const provider = new OAuthProvider(ProviderId.APPLE);
    this.applySignInOptions(options || {}, provider);
    const userCredential = await this.signInWithPopupOrRedirect(
      provider,
      options?.mode,
    );
    const authCredential = OAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async signInWithCustomToken(
    options: SignInWithCustomTokenOptions,
  ): Promise<SignInResult> {
    const auth = getAuth(getApp(this.currentAppName));
    const userCredential = await signInWithCustomToken(auth, options.token);
    return this.createSignInResult(userCredential, null);
  }

  public async signInWithEmailAndPassword(
    options: SignInWithEmailAndPasswordOptions,
  ): Promise<SignInResult> {
    const auth = getAuth(getApp(this.currentAppName));
    const userCredential = await signInWithEmailAndPassword(
      auth,
      options.email,
      options.password,
    );
    return this.createSignInResult(userCredential, null);
  }

  public async signInWithEmailLink(
    options: SignInWithEmailLinkOptions,
  ): Promise<SignInResult> {
    const auth = getAuth(getApp(this.currentAppName));
    const userCredential = await signInWithEmailLink(
      auth,
      options.email,
      options.emailLink,
    );
    return this.createSignInResult(userCredential, null);
  }

  public async signInWithFacebook(
    options?: SignInWithOAuthOptions,
  ): Promise<SignInResult> {
    const provider = new FacebookAuthProvider();
    this.applySignInOptions(options || {}, provider);
    const userCredential = await this.signInWithPopupOrRedirect(
      provider,
      options?.mode,
    );
    const authCredential =
      FacebookAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async signInWithGithub(
    options?: SignInWithOAuthOptions,
  ): Promise<SignInResult> {
    const provider = new GithubAuthProvider();
    this.applySignInOptions(options || {}, provider);
    const userCredential = await this.signInWithPopupOrRedirect(
      provider,
      options?.mode,
    );
    const authCredential =
      GithubAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async signInWithGoogle(
    options?: SignInWithOAuthOptions,
  ): Promise<SignInResult> {
    const provider = new GoogleAuthProvider();
    this.applySignInOptions(options || {}, provider);
    const userCredential = await this.signInWithPopupOrRedirect(
      provider,
      options?.mode,
    );
    const authCredential =
      GoogleAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async signInWithMicrosoft(
    options?: SignInWithOAuthOptions,
  ): Promise<SignInResult> {
    const provider = new OAuthProvider(ProviderId.MICROSOFT);
    this.applySignInOptions(options || {}, provider);
    const userCredential = await this.signInWithPopupOrRedirect(
      provider,
      options?.mode,
    );
    const authCredential = OAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async signInWithOpenIdConnect(
    options: SignInWithOpenIdConnectOptions,
  ): Promise<SignInResult> {
    const provider = new OAuthProvider(options.providerId);
    this.applySignInOptions(options, provider);
    const userCredential = await this.signInWithPopupOrRedirect(
      provider,
      options.mode,
    );
    const authCredential = OAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async signInWithPhoneNumber(
    options: SignInWithPhoneNumberOptions,
  ): Promise<void> {
    if (!options.phoneNumber) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_PHONE_NUMBER_MISSING);
    }
    if (
      !options.recaptchaVerifier ||
      !(options.recaptchaVerifier instanceof RecaptchaVerifier)
    ) {
      throw new Error(
        FirebaseAuthenticationWeb.ERROR_RECAPTCHA_VERIFIER_MISSING,
      );
    }
    const auth = getAuth(getApp(this.currentAppName));
    try {
      const confirmationResult = await signInWithPhoneNumber(
        auth,
        options.phoneNumber,
        options.recaptchaVerifier,
      );
      const { verificationId } = confirmationResult;
      this.lastConfirmationResult.set(verificationId, confirmationResult);
      const event: PhoneCodeSentEvent = {
        verificationId,
      };
      this.notifyListeners(
        FirebaseAuthenticationWeb.PHONE_CODE_SENT_EVENT,
        event,
      );
    } catch (error) {
      const event: PhoneVerificationFailedEvent = {
        message: this.getErrorMessage(error),
      };
      this.notifyListeners(
        FirebaseAuthenticationWeb.PHONE_VERIFICATION_FAILED_EVENT,
        event,
      );
    }
  }

  public async signInWithPlayGames(): Promise<SignInResult> {
    this.throwNotAvailableError();
  }

  public async signInWithGameCenter(): Promise<SignInResult> {
    this.throwNotAvailableError();
  }

  public async signInWithTwitter(
    options?: SignInWithOAuthOptions,
  ): Promise<SignInResult> {
    const provider = new TwitterAuthProvider();
    this.applySignInOptions(options || {}, provider);
    const userCredential = await this.signInWithPopupOrRedirect(
      provider,
      options?.mode,
    );
    const authCredential =
      TwitterAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async signInWithYahoo(
    options?: SignInWithOAuthOptions,
  ): Promise<SignInResult> {
    const provider = new OAuthProvider(ProviderId.YAHOO);
    this.applySignInOptions(options || {}, provider);
    const userCredential = await this.signInWithPopupOrRedirect(
      provider,
      options?.mode,
    );
    const authCredential = OAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async signOut(): Promise<void> {
    const auth = getAuth(getApp(this.currentAppName));
    await auth.signOut();
  }

  public async unlink(options: UnlinkOptions): Promise<UnlinkResult> {
    const auth = getAuth(getApp(this.currentAppName));
    if (!auth.currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    const user = await unlink(auth.currentUser, options.providerId);
    const userResult = this.createUserResult(user);
    const result: UnlinkResult = {
      user: userResult,
    };
    return result;
  }

  public async updateEmail(options: UpdateEmailOptions): Promise<void> {
    const auth = getAuth(getApp(this.currentAppName));
    const currentUser = auth.currentUser;
    if (!currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    return updateEmail(currentUser, options.newEmail);
  }

  public async updatePassword(options: UpdatePasswordOptions): Promise<void> {
    const auth = getAuth(getApp(this.currentAppName));
    const currentUser = auth.currentUser;
    if (!currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    return updatePassword(currentUser, options.newPassword);
  }

  public async updateProfile(options: UpdateProfileOptions): Promise<void> {
    const auth = getAuth(getApp(this.currentAppName));
    const currentUser = auth.currentUser;
    if (!currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    return updateProfile(currentUser, {
      displayName: options.displayName,
      photoURL: options.photoUrl,
    });
  }

  public async useAppLanguage(): Promise<void> {
    const auth = getAuth(getApp(this.currentAppName));
    auth.useDeviceLanguage();
  }

  public async useEmulator(options: UseEmulatorOptions): Promise<void> {
    const auth = getAuth(getApp(this.currentAppName));
    const port = options.port || 9099;
    const scheme = options.scheme || 'http';
    if (options.host.includes('://')) {
      connectAuthEmulator(auth, `${options.host}:${port}`);
    } else {
      connectAuthEmulator(auth, `${scheme}://${options.host}:${port}`);
    }
  }

  private handleAuthStateChange(user: FirebaseUser | null): void {
    const userResult = this.createUserResult(user);
    const change: AuthStateChange = {
      user: userResult,
    };
    this.notifyListeners(
      FirebaseAuthenticationWeb.AUTH_STATE_CHANGE_EVENT,
      change,
      true,
    );
  }

  private applySignInOptions(
    options: SignInWithOAuthOptions,
    provider: OAuthProvider | GoogleAuthProvider | FacebookAuthProvider,
  ) {
    if (options.customParameters) {
      const customParameters: FirebaseCustomParameters = {};
      options.customParameters.map(parameter => {
        customParameters[parameter.key] = parameter.value;
      });
      provider.setCustomParameters(customParameters);
    }
    if (options.scopes) {
      for (const scope of options.scopes) {
        provider.addScope(scope);
      }
    }
  }

  public signInWithPopupOrRedirect(
    provider: FirebaseAuthProvider,
    mode?: 'popup' | 'redirect',
  ): Promise<FirebaseUserCredential | never> {
    const auth = getAuth(getApp(this.currentAppName));
    if (mode === 'redirect') {
      return signInWithRedirect(auth, provider);
    } else {
      return signInWithPopup(auth, provider);
    }
  }

  public linkCurrentUserWithPopupOrRedirect(
    provider: FirebaseAuthProvider,
    mode?: 'popup' | 'redirect',
  ): Promise<FirebaseUserCredential | never> {
    const auth = getAuth(getApp(this.currentAppName));
    if (!auth.currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    if (mode === 'redirect') {
      return linkWithRedirect(auth.currentUser, provider);
    } else {
      return linkWithPopup(auth.currentUser, provider);
    }
  }

  public linkCurrentUserWithCredential(
    credential: FirebaseAuthCredential,
  ): Promise<FirebaseUserCredential> {
    const auth = getAuth(getApp(this.currentAppName));
    if (!auth.currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    return linkWithCredential(auth.currentUser, credential);
  }

  private createSignInResult(
    userCredential: FirebaseUserCredential | null,
    authCredential: FirebaseAuthCredential | null,
  ): SignInResult {
    const userResult = this.createUserResult(userCredential?.user || null);
    const credentialResult = this.createCredentialResult(authCredential);
    const additionalUserInfoResult =
      this.createAdditionalUserInfoResult(userCredential);
    const result: SignInResult = {
      user: userResult,
      credential: credentialResult,
      additionalUserInfo: additionalUserInfoResult,
    };
    return result;
  }

  private createCredentialResult(
    credential: FirebaseAuthCredential | null,
  ): AuthCredential | null {
    if (!credential) {
      return null;
    }
    const result: AuthCredential = {
      providerId: credential.providerId,
    };
    if (credential instanceof OAuthCredential) {
      result.accessToken = credential.accessToken;
      result.idToken = credential.idToken;
      result.secret = credential.secret;
    }
    return result;
  }

  private createUserResult(user: FirebaseUser | null): User | null {
    if (!user) {
      return null;
    }
    const result: User = {
      displayName: user.displayName,
      email: user.email,
      emailVerified: user.emailVerified,
      isAnonymous: user.isAnonymous,
      metadata: this.createUserMetadataResult(user.metadata),
      phoneNumber: user.phoneNumber,
      photoUrl: user.photoURL,
      providerData: this.createUserProviderDataResult(user.providerData),
      providerId: user.providerId,
      tenantId: user.tenantId,
      uid: user.uid,
    };
    return result;
  }

  private createUserMetadataResult(
    metadata: FirebaseUserMetadata,
  ): UserMetadata {
    const result: UserMetadata = {};
    if (metadata.creationTime) {
      result.creationTime = Date.parse(metadata.creationTime);
    }
    if (metadata.lastSignInTime) {
      result.lastSignInTime = Date.parse(metadata.lastSignInTime);
    }
    return result;
  }

  private createUserProviderDataResult(
    providerData: FirebaseUserInfo[],
  ): UserInfo[] {
    return providerData.map(data => ({
      displayName: data.displayName,
      email: data.email,
      phoneNumber: data.phoneNumber,
      photoUrl: data.photoURL,
      providerId: data.providerId,
      uid: data.uid,
    }));
  }

  private createAdditionalUserInfoResult(
    credential: FirebaseUserCredential | null,
  ): AdditionalUserInfo | null {
    if (!credential) {
      return null;
    }
    const additionalUserInfo = getAdditionalUserInfo(credential);
    if (!additionalUserInfo) {
      return null;
    }
    const { isNewUser, profile, providerId, username } = additionalUserInfo;
    const result: AdditionalUserInfo = {
      isNewUser,
    };
    if (providerId !== null) {
      result.providerId = providerId;
    }
    if (profile !== null) {
      result.profile = profile as { [key: string]: unknown };
    }
    if (username !== null && username !== undefined) {
      result.username = username;
    }
    return result;
  }

  private getErrorMessage(error: unknown): string {
    if (
      error instanceof Object &&
      'message' in error &&
      typeof error['message'] === 'string'
    ) {
      return error['message'];
    }
    return JSON.stringify(error);
  }

  private throwNotAvailableError(): never {
    throw new Error('Not available on web.');
  }
}
