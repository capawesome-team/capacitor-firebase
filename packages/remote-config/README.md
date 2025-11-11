# @capacitor-firebase/remote-config

Unofficial Capacitor plugin for [Firebase Remote Config](https://firebase.google.com/docs/remote-config).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Installation

```bash
npm install @capacitor-firebase/remote-config firebase
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#android) / [iOS](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#ios) / [Web](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#web)).

### Android

Google Analytics is required for the [conditional targeting of app instances](https://firebase.google.com/docs/remote-config/parameters#conditions_rules_and_conditional_values) to user properties and audiences. Make sure that you install the [Capacitor Firebase Analytics](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/analytics) plugin in your project.

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$firebaseConfigVersion` version of `com.google.firebase:firebase-config` (default: `22.1.0`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-firebase-plugin-demo](https://github.com/robingenz/capacitor-firebase-plugin-demo)

## Starter templates

The following starter templates are available:

- [Ionstarter Angular Firebase](https://ionstarter.dev/)

## Usage

```typescript
import { FirebaseRemoteConfig } from '@capacitor-firebase/remote-config';

const activate = async () => {
  await FirebaseRemoteConfig.activate();
};

const fetchAndActivate = async () => {
  await FirebaseRemoteConfig.fetchAndActivate();
};

const fetchConfig = async () => {
  await FirebaseRemoteConfig.fetchConfig({
    minimumFetchIntervalInSeconds: 1200,
  });
};

const getBoolean = async () => {
  const { value } = await FirebaseRemoteConfig.getBoolean({
    key: 'is_sale',
  });
  return value;
};

const getNumber = async () => {
  const { value } = await FirebaseRemoteConfig.getNumber({
    key: 'upcoming_maintenance',
  });
  return value;
};

const getString = async () => {
  const { value } = await FirebaseRemoteConfig.getString({
    key: 'license_key',
  });
  return value;
};

const setSettings = async () => {
  await FirebaseRemoteConfig.setSettings({
    fetchTimeoutInSeconds: 10,
    minimumFetchIntervalInSeconds: 0,
  });
};

const addConfigUpdateListener = async () => {
  const callbackId = await FirebaseRemoteConfig.addConfigUpdateListener(
    (event, error) => {
      if (error) {
        console.error(error);
      } else {
        console.log(event);
      }
    }
  );
  return callbackId;
};

const removeConfigUpdateListener = async (callbackId: string) => {
  await FirebaseRemoteConfig.removeConfigUpdateListener({
    callbackId,
  });
};

const removeAllListeners = async () => {
  await FirebaseRemoteConfig.removeAllListeners();
};
```

## API

<docgen-index>

* [`activate()`](#activate)
* [`fetchAndActivate()`](#fetchandactivate)
* [`fetchConfig(...)`](#fetchconfig)
* [`getBoolean(...)`](#getboolean)
* [`getNumber(...)`](#getnumber)
* [`getString(...)`](#getstring)
* [`getInfo()`](#getinfo)
* [`setMinimumFetchInterval(...)`](#setminimumfetchinterval)
* [`setSettings(...)`](#setsettings)
* [`addConfigUpdateListener(...)`](#addconfigupdatelistener)
* [`removeConfigUpdateListener(...)`](#removeconfigupdatelistener)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### activate()

```typescript
activate() => Promise<void>
```

Make the last fetched configuration available to the getters.

**Since:** 1.3.0

--------------------


### fetchAndActivate()

```typescript
fetchAndActivate() => Promise<void>
```

Perform fetch and activate operations.

**Since:** 1.3.0

--------------------


### fetchConfig(...)

```typescript
fetchConfig(options?: FetchConfigOptions | undefined) => Promise<void>
```

Fetch and cache configuration from the Remote Config service.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#fetchconfigoptions">FetchConfigOptions</a></code> |

**Since:** 1.3.0

--------------------


### getBoolean(...)

```typescript
getBoolean(options: GetBooleanOptions) => Promise<GetBooleanResult>
```

Get the value for the given key as a boolean.

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code><a href="#getoptions">GetOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getbooleanresult">GetBooleanResult</a>&gt;</code>

**Since:** 1.3.0

--------------------


### getNumber(...)

```typescript
getNumber(options: GetNumberOptions) => Promise<GetNumberResult>
```

Get the value for the given key as a number.

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code><a href="#getoptions">GetOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getnumberresult">GetNumberResult</a>&gt;</code>

**Since:** 1.3.0

--------------------


### getString(...)

```typescript
getString(options: GetStringOptions) => Promise<GetStringResult>
```

Get the value for the given key as a string.

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code><a href="#getoptions">GetOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getstringresult">GetStringResult</a>&gt;</code>

**Since:** 1.3.0

--------------------


### getInfo()

```typescript
getInfo() => Promise<GetInfoResult>
```

Get information about the last fetch operation.

**Returns:** <code>Promise&lt;<a href="#getinforesult">GetInfoResult</a>&gt;</code>

**Since:** 7.5.0

--------------------


### setMinimumFetchInterval(...)

```typescript
setMinimumFetchInterval(options: SetMinimumFetchIntervalOptions) => Promise<void>
```

Set the minimum fetch interval.

Only available for Web.

| Param         | Type                                                                                      |
| ------------- | ----------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setminimumfetchintervaloptions">SetMinimumFetchIntervalOptions</a></code> |

**Since:** 1.3.0

--------------------


### setSettings(...)

```typescript
setSettings(options: SetSettingsOptions) => Promise<void>
```

Set the remote config settings.

On Android, the settings values are persisted in SharedPreferences.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#setsettingsoptions">SetSettingsOptions</a></code> |

**Since:** 6.2.0

--------------------


### addConfigUpdateListener(...)

```typescript
addConfigUpdateListener(callback: AddConfigUpdateListenerOptionsCallback) => Promise<CallbackId>
```

Add a listener for the config update event.

Only available for Android and iOS.

| Param          | Type                                                                                                      |
| -------------- | --------------------------------------------------------------------------------------------------------- |
| **`callback`** | <code><a href="#addconfigupdatelisteneroptionscallback">AddConfigUpdateListenerOptionsCallback</a></code> |

**Returns:** <code>Promise&lt;string&gt;</code>

**Since:** 5.4.0

--------------------


### removeConfigUpdateListener(...)

```typescript
removeConfigUpdateListener(options: RemoveConfigUpdateListenerOptions) => Promise<void>
```

Remove a listener for the config update event.

Only available for Android and iOS.

| Param         | Type                                                                                            |
| ------------- | ----------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#removeconfigupdatelisteneroptions">RemoveConfigUpdateListenerOptions</a></code> |

**Since:** 5.4.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 5.4.0

--------------------


### Interfaces


#### FetchConfigOptions

| Prop                                | Type                | Description                                                                                                                                                                                                               | Default            | Since |
| ----------------------------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`minimumFetchIntervalInSeconds`** | <code>number</code> | Define the maximum age in seconds of an entry in the config cache before it is considered stale. During development, it's recommended to set a relatively low minimum fetch interval. Only available for Android and iOS. | <code>43200</code> | 1.3.0 |


#### GetBooleanResult

| Prop         | Type                                                      | Description                                                                         | Since |
| ------------ | --------------------------------------------------------- | ----------------------------------------------------------------------------------- | ----- |
| **`value`**  | <code>boolean</code>                                      | The value for the given key as a boolean.                                           | 1.3.0 |
| **`source`** | <code><a href="#getvaluesource">GetValueSource</a></code> | Indicates at which source this value came from. Only available for Android and iOS. | 1.3.0 |


#### GetOptions

| Prop      | Type                | Description                  | Since |
| --------- | ------------------- | ---------------------------- | ----- |
| **`key`** | <code>string</code> | The key of the value to get. | 1.3.0 |


#### GetNumberResult

| Prop         | Type                                                      | Description                                                                         | Since |
| ------------ | --------------------------------------------------------- | ----------------------------------------------------------------------------------- | ----- |
| **`value`**  | <code>number</code>                                       | The value for the given key as a number.                                            | 1.3.0 |
| **`source`** | <code><a href="#getvaluesource">GetValueSource</a></code> | Indicates at which source this value came from. Only available for Android and iOS. | 1.3.0 |


#### GetStringResult

| Prop         | Type                                                      | Description                                                                         | Since |
| ------------ | --------------------------------------------------------- | ----------------------------------------------------------------------------------- | ----- |
| **`value`**  | <code>string</code>                                       | The value for the given key as a string.                                            | 1.3.0 |
| **`source`** | <code><a href="#getvaluesource">GetValueSource</a></code> | Indicates at which source this value came from. Only available for Android and iOS. | 1.3.0 |


#### GetInfoResult

| Prop                  | Type                                                        | Description                                                                                                                      | Since |
| --------------------- | ----------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`lastFetchTime`**   | <code>number</code>                                         | The Unix timestamp in milliseconds of the last successful fetch, or -1 if no fetch has occurred or initialization is incomplete. | 7.5.0 |
| **`lastFetchStatus`** | <code><a href="#lastfetchstatus">LastFetchStatus</a></code> | The status of the last fetch attempt.                                                                                            | 7.5.0 |


#### SetMinimumFetchIntervalOptions

| Prop                                | Type                | Description                                                                                                                                                                           | Default            | Since |
| ----------------------------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`minimumFetchIntervalInSeconds`** | <code>number</code> | Define the maximum age in seconds of an entry in the config cache before it is considered stale. During development, it's recommended to set a relatively low minimum fetch interval. | <code>43200</code> | 1.3.0 |


#### SetSettingsOptions

| Prop                                | Type                | Description                                                                                                                                                                           | Default            | Since |
| ----------------------------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`fetchTimeoutInSeconds`**         | <code>number</code> | Defines the maximum amount of milliseconds to wait for a response when fetching configuration from the Remote Config server.                                                          | <code>60</code>    | 6.2.0 |
| **`minimumFetchIntervalInSeconds`** | <code>number</code> | Define the maximum age in seconds of an entry in the config cache before it is considered stale. During development, it's recommended to set a relatively low minimum fetch interval. | <code>43200</code> | 6.2.0 |


#### AddConfigUpdateListenerOptionsCallbackEvent

| Prop              | Type                  | Description                                                                        | Since |
| ----------------- | --------------------- | ---------------------------------------------------------------------------------- | ----- |
| **`updatedKeys`** | <code>string[]</code> | Parameter keys whose values have been updated from the currently activated values. | 5.4.0 |


#### RemoveConfigUpdateListenerOptions

| Prop     | Type                                              | Description                       | Since |
| -------- | ------------------------------------------------- | --------------------------------- | ----- |
| **`id`** | <code><a href="#callbackid">CallbackId</a></code> | The id of the listener to remove. | 5.4.0 |


### Type Aliases


#### GetBooleanOptions

<code><a href="#getoptions">GetOptions</a></code>


#### GetNumberOptions

<code><a href="#getoptions">GetOptions</a></code>


#### GetStringOptions

<code><a href="#getoptions">GetOptions</a></code>


#### AddConfigUpdateListenerOptionsCallback

<code>(event: <a href="#addconfigupdatelisteneroptionscallbackevent">AddConfigUpdateListenerOptionsCallbackEvent</a> | null, error: any): void</code>


#### CallbackId

<code>string</code>


### Enums


#### GetValueSource

| Members       | Value          | Description                                                                             | Since |
| ------------- | -------------- | --------------------------------------------------------------------------------------- | ----- |
| **`Static`**  | <code>0</code> | Indicates that the value returned is the static default value.                          | 1.3.0 |
| **`Default`** | <code>1</code> | Indicates that the value returned was retrieved from the defaults set by the client.    | 1.3.0 |
| **`Remote`**  | <code>2</code> | Indicates that the value returned was retrieved from the Firebase Remote Config Server. | 1.3.0 |


#### LastFetchStatus

| Members          | Value          |
| ---------------- | -------------- |
| **`NoFetchYet`** | <code>0</code> |
| **`Success`**    | <code>1</code> |
| **`Failure`**    | <code>2</code> |
| **`Throttled`**  | <code>3</code> |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/remote-config/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/remote-config/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
