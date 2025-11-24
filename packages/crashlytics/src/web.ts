import { WebPlugin } from '@capacitor/core';

import type {
  DidCrashOnPreviousExecutionResult,
  FirebaseCrashlyticsPlugin,
  IsEnabledResult,
  LogOptions,
  RecordExceptionOptions,
  SetCustomKeyOptions,
  SetEnabledOptions,
  SetUserIdOptions,
} from './definitions';

export class FirebaseCrashlyticsWeb extends WebPlugin implements FirebaseCrashlyticsPlugin {
  public async crash(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async setCustomKey(_options: SetCustomKeyOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async setUserId(_options: SetUserIdOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async log(_options: LogOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async setEnabled(_options: SetEnabledOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async isEnabled(): Promise<IsEnabledResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async didCrashOnPreviousExecution(): Promise<DidCrashOnPreviousExecutionResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async sendUnsentReports(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async deleteUnsentReports(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async recordException(_options: RecordExceptionOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
