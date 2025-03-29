import { WebPlugin } from '@capacitor/core';

import type {
  FirebaseAnalyticsPlugin,
  GetAppInstanceIdResult,
  GetSessionIdResult,
  IsEnabledResult,
  LogEventOptions,
  SetConsentOptions,
  SetCurrentScreenOptions,
  SetEnabledOptions,
  SetSessionTimeoutDurationOptions,
  SetUserIdOptions,
  SetUserPropertyOptions,
} from './definitions';

export class FirebaseAnalyticsWeb
  extends WebPlugin
  implements FirebaseAnalyticsPlugin {

  public async getAppSessionId(): Promise<GetSessionIdResult> {
    return {};
  }

  public async getAppInstanceId(): Promise<GetAppInstanceIdResult> {
    // throw this.unimplemented('Not implemented on web.');
    return {}
  }

  public async setConsent(_options: SetConsentOptions): Promise<void> {
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

  public async setUserId(_options: SetUserIdOptions): Promise<void> {
    // const analytics = getAnalytics();
    // setUserId(analytics, options.userId);
    return;
  }

  public async setUserProperty(_options: SetUserPropertyOptions): Promise<void> {
    // const analytics = getAnalytics();
    // setUserProperties(analytics, {
    //   [options.key]: options.value,
    // });
    return;
  }

  public async setCurrentScreen(
    _options: SetCurrentScreenOptions,
  ): Promise<void> {
    // const analytics = getAnalytics();
    // logEvent(analytics, 'screen_view', {
    //   firebase_screen: options.screenName || undefined,
    //   firebase_screen_class: options.screenClassOverride || undefined,
    // });
    return;
  }

  public async logEvent(_options: LogEventOptions): Promise<void> {
    // const analytics = getAnalytics();
    // logEvent(analytics, options.name, options.params);
    return;
  }

  public async setSessionTimeoutDuration(
    _options: SetSessionTimeoutDurationOptions,
  ): Promise<void> {
    // throw this.unimplemented('Not implemented on web.');
    return;
  }

  public async setEnabled(_options: SetEnabledOptions): Promise<void> {
    // const analytics = getAnalytics();
    // setAnalyticsCollectionEnabled(analytics, _options.enabled);
    return;
  }

  public async isEnabled(): Promise<IsEnabledResult> {
    // const enabled = (window as any)['ga-disable-analyticsId'] === true;
    // return {
    //   enabled,
    // };
    return { enabled: true };
  }

  public async resetAnalyticsData(): Promise<void> {
    // throw this.unimplemented('Not implemented on web.');
    return;
  }
}
