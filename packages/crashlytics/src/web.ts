import { WebPlugin } from '@capacitor/core';

import type {
  FirebaseCrashlyticsPlugin,
  ContextOptions,
  RecordExceptionOptions,
} from './definitions';

export class FirebaseCrashlyticsWeb
  extends WebPlugin
  implements FirebaseCrashlyticsPlugin {
  public async setUserId(_options: { userId: string }): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async addLogMessage(_options: { message: string }): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async setEnabled(_options: { enabled: boolean }): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async crash(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async setContext(options: ContextOptions): Promise<void> {
    console.log(options);
    throw this.unimplemented('Not implemented on web.');
  }

  public async isEnabled(): Promise<{ enabled: boolean }> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async didCrashDuringPreviousExecution(): Promise<{
    crashed: boolean;
  }> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async sendUnsentReports(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async deleteUnsentReports(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async recordException(
    _options: RecordExceptionOptions,
  ): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
