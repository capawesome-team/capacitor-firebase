import { WebPlugin } from '@capacitor/core';
import { getApp } from 'firebase/app';
import { getToken, initializeAppCheck, onTokenChanged, setTokenAutoRefreshEnabled, } from 'firebase/app-check';
export class FirebaseAppCheckWeb extends WebPlugin {
    get appCheckInstance() {
        return this._appCheckInstance;
    }
    set appCheckInstance(value) {
        this._appCheckInstance = value;
        if (value) {
            this.registerOnTokenChangedListener();
        }
        else {
            this.unregisterOnTokenChangedListener();
        }
    }
    async getToken(options) {
        if (!this.appCheckInstance) {
            throw new Error(FirebaseAppCheckWeb.errorNotInitialized);
        }
        const result = await getToken(this.appCheckInstance, options === null || options === void 0 ? void 0 : options.forceRefresh);
        return {
            token: result.token,
        };
    }
    async initialize(options) {
        if (options === null || options === void 0 ? void 0 : options.debugToken) {
            self.FIREBASE_APPCHECK_DEBUG_TOKEN = options.debugToken;
        }
        else if (options === null || options === void 0 ? void 0 : options.debug) {
            self.FIREBASE_APPCHECK_DEBUG_TOKEN = true;
        }
        let provider = options === null || options === void 0 ? void 0 : options.provider;
        if (!provider) {
            if (!(options === null || options === void 0 ? void 0 : options.siteKey)) {
                throw new Error(FirebaseAppCheckWeb.errorSiteKeyMissing);
            }
            const { ReCaptchaV3Provider } = await import('firebase/app-check');
            provider = new ReCaptchaV3Provider(options === null || options === void 0 ? void 0 : options.siteKey);
        }
        const app = getApp();
        this.appCheckInstance = initializeAppCheck(app, {
            provider,
            isTokenAutoRefreshEnabled: options === null || options === void 0 ? void 0 : options.isTokenAutoRefreshEnabled,
        });
    }
    async setTokenAutoRefreshEnabled(options) {
        if (!this.appCheckInstance) {
            throw new Error(FirebaseAppCheckWeb.errorNotInitialized);
        }
        setTokenAutoRefreshEnabled(this.appCheckInstance, options.enabled);
    }
    registerOnTokenChangedListener() {
        if (!this.appCheckInstance) {
            return;
        }
        this.onTokenChangedListenerUnsubscribe = onTokenChanged(this.appCheckInstance, (tokenResult) => this.handleTokenChanged(tokenResult.token));
    }
    unregisterOnTokenChangedListener() {
        if (this.onTokenChangedListenerUnsubscribe) {
            this.onTokenChangedListenerUnsubscribe();
        }
    }
    handleTokenChanged(token) {
        const event = {
            token,
        };
        this.notifyListeners(FirebaseAppCheckWeb.tokenChangedEvent, event);
    }
}
FirebaseAppCheckWeb.tokenChangedEvent = 'tokenChanged';
FirebaseAppCheckWeb.errorNotInitialized = 'AppCheck has not been initialized.';
FirebaseAppCheckWeb.errorSiteKeyMissing = 'siteKey must be provided.';
//# sourceMappingURL=web.js.map