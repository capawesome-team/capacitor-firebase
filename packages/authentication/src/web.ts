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
  FacebookAuthProvider,
  getAdditionalUserInfo,
  getAuth,
  GoogleAuthProvider,
  OAuthCredential,
  OAuthProvider,
  sendEmailVerification,
  sendPasswordResetEmail,
  signInWithCustomToken,
  signInWithEmailAndPassword,
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
  SendPasswordResetEmailOptions,
  SetLanguageCodeOptions,
  SignInOptions,
  SignInResult,
  SignInWithCustomTokenOptions,
  SignInWithEmailAndPasswordOptions,
  SignInWithPhoneNumberOptions,
  UpdateEmailOptions,
  UpdatePasswordOptions,
  UseEmulatorOptions,
  User,
} from './definitions';

export class FirebaseAuthenticationWeb
  extends WebPlugin
  implements FirebaseAuthenticationPlugin {
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

  public async setLanguageCode(options: SetLanguageCodeOptions): Promise<void> {
    const auth = getAuth();
    auth.languageCode = options.languageCode;
  }

  public async signInWithApple(options?: SignInOptions): Promise<SignInResult> {
    const provider = new OAuthProvider('apple.com');
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

  public async signInWithFacebook(
    options?: SignInOptions,
  ): Promise<SignInResult> {
    const provider = new FacebookAuthProvider();
    this.applySignInOptions(options || {}, provider);
    const auth = getAuth();
    const userCredential = await signInWithPopup(auth, provider);
    const authCredential = FacebookAuthProvider.credentialFromResult(
      userCredential,
    );
    return this.createSignInResult(userCredential, authCredential);
  }

  public async signInWithGithub(
    options?: SignInOptions,
  ): Promise<SignInResult> {
    const provider = new OAuthProvider('github.com');
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
    const authCredential = GoogleAuthProvider.credentialFromResult(
      userCredential,
    );
    return this.createSignInResult(userCredential, authCredential);
  }

  public async signInWithMicrosoft(
    options?: SignInOptions,
  ): Promise<SignInResult> {
    const provider = new OAuthProvider('microsoft.com');
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
    const provider = new OAuthProvider('twitter.com');
    this.applySignInOptions(options || {}, provider);
    const auth = getAuth();
    const userCredential = await signInWithPopup(auth, provider);
    const authCredential = OAuthProvider.credentialFromResult(userCredential);
    return this.createSignInResult(userCredential, authCredential);
  }

  public async signInWithYahoo(options?: SignInOptions): Promise<SignInResult> {
    const provider = new OAuthProvider('yahoo.com');
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
    const additionalUserInfoResult = this.createAdditionalUserInfoResult(
      userCredential,
    );
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
