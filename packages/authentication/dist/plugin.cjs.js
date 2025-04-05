'use strict';

var core = require('@capacitor/core');
var auth = require('firebase/auth');

/// <reference types="@capacitor/cli" />
/**
 * @since 5.2.0
 */
exports.Persistence = void 0;
(function (Persistence) {
    /**
     * Long term persistence using IndexedDB.
     *
     * @since 5.2.0
     */
    Persistence["IndexedDbLocal"] = "INDEXED_DB_LOCAL";
    /**
     * No persistence.
     *
     * @since 5.2.0
     */
    Persistence["InMemory"] = "IN_MEMORY";
    /**
     * Long term persistence using local storage.
     *
     * @since 5.2.0
     */
    Persistence["BrowserLocal"] = "BROWSER_LOCAL";
    /**
     * Temporary persistence using session storage.
     *
     * @since 5.2.0
     */
    Persistence["BrowserSession"] = "BROWSER_SESSION";
})(exports.Persistence || (exports.Persistence = {}));
exports.ProviderId = void 0;
(function (ProviderId) {
    ProviderId["APPLE"] = "apple.com";
    ProviderId["FACEBOOK"] = "facebook.com";
    ProviderId["GAME_CENTER"] = "gc.apple.com";
    ProviderId["GITHUB"] = "github.com";
    ProviderId["GOOGLE"] = "google.com";
    ProviderId["MICROSOFT"] = "microsoft.com";
    ProviderId["PLAY_GAMES"] = "playgames.google.com";
    ProviderId["TWITTER"] = "twitter.com";
    ProviderId["YAHOO"] = "yahoo.com";
    ProviderId["PASSWORD"] = "password";
    ProviderId["PHONE"] = "phone";
})(exports.ProviderId || (exports.ProviderId = {}));

const FirebaseAuthentication = core.registerPlugin('FirebaseAuthentication', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.FirebaseAuthenticationWeb()),
});

