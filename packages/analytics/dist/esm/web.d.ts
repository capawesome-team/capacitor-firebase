import { WebPlugin } from '@capacitor/core';
import type { FirebaseAnalyticsPlugin, GetAppInstanceIdResult, GetSessionIdResult, IsEnabledResult, LogEventOptions, SetConsentOptions, SetCurrentScreenOptions, SetEnabledOptions, SetSessionTimeoutDurationOptions, SetUserIdOptions, SetUserPropertyOptions } from './definitions';
export declare class FirebaseAnalyticsWeb extends WebPlugin implements FirebaseAnalyticsPlugin {
    getAppSessionId(): Promise<GetSessionIdResult>;
    getAppInstanceId(): Promise<GetAppInstanceIdResult>;
    setConsent(_options: SetConsentOptions): Promise<void>;
    setUserId(_options: SetUserIdOptions): Promise<void>;
    setUserProperty(_options: SetUserPropertyOptions): Promise<void>;
    setCurrentScreen(_options: SetCurrentScreenOptions): Promise<void>;
    logEvent(_options: LogEventOptions): Promise<void>;
    setSessionTimeoutDuration(_options: SetSessionTimeoutDurationOptions): Promise<void>;
    setEnabled(_options: SetEnabledOptions): Promise<void>;
    isEnabled(): Promise<IsEnabledResult>;
    resetAnalyticsData(): Promise<void>;
}
