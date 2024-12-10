import { WebPlugin } from '@capacitor/core';
import type { PerformanceTrace } from 'firebase/performance';
import { getPerformance, trace as createTrace } from 'firebase/performance';

import type {
  FirebasePerformancePlugin,
  IncrementMetricOptions,
  IsEnabledResult,
  SetEnabledOptions,
  StartTraceOptions,
  StopTraceOptions,
  PutAttributeOptions,
  GetAttributeOptions,
  GetAttributeResult,
  GetAttributesOptions,
  GetAttributesResult,
  RemoveAttributeOptions,
  PutMetricOptions,
  GetMetricResult,
  RecordOptions,
} from './definitions';

const traceNotFoundError = 'No trace found for the given name.';

export class FirebasePerformanceWeb
  extends WebPlugin
  implements FirebasePerformancePlugin
{
  private traces: { [key: string]: PerformanceTrace | undefined } = {};

  public async startTrace(options: StartTraceOptions): Promise<void> {
    const perf = getPerformance();
    const trace = createTrace(perf, options.traceName);
    trace.start();
    this.traces[options.traceName] = trace;
  }

  public async stopTrace(options: StopTraceOptions): Promise<void> {
    const trace = this.traces[options.traceName];
    if (!trace) {
      return;
    }
    trace.stop();
    delete this.traces[options.traceName];
  }

  public async incrementMetric(options: IncrementMetricOptions): Promise<void> {
    const trace = this.traces[options.traceName];
    if (!trace) {
      return;
    }
    trace.incrementMetric(options.metricName, options.incrementBy);
  }

  public async setEnabled(options: SetEnabledOptions): Promise<void> {
    const perf = getPerformance();
    perf.instrumentationEnabled = options.enabled;
    perf.dataCollectionEnabled = options.enabled;
  }

  public async isEnabled(): Promise<IsEnabledResult> {
    const perf = getPerformance();
    const result: IsEnabledResult = {
      enabled: perf.instrumentationEnabled || perf.dataCollectionEnabled,
    };
    return result;
  }

  public async putAttribute({
    traceName,
    attribute,
    value,
  }: PutAttributeOptions): Promise<void> {
    const trace = this.traces[traceName];
    if (!trace) {
      return;
    }
    trace.putAttribute(attribute, value);
    return;
  }

  public async getAttribute({
    traceName,
    attribute,
  }: GetAttributeOptions): Promise<GetAttributeResult> {
    const trace = this.traces[traceName];
    if (!trace) {
      throw new Error(traceNotFoundError);
    }
    return { value: trace.getAttribute(attribute) ?? null };
  }

  public async getAttributes({
    traceName,
  }: GetAttributesOptions): Promise<GetAttributesResult> {
    const trace = this.traces[traceName];
    if (!trace) {
      throw new Error(traceNotFoundError);
    }
    return { result: trace.getAttributes() };
  }

  public async removeAttribute({
    traceName,
    attribute,
  }: RemoveAttributeOptions): Promise<void> {
    const trace = this.traces[traceName];
    if (!trace) {
      throw new Error(traceNotFoundError);
    }
    trace.removeAttribute(attribute);
  }

  public async putMetric({
    traceName,
    metricName,
    num,
  }: PutMetricOptions): Promise<void> {
    const trace = this.traces[traceName];
    if (!trace) {
      throw new Error(traceNotFoundError);
    }
    trace.putMetric(metricName, num);
  }

  public async getMetric({
    traceName,
    metricName,
  }: PutMetricOptions): Promise<GetMetricResult> {
    const trace = this.traces[traceName];
    if (!trace) {
      throw new Error(traceNotFoundError);
    }
    return { value: trace.getMetric(metricName) };
  }

  public async record({
    traceName,
    startTime,
    duration,
    options,
  }: RecordOptions): Promise<void> {
    const trace = this.traces[traceName];
    if (!trace) {
      throw new Error(traceNotFoundError);
    }
    trace.record(startTime, duration, options);
  }
}
