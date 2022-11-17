import { WebPlugin } from '@capacitor/core';
import type {
  AuthCredential as FirebaseAuthCredential,
  AuthProvider as FirebaseAuthProvider,
  CustomParameters as FirebaseCustomParameters,
  User as FirebaseUser,
  UserCredential as FirebaseUserCredential,
} from 'firebase/auth';
import {
  getRedirectResult,
  linkWithPopup,
  linkWithRedirect,
  signInWithPopup,
  signInWithRedirect,
  applyActionCode,
  confirmPasswordReset,
  connectAuthEmulator,
  createUserWithEmailAndPassword,
  EmailAuthProvider,
  FacebookAuthProvider,
  getAdditionalUserInfo,
  getAuth,
  GithubAuthProvider,
  GoogleAuthProvider,
  isSignInWithEmailLink,
  linkWithCredential,
  OAuthCredential,
  OAuthProvider,
  sendEmailVerification,
  sendPasswordResetEmail,
  sendSignInLinkToEmail,
  signInAnonymously,
  signInWithCustomToken,
  signInWithEmailAndPassword,
  signInWithEmailLink,
  TwitterAuthProvider,
  unlink,
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
  GetTenantIdResult,
  IsSignInWithEmailLinkOptions,
  IsSignInWithEmailLinkResult,
  LinkResult,
  LinkWithEmailAndPasswordOptions,
  LinkWithEmailLinkOptions,
  LinkWithOAuthOptions,
  LinkWithPhoneNumberOptions,
  SendPasswordResetEmailOptions,
  SendSignInLinkToEmailOptions,
  SetLanguageCodeOptions,
  SetTenantIdOptions,
  SignInOptions,
  SignInResult,
  SignInWithCustomTokenOptions,
  SignInWithEmailAndPasswordOptions,
  SignInWithEmailLinkOptions,
  SignInWithOAuthOptions,
  SignInWithPhoneNumberOptions,
  UnlinkOptions,
  UnlinkResult,
  UpdateEmailOptions,
  UpdatePasswordOptions,
  UseEmulatorOptions,
  User,
} from './definitions';
import { ProviderId } from './definitions';

export class FirebaseAuthenticationWeb
  extends WebPlugin
  implements FirebaseAuthenticationPlugin
{
  public static readonly ERROR_NO_USER_SIGNED_IN = 'No user is signed in.';

  constructor() {
    super();
    const auth = getAuth();
    auth.onAuthStateChanged(user => this.handleAuthStateChange(user));
    getRedirectResult(auth).then(result => {
      if (!result) {
        return;
      }
      this.handleAuthStateChange(result.user);
    });
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

  public async getTenantId(): Promise<GetTenantIdResult> {
    const auth = getAuth();
    return {
      tenantId: auth.tenantId,
    };
  }

  public async isSignInWithEmailLink(
    options: IsSignInWithEmailLinkOptions,
  ): Promise<IsSignInWithEmailLinkResult> {
    const auth = getAuth();
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
    throw new Error('Not available on web.');
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

  public async linkWithPhoneNumber(
    _options: LinkWithPhoneNumberOptions,
  ): Promise<LinkResult> {
    throw new Error('Not implemented on web.');
  }

  public async linkWithPlayGames(): Promise<LinkResult> {
    throw new Error('Not available on web.');
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

  public async setTenantId(options: SetTenantIdOptions): Promise<void> {
    const auth = getAuth();
    auth.tenantId = options.tenantId;
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

  public async signInWithPhoneNumber(
    _options: SignInWithPhoneNumberOptions,
  ): Promise<SignInResult> {
    throw new Error('Not implemented on web.');
  }

  public async signInWithPlayGames(): Promise<SignInResult> {
    throw new Error('Not available on web.');
  }

  public async signInWithGameCenter(): Promise<SignInResult> {
    throw new Error('Not available on web.');
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
    const auth = getAuth();
    await auth.signOut();
  }

  public async unlink(options: UnlinkOptions): Promise<UnlinkResult> {
    const auth = getAuth();
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
    const auth = getAuth();
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
    const auth = getAuth();
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
    const auth = getAuth();
    if (!auth.currentUser) {
      throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
    }
    return linkWithCredential(auth.currentUser, credential);
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
