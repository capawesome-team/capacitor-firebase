export interface FirebaseAnalyticsPlugin {
  /**
   * Sets a user ID (identifier) that is associated with subsequent fatal and non-fatal reports.
   *
   * Only available for Android and iOS.
   */
  setUserId(options: SetUserIdOptions): Promise<void>;
  /**
   * Sets a custom user property to a given value.
   */
  setUserProperty(options: SetUserPropertyOptions): Promise<void>;
  /**
   * Sets the current screen name.
   */
  setCurrentScreen(options: SetCurrentScreenOptions): Promise<void>;
  /**
   * Logs an app event.
   */
  logEvent(options: LogEventOptions): Promise<void>;
  /**
   * Sets the duration of inactivity that terminates the current session.
   *
   * Only available for Android and iOS.
   */
  setSessionTimeoutDuration(
    options: SetSessionTimeoutDurationOptions,
  ): Promise<void>;
  /**
   * Enables/disables automatic data collection.
   * The value does not apply until the next run of the app.
   */
  setEnabled(options: SetEnabledOptions): Promise<void>;
  /**
   * Returns whether or not automatic data collection is enabled.
   *
   * Only available for Web.
   */
  isEnabled(): Promise<IsEnabledResult>;
  /**
   * Clears all analytics data for this app from the device.
   * Resets the app instance id.
   *
   * Only available for Android and iOS.
   */
  resetAnalyticsData(): Promise<void>;
}

export interface SetUserIdOptions {
  userId: string | null;
}

export interface SetUserPropertyOptions {
  key: string;
  value: string | null;
}

export interface SetCurrentScreenOptions {
  screenName: string | null;
  /**
   * Only available for Android and iOS.
   *
   * Default: `null`
   */
  screenClassOverride?: string | null;
}

export interface LogEventOptions {
  /**
   * The event name.
   */
  name: string;
  /**
   * The optional event params.
   */
  params?: { [key: string]: any };
}

export interface SetSessionTimeoutDurationOptions {
  /**
   * Duration in seconds.
   *
   * Default: `1800` (30 minutes)
   */
  duration: number;
}

export interface SetEnabledOptions {
  enabled: boolean;
}

export interface IsEnabledResult {
  enabled: boolean;
}
