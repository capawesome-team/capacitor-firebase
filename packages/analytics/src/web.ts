import { WebPlugin } from '@capacitor/core';
import {
  getAnalytics,
  logEvent,
  setAnalyticsCollectionEnabled,
  setUserId,
  setUserProperties,
} from 'firebase/analytics';

import type {
  FirebaseAnalyticsPlugin,
  IsEnabledResult,
  LogEventOptions,
  SetCurrentScreenOptions,
  SetEnabledOptions,
  SetSessionTimeoutDurationOptions,
  SetUserIdOptions,
  SetUserPropertyOptions,
} from './definitions';

export class FirebaseAnalyticsWeb
  extends WebPlugin
  implements FirebaseAnalyticsPlugin
{
  public async setUserId(options: SetUserIdOptions): Promise<void> {
    const analytics = getAnalytics();
    setUserId(analytics, options.userId || '');
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
}
