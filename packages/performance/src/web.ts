import { WebPlugin } from '@capacitor/core';
import type { PerformanceTrace } from 'firebase/performance';
import { trace as createTrace, getPerformance } from 'firebase/performance';

import type {
  FirebasePerformancePlugin,
  GetAttributeOptions,
  GetAttributeResult,
  GetAttributesOptions,
  GetAttributesResult,
  GetMetricOptions,
  GetMetricResult,
  IncrementMetricOptions,
  IsEnabledResult,
  PutAttributeOptions,
  PutMetricOptions,
  RecordOptions,
  RemoveAttributeOptions,
  SetEnabledOptions,
  StartTraceOptions,
  StopTraceOptions,
} from './definitions';

export class FirebasePerformanceWeb extends WebPlugin implements FirebasePerformancePlugin {
  private static readonly ERROR_TRACE_NOT_FOUND = 'No trace was found with the provided traceName.';
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
      throw new Error(FirebasePerformanceWeb.ERROR_TRACE_NOT_FOUND);
    }
    trace.stop();
    delete this.traces[options.traceName];
  }

  public async incrementMetric(options: IncrementMetricOptions): Promise<void> {
    const trace = this.traces[options.traceName];
    if (!trace) {
      throw new Error(FirebasePerformanceWeb.ERROR_TRACE_NOT_FOUND);
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

  public async putAttribute({ traceName, attribute, value }: PutAttributeOptions): Promise<void> {
    const trace = this.traces[traceName];
    if (!trace) {
      throw new Error(FirebasePerformanceWeb.ERROR_TRACE_NOT_FOUND);
    }
    trace.putAttribute(attribute, value);
    return;
  }

  public async getAttribute({ traceName, attribute }: GetAttributeOptions): Promise<GetAttributeResult> {
    const trace = this.traces[traceName];
    if (!trace) {
      throw new Error(FirebasePerformanceWeb.ERROR_TRACE_NOT_FOUND);
    }
    return { value: trace.getAttribute(attribute) ?? null };
  }

  public async getAttributes({ traceName }: GetAttributesOptions): Promise<GetAttributesResult> {
    const trace = this.traces[traceName];
    if (!trace) {
      throw new Error(FirebasePerformanceWeb.ERROR_TRACE_NOT_FOUND);
    }
    return { attributes: trace.getAttributes() };
  }

  public async removeAttribute({ traceName, attribute }: RemoveAttributeOptions): Promise<void> {
    const trace = this.traces[traceName];
    if (!trace) {
      throw new Error(FirebasePerformanceWeb.ERROR_TRACE_NOT_FOUND);
    }
    trace.removeAttribute(attribute);
  }

  public async putMetric({ traceName, metricName, num }: PutMetricOptions): Promise<void> {
    const trace = this.traces[traceName];
    if (!trace) {
      throw new Error(FirebasePerformanceWeb.ERROR_TRACE_NOT_FOUND);
    }
    trace.putMetric(metricName, num);
  }

  public async getMetric({ traceName, metricName }: GetMetricOptions): Promise<GetMetricResult> {
    const trace = this.traces[traceName];
    if (!trace) {
      throw new Error(FirebasePerformanceWeb.ERROR_TRACE_NOT_FOUND);
    }
    return { value: trace.getMetric(metricName) };
  }

  public async record({ traceName, startTime, duration, options }: RecordOptions): Promise<void> {
    const perf = getPerformance();
    const trace = createTrace(perf, traceName);
    trace.record(startTime, duration, options);
  }
}
