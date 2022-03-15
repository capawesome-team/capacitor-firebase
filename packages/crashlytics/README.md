# @capacitor-firebase/crashlytics

⚡️ Capacitor plugin for Firebase Crashlytics.

## Installation

```bash
npm install @capacitor-firebase/crashlytics
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://firebase.google.com/docs/android/setup) / [iOS](https://firebase.google.com/docs/ios/setup)).

### Android

See [Add the Firebase Crashlytics plugin to your app](https://firebase.google.com/docs/crashlytics/get-started?platform=android#add-plugin) and follow the instructions to set up your app correctly.

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$firebaseCrashlyticsVersion` version of `com.google.firebase:firebase-crashlytics` (default: `18.1.0`)

### iOS

See [Set up Xcode to automatically upload dSYM files](https://firebase.google.com/docs/crashlytics/get-started?platform=ios#set-up-dsym-uploading) and follow the instructions to set up Xcode correctly.  
**Attention**: The path used in section `4.c` of the guide should be:

```shell
"${PODS_ROOT}/FirebaseCrashlytics/run"
```

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-firebase-plugin-demo](https://github.com/robingenz/capacitor-firebase-plugin-demo)

## Usage

```typescript
import { FirebaseCrashlytics } from '@capacitor-firebase/crashlytics';

const crash = async () => {
  await FirebaseCrashlytics.crash({ message: 'Test' });
};

const setCustomKey = async () => {
  await FirebaseCrashlytics.setCustomKey({
    key: 'page',
    value: 'home',
    type: 'string',
  });
};

const setUserId = async () => {
  await FirebaseCrashlytics.setUserId({
    userId: '123',
  });
};

const log = async () => {
  await FirebaseCrashlytics.log({
    message: 'Test',
  });
};

const setEnabled = async () => {
  await FirebaseCrashlytics.setEnabled({
    enabled: true,
  });
};

const isEnabled = async () => {
  const { enabled } = await FirebaseCrashlytics.isEnabled();
  return enabled;
};

const didCrashOnPreviousExecution = async () => {
  const { crashed } = await FirebaseCrashlytics.didCrashOnPreviousExecution();
  return crashed;
};

const sendUnsentReports = async () => {
  await FirebaseCrashlytics.sendUnsentReports();
};

const deleteUnsentReports = async () => {
  await FirebaseCrashlytics.deleteUnsentReports();
};

const recordException = async () => {
  await FirebaseCrashlytics.recordException({
    message: 'This is a non-fatal message.',
  });
};
```

## API

<docgen-index>

* [`crash(...)`](#crash)
* [`setCustomKey(...)`](#setcustomkey)
* [`setUserId(...)`](#setuserid)
* [`log(...)`](#log)
* [`setEnabled(...)`](#setenabled)
* [`isEnabled()`](#isenabled)
* [`didCrashOnPreviousExecution()`](#didcrashonpreviousexecution)
* [`sendUnsentReports()`](#sendunsentreports)
* [`deleteUnsentReports()`](#deleteunsentreports)
* [`recordException(...)`](#recordexception)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### crash(...)

```typescript
crash(options: CrashOptions) => Promise<void>
```

Forces a crash to test the implementation.

Only available for Android and iOS.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#crashoptions">CrashOptions</a></code> |

--------------------


### setCustomKey(...)

```typescript
setCustomKey(options: SetCustomKeyOptions) => Promise<void>
```

Sets a custom key and value that is associated with subsequent fatal and non-fatal reports.

Only available for Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#setcustomkeyoptions">SetCustomKeyOptions</a></code> |

--------------------


### setUserId(...)

```typescript
setUserId(options: SetUserIdOptions) => Promise<void>
```

Sets a user ID (identifier) that is associated with subsequent fatal and non-fatal reports.

Only available for Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#setuseridoptions">SetUserIdOptions</a></code> |

--------------------


### log(...)

```typescript
log(options: LogOptions) => Promise<void>
```

Adds a custom log message that is sent with your crash data to give yourself more context for the events leading up to a crash.

Only available for Android and iOS.

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code><a href="#logoptions">LogOptions</a></code> |

--------------------


### setEnabled(...)

```typescript
setEnabled(options: SetEnabledOptions) => Promise<void>
```

Enables/disables automatic data collection.
The value does not apply until the next run of the app.

Only available for Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#setenabledoptions">SetEnabledOptions</a></code> |

--------------------


### isEnabled()

```typescript
isEnabled() => Promise<IsEnabledResult>
```

Returns whether or not automatic data collection is enabled.

Only available for iOS.

**Returns:** <code>Promise&lt;<a href="#isenabledresult">IsEnabledResult</a>&gt;</code>

--------------------


### didCrashOnPreviousExecution()

```typescript
didCrashOnPreviousExecution() => Promise<DidCrashOnPreviousExecutionResult>
```

Returns whether the app crashed during the previous execution.

Only available for Android and iOS.

**Returns:** <code>Promise&lt;<a href="#didcrashonpreviousexecutionresult">DidCrashOnPreviousExecutionResult</a>&gt;</code>

--------------------


### sendUnsentReports()

```typescript
sendUnsentReports() => Promise<void>
```

Uploads any unsent reports to Crashlytics.
When automatic data collection is enabled, Crashlytics automatically uploads reports at startup.

Only available for Android and iOS.

--------------------


### deleteUnsentReports()

```typescript
deleteUnsentReports() => Promise<void>
```

Deletes any unsent reports on the device.

Only available for Android and iOS.

--------------------


### recordException(...)

```typescript
recordException(options: RecordExceptionOptions) => Promise<void>
```

Records a non-fatal report to send to Crashlytics.

Only available for Android and iOS.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#recordexceptionoptions">RecordExceptionOptions</a></code> |

--------------------


### Interfaces


#### CrashOptions

| Prop          | Type                |
| ------------- | ------------------- |
| **`message`** | <code>string</code> |


#### SetCustomKeyOptions

| Prop        | Type                                                                         |
| ----------- | ---------------------------------------------------------------------------- |
| **`key`**   | <code>string</code>                                                          |
| **`value`** | <code>string \| number \| boolean</code>                                     |
| **`type`**  | <code>'string' \| 'boolean' \| 'long' \| 'double' \| 'int' \| 'float'</code> |


#### SetUserIdOptions

| Prop         | Type                |
| ------------ | ------------------- |
| **`userId`** | <code>string</code> |


#### LogOptions

| Prop          | Type                |
| ------------- | ------------------- |
| **`message`** | <code>string</code> |


#### SetEnabledOptions

| Prop          | Type                 |
| ------------- | -------------------- |
| **`enabled`** | <code>boolean</code> |


#### IsEnabledResult

| Prop          | Type                 |
| ------------- | -------------------- |
| **`enabled`** | <code>boolean</code> |


#### DidCrashOnPreviousExecutionResult

| Prop          | Type                 |
| ------------- | -------------------- |
| **`crashed`** | <code>boolean</code> |


#### RecordExceptionOptions

| Prop          | Type                | Description                                                        |
| ------------- | ------------------- | ------------------------------------------------------------------ |
| **`message`** | <code>string</code> |                                                                    |
| **`code`**    | <code>number</code> | Error code within a specific error domain. Only available for iOS. |
| **`domain`**  | <code>string</code> | A string containing the error domain. Only available for iOS.      |

</docgen-api>

## Test your implementation

[Here](https://firebase.google.com/docs/crashlytics/force-a-crash) you can find more information on how to test the Firebase Crashlytics implementation.
Among other things, you will find information on how to correctly [adjust the project's debug settings](https://firebase.google.com/docs/crashlytics/force-a-crash?platform=ios#adjust_your_projects_debug_settings) under iOS and how to [test it out](https://firebase.google.com/docs/crashlytics/force-a-crash?platform=ios#test_it_out).

If you get obfuscated crash reports for iOS, make sure you have [initialized Crashlytics](https://firebase.google.com/docs/crashlytics/get-started?platform=ios#initialize-crashlytics) correctly and take a look at [this guide](https://firebase.google.com/docs/crashlytics/get-deobfuscated-reports?platform=ios), which provides some ways to troubleshoot if Crashlytics can't find your app's dSYM.

## Changelog

See [CHANGELOG.md](./CHANGELOG.md).

## License

See [LICENSE](./LICENSE).

## Credits

This plugin is based on the [Capacitor Community Firebase Crashlytics plugin](https://github.com/capacitor-community/firebase-crashlytics).
Thanks to everyone who contributed to the project!
