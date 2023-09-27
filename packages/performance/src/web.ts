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
} from './definitions';

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
}
