# @capgo/capacitor-firebase-performance

Unofficial Capacitor plugin for [Firebase Performance Monitoring](https://firebase.google.com/docs/perf-mon).[^1]

<div class="capgo-z29o10a">
  <a href="https://capgo.app/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capgo Cloud" src="https://capgo.app/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Installation

```bash
npm install @capgo/capacitor-firebase-performance firebase
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://github.com/cap-go/capacitor-firebase/blob/main/docs/firebase-setup.md#android) / [iOS](https://github.com/cap-go/capacitor-firebase/blob/main/docs/firebase-setup.md#ios) / [Web](https://github.com/cap-go/capacitor-firebase/blob/main/docs/firebase-setup.md#web)).

### Android

See [Add the Performance Monitoring plugin to your app](https://firebase.google.com/docs/perf-mon/get-started-android#add-perfmon-plugin) and follow the instructions to set up your app correctly.

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$firebasePerfVersion` version of `com.google.firebase:firebase-perf` (default: `21.0.4`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-firebase-plugin-demo](https://github.com/robingenz/capacitor-firebase-plugin-demo)

## Usage

```typescript
import { FirebasePerformance } from '@capgo/capacitor-firebase-performance';

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

const putAttribute = async () => {
  await FirebasePerformance.putAttribute({
    traceName: 'test_trace',
    attribute: 'user_id',
    value: '123',
  });
};

const getAttribute = async () => {
  const result = await FirebasePerformance.getAttribute({
    traceName: 'test_trace',
    attribute: 'user_id',
  });
  return result.attributes;
};

const getAttributes = async () => {
  const result = await FirebasePerformance.getAttributes({ traceName: 'test_trace' });
  return result.attributes;
};

const removeAttribute = async () => {
  await FirebasePerformance.removeAttribute({
    traceName: 'test_trace',
    attribute: 'user_id',
  });
};

const putMetric = async () => {
  await FirebasePerformance.putMetric({
    traceName: 'test_trace',
    metricName: 'item_cache_hit',
    num: 1,
  });
};

const getMetric = async () => {
  const result = await FirebasePerformance.getMetric({
    traceName: 'test_trace',
    metricName: 'item_cache_hit',
  });
  return result.value;
};

const record = async () => {
  await FirebasePerformance.record({
    traceName: 'test_trace',
    startTime: Date.now(),
    duration: 1000,
    options: {
      metrics: {
        item_cache_hit: 1,
      },
      attributes: {
        user_id: '123',
      },
    },
  });
};
```

## API

<docgen-index>

* [`startTrace(...)`](#starttrace)
* [`stopTrace(...)`](#stoptrace)
* [`incrementMetric(...)`](#incrementmetric)
* [`setEnabled(...)`](#setenabled)
* [`isEnabled()`](#isenabled)
* [`putAttribute(...)`](#putattribute)
* [`getAttribute(...)`](#getattribute)
* [`getAttributes(...)`](#getattributes)
* [`removeAttribute(...)`](#removeattribute)
* [`putMetric(...)`](#putmetric)
* [`getMetric(...)`](#getmetric)
* [`record(...)`](#record)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

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


### putAttribute(...)

```typescript
putAttribute(options: PutAttributeOptions) => Promise<void>
```

Sets a custom attribute of a trace to a given value.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#putattributeoptions">PutAttributeOptions</a></code> |

**Since:** 6.3.0

--------------------


### getAttribute(...)

```typescript
getAttribute(options: GetAttributeOptions) => Promise<GetAttributeResult>
```

Returns the value of a custom attribute of a trace.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#getattributeoptions">GetAttributeOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getattributeresult">GetAttributeResult</a>&gt;</code>

**Since:** 6.3.0

--------------------


### getAttributes(...)

```typescript
getAttributes(options: GetAttributesOptions) => Promise<GetAttributesResult>
```

Gets the all the custom attributes of a trace with their values.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#getattributesoptions">GetAttributesOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getattributesresult">GetAttributesResult</a>&gt;</code>

**Since:** 6.3.0

--------------------


### removeAttribute(...)

```typescript
removeAttribute(options: RemoveAttributeOptions) => Promise<void>
```

Removes a custom attribute from a trace given its name.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#getattributeoptions">GetAttributeOptions</a></code> |

**Since:** 6.3.0

--------------------


### putMetric(...)

```typescript
putMetric(options: PutMetricOptions) => Promise<void>
```

Sets the value of a custom metric.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#putmetricoptions">PutMetricOptions</a></code> |

**Since:** 6.3.0

--------------------


### getMetric(...)

```typescript
getMetric(options: GetMetricOptions) => Promise<GetMetricResult>
```

Get the value of a custom metric by name.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#getmetricoptions">GetMetricOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getmetricresult">GetMetricResult</a>&gt;</code>

**Since:** 6.3.0

--------------------


### record(...)

```typescript
record(options: RecordOptions) => Promise<void>
```

Records a trace given its name and options.

Only available on web.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#recordoptions">RecordOptions</a></code> |

**Since:** 6.3.0

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


#### PutAttributeOptions

| Prop            | Type                | Description                             | Since |
| --------------- | ------------------- | --------------------------------------- | ----- |
| **`traceName`** | <code>string</code> | Name of the trace to set its attribute. | 6.3.0 |
| **`attribute`** | <code>string</code> | Name of the attribute to set its value. | 6.3.0 |
| **`value`**     | <code>string</code> | The value to set to the attribute.      | 6.3.0 |


#### GetAttributeResult

| Prop        | Type                        | Description                        | Since |
| ----------- | --------------------------- | ---------------------------------- | ----- |
| **`value`** | <code>string \| null</code> | The value of the custom attribute. | 6.3.0 |


#### GetAttributeOptions

| Prop            | Type                | Description                                  | Since |
| --------------- | ------------------- | -------------------------------------------- | ----- |
| **`traceName`** | <code>string</code> | Name of the trace to set its attribute.      | 6.3.0 |
| **`attribute`** | <code>string</code> | Name of the attribute to retrieve its value. | 6.3.0 |


#### GetAttributesResult

| Prop             | Type                                    | Description                                                  | Since |
| ---------------- | --------------------------------------- | ------------------------------------------------------------ | ----- |
| **`attributes`** | <code>{ [key: string]: string; }</code> | A map of all custom attributes of a trace with their values. | 6.3.0 |


#### GetAttributesOptions

| Prop            | Type                | Description                              | Since |
| --------------- | ------------------- | ---------------------------------------- | ----- |
| **`traceName`** | <code>string</code> | Name of the trace to get its attributes. | 6.3.0 |


#### PutMetricOptions

| Prop             | Type                | Description                                                                              | Since |
| ---------------- | ------------------- | ---------------------------------------------------------------------------------------- | ----- |
| **`traceName`**  | <code>string</code> | Name of the trace to set its metric.                                                     | 6.3.0 |
| **`metricName`** | <code>string</code> | The metric name.                                                                         | 6.3.0 |
| **`num`**        | <code>number</code> | The value to set for the metric. The given value is floored down to the nearest integer. | 6.3.0 |


#### GetMetricResult

| Prop        | Type                | Description                        | Since |
| ----------- | ------------------- | ---------------------------------- | ----- |
| **`value`** | <code>number</code> | The value of the metric if exists. | 6.3.0 |


#### GetMetricOptions

| Prop             | Type                | Description                          | Since |
| ---------------- | ------------------- | ------------------------------------ | ----- |
| **`traceName`**  | <code>string</code> | Name of the trace to get its metric. | 6.3.0 |
| **`metricName`** | <code>string</code> | The metric name.                     | 6.3.0 |


#### RecordOptions

| Prop            | Type                                                                                            | Description                                                                   | Since |
| --------------- | ----------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------- | ----- |
| **`traceName`** | <code>string</code>                                                                             | Name of the trace to record.                                                  | 6.3.0 |
| **`startTime`** | <code>number</code>                                                                             | Start time of the trace since epoch in milliseconds.                          | 6.3.0 |
| **`duration`**  | <code>number</code>                                                                             | The duration of the trace in milliseconds.                                    | 6.3.0 |
| **`options`**   | <code>{ metrics?: { [key: string]: number; }; attributes?: { [key: string]: string; }; }</code> | An optional object that holds optional maps of custom metrics and attributes. | 6.3.0 |


### Type Aliases


#### RemoveAttributeOptions

<code><a href="#getattributeoptions">GetAttributeOptions</a></code>

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/cap-go/capacitor-firebase/blob/main/packages/performance/CHANGELOG.md).

## License

See [LICENSE](https://github.com/cap-go/capacitor-firebase/blob/main/packages/performance/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
