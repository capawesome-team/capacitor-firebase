import { WebPlugin } from '@capacitor/core';
import type { PerformanceTrace } from 'firebase/performance';
import { getPerformance, trace as createTrace } from 'firebase/performance';

import type {
  FirebaseMessagingPlugin,
  IncrementMetricOptions,
  IsPerformanceCollectionEnabledResult,
  SetPerformanceCollectionEnabledOptions,
  StartTraceOptions,
  StopTraceOptions,
} from './definitions';

export class FirebaseMessagingWeb
  extends WebPlugin
  implements FirebaseMessagingPlugin {}
