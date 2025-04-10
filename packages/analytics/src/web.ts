import { WebPlugin } from '@capacitor/core';
import type { ConsentSettings, ConsentStatusString } from 'firebase/analytics';
import {
  getAnalytics,
  logEvent,
  setAnalyticsCollectionEnabled,
  setConsent,
  setUserId,
  setUserProperties,
} from 'firebase/analytics';

import type {
  FirebaseAnalyticsPlugin,
  GetAppInstanceIdResult,
  InitiateOnDeviceConversionMeasurementWithEmailOptions,
  InitiateOnDeviceConversionMeasurementWithPhoneNumberOptions,
  InitiateOnDeviceConversionMeasurementWithHashedEmailOptions,
  InitiateOnDeviceConversionMeasurementWithHashedPhoneNumberOptions,
  IsEnabledResult,
  LogEventOptions,
  SetConsentOptions,
  SetCurrentScreenOptions,
  SetEnabledOptions,
  SetSessionTimeoutDurationOptions,
  SetUserIdOptions,
  SetUserPropertyOptions,
} from './definitions';
import { ConsentStatus, ConsentType } from './definitions';

export class FirebaseAnalyticsWeb
  extends WebPlugin
  implements FirebaseAnalyticsPlugin
{
  public async getAppInstanceId(): Promise<GetAppInstanceIdResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async setConsent(options: SetConsentOptions): Promise<void> {
    const status: ConsentStatusString =
      options.status === ConsentStatus.Granted ? 'granted' : 'denied';
    const consentSettings: ConsentSettings = {};
    switch (options.type) {
      case ConsentType.AdPersonalization:
        consentSettings.ad_personalization = status;
        break;
      case ConsentType.AdStorage:
        consentSettings.ad_storage = status;
        break;
      case ConsentType.AdUserData:
        consentSettings.ad_user_data = status;
        break;
      case ConsentType.AnalyticsStorage:
        consentSettings.analytics_storage = status;
        break;
      case ConsentType.FunctionalityStorage:
        consentSettings.functionality_storage = status;
        break;
      case ConsentType.PersonalizationStorage:
        consentSettings.personalization_storage = status;
        break;
    }
    setConsent(consentSettings);
  }

  public async setUserId(options: SetUserIdOptions): Promise<void> {
    const analytics = getAnalytics();
    setUserId(analytics, options.userId);
  }

  public async setUserProperty(options: SetUserPropertyOptions): Promise<void> {
    const analytics = getAnalytics();
    setUserProperties(analytics, {
      [options.key]: options.value,
    });
  }

  public async setCurrentScreen(
    options: SetCurrentScreenOptions,
  ): Promise<void> {
    const analytics = getAnalytics();
    logEvent(analytics, 'screen_view', {
      firebase_screen: options.screenName || undefined,
      firebase_screen_class: options.screenClassOverride || undefined,
    });
  }

  public async logEvent(options: LogEventOptions): Promise<void> {
    const analytics = getAnalytics();
    logEvent(analytics, options.name, options.params);
  }

  public async setSessionTimeoutDuration(
    _options: SetSessionTimeoutDurationOptions,
  ): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async setEnabled(_options: SetEnabledOptions): Promise<void> {
    const analytics = getAnalytics();
    setAnalyticsCollectionEnabled(analytics, _options.enabled);
  }

  public async isEnabled(): Promise<IsEnabledResult> {
    const enabled = (window as any)['ga-disable-analyticsId'] === true;
    return {
      enabled,
    };
  }

  public async resetAnalyticsData(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async initiateOnDeviceConversionMeasurementWithEmail(
    options: InitiateOnDeviceConversionMeasurementWithEmailOptions,
  ): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async initiateOnDeviceConversionMeasurementWithPhoneNumber(
    options: InitiateOnDeviceConversionMeasurementWithPhoneNumberOptions,
  ): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async initiateOnDeviceConversionMeasurementWithHashedEmail(
    options: InitiateOnDeviceConversionMeasurementWithHashedEmailOptions,
  ): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async initiateOnDeviceConversionMeasurementWithHashedPhoneNumber(
    options: InitiateOnDeviceConversionMeasurementWithHashedPhoneNumberOptions,
  ): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
