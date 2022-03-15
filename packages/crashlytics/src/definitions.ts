export interface FirebaseCrashlyticsPlugin {
  /**
   * Forces a crash to test the implementation.
   *
   * Only available for Android and iOS.
   */
  crash(options: CrashOptions): Promise<void>;
  /**
   * Sets a custom key and value that is associated with subsequent fatal and non-fatal reports.
   *
   * Only available for Android and iOS.
   */
  setCustomKey(options: SetCustomKeyOptions): Promise<void>;
  /**
   * Sets a user ID (identifier) that is associated with subsequent fatal and non-fatal reports.
   *
   * Only available for Android and iOS.
   */
  setUserId(options: SetUserIdOptions): Promise<void>;
  /**
   * Adds a custom log message that is sent with your crash data to give yourself more context for the events leading up to a crash.
   *
   * Only available for Android and iOS.
   */
  log(options: LogOptions): Promise<void>;
  /**
   * Enables/disables automatic data collection.
   * The value does not apply until the next run of the app.
   *
   * Only available for Android and iOS.
   */
  setEnabled(options: SetEnabledOptions): Promise<void>;
  /**
   * Returns whether or not automatic data collection is enabled.
   *
   * Only available for iOS.
   */
  isEnabled(): Promise<IsEnabledResult>;
  /**
   * Returns whether the app crashed during the previous execution.
   *
   * Only available for Android and iOS.
   */
  didCrashOnPreviousExecution(): Promise<DidCrashOnPreviousExecutionResult>;
  /**
   * Uploads any unsent reports to Crashlytics.
   * When automatic data collection is enabled, Crashlytics automatically uploads reports at startup.
   *
   * Only available for Android and iOS.
   */
  sendUnsentReports(): Promise<void>;
  /**
   * Deletes any unsent reports on the device.
   *
   * Only available for Android and iOS.
   */
  deleteUnsentReports(): Promise<void>;
  /**
   * Records a non-fatal report to send to Crashlytics.
   *
   * Only available for Android and iOS.
   */
  recordException(options: RecordExceptionOptions): Promise<void>;
}

export interface CrashOptions {
  message: string;
}

export interface SetCustomKeyOptions {
  key: string;
  value: string | number | boolean;
  type: 'string' | 'long' | 'double' | 'boolean' | 'int' | 'float';
}

export interface SetUserIdOptions {
  userId: string;
}

export interface LogOptions {
  message: string;
}

export interface SetEnabledOptions {
  enabled: boolean;
}

export interface IsEnabledResult {
  enabled: boolean;
}

export interface DidCrashOnPreviousExecutionResult {
  crashed: boolean;
}

export interface RecordExceptionOptions {
  message: string;
  /**
   * Error code within a specific error domain.
   *
   * Only available for iOS.
   */
  code?: number;
  /**
   * A string containing the error domain.
   *
   * Only available for iOS.
   */
  domain?: string;
}
