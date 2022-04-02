import { WebPlugin } from '@capacitor/core';
import type {
  AuthCredential as FirebaseAuthCredential,
  User,
  UserCredential,
} from 'firebase/auth';
import {
  applyActionCode,
  confirmPasswordReset,
  createUserWithEmailAndPassword,
  sendEmailVerification,
  sendPasswordResetEmail,
  signInWithEmailAndPassword,
  updateEmail,
  updatePassword,
  connectAuthEmulator,
  FacebookAuthProvider,
  getAuth,
  GoogleAuthProvider,
  OAuthCredential,
  OAuthProvider,
  signInWithCustomToken,
  signInWithPopup,
} from 'firebase/auth';

import type {
  ApplyActionCodeOptions,
  AuthCredential,
  AuthStateChange,
  ConfirmPasswordResetOptions,
  CreateUserWithEmailAndPasswordOptions,
  FirebaseAuthenticationPlugin,
  GetCurrentUserResult,
  GetIdTokenResult,
  SendEmailVerificationOptions,
  SendPasswordResetEmailOptions,
  SetLanguageCodeOptions,
  SignInResult,
  SignInWithCustomTokenOptions,
  SignInWithEmailAndPasswordOptions,
  SignInWithPhoneNumberOptions,
  UpdateEmailOptions,
  UpdatePasswordOptions,
  UseEmulatorOptions,
} from './definitions';

export class FirebaseAuthenticationWeb
  extends WebPlugin
  implements FirebaseAuthenticationPlugin {
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
    const credential = await createUserWithEmailAndPassword(
      auth,
      options.email,
      options.password,
    );
    return this.createSignInResultFromUserCredential(credential);
  }

  public async confirmPasswordReset(
    options: ConfirmPasswordResetOptions,
  ): Promise<void> {
    const auth = getAuth();
    return confirmPasswordReset(auth, options.oobCode, options.newPassword);
  }

  public async getCurrentUser(): Promise<GetCurrentUserResult> {
    const auth = getAuth();
    const result: GetCurrentUserResult = {
      user: auth.currentUser,
    };
    return result;
  }

  public async getIdToken(): Promise<GetIdTokenResult> {
    const auth = getAuth();
    const idToken = await auth.currentUser?.getIdToken();
    const result: GetIdTokenResult = {
      token: idToken || '',
    };
    return result;
  }

  public async sendEmailVerification(
    options: SendEmailVerificationOptions,
  ): Promise<void> {
    return sendEmailVerification(options.user, options.actionCodeSettings);
  }

  public async sendPasswordResetEmail(
    options: SendPasswordResetEmailOptions,
  ): Promise<void> {
    const auth = getAuth();
    return sendPasswordResetEmail(
      auth,
      options.email,
      options.actionCodeSettings,
    );
  }

  public async setLanguageCode(options: SetLanguageCodeOptions): Promise<void> {
    const auth = getAuth();
    auth.languageCode = options.languageCode;
  }

  public async signInWithApple(): Promise<SignInResult> {
    const provider = new OAuthProvider('apple.com');
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = OAuthProvider.credentialFromResult(result);
    return this.createSignInResultFromAuthCredential(result.user, credential);
  }

  public async signInWithCustomToken(
    options: SignInWithCustomTokenOptions,
  ): Promise<SignInResult> {
    const auth = getAuth();
    const result = await signInWithCustomToken(auth, options.token);
    return this.createSignInResultFromAuthCredential(result.user, null);
  }

  public async signInWithEmailAndPassword(
    options: SignInWithEmailAndPasswordOptions,
  ): Promise<SignInResult> {
    const auth = getAuth();
    const credential = await signInWithEmailAndPassword(
      auth,
      options.email,
      options.password,
    );
    return this.createSignInResultFromUserCredential(credential);
  }

  public async signInWithFacebook(): Promise<SignInResult> {
    const provider = new FacebookAuthProvider();
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = FacebookAuthProvider.credentialFromResult(result);
    return this.createSignInResultFromAuthCredential(result.user, credential);
  }

  public async signInWithGithub(): Promise<SignInResult> {
    const provider = new OAuthProvider('github.com');
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = OAuthProvider.credentialFromResult(result);
    return this.createSignInResultFromAuthCredential(result.user, credential);
  }

  public async signInWithGoogle(): Promise<SignInResult> {
    const provider = new GoogleAuthProvider();
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = GoogleAuthProvider.credentialFromResult(result);
    return this.createSignInResultFromAuthCredential(result.user, credential);
  }

  public async signInWithMicrosoft(): Promise<SignInResult> {
    const provider = new OAuthProvider('microsoft.com');
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = OAuthProvider.credentialFromResult(result);
    return this.createSignInResultFromAuthCredential(result.user, credential);
  }

  public async signInWithPhoneNumber(
    _options: SignInWithPhoneNumberOptions,
  ): Promise<SignInResult> {
    throw new Error('Not implemented on web.');
  }

  public async signInWithPlayGames(): Promise<SignInResult> {
    throw new Error('Not available on web.');
  }

  public async signInWithTwitter(): Promise<SignInResult> {
    const provider = new OAuthProvider('twitter.com');
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = OAuthProvider.credentialFromResult(result);
    return this.createSignInResultFromAuthCredential(result.user, credential);
  }

  public async signInWithYahoo(): Promise<SignInResult> {
    const provider = new OAuthProvider('yahoo.com');
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = OAuthProvider.credentialFromResult(result);
    return this.createSignInResultFromAuthCredential(result.user, credential);
  }

  public async signOut(): Promise<void> {
    const auth = getAuth();
    await auth.signOut();
  }

  public async updateEmail(options: UpdateEmailOptions): Promise<void> {
    return updateEmail(options.user, options.newEmail);
  }

  public async updatePassword(options: UpdatePasswordOptions): Promise<void> {
    return updatePassword(options.user, options.newPassword);
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

  private handleAuthStateChange(user: User | null): void {
    const change: AuthStateChange = {
      user,
    };
    this.notifyListeners('authStateChange', change);
  }

  private createSignInResultFromAuthCredential(
    user: User,
    credential: FirebaseAuthCredential | null,
  ): SignInResult {
    const credentialResult = this.createCredentialResult(credential);
    const result: SignInResult = {
      user,
      credential: credentialResult,
    };
    return result;
  }

  private createSignInResultFromUserCredential(
    credential: UserCredential,
  ): SignInResult {
    const result: SignInResult = {
      user: credential.user,
      credential: null,
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
}
