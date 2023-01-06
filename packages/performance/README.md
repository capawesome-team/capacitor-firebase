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

- `$firebasePerfVersion` version of `com.google.firebase:firebase-perf` (default: `20.3.0`)

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

const setEnabled = async () => {
  await FirebasePerformance.setEnabled({ enabled: true });
};

const isEnabled = async () => {
  const result = await FirebasePerformance.isEnabled();
  return result.enabled;
};
```

## API

<docgen-index>

* [`startTrace(...)`](#starttrace)
* [`stopTrace(...)`](#stoptrace)
* [`incrementMetric(...)`](#incrementmetric)
* [`setEnabled(...)`](#setenabled)
* [`isEnabled()`](#isenabled)
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

**Since:** 0.1.0

--------------------


### stopTrace(...)

```typescript
stopTrace(options: StopTraceOptions) => Promise<void>
```

Stops a trace.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#stoptraceoptions">StopTraceOptions</a></code> |

**Since:** 0.1.0

--------------------


### incrementMetric(...)

```typescript
incrementMetric(options: IncrementMetricOptions) => Promise<void>
```

Atomically increments the metric with the given name for the selected trace by the `incrementBy` value.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#incrementmetricoptions">IncrementMetricOptions</a></code> |

**Since:** 0.1.0

--------------------


### setEnabled(...)

```typescript
setEnabled(options: SetEnabledOptions) => Promise<void>
```

Enables or disables performance monitoring.
Will be applied with the next start of the app.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#setenabledoptions">SetEnabledOptions</a></code> |

**Since:** 0.1.0

--------------------


### isEnabled()

```typescript
isEnabled() => Promise<IsEnabledResult>
```

Determines whether performance monitoring is enabled or disabled.

**Returns:** <code>Promise&lt;<a href="#isenabledresult">IsEnabledResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### StartTraceOptions

| Prop            | Type                | Description                                                                                                                                                                                     | Since |
| --------------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`traceName`** | <code>string</code> | Custom trace name. Names for custom code traces must meet the following requirements: no leading or trailing whitespace, no leading underscore (_) character, and max length is 100 characters. | 0.1.0 |


#### StopTraceOptions

| Prop            | Type                | Description                                       | Since |
| --------------- | ------------------- | ------------------------------------------------- | ----- |
| **`traceName`** | <code>string</code> | Name of the trace that was set with `startTrace`. | 0.1.0 |


#### IncrementMetricOptions

| Prop              | Type                | Description                                       | Default        | Since |
| ----------------- | ------------------- | ------------------------------------------------- | -------------- | ----- |
| **`traceName`**   | <code>string</code> | Name of the trace that was set with `startTrace`. |                | 0.1.0 |
| **`metricName`**  | <code>string</code> | Name of the metric to be incremented.             |                | 0.1.0 |
| **`incrementBy`** | <code>number</code> | Amount by which the metric has to be incremented. | <code>1</code> | 0.1.0 |


#### SetEnabledOptions

| Prop          | Type                 | Description                               | Since |
| ------------- | -------------------- | ----------------------------------------- | ----- |
| **`enabled`** | <code>boolean</code> | Should performance monitoring be enabled. | 0.1.0 |


#### IsEnabledResult

| Prop          | Type                 | Description                                                     | Since |
| ------------- | -------------------- | --------------------------------------------------------------- | ----- |
| **`enabled`** | <code>boolean</code> | `true` if performance monitoring is enabled, otherwise `false`. | 0.1.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](/packages/performance/CHANGELOG.md).

## License

See [LICENSE](/packages/performance/LICENSE).
