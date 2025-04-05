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
     * Sets a custom attribute of a trace to a given value.
     *
     * @since 6.3.0
     */
    putAttribute(options: PutAttributeOptions): Promise<void>;
    /**
     * Returns the value of a custom attribute of a trace.
     *
     * @since 6.3.0
     */
    getAttribute(options: GetAttributeOptions): Promise<GetAttributeResult>;
    /**
     * Gets the all the custom attributes of a trace with their values.
     *
     * @since 6.3.0
     */
    getAttributes(options: GetAttributesOptions): Promise<GetAttributesResult>;
    /**
     * Removes a custom attribute from a trace given its name.
     *
     * @since 6.3.0
     */
    removeAttribute(options: RemoveAttributeOptions): Promise<void>;
    /**
     * Sets the value of a custom metric.
     *
     * @since 6.3.0
     */
    putMetric(options: PutMetricOptions): Promise<void>;
    /**
     * Get the value of a custom metric by name.
     *
     * @since 6.3.0
     */
    getMetric(options: GetMetricOptions): Promise<GetMetricResult>;
    /**
     * Records a trace given its name and options.
     *
     * Only available on web.
     *
     * @since 6.3.0
     */
    record(options: RecordOptions): Promise<void>;
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
/**
 * @since 6.3.0
 */
export interface PutAttributeOptions {
    /**
     * Name of the trace to set its attribute.
     *
     * @since 6.3.0
     */
    traceName: string;
    /**
     * Name of the attribute to set its value.
     *
     * @since 6.3.0
     * @example "experiment"
     */
    attribute: string;
    /**
     * The value to set to the attribute.
     *
     * @since 6.3.0
     * @example "A"
     */
    value: string;
}
/**
 * @since 6.3.0
 */
export interface GetAttributeOptions {
    /**
     * Name of the trace to set its attribute.
     *
     * @since 6.3.0
     */
    traceName: string;
    /**
     * Name of the attribute to retrieve its value.
     *
     * @since 6.3.0
     */
    attribute: string;
}
/**
 * @since 6.3.0
 */
export interface GetAttributeResult {
    /**
     * The value of the custom attribute.
     *
     * @since 6.3.0
     */
    value: string | null;
}
/**
 * @since 6.3.0
 */
export interface GetAttributesOptions {
    /**
     * Name of the trace to get its attributes.
     *
     * @since 6.3.0
     */
    traceName: string;
}
/**
 * @since 6.3.0
 */
export interface GetAttributesResult {
    /**
     * A map of all custom attributes of a trace with their values.
     *
     * @since 6.3.0
     */
    attributes: {
        [key: string]: string;
    };
}
/**
 * @since 6.3.0
 */
export declare type RemoveAttributeOptions = GetAttributeOptions;
/**
 * @since 6.3.0
 */
export interface PutMetricOptions {
    /**
     * Name of the trace to set its metric.
     *
     * @since 6.3.0
     */
    traceName: string;
    /**
     * The metric name.
     *
     * @since 6.3.0
     */
    metricName: string;
    /**
     * The value to set for the metric.
     * The given value is floored down to the nearest integer.
     *
     * @since 6.3.0
     */
    num: number;
}
/**
 * @since 6.3.0
 */
export interface GetMetricOptions {
    /**
     * Name of the trace to get its metric.
     *
     * @since 6.3.0
     */
    traceName: string;
    /**
     * The metric name.
     *
     * @since 6.3.0
     */
    metricName: string;
}
/**
 * @since 6.3.0
 */
export interface GetMetricResult {
    /**
     * The value of the metric if exists.
     *
     * @since 6.3.0
     */
    value: number;
}
/**
 * @since 6.3.0
 */
export interface RecordOptions {
    /**
     * Name of the trace to record.
     *
     * @since 6.3.0
     */
    traceName: string;
    /**
     * Start time of the trace since epoch in milliseconds.
     *
     * @since 6.3.0
     */
    startTime: number;
    /**
     * The duration of the trace in milliseconds.
     *
     * @since 6.3.0
     */
    duration: number;
    /**
     * An optional object that holds optional maps of custom metrics and attributes.
     *
     * @since 6.3.0
     */
    options?: {
        /**
         * An optional map of metrics to be set on the trace.
         *
         * @since 6.3.0
         */
        metrics?: {
            [key: string]: number;
        };
        /**
         * An optional map of attributes to be set on the trace.
         *
         * @since 6.3.0
         */
        attributes?: {
            [key: string]: string;
        };
    };
}
