import { WebPlugin } from '@capacitor/core';
import type {
  AuthCredential as FirebaseAuthCredential,
  User as FirebaseUser,
} from 'firebase/auth';
import {
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
  AuthCredential,
  AuthStateChange,
  FirebaseAuthenticationPlugin,
  GetCurrentUserResult,
  GetIdTokenResult,
  SetLanguageCodeOptions,
  SignInOptions,
  SignInResult,
  SignInWithCustomTokenOptions,
  SignInWithPhoneNumberOptions,
  UseEmulatorOptions,
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

  // Attach scopes to provider if they're there.
  // Takes in either a string describing an OAuthProvider, or specific other providers.
  private createProviderWithScopes(provider: string | GoogleAuthProvider | FacebookAuthProvider, options?: SignInOptions) {
    if (typeof provider === "string") provider = new OAuthProvider(provider);
    if (options?.scopes) {
      for (let scope of options.scopes) {
        provider.addScope(scope);
      }
    }
    return provider;
  }

  public async signInWithApple(options?: SignInOptions): Promise<SignInResult> {
    const provider = this.createProviderWithScopes('apple.com', options);
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = OAuthProvider.credentialFromResult(result);
    return this.createSignInResult(result.user, credential);
  }

  public async signInWithFacebook(options?: SignInOptions): Promise<SignInResult> {
    const provider = this.createProviderWithScopes(new FacebookAuthProvider(), options);
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = FacebookAuthProvider.credentialFromResult(result);
    return this.createSignInResult(result.user, credential);
  }

  public async signInWithGithub(options?: SignInOptions): Promise<SignInResult> {
    const provider = this.createProviderWithScopes('github.com', options);
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = OAuthProvider.credentialFromResult(result);
    return this.createSignInResult(result.user, credential);
  }

  public async signInWithGoogle(options?: SignInOptions): Promise<SignInResult> {
    const provider = this.createProviderWithScopes(new GoogleAuthProvider(), options);
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = GoogleAuthProvider.credentialFromResult(result);
    return this.createSignInResult(result.user, credential);
  }

  public async signInWithMicrosoft(options?: SignInOptions): Promise<SignInResult> {
    const provider = this.createProviderWithScopes('microsoft.com', options);
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = OAuthProvider.credentialFromResult(result);
    return this.createSignInResult(result.user, credential);
  }

  public async signInWithPlayGames(): Promise<SignInResult> {
    throw new Error('Not available on web.');
  }

  public async signInWithTwitter(options?: SignInOptions): Promise<SignInResult> {
    const provider = this.createProviderWithScopes('twitter.com', options);
    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);
    const credential = OAuthProvider.credentialFromResult(result);
    return this.createSignInResult(result.user, credential);
  }

  public async signInWithYahoo(options?: SignInOptions): Promise<SignInResult> {
    const provider = this.createProviderWithScopes('yahoo.com', options);
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
