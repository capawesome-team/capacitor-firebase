import { WebPlugin } from '@capacitor/core';
export class FirebaseAnalyticsWeb extends WebPlugin {
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
//# sourceMappingURL=web.js.map