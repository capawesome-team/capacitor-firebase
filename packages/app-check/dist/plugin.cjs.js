'use strict';

var core = require('@capacitor/core');
var app = require('firebase/app');
var appCheck = require('firebase/app-check');

const FirebaseAppCheck = core.registerPlugin('FirebaseAppCheck', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.FirebaseAppCheckWeb()),
});

class FirebaseAppCheckWeb extends core.WebPlugin {
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
        const result = await appCheck.getToken(this.appCheckInstance, options === null || options === undefined ? undefined : options.forceRefresh);
        return {
            token: result.token,
        };
    }
    async initialize(options) {
        if (options === null || options === undefined ? undefined : options.debugToken) {
            self.FIREBASE_APPCHECK_DEBUG_TOKEN = options.debugToken;
        }
        else if (options === null || options === undefined ? undefined : options.debug) {
            self.FIREBASE_APPCHECK_DEBUG_TOKEN = true;
        }
        let provider = options === null || options === undefined ? undefined : options.provider;
        if (!provider) {
            if (!(options === null || options === undefined ? undefined : options.siteKey)) {
                throw new Error(FirebaseAppCheckWeb.errorSiteKeyMissing);
            }
            const { ReCaptchaV3Provider } = await import('firebase/app-check');
            provider = new ReCaptchaV3Provider(options === null || options === undefined ? undefined : options.siteKey);
        }
        const app$1 = app.getApp();
        this.appCheckInstance = appCheck.initializeAppCheck(app$1, {
            provider,
            isTokenAutoRefreshEnabled: options === null || options === undefined ? undefined : options.isTokenAutoRefreshEnabled,
        });
    }
    async setTokenAutoRefreshEnabled(options) {
        if (!this.appCheckInstance) {
            throw new Error(FirebaseAppCheckWeb.errorNotInitialized);
        }
        appCheck.setTokenAutoRefreshEnabled(this.appCheckInstance, options.enabled);
    }
    registerOnTokenChangedListener() {
        if (!this.appCheckInstance) {
            return;
        }
        this.onTokenChangedListenerUnsubscribe = appCheck.onTokenChanged(this.appCheckInstance, (tokenResult) => this.handleTokenChanged(tokenResult.token));
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

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    FirebaseAppCheckWeb: FirebaseAppCheckWeb
});

exports.FirebaseAppCheck = FirebaseAppCheck;
//# sourceMappingURL=plugin.cjs.js.map
