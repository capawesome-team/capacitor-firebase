# @capacitor-firebase/performance

Unofficial Capacitor plugin for [Firebase Performance Monitoring](https://firebase.google.com/docs/perf-mon).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The Firebase Performance Monitoring plugin is typically used to gain insight into the performance characteristics of your app, for example:

- **Custom code traces**: Measure how long specific tasks in your app take, such as loading data or processing an image.
- **Custom metrics**: Count performance-related events within a trace, such as cache hits or retries.
- **Performance segmentation**: Add custom attributes to your traces, such as a user id, to segment your performance data.
- **Privacy compliance**: Enable or disable performance monitoring at runtime, for example based on the user's consent.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 7.x.x          | 7.x.x             | Deprecated     |
| 6.x.x          | 6.x.x             | Deprecated     |
| 5.x.x          | 5.x.x             | Deprecated     |
| 1.x.x          | 4.x.x             | Deprecated     |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capacitor-firebase/performance` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capacitor-firebase/performance firebase
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#android) / [iOS](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#ios) / [Web](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#web)).

### Android

See [Add the Performance Monitoring plugin to your app](https://firebase.google.com/docs/perf-mon/get-started-android#add-perfmon-plugin) and follow the instructions to set up your app correctly.

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$firebasePerfVersion` version of `com.google.firebase:firebase-perf` (default: `22.0.4`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

### iOS

#### Swift Package Manager

Add the following to your `capacitor.config.json` (or `capacitor.config.ts`) to avoid a [SwiftPM package identity collision](https://github.com/capawesome-team/capacitor-firebase/issues/959):

```json
{
  "experimental": {
    "ios": {
      "spm": {
        "packageOptions": {
          "@capacitor-firebase/performance": {
            "symlink": true
          }
        }
      }
    }
  }
}
```

**Attention**: SPM `packageOptions` support requires Capacitor CLI **8.4.0+**.

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-firebase-plugin-demo](https://github.com/robingenz/capacitor-firebase-plugin-demo)

## Usage

The following examples show how to start and stop traces, record custom metrics, add custom attributes, toggle performance monitoring, and record a trace manually.

### Start and stop a trace

Start a custom code trace to measure how long a specific task in your app takes and stop it as soon as the task is completed:

```typescript
import { FirebasePerformance } from '@capacitor-firebase/performance';

const startTrace = async () => {
  await FirebasePerformance.startTrace({ traceName: 'test_trace' });
};

const stopTrace = async () => {
  await FirebasePerformance.stopTrace({ traceName: 'test_trace' });
};
```

### Record custom metrics

Set the value of a custom metric for a trace, increment it atomically, or read its current value. Note that metric values are floored down to the nearest integer:

```typescript
import { FirebasePerformance } from '@capacitor-firebase/performance';

const putMetric = async () => {
  await FirebasePerformance.putMetric({
    traceName: 'test_trace',
    metricName: 'item_cache_hit',
    num: 1,
  });
};

const incrementMetric = async () => {
  await FirebasePerformance.incrementMetric({
    traceName: 'test_trace',
    metricName: 'item_cache_hit',
    incrementBy: 1,
  });
};

const getMetric = async () => {
  const result = await FirebasePerformance.getMetric({
    traceName: 'test_trace',
    metricName: 'item_cache_hit',
  });
  return result.value;
};
```

### Add custom attributes to a trace

Set custom attributes on a trace, for example a user id, to segment your performance data. You can also read or remove them again:

```typescript
import { FirebasePerformance } from '@capacitor-firebase/performance';

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
```

### Enable or disable performance monitoring

Enable or disable performance monitoring at runtime, for example based on the user's consent. The setting is applied with the next start of the app:

```typescript
import { FirebasePerformance } from '@capacitor-firebase/performance';

const setEnabled = async () => {
  await FirebasePerformance.setEnabled({ enabled: true });
};

const isEnabled = async () => {
  const result = await FirebasePerformance.isEnabled();
  return result.enabled;
};
```

### Record a trace manually

Record a trace for a task that has already been completed by providing its start time and duration. Only available on Web:

```typescript
import { FirebasePerformance } from '@capacitor-firebase/performance';

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

## FAQ

### How do I measure the duration of a specific task in my app?

Start a custom code trace with `startTrace(...)` before the task begins and stop it with `stopTrace(...)` as soon as the task is completed, as shown in the [usage example](#start-and-stop-a-trace) above. You can enrich the trace with custom metrics and attributes before stopping it.

### What are the requirements for custom trace names?

Names for custom code traces must not contain leading or trailing whitespace, must not start with an underscore (`_`), and may have a maximum length of 100 characters.

### What is the difference between a metric and an attribute?

A metric is a numeric value of a trace, for example a counter for cache hits, which is set with `putMetric(...)` or incremented with `incrementMetric(...)`. Note that metric values are floored down to the nearest integer. An attribute is a string value of a trace, for example a user id, which is set with `putAttribute(...)` and can be used to segment your performance data.

### Can users disable performance monitoring at runtime?

Yes, call `setEnabled(...)` to enable or disable performance monitoring, for example based on the user's consent. The setting is applied with the next start of the app. Use `isEnabled()` to determine whether performance monitoring is currently enabled or disabled.

### Why does the `record` method not work on Android and iOS?

The `record(...)` method is only available on Web. On Android and iOS, use `startTrace(...)` and `stopTrace(...)` to measure the duration of a task instead.

## Related Plugins

- [Firebase Analytics](https://capawesome.io/docs/sdks/capacitor/firebase/analytics/): Log events and user properties with Firebase Analytics.
- [Firebase Crashlytics](https://capawesome.io/docs/sdks/capacitor/firebase/crashlytics/): Track and analyze app crashes with Firebase Crashlytics.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/performance/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/performance/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
