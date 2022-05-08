# @capacitor-firebase/performance

⚡️ Capacitor plugin for Firebase Performance Monitoring.

## Installation

```bash
npm install @capacitor-firebase/performance firebase
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://firebase.google.com/docs/android/setup) / [iOS](https://firebase.google.com/docs/ios/setup) / [Web](https://firebase.google.com/docs/web/setup)).

### Android

See [Add the Performance Monitoring plugin to your app](https://firebase.google.com/docs/perf-mon/get-started-android#add-perfmon-plugin) and follow the instructions to set up your app correctly.

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$firebasePerfVersion` version of `com.google.firebase:firebase-perf` (default: `20.0.6`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-firebase-plugin-demo](https://github.com/robingenz/capacitor-firebase-plugin-demo)

## Usage

```typescript
import { FirebasePerformance } from '@capacitor-firebase/performance';

const startTrace = async () => {
  await FirebasePerformance.startTrace({ traceName: 'test_trace' });
};

const stopTrace = async () => {
  await FirebasePerformance.stopTrace({ traceName: 'test_trace' });
};

const incrementMetric = async () => {
  await FirebasePerformance.incrementMetric({
    traceName: 'test_trace',
    metricName: 'item_cache_hit',
    incrementBy: 1,
  });
};

const setPerformanceCollectionEnabled = async () => {
  await FirebasePerformance.setPerformanceCollectionEnabled({ enabled: true });
};

const isPerformanceCollectionEnabled = async () => {
  const result = await FirebasePerformance.isPerformanceCollectionEnabled();
  return result.enabled;
};
```

## API

<docgen-index>

* [`startTrace(...)`](#starttrace)
* [`stopTrace(...)`](#stoptrace)
* [`incrementMetric(...)`](#incrementmetric)
* [`setPerformanceCollectionEnabled(...)`](#setperformancecollectionenabled)
* [`isPerformanceCollectionEnabled()`](#isperformancecollectionenabled)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### startTrace(...)

```typescript
startTrace(options: StartTraceOptions) => Promise<void>
```

Starts a trace.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#starttraceoptions">StartTraceOptions</a></code> |

--------------------


### stopTrace(...)

```typescript
stopTrace(options: StopTraceOptions) => Promise<void>
```

Stops a trace.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#stoptraceoptions">StopTraceOptions</a></code> |

--------------------


### incrementMetric(...)

```typescript
incrementMetric(options: IncrementMetricOptions) => Promise<void>
```

Atomically increments the metric with the given name for the selected trace by the `incrementBy` value.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#incrementmetricoptions">IncrementMetricOptions</a></code> |

--------------------


### setPerformanceCollectionEnabled(...)

```typescript
setPerformanceCollectionEnabled(options: SetPerformanceCollectionEnabledOptions) => Promise<void>
```

Enables or disables performance monitoring.
Will be applied with the next start of the app.

| Param         | Type                                                                                                      |
| ------------- | --------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setperformancecollectionenabledoptions">SetPerformanceCollectionEnabledOptions</a></code> |

--------------------


### isPerformanceCollectionEnabled()

```typescript
isPerformanceCollectionEnabled() => Promise<IsPerformanceCollectionEnabledResult>
```

Determines whether performance monitoring is enabled or disabled.

**Returns:** <code>Promise&lt;<a href="#isperformancecollectionenabledresult">IsPerformanceCollectionEnabledResult</a>&gt;</code>

--------------------


### Interfaces


#### StartTraceOptions

| Prop            | Type                | Description                                                                                                                                                                                     |
| --------------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`traceName`** | <code>string</code> | Custom trace name. Names for custom code traces must meet the following requirements: no leading or trailing whitespace, no leading underscore (_) character, and max length is 100 characters. |


#### StopTraceOptions

| Prop            | Type                | Description                                       |
| --------------- | ------------------- | ------------------------------------------------- |
| **`traceName`** | <code>string</code> | Name of the trace that was set with `startTrace`. |


#### IncrementMetricOptions

| Prop              | Type                | Description                                                    |
| ----------------- | ------------------- | -------------------------------------------------------------- |
| **`traceName`**   | <code>string</code> | Name of the trace that was set with `startTrace`.              |
| **`metricName`**  | <code>string</code> | Name of the metric to be incremented.                          |
| **`incrementBy`** | <code>number</code> | Amount by which the metric has to be incremented. Default: `1` |


#### SetPerformanceCollectionEnabledOptions

| Prop          | Type                 | Description                               |
| ------------- | -------------------- | ----------------------------------------- |
| **`enabled`** | <code>boolean</code> | Should performance monitoring be enabled. |


#### IsPerformanceCollectionEnabledResult

| Prop          | Type                 | Description                                                     |
| ------------- | -------------------- | --------------------------------------------------------------- |
| **`enabled`** | <code>boolean</code> | `true` if performance monitoring is enabled, otherwise `false`. |

</docgen-api>

## Changelog

See [CHANGELOG.md](/packages/performance/CHANGELOG.md).

## License

See [LICENSE](/packages/performance/LICENSE).
