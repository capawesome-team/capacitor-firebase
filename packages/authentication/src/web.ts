import { WebPlugin } from '@capacitor/core';
import type {
  AuthCredential as FirebaseAuthCredential,
  CustomParameters,
  User as FirebaseUser,
  UserCredential as FirebaseUserCredential,
} from 'firebase/auth';
import {
  applyActionCode,
  confirmPasswordReset,
  connectAuthEmulator,
  createUserWithEmailAndPassword,
  EmailAuthProvider,
  FacebookAuthProvider,
  getAdditionalUserInfo,
  getAuth,
  GoogleAuthProvider,
  isSignInWithEmailLink,
  linkWithCredential,
  linkWithPopup,
  OAuthCredential,
  OAuthProvider,
  sendEmailVerification,
  sendPasswordResetEmail,
  sendSignInLinkToEmail,
  signInAnonymously,
  signInWithCustomToken,
  signInWithEmailAndPassword,
  signInWithEmailLink,
  signInWithPopup,
  updateEmail,
  updatePassword,
} from 'firebase/auth';

import type {
  AdditionalUserInfo,
  ApplyActionCodeOptions,
  AuthCredential,
  AuthStateChange,
  ConfirmPasswordResetOptions,
  CreateUserWithEmailAndPasswordOptions,
  FirebaseAuthenticationPlugin,
  GetCurrentUserResult,
  GetIdTokenOptions,
  GetIdTokenResult,
  IsSignInWithEmailLinkOptions,
  IsSignInWithEmailLinkResult,
  LinkWithEmailAndPasswordOptions,
  LinkWithEmailLinkOptions,
  LinkWithPhoneNumberOptions,
  SendPasswordResetEmailOptions,
  SendSignInLinkToEmailOptions,
  SetLanguageCodeOptions,
  SignInOptions,
  SignInResult,
  SignInWithCustomTokenOptions,
  SignInWithEmailAndPasswordOptions,
  SignInWithEmailLinkOptions,
  SignInWithPhoneNumberOptions,
  UpdateEmailOptions,
  UpdatePasswordOptions,
  UseEmulatorOptions,
  User,
} from './definitions';

/**
 * Enumeration of supported sign-in methods.
 *
 * @since 1.2.0
 */
export declare const SignInMethod: {
  /** Email link sign in method */
  readonly EMAIL_LINK: 'emailLink';
  /** Email/password sign in method */
  readonly EMAIL_PASSWORD: 'password';
  /** Apple sign in method */
  readonly APPLE: 'apple.com';
  /** Facebook sign in method */
  readonly FACEBOOK: 'facebook.com';
  /** GitHub sign in method */
  readonly GITHUB: 'github.com';
  /** Google sign in method */
  readonly GOOGLE: 'google.com';
  /** Microsoft sign in method */
  readonly MICROSOFT: 'microsoft.com';
  /** Phone sign in method */
  readonly PHONE: 'phone';
  /** Twitter sign in method */
  readonly TWITTER: 'twitter.com';
  /** Yahoo sign in method */
  readonly YAHOO: 'yahoo.com';
};

