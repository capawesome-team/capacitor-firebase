export interface FirebasePerformancePlugin {
  /**
   * Starts a trace.
   */
  startTrace(options: StartTraceOptions): Promise<void>;
  /**
   * Stops a trace.
   */
  stopTrace(options: StopTraceOptions): Promise<void>;
  /**
   * Atomically increments the metric with the given name for the selected trace by the `incrementBy` value.
   */
  incrementMetric(options: IncrementMetricOptions): Promise<void>;
  /**
   * Enables or disables performance monitoring.
   * Will be applied with the next start of the app.
   */
  setPerformanceCollectionEnabled(
    options: SetPerformanceCollectionEnabledOptions,
  ): Promise<void>;
  /**
   * Determines whether performance monitoring is enabled or disabled.
   */
  isPerformanceCollectionEnabled(): Promise<IsPerformanceCollectionEnabledResult>;
}

export interface StartTraceOptions {
  /**
   * Custom trace name.
   *
   * Names for custom code traces must meet the following requirements:
   * no leading or trailing whitespace, no leading underscore (_) character,
   * and max length is 100 characters.
   */
  traceName: string;
}

export interface StopTraceOptions {
  /**
   * Name of the trace that was set with `startTrace`.
   */
  traceName: string;
}

export interface IncrementMetricOptions {
  /**
   * Name of the trace that was set with `startTrace`.
   */
  traceName: string;
  /**
   * Name of the metric to be incremented.
   */
  metricName: string;
  /**
   * Amount by which the metric has to be incremented.
   *
   * Default: `1`
   */
  incrementBy?: number;
}

export interface SetPerformanceCollectionEnabledOptions {
  /**
   * Should performance monitoring be enabled.
   */
  enabled: boolean;
}

export interface IsPerformanceCollectionEnabledResult {
  /**
   * `true` if performance monitoring is enabled, otherwise `false`.
   */
  enabled: boolean;
}
