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

  /**
   * Records a trace from given parameters. This provides a direct way to use trace without a need to start/stop.
   * This is useful for use cases in which the trace cannot directly be used
   * (e.g. if the duration was captured before the Performance SDK was loaded).
   *
   * @param startTime - The start time of the trace in milliseconds since the epoch.
   * @param duration - The duration of the trace in milliseconds.
   * @param options - Additional options for recording the trace.
   * @since 0.1.0
   */
  record(
    startTime: number,
    duration: number,
    options?: RecordOptions
  ): void;

  /**
   * Retrieves the value which a custom attribute is set to.
   *
   * @param attr - The name of the custom attribute.
   * @returns The value of the custom attribute, or `undefined` if not set.
   * @since 0.1.0
   */
  getAttribute(attr: string): string | undefined;

  /**
   * Returns a map of all custom attributes of a trace instance.
   *
   * @returns A map of custom attributes where keys are attribute names and values are attribute values.
   * @since 0.1.0
   */
  getAttributes(): { [key: string]: string };

  /**
   * Removes the specified custom attribute from a trace instance.
   *
   * @param attr - The name of the custom attribute to be removed.
   * @since 0.1.0
   */
  removeAttribute(attr: string): void;

  /**
   * Sets the value of the specified custom metric to the given number.
   * The value will be floored down to an integer.
   *
   * @param metricName - The name of the custom metric.
   * @param num - The value to set for the custom metric.
   * @since 0.1.0
   */
  putMetric(metricName: string, num: number): void;

  /**
   * Returns the value of the custom metric by that name.
   * If a custom metric with that name does not exist, it will return zero.
   *
   * @param metricName - The name of the custom metric.
   * @returns The value of the custom metric, or zero if not set.
   * @since 0.1.0
   */
  getMetric(metricName: string): number;

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

export interface RecordOptions {
  /**
   * Custom metrics associated with the trace.
   * @since 0.1.0
   */
  metrics?: { [key: string]: number };
  
  /**
   * Custom attributes associated with the trace.
   * @since 0.1.0
   */
  attributes?: { [key: string]: string };

  /**
   * Additional attribute to be added to the trace.
   * @since 0.1.0
   */
  putAttribute?: PutAttributeOptions;
}

/**
 * Options for adding a custom attribute.
 * @since 0.1.0
 */
export interface PutAttributeOptions {
  /**
   * Name of the attribute to be added.
   * @since 0.1.0
   */
  attributeName: string;

  /**
   * Value of the attribute to be added.
   * @since 0.1.0
   */
  attributeValue: string;
}