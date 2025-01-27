# @capacitor-firebase/crashlytics

Unofficial Capacitor plugin for [Firebase Crashlytics](https://firebase.google.com/docs/crashlytics/).[^1]

## Installation

```bash
npm install @capacitor-firebase/crashlytics
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#android) / [iOS](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#ios)).

### Android

#### Crashlytics Gradle plugin

First, add the dependency for the Crashlytics Gradle plugin to your root-level (project-level) Gradle file (`<project>/build.gradle`):

```diff
buildscript {
    dependencies {
+       // Add the dependency for the Crashlytics Gradle plugin
+       classpath 'com.google.firebase:firebase-crashlytics-gradle:2.9.9'
    }
}
```

Add the Crashlytics Gradle plugin to your module (app-level) Gradle file (usually `<project>/<app-module>/build.gradle`):

```gradle
apply plugin: 'com.google.firebase.crashlytics'
```

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$firebaseCrashlyticsVersion` version of `com.google.firebase:firebase-crashlytics` (default: `19.4.0`)

### iOS

To generate human readable crash reports, Crashlytics needs your project's debug symbol (dSYM) files.
The following steps describe how to automatically upload dSYM files to Firebase whenever you build your app:

1. Open your project's Xcode workspace, then select its project file in the left navigator.
2. From the **TARGETS** list, select your main build target.
3. Click the **Build Settings** tab, then complete the following steps so that Xcode produces dSYMs for your builds.
   1. Click **All**, then search for `debug information format`.
   2. Set **Debug Information Format** to `DWARF with dSYM File` for all your build types.
4. Click the **Build Phases** tab and complete the following steps:
   1. Click the **+** button, then select **New Run Script Phase**.
   2. Expand the new **Run Script** section.
   3. In the script field (located under the *Shell* label), add the following run script:
      ```
      "${PODS_ROOT}/FirebaseCrashlytics/run"
      ```
   4. In the Input Files section, add the paths for the locations of the following files:
      ```
      ${DWARF_DSYM_FOLDER_PATH}/${DWARF_DSYM_FILE_NAME}/Contents/Resources/DWARF/${TARGET_NAME}
      ```
      ```
      $(SRCROOT)/$(BUILT_PRODUCTS_DIR)/$(INFOPLIST_PATH)
      ```

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-firebase-plugin-demo](https://github.com/robingenz/capacitor-firebase-plugin-demo)

## Starter templates

The following starter templates are available:

- [Ionstarter Angular Firebase](https://ionstarter.dev/)

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

import * as StackTrace from 'stacktrace-js';
const recordExceptionWithStacktrace = async (error: Error) => {
  const stacktrace = await StackTrace.fromError(error);
  await FirebaseCrashlytics.recordException({
    message: 'This is a non-fatal message.',
    stacktrace,
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

**Since:** 0.1.0

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

**Since:** 0.1.0

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

**Since:** 0.1.0

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

**Since:** 0.1.0

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

**Since:** 0.1.0

--------------------


### isEnabled()

```typescript
isEnabled() => Promise<IsEnabledResult>
```

Returns whether or not automatic data collection is enabled.

Only available for iOS.

**Returns:** <code>Promise&lt;<a href="#isenabledresult">IsEnabledResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### didCrashOnPreviousExecution()

```typescript
didCrashOnPreviousExecution() => Promise<DidCrashOnPreviousExecutionResult>
```

Returns whether the app crashed during the previous execution.

Only available for Android and iOS.

**Returns:** <code>Promise&lt;<a href="#didcrashonpreviousexecutionresult">DidCrashOnPreviousExecutionResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### sendUnsentReports()

```typescript
sendUnsentReports() => Promise<void>
```

Uploads any unsent reports to Crashlytics at next startup.

When automatic data collection is enabled, Crashlytics automatically uploads reports at startup.

Only available for Android and iOS.

**Since:** 0.1.0

--------------------


### deleteUnsentReports()

```typescript
deleteUnsentReports() => Promise<void>
```

Deletes any unsent reports on the device.

Only available for Android and iOS.

**Since:** 0.1.0

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

**Since:** 0.1.0

--------------------


### Interfaces


#### CrashOptions

| Prop          | Type                | Since |
| ------------- | ------------------- | ----- |
| **`message`** | <code>string</code> | 0.1.0 |


#### SetCustomKeyOptions

| Prop        | Type                                                                         | Since |
| ----------- | ---------------------------------------------------------------------------- | ----- |
| **`key`**   | <code>string</code>                                                          | 0.1.0 |
| **`value`** | <code>string \| number \| boolean</code>                                     | 0.1.0 |
| **`type`**  | <code>'string' \| 'boolean' \| 'long' \| 'double' \| 'int' \| 'float'</code> | 0.1.0 |


#### SetUserIdOptions

| Prop         | Type                | Since |
| ------------ | ------------------- | ----- |
| **`userId`** | <code>string</code> | 0.1.0 |


#### LogOptions

| Prop          | Type                | Since |
| ------------- | ------------------- | ----- |
| **`message`** | <code>string</code> | 0.1.0 |


#### SetEnabledOptions

| Prop          | Type                 | Since |
| ------------- | -------------------- | ----- |
| **`enabled`** | <code>boolean</code> | 0.1.0 |


#### IsEnabledResult

| Prop          | Type                 | Since |
| ------------- | -------------------- | ----- |
| **`enabled`** | <code>boolean</code> | 0.1.0 |


#### DidCrashOnPreviousExecutionResult

| Prop          | Type                 | Since |
| ------------- | -------------------- | ----- |
| **`crashed`** | <code>boolean</code> | 0.1.0 |


#### RecordExceptionOptions

| Prop             | Type                      | Description                                                                           | Since |
| ---------------- | ------------------------- | ------------------------------------------------------------------------------------- | ----- |
| **`message`**    | <code>string</code>       |                                                                                       | 0.1.0 |
| **`code`**       | <code>number</code>       | Error code within a specific error domain. Only available for iOS.                    | 0.1.0 |
| **`domain`**     | <code>string</code>       | A string containing the error domain. Only available for iOS.                         | 0.1.0 |
| **`stacktrace`** | <code>StackFrame[]</code> | A stacktrace generated by stacktrace.js. Cannot be combined with `code` and `domain`. | 1.1.0 |


#### StackFrame

Subset of the Stacktrace generated by stacktrace.js.

| Prop               | Type                |
| ------------------ | ------------------- |
| **`lineNumber`**   | <code>number</code> |
| **`fileName`**     | <code>string</code> |
| **`functionName`** | <code>string</code> |

</docgen-api>

## Test your implementation

[Here](https://firebase.google.com/docs/crashlytics/force-a-crash) you can find more information on how to test the Firebase Crashlytics implementation.
Among other things, you will find information on how to correctly [adjust the project's debug settings](https://firebase.google.com/docs/crashlytics/force-a-crash?platform=ios#adjust_your_projects_debug_settings) under iOS and how to [test it out](https://firebase.google.com/docs/crashlytics/force-a-crash?platform=ios#test_it_out).

If you get obfuscated crash reports for iOS, make sure you have [initialized Crashlytics](https://firebase.google.com/docs/crashlytics/get-started?platform=ios#initialize-crashlytics) correctly and take a look at [this guide](https://firebase.google.com/docs/crashlytics/get-deobfuscated-reports?platform=ios), which provides some ways to troubleshoot if Crashlytics can't find your app's dSYM.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/crashlytics/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/crashlytics/LICENSE).

## Credits

This plugin is based on the [Capacitor Community Firebase Crashlytics](https://github.com/capacitor-community/firebase-crashlytics) plugin.
Thanks to everyone who contributed to the project!

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
