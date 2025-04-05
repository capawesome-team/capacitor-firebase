var capacitorFirebaseAnalytics = (function (exports, core) {
    'use strict';

    /**
     * @since 6.0.0
     */
    exports.ConsentType = void 0;
    (function (ConsentType) {
        /**
         * @since 6.0.0
         */
        ConsentType["AdPersonalization"] = "AD_PERSONALIZATION";
        /**
         * @since 6.0.0
         */
        ConsentType["AdStorage"] = "AD_STORAGE";
        /**
         * @since 6.0.0
         */
        ConsentType["AdUserData"] = "AD_USER_DATA";
        /**
         * @since 6.0.0
         */
        ConsentType["AnalyticsStorage"] = "ANALYTICS_STORAGE";
        /**
         * @since 6.0.0
         */
        ConsentType["FunctionalityStorage"] = "FUNCTIONALITY_STORAGE";
        /**
         * @since 6.0.0
         */
        ConsentType["PersonalizationStorage"] = "PERSONALIZATION_STORAGE";
    })(exports.ConsentType || (exports.ConsentType = {}));
    /**
     * @since 6.0.0
     */
    exports.ConsentStatus = void 0;
    (function (ConsentStatus) {
        /**
         * @since 6.0.0
         */
        ConsentStatus["Granted"] = "GRANTED";
        /**
         * @since 6.0.0
         */
        ConsentStatus["Denied"] = "DENIED";
    })(exports.ConsentStatus || (exports.ConsentStatus = {}));

    const FirebaseAnalytics = core.registerPlugin('FirebaseAnalytics', {
        web: () => Promise.resolve().then(function () { return web; }).then(m => new m.FirebaseAnalyticsWeb()),
    });

    class FirebaseAnalyticsWeb extends core.WebPlugin {
        async getAppSessionId() {
            return {};
        }
        async getAppInstanceId() {
            // throw this.unimplemented('Not implemented on web.');
            return {};
        }
        async setConsent(_options) {
            return;
            // const status: ConsentStatusString =
            //   options.status === ConsentStatus.Granted ? 'granted' : 'denied';
            // const consentSettings: ConsentSettings = {};
            // switch (options.type) {
            //   case ConsentType.AdPersonalization:
            //     consentSettings.ad_personalization = status;
            //     break;
            //   case ConsentType.AdStorage:
            //     consentSettings.ad_storage = status;
            //     break;
            //   case ConsentType.AdUserData:
            //     consentSettings.ad_user_data = status;
            //     break;
            //   case ConsentType.AnalyticsStorage:
            //     consentSettings.analytics_storage = status;
            //     break;
            //   case ConsentType.FunctionalityStorage:
            //     consentSettings.functionality_storage = status;
            //     break;
            //   case ConsentType.PersonalizationStorage:
            //     consentSettings.personalization_storage = status;
            //     break;
            // }
            // setConsent(consentSettings);
        }
        async setUserId(_options) {
            // const analytics = getAnalytics();
            // setUserId(analytics, options.userId);
            return;
        }
        async setUserProperty(_options) {
            // const analytics = getAnalytics();
            // setUserProperties(analytics, {
            //   [options.key]: options.value,
            // });
            return;
        }
        async setCurrentScreen(_options) {
            // const analytics = getAnalytics();
            // logEvent(analytics, 'screen_view', {
            //   firebase_screen: options.screenName || undefined,
            //   firebase_screen_class: options.screenClassOverride || undefined,
            // });
            return;
        }
        async logEvent(_options) {
            // const analytics = getAnalytics();
            // logEvent(analytics, options.name, options.params);
            return;
        }
        async setSessionTimeoutDuration(_options) {
            // throw this.unimplemented('Not implemented on web.');
            return;
        }
        async setEnabled(_options) {
            // const analytics = getAnalytics();
            // setAnalyticsCollectionEnabled(analytics, _options.enabled);
            return;
        }
        async isEnabled() {
            // const enabled = (window as any)['ga-disable-analyticsId'] === true;
            // return {
            //   enabled,
            // };
            return { enabled: true };
        }
        async resetAnalyticsData() {
            // throw this.unimplemented('Not implemented on web.');
            return;
        }
    }

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        FirebaseAnalyticsWeb: FirebaseAnalyticsWeb
    });

    exports.FirebaseAnalytics = FirebaseAnalytics;

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
