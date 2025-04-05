import { WebPlugin } from '@capacitor/core';
import type { FirebasePerformancePlugin, GetAttributeOptions, GetAttributeResult, GetAttributesOptions, GetAttributesResult, GetMetricOptions, GetMetricResult, IncrementMetricOptions, IsEnabledResult, PutAttributeOptions, PutMetricOptions, RecordOptions, RemoveAttributeOptions, SetEnabledOptions, StartTraceOptions, StopTraceOptions } from './definitions';
export declare class FirebasePerformanceWeb extends WebPlugin implements FirebasePerformancePlugin {
    private static readonly ERROR_TRACE_NOT_FOUND;
    private traces;
    startTrace(options: StartTraceOptions): Promise<void>;
    stopTrace(options: StopTraceOptions): Promise<void>;
    incrementMetric(options: IncrementMetricOptions): Promise<void>;
    setEnabled(options: SetEnabledOptions): Promise<void>;
    isEnabled(): Promise<IsEnabledResult>;
    putAttribute({ traceName, attribute, value, }: PutAttributeOptions): Promise<void>;
    getAttribute({ traceName, attribute, }: GetAttributeOptions): Promise<GetAttributeResult>;
    getAttributes({ traceName, }: GetAttributesOptions): Promise<GetAttributesResult>;
    removeAttribute({ traceName, attribute, }: RemoveAttributeOptions): Promise<void>;
    putMetric({ traceName, metricName, num, }: PutMetricOptions): Promise<void>;
    getMetric({ traceName, metricName, }: GetMetricOptions): Promise<GetMetricResult>;
    record({ traceName, startTime, duration, options, }: RecordOptions): Promise<void>;
}