export class FirebaseAuthenticationWeb
  extends WebPlugin
  implements FirebaseAuthenticationPlugin
{
  public static readonly ERROR_NO_USER_SIGNED_IN = 'No user is signed in.';

  constructor() {
    super();
    const auth = getAuth();
    auth.onAuthStateChanged(user => this.handleAuthStateChange(user));
  }

  public async applyActionCode(options: ApplyActionCodeOptions): Promise<void> {
    const auth = getAuth();
    return applyActionCode(auth, options.oobCode);
  }

  public async createUserWithEmailAndPassword(
    options: CreateUserWithEmailAndPasswordOptions,
  ): Promise<SignInResult> {
    const auth = getAuth();
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
    const auth = getAuth();
    return confirmPasswordReset(auth, options.oobCode, options.newPassword);
  }

  public async getCurrentUser(): Promise<GetCurrentUserResult> {
    const auth = getAuth();
    const userResult = this.createUserResult(auth.currentUser);
    const result: GetCurrentUserResult = {
      user: userResult,
    };
    return result;
  }

  public async getIdToken(
    options?: GetIdTokenOptions,
  ): Promise<GetIdTokenResult> {
    const auth = getAuth();
    if (!auth.currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    const idToken = await auth.currentUser.getIdToken(options?.forceRefresh);
    const result: GetIdTokenResult = {
      token: idToken || '',
    };
    return result;
  }

  public async isSignInWithEmailLink(
    options: IsSignInWithEmailLinkOptions,
  ): Promise<IsSignInWithEmailLinkResult> {
    const auth = getAuth();
    return {
      isSignInWithEmailLink: isSignInWithEmailLink(auth, options.emailLink),
    };
  }

  public async linkWithApple(): Promise<SignInResult> {
    const auth = getAuth();
    if (!auth.currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    const provider = new OAuthProvider(SignInMethod.APPLE);
    const userCredential = await linkWithPopup(auth.currentUser, provider);
    const authCredential = OAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async linkWithEmailAndPassword(
    options: LinkWithEmailAndPasswordOptions,
  ): Promise<SignInResult> {
    const auth = getAuth();
    if (!auth.currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    const authCredential = EmailAuthProvider.credential(
      options.email,
      options.password,
    );
    const userCredential = await linkWithCredential(
      auth.currentUser,
      authCredential,
    );
    return this.createSignInResult(userCredential, authCredential);
  }

  public async linkWithEmailLink(
    options: LinkWithEmailLinkOptions,
  ): Promise<SignInResult> {
    const auth = getAuth();
    if (!auth.currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    // Construct the email link credential from the current URL.
    const authCredential = EmailAuthProvider.credentialWithLink(
      options.email,
      options.emailLink,
    );
    const userCredential = await linkWithCredential(
      auth.currentUser,
      authCredential,
    );
    return this.createSignInResult(userCredential, authCredential);
  }

  public async linkWithFacebook(): Promise<SignInResult> {
    const auth = getAuth();
    if (!auth.currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    const provider = new FacebookAuthProvider();
    const userCredential = await linkWithPopup(auth.currentUser, provider);
    const authCredential =
      FacebookAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async linkWithGithub(): Promise<SignInResult> {
    const auth = getAuth();
    if (!auth.currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    const provider = new OAuthProvider(SignInMethod.GITHUB);
    const userCredential = await linkWithPopup(auth.currentUser, provider);
    const authCredential = OAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async linkWithGoogle(): Promise<SignInResult> {
    const auth = getAuth();
    if (!auth.currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    const provider = new GoogleAuthProvider();
    const userCredential = await linkWithPopup(auth.currentUser, provider);
    const authCredential =
      GoogleAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async linkWithMicrosoft(): Promise<SignInResult> {
    const auth = getAuth();
    if (!auth.currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    const provider = new OAuthProvider(SignInMethod.MICROSOFT);
    const userCredential = await linkWithPopup(auth.currentUser, provider);
    const authCredential = OAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async linkWithPhoneNumber(
    _options: LinkWithPhoneNumberOptions,
  ): Promise<SignInResult> {
    throw new Error('Not implemented on web.');
  }

  public async linkWithPlayGames(): Promise<SignInResult> {
    throw new Error('Not available on web.');
  }

  public async linkWithTwitter(): Promise<SignInResult> {
    const auth = getAuth();
    if (!auth.currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    const provider = new OAuthProvider(SignInMethod.TWITTER);
    const userCredential = await linkWithPopup(auth.currentUser, provider);
    const authCredential = OAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async linkWithYahoo(): Promise<SignInResult> {
    const auth = getAuth();
    if (!auth.currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    const provider = new OAuthProvider(SignInMethod.YAHOO);
    const userCredential = await linkWithPopup(auth.currentUser, provider);
    const authCredential = OAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async sendEmailVerification(): Promise<void> {
    const auth = getAuth();
    const currentUser = auth.currentUser;
    if (!currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    return sendEmailVerification(currentUser);
  }

  public async sendPasswordResetEmail(
    options: SendPasswordResetEmailOptions,
  ): Promise<void> {
    const auth = getAuth();
    return sendPasswordResetEmail(auth, options.email);
  }

  public async sendSignInLinkToEmail(
    options: SendSignInLinkToEmailOptions,
  ): Promise<void> {
    const auth = getAuth();
    return sendSignInLinkToEmail(
      auth,
      options.email,
      options.actionCodeSettings,
    );
  }

  public async setLanguageCode(options: SetLanguageCodeOptions): Promise<void> {
    const auth = getAuth();
    auth.languageCode = options.languageCode;
  }

  public async signInAnonymously(): Promise<SignInResult> {
    const auth = getAuth();
    const userCredential = await signInAnonymously(auth);
    return this.createSignInResult(userCredential, null);
  }

  public async signInWithApple(options?: SignInOptions): Promise<SignInResult> {
    const provider = new OAuthProvider(SignInMethod.APPLE);
    this.applySignInOptions(options || {}, provider);
    const auth = getAuth();
    const userCredential = await signInWithPopup(auth, provider);
    const authCredential = OAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async signInWithCustomToken(
    options: SignInWithCustomTokenOptions,
  ): Promise<SignInResult> {
    const auth = getAuth();
    const userCredential = await signInWithCustomToken(auth, options.token);
    return this.createSignInResult(userCredential, null);
  }

  public async signInWithEmailAndPassword(
    options: SignInWithEmailAndPasswordOptions,
  ): Promise<SignInResult> {
    const auth = getAuth();
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
    const auth = getAuth();
    const userCredential = await signInWithEmailLink(
      auth,
      options.email,
      options.emailLink,
    );
    return this.createSignInResult(userCredential, null);
  }

  public async signInWithFacebook(
    options?: SignInOptions,
  ): Promise<SignInResult> {
    const provider = new FacebookAuthProvider();
    this.applySignInOptions(options || {}, provider);
    const auth = getAuth();
    const userCredential = await signInWithPopup(auth, provider);
    const authCredential =
      FacebookAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async signInWithGithub(
    options?: SignInOptions,
  ): Promise<SignInResult> {
    const provider = new OAuthProvider(SignInMethod.GITHUB);
    this.applySignInOptions(options || {}, provider);
    const auth = getAuth();
    const userCredential = await signInWithPopup(auth, provider);
    const authCredential = OAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async signInWithGoogle(
    options?: SignInOptions,
  ): Promise<SignInResult> {
    const provider = new GoogleAuthProvider();
    this.applySignInOptions(options || {}, provider);
    const auth = getAuth();
    const userCredential = await signInWithPopup(auth, provider);
    const authCredential =
      GoogleAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async signInWithMicrosoft(
    options?: SignInOptions,
  ): Promise<SignInResult> {
    const provider = new OAuthProvider(SignInMethod.MICROSOFT);
    this.applySignInOptions(options || {}, provider);
    const auth = getAuth();
    const userCredential = await signInWithPopup(auth, provider);
    const authCredential = OAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async signInWithPhoneNumber(
    _options: SignInWithPhoneNumberOptions,
  ): Promise<SignInResult> {
    throw new Error('Not implemented on web.');
  }

  public async signInWithPlayGames(): Promise<SignInResult> {
    throw new Error('Not available on web.');
  }

  public async signInWithTwitter(
    options?: SignInOptions,
  ): Promise<SignInResult> {
    const provider = new OAuthProvider(SignInMethod.TWITTER);
    this.applySignInOptions(options || {}, provider);
    const auth = getAuth();
    const userCredential = await signInWithPopup(auth, provider);
    const authCredential = OAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async signInWithYahoo(options?: SignInOptions): Promise<SignInResult> {
    const provider = new OAuthProvider(SignInMethod.YAHOO);
    this.applySignInOptions(options || {}, provider);
    const auth = getAuth();
    const userCredential = await signInWithPopup(auth, provider);
    const authCredential = OAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async signOut(): Promise<void> {
    const auth = getAuth();
    await auth.signOut();
  }

  public async updateEmail(options: UpdateEmailOptions): Promise<void> {
    const auth = getAuth();
    const currentUser = auth.currentUser;
    if (!currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    return updateEmail(currentUser, options.newEmail);
  }

  public async updatePassword(options: UpdatePasswordOptions): Promise<void> {
    const auth = getAuth();
    const currentUser = auth.currentUser;
    if (!currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    return updatePassword(currentUser, options.newPassword);
  }

  public async useAppLanguage(): Promise<void> {
    const auth = getAuth();
    auth.useDeviceLanguage();
  }

  public async useEmulator(options: UseEmulatorOptions): Promise<void> {
    const auth = getAuth();
    const port = options.port || 9099;
    connectAuthEmulator(auth, `${options.host}:${port}`);
  }

  private handleAuthStateChange(user: FirebaseUser | null): void {
    const userResult = this.createUserResult(user);
    const change: AuthStateChange = {
      user: userResult,
    };
    this.notifyListeners('authStateChange', change);
  }

  private applySignInOptions(
    options: SignInOptions,
    provider: OAuthProvider | GoogleAuthProvider | FacebookAuthProvider,
  ) {
    if (options.customParameters) {
      const customParameters: CustomParameters = {};
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

  private createSignInResult(
    userCredential: FirebaseUserCredential,
    authCredential: FirebaseAuthCredential | null,
  ): SignInResult {
    const userResult = this.createUserResult(userCredential.user);
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
      phoneNumber: user.phoneNumber,
      photoUrl: user.photoURL,
      providerId: user.providerId,
      tenantId: user.tenantId,
      uid: user.uid,
    };
    return result;
  }

  private createAdditionalUserInfoResult(
    credential: FirebaseUserCredential,
  ): AdditionalUserInfo | null {
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
}
