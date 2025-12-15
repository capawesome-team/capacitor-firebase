# @capacitor-firebase/app-check

Unofficial Capacitor plugin for [Firebase App Check](https://firebase.google.com/docs/app-check).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 7.x.x          | 7.x.x             | Deprecated     |
| 6.x.x          | 6.x.x             | Deprecated     |
| 5.x.x          | 5.x.x             | Deprecated     |

## Installation

```bash
npm install @capacitor-firebase/app-check firebase
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#android) / [iOS](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#ios) / [Web](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#web)).

### Android

See [Set up your Firebase project](https://firebase.google.com/docs/app-check/android/play-integrity-provider#project-setup) and follow the instructions to set up your app correctly.

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$firebaseAppCheckPlayIntegrityVersion` version of `com.google.firebase:firebase-appcheck-playintegrity` (default: `18.0.0`)
- `$firebaseAppCheckDebugVersion` version of `com.google.firebase:firebase-appcheck-debug` (default: `18.0.0`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

### iOS

On **iOS 14 and later**, see [Set up your Firebase project](https://firebase.google.com/docs/app-check/ios/app-attest-provider#project-setup) and follow the instructions to set up your app correctly.

On **iOS 13**, see [Set up your Firebase project](https://firebase.google.com/docs/app-check/ios/devicecheck-provider#project-setup) and follow the instructions to set up your app correctly.

Make sure that the private key (\*.p8) you upload to Firebase has `DeviceCheck` selected as a service.

### Web

See [Set up your Firebase project](https://firebase.google.com/docs/app-check/web/recaptcha-provider#project-setup) and follow the instructions to set up your app correctly.

## Configuration

No configuration required for this plugin.

## Firebase JavaScript SDK

[Here](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/app-check/docs/firebase-js-sdk.md) you can find information on how to use the plugin with the Firebase JS SDK.

## Demo

A working example can be found here: [robingenz/capacitor-firebase-plugin-demo](https://github.com/robingenz/capacitor-firebase-plugin-demo)

## Usage

```typescript
import { FirebaseAppCheck } from '@capacitor-firebase/app-check';
import { ReCaptchaV3Provider } from '@capacitor-firebase/app-check';
import { Capacitor } from '@capacitor/core';

const initialize = async () => {
  await FirebaseAppCheck.initialize({
    provider: Capacitor.getPlatform() === 'web' ? new ReCaptchaV3Provider('myKey') : undefined,
  });
};

const getToken = async () => {
  const { token } = FirebaseAppCheck.getToken({
    forceRefresh: false,
  });
  return token;
};

const setTokenAutoRefreshEnabled = async () => {
  await FirebaseAppCheck.setTokenAutoRefreshEnabled({ enabled: true });
};

const addTokenChangedListener = async () => {
  await FirebaseAppCheck.addListener('tokenChanged', event => {
    console.log('tokenChanged', { event });
  });
};

const removeAllListeners = async () => {
  await FirebaseAppCheck.removeAllListeners();
};
```

## API

<docgen-index>

* [`getToken(...)`](#gettoken)
* [`initialize(...)`](#initialize)
* [`setTokenAutoRefreshEnabled(...)`](#settokenautorefreshenabled)
* [`addListener('tokenChanged', ...)`](#addlistenertokenchanged-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getToken(...)

```typescript
getToken(options?: GetTokenOptions | undefined) => Promise<GetTokenResult>
```

Get the current App Check token.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#gettokenoptions">GetTokenOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#gettokenresult">GetTokenResult</a>&gt;</code>

**Since:** 1.3.0

--------------------


### initialize(...)

```typescript
initialize(options?: InitializeOptions | undefined) => Promise<void>
```

Activate App Check for the given app.
Can be called only once per app.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#initializeoptions">InitializeOptions</a></code> |

**Since:** 1.3.0

--------------------


### setTokenAutoRefreshEnabled(...)

```typescript
setTokenAutoRefreshEnabled(options: SetTokenAutoRefreshEnabledOptions) => Promise<void>
```

Set whether the App Check token should be refreshed automatically or not.

| Param         | Type                                                                                            |
| ------------- | ----------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#settokenautorefreshenabledoptions">SetTokenAutoRefreshEnabledOptions</a></code> |

**Since:** 1.3.0

--------------------


### addListener('tokenChanged', ...)

```typescript
addListener(eventName: 'tokenChanged', listenerFunc: TokenChangedListener) => Promise<PluginListenerHandle>
```

Called when the App Check token changed.

| Param              | Type                                                                  |
| ------------------ | --------------------------------------------------------------------- |
| **`eventName`**    | <code>'tokenChanged'</code>                                           |
| **`listenerFunc`** | <code><a href="#tokenchangedlistener">TokenChangedListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 1.3.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

Only available for Web.

**Since:** 1.3.0

--------------------


### Interfaces


#### GetTokenResult

| Prop                   | Type                | Description                                                                                                      | Since |
| ---------------------- | ------------------- | ---------------------------------------------------------------------------------------------------------------- | ----- |
| **`token`**            | <code>string</code> | The App Check token in JWT format.                                                                               | 1.3.0 |
| **`expireTimeMillis`** | <code>number</code> | The timestamp after which the token will expire in milliseconds since epoch. Only available for Android and iOS. | 1.3.0 |


#### GetTokenOptions

| Prop               | Type                 | Description                                                                                                 | Default            | Since |
| ------------------ | -------------------- | ----------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`forceRefresh`** | <code>boolean</code> | If `true`, will always try to fetch a fresh token. If `false`, will use a cached token if found in storage. | <code>false</code> | 1.3.0 |


#### InitializeOptions

| Prop                            | Type                           | Description                                                                                                                                                                                                                                                                                                                                                                                                                                         | Default                          | Since |
| ------------------------------- | ------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------- | ----- |
| **`debug`**                     | <code>boolean</code>           | If `true`, the debug provider is used. ⚠️ **Attention**: The debug provider allows access to your Firebase resources from unverified devices. Don't use the debug provider in production builds of your app, and don't share your debug builds with untrusted parties. ⚠️ **Deprecated**: Use `debugToken` instead. This option will be removed in the next major version. Read more: https://firebase.google.com/docs/app-check/web/debug-provider | <code>false</code>               | 1.3.0 |
| **`debugToken`**                | <code>string \| boolean</code> | If `true`, the debug provider is used. On **Web**, you can also set a predefined debug token string instead of `true`. On Android and iOS, you have to use environment variables for this. ⚠️ **Attention**: The debug provider allows access to your Firebase resources from unverified devices. Don't use the debug provider in production builds of your app, and don't share your debug builds with untrusted parties.                          | <code>false</code>               | 7.1.0 |
| **`isTokenAutoRefreshEnabled`** | <code>boolean</code>           | If `true`, the SDK automatically refreshes App Check tokens as needed.                                                                                                                                                                                                                                                                                                                                                                              | <code>false</code>               | 1.3.0 |
| **`provider`**                  | <code>any</code>               | The provider to use for App Check. Must be an instance of `ReCaptchaV3Provider`, `ReCaptchaEnterpriseProvider`, or `CustomProvider`. Only available for Web.                                                                                                                                                                                                                                                                                        | <code>ReCaptchaV3Provider</code> | 7.1.0 |
| **`siteKey`**                   | <code>string</code>            | The reCAPTCHA v3 site key (public key). This option is ignored when `provider` is set. Only available for Web.                                                                                                                                                                                                                                                                                                                                      |                                  | 1.3.0 |


#### SetTokenAutoRefreshEnabledOptions

| Prop          | Type                 | Description                                                                                                                      | Since |
| ------------- | -------------------- | -------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`enabled`** | <code>boolean</code> | If `true`, the SDK automatically refreshes App Check tokens as needed. This overrides any value set during initializeAppCheck(). | 1.3.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### TokenChangedEvent

| Prop        | Type                | Description                        | Since |
| ----------- | ------------------- | ---------------------------------- | ----- |
| **`token`** | <code>string</code> | The App Check token in JWT format. | 1.3.0 |


### Type Aliases


#### TokenChangedListener

Callback to receive the token change event.

<code>(event: <a href="#tokenchangedevent">TokenChangedEvent</a>): void</code>

</docgen-api>

## Testing

### Android

Follow these steps to test your implementation on a real device:

1. Start your app on the Android device.
1. Run the following command to grab your temporary secret from the android logs:

```
adb logcat | grep DebugAppCheckProvider
```

The output should look like this:

```
D DebugAppCheckProvider: Enter this debug secret into the allow list in
the Firebase Console for your project: XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX
```

1. Next, open the [App Check project](https://console.firebase.google.com/u/0/project/_/appcheck/apps) in the Firebase Console and select Manage debug tokens from the overflow menu of your app. Then, register the debug secret from the output.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/app-check/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/app-check/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
