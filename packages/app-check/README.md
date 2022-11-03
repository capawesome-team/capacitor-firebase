# @capacitor-firebase/app-check

⚡️ Capacitor plugin for Firebase App Check.

From the [Firebase App Check Docs](https://firebase.google.com/docs/app-check):
>App Check works alongside other Firebase services to help protect your backend resources from abuse, such as billing fraud or phishing.

This plug-in allows the use of App Check in the native layer of your app to verify the user's device, with the ability to then retrieve tokens in your web layer for use with the Firebase web SDKs and APIs.

## Installation

```bash
npm install @capacitor-firebase/crashlytics
npx cap sync
```

You must enable App Check within your Firebase Project before installing this plugin. See the sections below for platform-specific set-up.

### iOS

AppAttest is the latest and recommended device verification method for iOS; however it requires iOS 14 or later. If you wish to also support iOS 13 and below, you can also add the DeviceCheck provider and only use DeviceCheck when the running device is lower than iOS 14. See the Firebase docs for details:
- [Set up App Check on iOS with DeviceCheck (< iOS 14)](https://firebase.google.com/docs/app-check/ios/devicecheck-provider#project-setup)
- [Set up App Check on iOS with AppAttest (iOS 14 <=)](https://firebase.google.com/docs/app-check/ios/app-attest-provider#project-setup)

### Android

The Play Integrity API is the recommended verification framework for Android, and the older SafetyNet has now been deprecated. See [Set up App Check on Android with Play Integrity](https://firebase.google.com/docs/app-check/android/play-integrity-provider) for details.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
// import firebase and app check
import { firebase } from 'firebase';
import { FirebaseAppCheck } from '@capacitor-firebase/app-check';

// initialise App Check
AppCheck.initialize({
  // enable debugging if in staging or dev environments. Default is false.
  debug: true
});

// create custom app-check provider
const appCheckCustomProvider = {
  getToken: async () => {
    // get the token from native
    const { token, exp: expTimeMillis } = await FirebaseAppCheck.getToken();

    return {
      token,
      expireTimeMillis
    }
  }
}

// activate app-check [set true to refresh the token automatically on expiry]
firebase.appCheck().activate(appCheckCustomProvider, true);

```

## API

<docgen-index>

* [`initialize(...)`](#initialize)
* [`getToken()`](#gettoken)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### initialize(...)

```typescript
initialize(options?: InitializationOptions | undefined) => Promise<{ success: boolean; }>
```

Initialise the App Check plugin. This must be called once (and once only) before
calling any other method. Returns boolean of success or failure. Check the native logs
for more information in the event of a failure.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#initializationoptions">InitializationOptions</a></code> |

**Returns:** <code>Promise&lt;{ success: boolean; }&gt;</code>

**Since:** 1.0.0

--------------------


### getToken()

```typescript
getToken() => Promise<AppCheckToken>
```

Get's the native AppCheck token from DeviceCheck/AppAttest on iOS or Play Integrity on Android.

**Returns:** <code>Promise&lt;<a href="#appchecktoken">AppCheckToken</a>&gt;</code>

**Since:** 1.0.0

--------------------


### Interfaces


#### InitializationOptions

| Prop        | Type                 | Description                                                                                                                                                                                                             | Since |
| ----------- | -------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`debug`** | <code>boolean</code> | If debug is enabled, the native layer will use debug mode and log a debug token to the console. This can be used to add to the App Check debug tokens list in the Firebase console and should only be used for testing. | 1.0.0 |


### Type Aliases


#### AppCheckToken

<code>{ /** * The token provided by the native environment to be sent to firebase. * * @since 1.0.0 */ token: string; /** * The expiration date of the token in milliseconds since epoch [Unix time] * * @since 1.0.0 */ exp: number; }</code>

</docgen-api>

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!
