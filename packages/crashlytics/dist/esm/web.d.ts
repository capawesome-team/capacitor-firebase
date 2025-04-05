import { WebPlugin } from '@capacitor/core';
import type { DidCrashOnPreviousExecutionResult, FirebaseCrashlyticsPlugin, IsEnabledResult, LogOptions, RecordExceptionOptions, SetCustomKeyOptions, SetEnabledOptions, SetUserIdOptions } from './definitions';
export declare class FirebaseCrashlyticsWeb extends WebPlugin implements FirebaseCrashlyticsPlugin {
    crash(): Promise<void>;
    setCustomKey(_options: SetCustomKeyOptions): Promise<void>;
    setUserId(_options: SetUserIdOptions): Promise<void>;
    log(_options: LogOptions): Promise<void>;
    setEnabled(_options: SetEnabledOptions): Promise<void>;
    isEnabled(): Promise<IsEnabledResult>;
    didCrashOnPreviousExecution(): Promise<DidCrashOnPreviousExecutionResult>;
    sendUnsentReports(): Promise<void>;
    deleteUnsentReports(): Promise<void>;
    recordException(_options: RecordExceptionOptions): Promise<void>;
}
