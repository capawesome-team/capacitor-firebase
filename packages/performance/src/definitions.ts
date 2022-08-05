export interface FirebasePerformancePlugin {
  /**
   * Starts a trace.
   *
   * @since 0.1.0
   */
  startTrace(options: StartTraceOptions): Promise<void>;
  /**
   * Stops a trace.
   *
   * @since 0.1.0
   */
  stopTrace(options: StopTraceOptions): Promise<void>;
  /**
   * Atomically increments the metric with the given name for the selected trace by the `incrementBy` value.
   *
   * @since 0.1.0
   */
  incrementMetric(options: IncrementMetricOptions): Promise<void>;
  /**
   * Enables or disables performance monitoring.
   * Will be applied with the next start of the app.
   *
   * @since 0.1.0
   */
  setEnabled(options: SetEnabledOptions): Promise<void>;
  /**
   * Determines whether performance monitoring is enabled or disabled.
   *
   * @since 0.1.0
   */
  isEnabled(): Promise<IsEnabledResult>;
}

/**
 * @since 0.1.0
 */
export interface StartTraceOptions {
  /**
   * Custom trace name.
   *
   * Names for custom code traces must meet the following requirements:
   * no leading or trailing whitespace, no leading underscore (_) character,
   * and max length is 100 characters.
   *
   * @since 0.1.0
   */
  traceName: string;
}

/**
 * @since 0.1.0
 */
export interface StopTraceOptions {
  /**
   * Name of the trace that was set with `startTrace`.
   *
   * @since 0.1.0
   */
  traceName: string;
}

/**
 * @since 0.1.0
 */
export interface IncrementMetricOptions {
  /**
   * Name of the trace that was set with `startTrace`.
   *
   * @since 0.1.0
   */
  traceName: string;
  /**
   * Name of the metric to be incremented.
   *
   * @since 0.1.0
   */
  metricName: string;
  /**
   * Amount by which the metric has to be incremented.
   *
   * @default 1
   * @since 0.1.0
   */
  incrementBy?: number;
}

/**
 * @since 0.1.0
 */
export interface SetEnabledOptions {
  /**
   * Should performance monitoring be enabled.
   *
   * @since 0.1.0
   */
  enabled: boolean;
}

export interface IsEnabledResult {
  /**
   * `true` if performance monitoring is enabled, otherwise `false`.
   *
   * @since 0.1.0
   */
  enabled: boolean;
}
