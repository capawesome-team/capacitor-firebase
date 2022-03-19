import { WebPlugin } from '@capacitor/core';
import type {
  AuthCredential as FirebaseAuthCredential,
  User as FirebaseUser,
} from 'firebase/auth';
import {
  FacebookAuthProvider,
  getAuth,
  GoogleAuthProvider,
  OAuthCredential,
  OAuthProvider,
  signInWithPopup,
  signInWithCustomToken,
} from 'firebase/auth';

import type {
  AuthCredential,
  AuthStateChange,
  FirebaseAuthenticationPlugin,
  GetCurrentUserResult,
  GetIdTokenResult,
  SetLanguageCodeOptions,
  SignInResult,
  SignInWithPhoneNumberOptions,
  SignInWithCustomTokenOptions,
  User,
} from './definitions';

export class FirebaseAuthenticationWeb
  extends WebPlugin
  implements FirebaseAuthenticationPlugin {
  constructor() {
    super();
    const auth = getAuth();
    auth.onAuthStateChanged(user => this.handleAuthStateChange(user));
  }

  public async getCurrentUser(): Promise<GetCurrentUserResult> {
    const auth = getAuth();
    const userResult = this.createUserResult(auth.currentUser);
    const result: GetCurrentUserResult = {
      user: userResult,
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

  public async setLanguageCode(options: SetLanguageCodeOptions): Promise<void> {
    const auth = getAuth();
    auth.languageCode = options.languageCode;
  }

  public async signInWithApple(): Promise<SignInResult> {
    const provider = new OAuthProvider('apple.com');
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = OAuthProvider.credentialFromResult(result);
    return this.createSignInResult(result.user, credential);
  }

  public async signInWithFacebook(): Promise<SignInResult> {
    const provider = new FacebookAuthProvider();
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = FacebookAuthProvider.credentialFromResult(result);
    return this.createSignInResult(result.user, credential);
  }

  public async signInWithGithub(): Promise<SignInResult> {
    const provider = new OAuthProvider('github.com');
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = OAuthProvider.credentialFromResult(result);
    return this.createSignInResult(result.user, credential);
  }

  public async signInWithGoogle(): Promise<SignInResult> {
    const provider = new GoogleAuthProvider();
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = GoogleAuthProvider.credentialFromResult(result);
    return this.createSignInResult(result.user, credential);
  }

  public async signInWithMicrosoft(): Promise<SignInResult> {
    const provider = new OAuthProvider('microsoft.com');
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = OAuthProvider.credentialFromResult(result);
    return this.createSignInResult(result.user, credential);
  }

  public async signInWithPlayGames(): Promise<SignInResult> {
    throw new Error('Not available on web.');
  }

  public async signInWithTwitter(): Promise<SignInResult> {
    const provider = new OAuthProvider('twitter.com');
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = OAuthProvider.credentialFromResult(result);
    return this.createSignInResult(result.user, credential);
  }

  public async signInWithYahoo(): Promise<SignInResult> {
    const provider = new OAuthProvider('yahoo.com');
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = OAuthProvider.credentialFromResult(result);
    return this.createSignInResult(result.user, credential);
  }

  public async signInWithPhoneNumber(
    _options: SignInWithPhoneNumberOptions,
  ): Promise<SignInResult> {
    throw new Error('Not implemented on web.');
  }

  public async signInWithCustomToken(
    options: SignInWithCustomTokenOptions,
  ): Promise<SignInResult> {
    const auth = getAuth();
    const result = await signInWithCustomToken(auth, options.token);
    return this.createSignInResult(result.user, null);
  }

  public async signOut(): Promise<void> {
    const auth = getAuth();
    await auth.signOut();
  }

  public async useAppLanguage(): Promise<void> {
    const auth = getAuth();
    auth.useDeviceLanguage();
  }

  private handleAuthStateChange(user: FirebaseUser | null): void {
    const userResult = this.createUserResult(user);
    const change: AuthStateChange = {
      user: userResult,
    };
    this.notifyListeners('authStateChange', change);
  }

  private createSignInResult(
    user: FirebaseUser,
    credential: FirebaseAuthCredential | null,
  ): SignInResult {
    const userResult = this.createUserResult(user);
    const credentialResult = this.createCredentialResult(credential);
    const result: SignInResult = {
      user: userResult,
      credential: credentialResult,
    };
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