class FirebaseAuthenticationWeb extends core.WebPlugin {
    constructor() {
        super();
        this.lastConfirmationResult = new Map();
        const auth$1 = auth.getAuth();
        auth$1.onAuthStateChanged(user => this.handleAuthStateChange(user));
        auth$1.onIdTokenChanged(user => void this.handleIdTokenChange(user));
    }
    async applyActionCode(options) {
        const auth$1 = auth.getAuth();
        return auth.applyActionCode(auth$1, options.oobCode);
    }
    async createUserWithEmailAndPassword(options) {
        const auth$1 = auth.getAuth();
        const userCredential = await auth.createUserWithEmailAndPassword(auth$1, options.email, options.password);
        return this.createSignInResult(userCredential, null);
    }
    async confirmPasswordReset(options) {
        const auth$1 = auth.getAuth();
        return auth.confirmPasswordReset(auth$1, options.oobCode, options.newPassword);
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
        const auth$1 = auth.getAuth();
        const currentUser = auth$1.currentUser;
        if (!currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        return auth.deleteUser(currentUser);
    }
    async fetchSignInMethodsForEmail(options) {
        const auth$1 = auth.getAuth();
        const signInMethods = await auth.fetchSignInMethodsForEmail(auth$1, options.email);
        return {
            signInMethods,
        };
    }
    async getPendingAuthResult() {
        this.throwNotAvailableError();
    }
    async getCurrentUser() {
        const auth$1 = auth.getAuth();
        const userResult = this.createUserResult(auth$1.currentUser);
        const result = {
            user: userResult,
        };
        return result;
    }
    async getIdToken(options) {
        const auth$1 = auth.getAuth();
        if (!auth$1.currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        const idToken = await auth$1.currentUser.getIdToken(options === null || options === undefined ? undefined : options.forceRefresh);
        const result = {
            token: idToken || '',
        };
        return result;
    }
    async getRedirectResult() {
        const auth$1 = auth.getAuth();
        const userCredential = await auth.getRedirectResult(auth$1);
        const authCredential = userCredential
            ? auth.OAuthProvider.credentialFromResult(userCredential)
            : null;
        return this.createSignInResult(userCredential, authCredential);
    }
    async getTenantId() {
        const auth$1 = auth.getAuth();
        return {
            tenantId: auth$1.tenantId,
        };
    }
    async isSignInWithEmailLink(options) {
        const auth$1 = auth.getAuth();
        return {
            isSignInWithEmailLink: auth.isSignInWithEmailLink(auth$1, options.emailLink),
        };
    }
    async linkWithApple(options) {
        const provider = new auth.OAuthProvider(exports.ProviderId.APPLE);
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.linkCurrentUserWithPopupOrRedirect(provider, options === null || options === undefined ? undefined : options.mode);
        const authCredential = auth.OAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async linkWithEmailAndPassword(options) {
        const authCredential = auth.EmailAuthProvider.credential(options.email, options.password);
        const userCredential = await this.linkCurrentUserWithCredential(authCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async linkWithEmailLink(options) {
        const authCredential = auth.EmailAuthProvider.credentialWithLink(options.email, options.emailLink);
        const userCredential = await this.linkCurrentUserWithCredential(authCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async linkWithFacebook(options) {
        const provider = new auth.FacebookAuthProvider();
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.linkCurrentUserWithPopupOrRedirect(provider, options === null || options === undefined ? undefined : options.mode);
        const authCredential = auth.FacebookAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async linkWithGameCenter() {
        this.throwNotAvailableError();
    }
    async linkWithGithub(options) {
        const provider = new auth.GithubAuthProvider();
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.linkCurrentUserWithPopupOrRedirect(provider, options === null || options === undefined ? undefined : options.mode);
        const authCredential = auth.GithubAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async linkWithGoogle(options) {
        const provider = new auth.GoogleAuthProvider();
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.linkCurrentUserWithPopupOrRedirect(provider, options === null || options === undefined ? undefined : options.mode);
        const authCredential = auth.GoogleAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async linkWithMicrosoft(options) {
        const provider = new auth.OAuthProvider(exports.ProviderId.MICROSOFT);
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.linkCurrentUserWithPopupOrRedirect(provider, options === null || options === undefined ? undefined : options.mode);
        const authCredential = auth.OAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async linkWithOpenIdConnect(options) {
        const provider = new auth.OAuthProvider(options.providerId);
        this.applySignInOptions(options, provider);
        const userCredential = await this.linkCurrentUserWithPopupOrRedirect(provider, options.mode);
        const authCredential = auth.OAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async linkWithPhoneNumber(options) {
        const auth$1 = auth.getAuth();
        const currentUser = auth$1.currentUser;
        if (!currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        if (!options.phoneNumber) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_PHONE_NUMBER_MISSING);
        }
        if (!options.recaptchaVerifier ||
            !(options.recaptchaVerifier instanceof auth.RecaptchaVerifier)) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_RECAPTCHA_VERIFIER_MISSING);
        }
        try {
            const confirmationResult = await auth.linkWithPhoneNumber(currentUser, options.phoneNumber, options.recaptchaVerifier);
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
        const provider = new auth.TwitterAuthProvider();
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.linkCurrentUserWithPopupOrRedirect(provider, options === null || options === undefined ? undefined : options.mode);
        const authCredential = auth.TwitterAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async linkWithYahoo(options) {
        const provider = new auth.OAuthProvider(exports.ProviderId.YAHOO);
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.linkCurrentUserWithPopupOrRedirect(provider, options === null || options === undefined ? undefined : options.mode);
        const authCredential = auth.OAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async reload() {
        const auth$1 = auth.getAuth();
        const currentUser = auth$1.currentUser;
        if (!currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        return auth.reload(currentUser);
    }
    async revokeAccessToken(options) {
        const auth$1 = auth.getAuth();
        return auth.revokeAccessToken(auth$1, options.token);
    }
    async sendEmailVerification(options) {
        const auth$1 = auth.getAuth();
        const currentUser = auth$1.currentUser;
        if (!currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        return auth.sendEmailVerification(currentUser, options === null || options === undefined ? undefined : options.actionCodeSettings);
    }
    async sendPasswordResetEmail(options) {
        const auth$1 = auth.getAuth();
        return auth.sendPasswordResetEmail(auth$1, options.email, options.actionCodeSettings);
    }
    async sendSignInLinkToEmail(options) {
        const auth$1 = auth.getAuth();
        return auth.sendSignInLinkToEmail(auth$1, options.email, options.actionCodeSettings);
    }
    async setLanguageCode(options) {
        const auth$1 = auth.getAuth();
        auth$1.languageCode = options.languageCode;
    }
    async setPersistence(options) {
        const auth$1 = auth.getAuth();
        switch (options.persistence) {
            case exports.Persistence.BrowserLocal:
                await auth.setPersistence(auth$1, auth.browserLocalPersistence);
                break;
            case exports.Persistence.BrowserSession:
                await auth.setPersistence(auth$1, auth.browserSessionPersistence);
                break;
            case exports.Persistence.IndexedDbLocal:
                await auth.setPersistence(auth$1, auth.indexedDBLocalPersistence);
                break;
            case exports.Persistence.InMemory:
                await auth.setPersistence(auth$1, auth.inMemoryPersistence);
                break;
        }
    }
    async setTenantId(options) {
        const auth$1 = auth.getAuth();
        auth$1.tenantId = options.tenantId;
    }
    async signInAnonymously() {
        const auth$1 = auth.getAuth();
        const userCredential = await auth.signInAnonymously(auth$1);
        return this.createSignInResult(userCredential, null);
    }
    async signInWithApple(options) {
        const provider = new auth.OAuthProvider(exports.ProviderId.APPLE);
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.signInWithPopupOrRedirect(provider, options === null || options === undefined ? undefined : options.mode);
        const authCredential = auth.OAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async signInWithCustomToken(options) {
        const auth$1 = auth.getAuth();
        const userCredential = await auth.signInWithCustomToken(auth$1, options.token);
        return this.createSignInResult(userCredential, null);
    }
    async signInWithEmailAndPassword(options) {
        const auth$1 = auth.getAuth();
        const userCredential = await auth.signInWithEmailAndPassword(auth$1, options.email, options.password);
        return this.createSignInResult(userCredential, null);
    }
    async signInWithEmailLink(options) {
        const auth$1 = auth.getAuth();
        const userCredential = await auth.signInWithEmailLink(auth$1, options.email, options.emailLink);
        return this.createSignInResult(userCredential, null);
    }
    async signInWithFacebook(options) {
        const provider = new auth.FacebookAuthProvider();
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.signInWithPopupOrRedirect(provider, options === null || options === undefined ? undefined : options.mode);
        const authCredential = auth.FacebookAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async signInWithGithub(options) {
        const provider = new auth.GithubAuthProvider();
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.signInWithPopupOrRedirect(provider, options === null || options === undefined ? undefined : options.mode);
        const authCredential = auth.GithubAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async signInWithGoogle(options) {
        const provider = new auth.GoogleAuthProvider();
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.signInWithPopupOrRedirect(provider, options === null || options === undefined ? undefined : options.mode);
        const authCredential = auth.GoogleAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async signInWithMicrosoft(options) {
        const provider = new auth.OAuthProvider(exports.ProviderId.MICROSOFT);
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.signInWithPopupOrRedirect(provider, options === null || options === undefined ? undefined : options.mode);
        const authCredential = auth.OAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async signInWithOpenIdConnect(options) {
        const provider = new auth.OAuthProvider(options.providerId);
        this.applySignInOptions(options, provider);
        const userCredential = await this.signInWithPopupOrRedirect(provider, options.mode);
        const authCredential = auth.OAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async signInWithPhoneNumber(options) {
        if (!options.phoneNumber) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_PHONE_NUMBER_MISSING);
        }
        if (!options.recaptchaVerifier ||
            !(options.recaptchaVerifier instanceof auth.RecaptchaVerifier)) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_RECAPTCHA_VERIFIER_MISSING);
        }
        const auth$1 = auth.getAuth();
        try {
            const confirmationResult = await auth.signInWithPhoneNumber(auth$1, options.phoneNumber, options.recaptchaVerifier);
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
        const provider = new auth.TwitterAuthProvider();
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.signInWithPopupOrRedirect(provider, options === null || options === undefined ? undefined : options.mode);
        const authCredential = auth.TwitterAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async signInWithYahoo(options) {
        const provider = new auth.OAuthProvider(exports.ProviderId.YAHOO);
        this.applySignInOptions(options || {}, provider);
        const userCredential = await this.signInWithPopupOrRedirect(provider, options === null || options === undefined ? undefined : options.mode);
        const authCredential = auth.OAuthProvider.credentialFromResult(userCredential);
        return this.createSignInResult(userCredential, authCredential);
    }
    async signOut() {
        const auth$1 = auth.getAuth();
        await auth$1.signOut();
    }
    async unlink(options) {
        const auth$1 = auth.getAuth();
        if (!auth$1.currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        const user = await auth.unlink(auth$1.currentUser, options.providerId);
        const userResult = this.createUserResult(user);
        const result = {
            user: userResult,
        };
        return result;
    }
    async updateEmail(options) {
        const auth$1 = auth.getAuth();
        const currentUser = auth$1.currentUser;
        if (!currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        return auth.updateEmail(currentUser, options.newEmail);
    }
    async updatePassword(options) {
        const auth$1 = auth.getAuth();
        const currentUser = auth$1.currentUser;
        if (!currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        return auth.updatePassword(currentUser, options.newPassword);
    }
    async updateProfile(options) {
        const auth$1 = auth.getAuth();
        const currentUser = auth$1.currentUser;
        if (!currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        return auth.updateProfile(currentUser, {
            displayName: options.displayName,
            photoURL: options.photoUrl,
        });
    }
    async useAppLanguage() {
        const auth$1 = auth.getAuth();
        auth$1.useDeviceLanguage();
    }
    async useEmulator(options) {
        const auth$1 = auth.getAuth();
        const port = options.port || 9099;
        const scheme = options.scheme || 'http';
        if (options.host.includes('://')) {
            auth.connectAuthEmulator(auth$1, `${options.host}:${port}`);
        }
        else {
            auth.connectAuthEmulator(auth$1, `${scheme}://${options.host}:${port}`);
        }
    }
    async verifyBeforeUpdateEmail(options) {
        const auth$1 = auth.getAuth();
        const currentUser = auth$1.currentUser;
        if (!currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        return auth.verifyBeforeUpdateEmail(currentUser, options === null || options === undefined ? undefined : options.newEmail, options === null || options === undefined ? undefined : options.actionCodeSettings);
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
        const auth$1 = auth.getAuth();
        if (mode === 'redirect') {
            return auth.signInWithRedirect(auth$1, provider);
        }
        else {
            return auth.signInWithPopup(auth$1, provider);
        }
    }
    linkCurrentUserWithPopupOrRedirect(provider, mode) {
        const auth$1 = auth.getAuth();
        if (!auth$1.currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        if (mode === 'redirect') {
            return auth.linkWithRedirect(auth$1.currentUser, provider);
        }
        else {
            return auth.linkWithPopup(auth$1.currentUser, provider);
        }
    }
    linkCurrentUserWithCredential(credential) {
        const auth$1 = auth.getAuth();
        if (!auth$1.currentUser) {
            throw new Error(FirebaseAuthenticationWeb.ERROR_NO_USER_SIGNED_IN);
        }
        return auth.linkWithCredential(auth$1.currentUser, credential);
    }
    requestAppTrackingTransparencyPermission() {
        this.throwNotAvailableError();
    }
    checkAppTrackingTransparencyPermission() {
        this.throwNotAvailableError();
    }
    createSignInResult(userCredential, authCredential) {
        const userResult = this.createUserResult((userCredential === null || userCredential === undefined ? undefined : userCredential.user) || null);
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
        if (credential instanceof auth.OAuthCredential) {
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
        const additionalUserInfo = auth.getAdditionalUserInfo(credential);
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

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    FirebaseAuthenticationWeb: FirebaseAuthenticationWeb
});

exports.FirebaseAuthentication = FirebaseAuthentication;
//# sourceMappingURL=plugin.cjs.js.map
