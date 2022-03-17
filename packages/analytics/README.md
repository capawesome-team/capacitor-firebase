# @capacitor-firebase/analytics

⚡️ Capacitor plugin for Firebase Analytics.

## Installation

```bash
npm install @capacitor-firebase/analytics firebase
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://firebase.google.com/docs/android/setup) / [iOS](https://firebase.google.com/docs/ios/setup)).

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$firebaseAnalyticsVersion` version of `com.google.firebase:firebase-analytics` (default: `20.1.0`)

### iOS

Add the `CapacitorFirebaseAnalytics/Analytics` pod to your `Podfile` (usually `ios/App/Podfile`):

```diff
target 'App' do
capacitor_pods
# Add your Pods here
+  pod 'CapacitorFirebaseAnalytics/Analytics', :path => '../../node_modules/@capacitor-firebase/analytics'
end
```

#### Disable Analytics data collection

See [Disable Analytics data collection](https://firebase.google.com/docs/analytics/configure-data-collection?platform=ios#disable_data_collection) if you want to disable Analytics data collection.

#### Disable IDFA collection

If you want to install Firebase without any IDFA collection capability, use the `CapacitorFirebaseAnalytics/AnalyticsWithoutAdIdSupport` pod in place of pod `CapacitorFirebaseAnalytics/Firebase`:

```diff
target 'App' do
capacitor_pods
# Add your Pods here
+  pod 'CapacitorFirebaseAnalytics/AnalyticsWithoutAdIdSupport', :path => '../../node_modules/@capacitor-firebase/analytics'
end
```

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-firebase-plugin-demo](https://github.com/robingenz/capacitor-firebase-plugin-demo)

## Usage

```typescript
import { FirebaseAnalytics } from '@capacitor-firebase/analytics';

const logEvent = async () => {
  await FirebaseAnalytics.logEvent({
    name: 'sign_up',
    params: { method: 'password' },
  });
};
```

## API

<docgen-index>

* [`setUserId(...)`](#setuserid)
* [`setUserProperty(...)`](#setuserproperty)
* [`setCurrentScreen(...)`](#setcurrentscreen)
* [`logEvent(...)`](#logevent)
* [`setSessionTimeoutDuration(...)`](#setsessiontimeoutduration)
* [`setEnabled(...)`](#setenabled)
* [`isEnabled()`](#isenabled)
* [`resetAnalyticsData()`](#resetanalyticsdata)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

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


### setUserProperty(...)

```typescript
setUserProperty(options: SetUserPropertyOptions) => Promise<void>
```

Sets a custom user property to a given value.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setuserpropertyoptions">SetUserPropertyOptions</a></code> |

--------------------


### setCurrentScreen(...)

```typescript
setCurrentScreen(options: SetCurrentScreenOptions) => Promise<void>
```

Sets the current screen name.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setcurrentscreenoptions">SetCurrentScreenOptions</a></code> |

--------------------


### logEvent(...)

```typescript
logEvent(options: LogEventOptions) => Promise<void>
```

Logs an app event.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#logeventoptions">LogEventOptions</a></code> |

--------------------


### setSessionTimeoutDuration(...)

```typescript
setSessionTimeoutDuration(options: SetSessionTimeoutDurationOptions) => Promise<void>
```

Sets the duration of inactivity that terminates the current session.

Only available for Android and iOS.

| Param         | Type                                                                                          |
| ------------- | --------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setsessiontimeoutdurationoptions">SetSessionTimeoutDurationOptions</a></code> |

--------------------


### setEnabled(...)

```typescript
setEnabled(options: SetEnabledOptions) => Promise<void>
```

Enables/disables automatic data collection.
The value does not apply until the next run of the app.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#setenabledoptions">SetEnabledOptions</a></code> |

--------------------


### isEnabled()

```typescript
isEnabled() => Promise<IsEnabledResult>
```

Returns whether or not automatic data collection is enabled.

Only available for Web.

**Returns:** <code>Promise&lt;<a href="#isenabledresult">IsEnabledResult</a>&gt;</code>

--------------------


### resetAnalyticsData()

```typescript
resetAnalyticsData() => Promise<void>
```

Clears all analytics data for this app from the device.
Resets the app instance id.

Only available for Android and iOS.

--------------------


### Interfaces


#### SetUserIdOptions

| Prop         | Type                        |
| ------------ | --------------------------- |
| **`userId`** | <code>string \| null</code> |


#### SetUserPropertyOptions

| Prop        | Type                        |
| ----------- | --------------------------- |
| **`key`**   | <code>string</code>         |
| **`value`** | <code>string \| null</code> |


#### SetCurrentScreenOptions

| Prop                      | Type                        |
| ------------------------- | --------------------------- |
| **`screenName`**          | <code>string \| null</code> |
| **`screenClassOverride`** | <code>string \| null</code> |


#### LogEventOptions

| Prop         | Type                                 | Description                |
| ------------ | ------------------------------------ | -------------------------- |
| **`name`**   | <code>string</code>                  | The event name.            |
| **`params`** | <code>{ [key: string]: any; }</code> | The optional event params. |


#### SetSessionTimeoutDurationOptions

| Prop           | Type                | Description                                       |
| -------------- | ------------------- | ------------------------------------------------- |
| **`duration`** | <code>number</code> | Duration in seconds. Default: `1800` (30 minutes) |


#### SetEnabledOptions

| Prop          | Type                 |
| ------------- | -------------------- |
| **`enabled`** | <code>boolean</code> |


#### IsEnabledResult

| Prop          | Type                 |
| ------------- | -------------------- |
| **`enabled`** | <code>boolean</code> |

</docgen-api>

## Changelog

See [CHANGELOG.md](./CHANGELOG.md).

## License

See [LICENSE](./LICENSE).
