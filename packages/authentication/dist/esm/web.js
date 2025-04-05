import { WebPlugin } from '@capacitor/core';
import { EmailAuthProvider, FacebookAuthProvider, GithubAuthProvider, GoogleAuthProvider, OAuthCredential, OAuthProvider, RecaptchaVerifier, TwitterAuthProvider, applyActionCode, browserLocalPersistence, browserSessionPersistence, confirmPasswordReset, connectAuthEmulator, createUserWithEmailAndPassword, deleteUser, fetchSignInMethodsForEmail, getAdditionalUserInfo, getAuth, getRedirectResult, inMemoryPersistence, indexedDBLocalPersistence, isSignInWithEmailLink, linkWithCredential, linkWithPhoneNumber, linkWithPopup, linkWithRedirect, reload, revokeAccessToken, sendEmailVerification, sendPasswordResetEmail, sendSignInLinkToEmail, setPersistence, signInAnonymously, signInWithCustomToken, signInWithEmailAndPassword, signInWithEmailLink, signInWithPhoneNumber, signInWithPopup, signInWithRedirect, unlink, updateEmail, updatePassword, updateProfile, verifyBeforeUpdateEmail, } from 'firebase/auth';
import { Persistence, ProviderId } from './definitions';
export class FirebaseAuthenticationWeb extends WebPlugin {
    constructor() {
        super();
        this.lastConfirmationResult = new Map();
        const auth = getAuth();
        auth.onAuthStateChanged(user => this.handleAuthStateChange(user));
        auth.onIdTokenChanged(user => void this.handleIdTokenChange(user));
    }
    async applyActionCode(options) {
        const auth = getAuth();
        return applyActionCode(auth, options.oobCode);
    }
    async createUserWithEmailAndPassword(options) {
        const auth = getAuth();
        const userCredential = await createUserWithEmailAndPassword(auth, options.email, options.password);
        return this.createSignInResult(userCredential, null);
    }
    async confirmPasswordReset(options) {
        const auth = getAuth();
        return confirmPasswordReset(auth, options.oobCode, options.newPassword);
    }
    async confirmVerificationCode(options) {
        const { verificationCode, verificationId } = options;
        const confirmationResult = this.lastConfirmationResult.get(verificationId);
        if (!confirmationResult) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_CONFIRMATION_RESULT_MISSING);
        }
        const userCredential = await confirmationResult.confirm(verificationCode);
        return this.createSignInResult(userCredential, null);
    }
    async deleteUser() {
        const auth = getAuth();
        const currentUser = auth.currentUser;
        if (!currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        return deleteUser(currentUser);
    }
    async fetchSignInMethodsForEmail(options) {
        const auth = getAuth();
        const signInMethods = await fetchSignInMethodsForEmail(auth, options.email);
        return {
            signInMethods,
        };
    }
    async getPendingAuthResult() {
        this.throwNotAvailableError();
    }
    async getCurrentUser() {
        const auth = getAuth();
        const userResult = this.createUserResult(auth.currentUser);
        const result = {
            user: userResult,
        };
        return result;
    }
    async getIdToken(options) {
        const auth = getAuth();
        if (!auth.currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        const idToken = await auth.currentUser.getIdToken(options === null || options === void 0 ? void 0 : options.forceRefresh);
        const result = {
            token: idToken || '',
        };
        return result;
    }
    async getRedirectResult() {
        const auth = getAuth();
        const userCredential = await getRedirectResult(auth);
        const authCredential = userCredential
            ? OAuthProvider.credentialFromResult(userCredential)
            : null;
        return this.createSignInResult(userCredential, authCredential);
    }
    async getTenantId() {
        const auth = getAuth();
        return {
            tenantId: auth.tenantId,
        };
    }
    async isSignInWithEmailLink(options) {
        const auth = getAuth();
        return {
            isSignInWithEmailLink: isSignInWithEmailLink(auth, options.emailLink),
        };
    }
    async linkWithApple(options) {
        const provider = new OAuthProvider(ProviderId.APPLE);
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.linkCurrentUserWithPopupOrRedirect(provider, options === null || options === void 0 ? void 0 : options.mode);
        const authCredential = OAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async linkWithEmailAndPassword(options) {
        const authCredential = EmailAuthProvider.credential(options.email, options.password);
        const userCredential = await this.linkCurrentUserWithCredential(authCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async linkWithEmailLink(options) {
        const authCredential = EmailAuthProvider.credentialWithLink(options.email, options.emailLink);
        const userCredential = await this.linkCurrentUserWithCredential(authCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async linkWithFacebook(options) {
        const provider = new FacebookAuthProvider();
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.linkCurrentUserWithPopupOrRedirect(provider, options === null || options === void 0 ? void 0 : options.mode);
        const authCredential = FacebookAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async linkWithGameCenter() {
        this.throwNotAvailableError();
    }
    async linkWithGithub(options) {
        const provider = new GithubAuthProvider();
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.linkCurrentUserWithPopupOrRedirect(provider, options === null || options === void 0 ? void 0 : options.mode);
        const authCredential = GithubAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async linkWithGoogle(options) {
        const provider = new GoogleAuthProvider();
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.linkCurrentUserWithPopupOrRedirect(provider, options === null || options === void 0 ? void 0 : options.mode);
        const authCredential = GoogleAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async linkWithMicrosoft(options) {
        const provider = new OAuthProvider(ProviderId.MICROSOFT);
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.linkCurrentUserWithPopupOrRedirect(provider, options === null || options === void 0 ? void 0 : options.mode);
        const authCredential = OAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async linkWithOpenIdConnect(options) {
        const provider = new OAuthProvider(options.providerId);
        this.applySignInOptions(options, provider);
        const userCredential = await this.linkCurrentUserWithPopupOrRedirect(provider, options.mode);
        const authCredential = OAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async linkWithPhoneNumber(options) {
        const auth = getAuth();
        const currentUser = auth.currentUser;
        if (!currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        if (!options.phoneNumber) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_PHONE_NUMBER_MISSING);
        }
        if (!options.recaptchaVerifier ||
            !(options.recaptchaVerifier instanceof RecaptchaVerifier)) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_RECAPTCHA_VERIFIER_MISSING);
        }
        try {
            const confirmationResult = await linkWithPhoneNumber(currentUser, options.phoneNumber, options.recaptchaVerifier);
            const { verificationId } = confirmationResult;
            this.lastConfirmationResult.set(verificationId, confirmationResult);
            const event = {
                verificationId,
            };
            this.notifyListeners(FirebaseAuthenticationWeb.PHONE_CODE_SENT_EVENT, event);
        }
        catch (error) {
            const event = {
                message: this.getErrorMessage(error),
            };
            this.notifyListeners(FirebaseAuthenticationWeb.PHONE_VERIFICATION_FAILED_EVENT, event);
        }
    }
    async linkWithPlayGames() {
        this.throwNotAvailableError();
    }
    async linkWithTwitter(options) {
        const provider = new TwitterAuthProvider();
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.linkCurrentUserWithPopupOrRedirect(provider, options === null || options === void 0 ? void 0 : options.mode);
        const authCredential = TwitterAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async linkWithYahoo(options) {
        const provider = new OAuthProvider(ProviderId.YAHOO);
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.linkCurrentUserWithPopupOrRedirect(provider, options === null || options === void 0 ? void 0 : options.mode);
        const authCredential = OAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async reload() {
        const auth = getAuth();
        const currentUser = auth.currentUser;
        if (!currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        return reload(currentUser);
    }
    async revokeAccessToken(options) {
        const auth = getAuth();
        return revokeAccessToken(auth, options.token);
    }
    async sendEmailVerification(options) {
        const auth = getAuth();
        const currentUser = auth.currentUser;
        if (!currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        return sendEmailVerification(currentUser, options === null || options === void 0 ? void 0 : options.actionCodeSettings);
    }
    async sendPasswordResetEmail(options) {
        const auth = getAuth();
        return sendPasswordResetEmail(auth, options.email, options.actionCodeSettings);
    }
    async sendSignInLinkToEmail(options) {
        const auth = getAuth();
        return sendSignInLinkToEmail(auth, options.email, options.actionCodeSettings);
    }
    async setLanguageCode(options) {
        const auth = getAuth();
        auth.languageCode = options.languageCode;
    }
    async setPersistence(options) {
        const auth = getAuth();
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
    async setTenantId(options) {
        const auth = getAuth();
        auth.tenantId = options.tenantId;
    }
    async signInAnonymously() {
        const auth = getAuth();
        const userCredential = await signInAnonymously(auth);
        return this.createSignInResult(userCredential, null);
    }
    async signInWithApple(options) {
        const provider = new OAuthProvider(ProviderId.APPLE);
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.signInWithPopupOrRedirect(provider, options === null || options === void 0 ? void 0 : options.mode);
        const authCredential = OAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async signInWithCustomToken(options) {
        const auth = getAuth();
        const userCredential = await signInWithCustomToken(auth, options.token);
        return this.createSignInResult(userCredential, null);
    }
    async signInWithEmailAndPassword(options) {
        const auth = getAuth();
        const userCredential = await signInWithEmailAndPassword(auth, options.email, options.password);
        return this.createSignInResult(userCredential, null);
    }
    async signInWithEmailLink(options) {
        const auth = getAuth();
        const userCredential = await signInWithEmailLink(auth, options.email, options.emailLink);
        return this.createSignInResult(userCredential, null);
    }
    async signInWithFacebook(options) {
        const provider = new FacebookAuthProvider();
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.signInWithPopupOrRedirect(provider, options === null || options === void 0 ? void 0 : options.mode);
        const authCredential = FacebookAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async signInWithGithub(options) {
        const provider = new GithubAuthProvider();
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.signInWithPopupOrRedirect(provider, options === null || options === void 0 ? void 0 : options.mode);
        const authCredential = GithubAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async signInWithGoogle(options) {
        const provider = new GoogleAuthProvider();
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.signInWithPopupOrRedirect(provider, options === null || options === void 0 ? void 0 : options.mode);
        const authCredential = GoogleAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async signInWithMicrosoft(options) {
        const provider = new OAuthProvider(ProviderId.MICROSOFT);
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.signInWithPopupOrRedirect(provider, options === null || options === void 0 ? void 0 : options.mode);
        const authCredential = OAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async signInWithOpenIdConnect(options) {
        const provider = new OAuthProvider(options.providerId);
        this.applySignInOptions(options, provider);
        const userCredential = await this.signInWithPopupOrRedirect(provider, options.mode);
        const authCredential = OAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async signInWithPhoneNumber(options) {
        if (!options.phoneNumber) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_PHONE_NUMBER_MISSING);
        }
        if (!options.recaptchaVerifier ||
            !(options.recaptchaVerifier instanceof RecaptchaVerifier)) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_RECAPTCHA_VERIFIER_MISSING);
        }
        const auth = getAuth();
        try {
            const confirmationResult = await signInWithPhoneNumber(auth, options.phoneNumber, options.recaptchaVerifier);
            const { verificationId } = confirmationResult;
            this.lastConfirmationResult.set(verificationId, confirmationResult);
            const event = {
                verificationId,
            };
            this.notifyListeners(FirebaseAuthenticationWeb.PHONE_CODE_SENT_EVENT, event);
        }
        catch (error) {
            const event = {
                message: this.getErrorMessage(error),
            };
            this.notifyListeners(FirebaseAuthenticationWeb.PHONE_VERIFICATION_FAILED_EVENT, event);
        }
    }
    async signInWithPlayGames() {
        this.throwNotAvailableError();
    }
    async signInWithGameCenter() {
        this.throwNotAvailableError();
    }
    async signInWithTwitter(options) {
        const provider = new TwitterAuthProvider();
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.signInWithPopupOrRedirect(provider, options === null || options === void 0 ? void 0 : options.mode);
        const authCredential = TwitterAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async signInWithYahoo(options) {
        const provider = new OAuthProvider(ProviderId.YAHOO);
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.signInWithPopupOrRedirect(provider, options === null || options === void 0 ? void 0 : options.mode);
        const authCredential = OAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async signOut() {
        const auth = getAuth();
        await auth.signOut();
    }
    async unlink(options) {
        const auth = getAuth();
        if (!auth.currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        const user = await unlink(auth.currentUser, options.providerId);
        const userResult = this.createUserResult(user);
        const result = {
            user: userResult,
        };
        return result;
    }
    async updateEmail(options) {
        const auth = getAuth();
        const currentUser = auth.currentUser;
        if (!currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        return updateEmail(currentUser, options.newEmail);
    }
    async updatePassword(options) {
        const auth = getAuth();
        const currentUser = auth.currentUser;
        if (!currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        return updatePassword(currentUser, options.newPassword);
    }
    async updateProfile(options) {
        const auth = getAuth();
        const currentUser = auth.currentUser;
        if (!currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        return updateProfile(currentUser, {
            displayName: options.displayName,
            photoURL: options.photoUrl,
        });
    }
    async useAppLanguage() {
        const auth = getAuth();
        auth.useDeviceLanguage();
    }
    async useEmulator(options) {
        const auth = getAuth();
        const port = options.port || 9099;
        const scheme = options.scheme || 'http';
        if (options.host.includes('://')) {
            connectAuthEmulator(auth, `${options.host}:${port}`);
        }
        else {
            connectAuthEmulator(auth, `${scheme}://${options.host}:${port}`);
        }
    }
    async verifyBeforeUpdateEmail(options) {
        const auth = getAuth();
        const currentUser = auth.currentUser;
        if (!currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        return verifyBeforeUpdateEmail(currentUser, options === null || options === void 0 ? void 0 : options.newEmail, options === null || options === void 0 ? void 0 : options.actionCodeSettings);
    }
    handleAuthStateChange(user) {
        const userResult = this.createUserResult(user);
        const change = {
            user: userResult,
        };
        this.notifyListeners(FirebaseAuthenticationWeb.AUTH_STATE_CHANGE_EVENT, change, true);
    }
    async handleIdTokenChange(user) {
        if (!user) {
            return;
        }
        const idToken = await user.getIdToken(false);
        const result = {
            token: idToken,
        };
        this.notifyListeners(FirebaseAuthenticationWeb.ID_TOKEN_CHANGE_EVENT, result, true);
    }
    applySignInOptions(options, provider) {
        if (options.customParameters) {
            const customParameters = {};
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
    signInWithPopupOrRedirect(provider, mode) {
        const auth = getAuth();
        if (mode === 'redirect') {
            return signInWithRedirect(auth, provider);
        }
        else {
            return signInWithPopup(auth, provider);
        }
    }
    linkCurrentUserWithPopupOrRedirect(provider, mode) {
        const auth = getAuth();
        if (!auth.currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        if (mode === 'redirect') {
            return linkWithRedirect(auth.currentUser, provider);
        }
        else {
            return linkWithPopup(auth.currentUser, provider);
        }
    }
    linkCurrentUserWithCredential(credential) {
        const auth = getAuth();
        if (!auth.currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        return linkWithCredential(auth.currentUser, credential);
    }
    requestAppTrackingTransparencyPermission() {
        this.throwNotAvailableError();
    }
    checkAppTrackingTransparencyPermission() {
        this.throwNotAvailableError();
    }
    createSignInResult(userCredential, authCredential) {
        const userResult = this.createUserResult((userCredential === null || userCredential === void 0 ? void 0 : userCredential.user) || null);
        const credentialResult = this.createCredentialResult(authCredential);
        const additionalUserInfoResult = this.createAdditionalUserInfoResult(userCredential);
        const result = {
            user: userResult,
            credential: credentialResult,
            additionalUserInfo: additionalUserInfoResult,
        };
        return result;
    }
    createCredentialResult(credential) {
        if (!credential) {
            return null;
        }
        const result = {
            providerId: credential.providerId,
        };
        if (credential instanceof OAuthCredential) {
            result.accessToken = credential.accessToken;
            result.idToken = credential.idToken;
            result.secret = credential.secret;
        }
        return result;
    }
    createUserResult(user) {
        if (!user) {
            return null;
        }
        const result = {
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
    createUserMetadataResult(metadata) {
        const result = {};
        if (metadata.creationTime) {
            result.creationTime = Date.parse(metadata.creationTime);
        }
        if (metadata.lastSignInTime) {
            result.lastSignInTime = Date.parse(metadata.lastSignInTime);
        }
        return result;
    }
    createUserProviderDataResult(providerData) {
        return providerData.map(data => ({
            displayName: data.displayName,
            email: data.email,
            phoneNumber: data.phoneNumber,
            photoUrl: data.photoURL,
            providerId: data.providerId,
            uid: data.uid,
        }));
    }
    createAdditionalUserInfoResult(credential) {
        if (!credential) {
            return null;
        }
        const additionalUserInfo = getAdditionalUserInfo(credential);
        if (!additionalUserInfo) {
            return null;
        }
        const { isNewUser, profile, providerId, username } = additionalUserInfo;
        const result = {
            isNewUser,
        };
        if (providerId !== null) {
            result.providerId = providerId;
        }
        if (profile !== null) {
            result.profile = profile;
        }
        if (username !== null && username !== undefined) {
            result.username = username;
        }
        return result;
    }
    getErrorMessage(error) {
        if (error instanceof Object &&
            'message' in error &&
            typeof error['message'] === 'string') {
            return error['message'];
        }
        return JSON.stringify(error);
    }
    throwNotAvailableError() {
        throw new Error('Not available on web.');
    }
}
FirebaseAuthenticationWeb.AUTH_STATE_CHANGE_EVENT = 'authStateChange';
FirebaseAuthenticationWeb.ID_TOKEN_CHANGE_EVENT = 'idTokenChange';
FirebaseAuthenticationWeb.PHONE_CODE_SENT_EVENT = 'phoneCodeSent';
FirebaseAuthenticationWeb.PHONE_VERIFICATION_FAILED_EVENT = 'phoneVerificationFailed';
FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN = 'No user is signed in.';
FirebaseAuthenticationWeb.ERROR_PHONE_NUMBER_MISSING = 'phoneNumber must be provided.';
FirebaseAuthenticationWeb.ERROR_RECAPTCHA_VERIFIER_MISSING = 'recaptchaVerifier must be provided and must be an instance of RecaptchaVerifier.';
FirebaseAuthenticationWeb.ERROR_CONFIRMATION_RESULT_MISSING = 'No confirmation result with this verification id was found.';
//# sourceMappingURL=web.js.map