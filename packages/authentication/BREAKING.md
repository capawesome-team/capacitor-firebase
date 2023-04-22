# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases of Capacitor Firebase Authentication plugin.

## Versions

- [Version 5.x.x](#version-5xx)
- [Version 1.x.x](#version-1xx)
- [Version 0.4.x](#version-04x)

## Version 5.x.x

### Capacitor 5

This plugin now supports Capacitor 5 only. Please run `npx cap sync` after updating this package.

If you want to use this plugin with Capacitor 4, please install version `1.4.0`:

```
npm i @capacitor-firebase/authentication@1.4.0
```

### Localized error messages

On Android, error messages were previously generated with `getLocalizedMessage`. They are no longer localized and are generated with `getMessage` instead.

You should therefore check your error handling.

### Phone Number Sign-In

Deprecated parameters were removed from the API for Phone Number Sign-In and a new method `confirmVerificationCode(...)` was added.

**Before**:

```ts
import { FirebaseAuthentication } from '@capacitor-firebase/authentication';

const signInWithPhoneNumber = async () => {
  const { verificationId } = await FirebaseAuthentication.signInWithPhoneNumber(
    {
      phoneNumber: '123456789',
    },
  );
  const verificationCode = window.prompt(
    'Please enter the verification code that was sent to your mobile device.',
  );
  const result = await FirebaseAuthentication.signInWithPhoneNumber({
    verificationId,
    verificationCode,
  });
  return result.user;
};
```

**Now**:

```ts
import { FirebaseAuthentication } from '@capacitor-firebase/authentication';

const signInWithPhoneNumber = async () => {
  return new Promise(async resolve => {
    // Attach `phoneCodeSent` listener to be notified as soon as the SMS is sent
    await FirebaseAuthentication.addListener('phoneCodeSent', async event => {
      // Ask the user for the SMS code
      const verificationCode = window.prompt(
        'Please enter the verification code that was sent to your mobile device.',
      );
      // Confirm the verification code
      const result = await FirebaseAuthentication.confirmVerificationCode({
        verificationId: event.verificationId,
        verificationCode,
      });
      resolve(result.user);
    });
    // Attach `phoneVerificationCompleted` listener to be notified if phone verification could be finished automatically
    await FirebaseAuthentication.addListener(
      'phoneVerificationCompleted',
      async event => {
        resolve(event.result.user);
      },
    );
    // Start sign in with phone number and send the SMS
    await FirebaseAuthentication.signInWithPhoneNumber({
      phoneNumber: '123456789',
    });
  });
};
```

## Version 1.x.x

### Capacitor 4

This plugin now supports Capacitor 4 only. Please run `npx cap sync` after updating this package.

If you want to use this plugin with Capacitor 3, please install version `0.5.1`:

```
npm i @capacitor-firebase/authentication@0.5.1
```

### `providers` configuration option

Using the `providers` [configuration](https://github.com/capawesome-team/capacitor-firebase/tree/main/packages/authentication#configuration) option you can select which providers (Google, Facebook, ...) should be loaded by the plugin.  
Previously, all providers were loaded by default.  
From now on, _no providers will be loaded by default_.  
Please set the `providers` configuration option and specify all providers you use.

**Example** (`capacitor.config.ts`):

```ts
/// <reference types="@capacitor-firebase/authentication" />

import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    FirebaseAuthentication: {
      skipNativeAuth: false,
      providers: ['apple.com', 'facebook.com'],
    },
  },
};

export default config;
```

### Facebook Sign-In

⚠️ You may encounter the following error with Gradle lint: `Class referenced in the manifest, com.facebook.FacebookActivity, was not found in the project or the libraries [MissingClass]`.  
Check out this comment in that case: [issues/117#issuecomment-1208612107](https://github.com/capawesome-team/capacitor-firebase/issues/117#issuecomment-1208612107).

## Version 0.4.x

Add the following `string` element to `android/app/src/main/res/values/strings.xml` after the `resources` element:

```diff
<string name="facebook_app_id">[APP_ID]</string>
<string name="fb_login_protocol_scheme">fb[APP_ID]</string>
+ <string name="facebook_client_token">[CLIENT_TOKEN]</string>
```

Add the following `meta-data` element to `android/app/src/main/AndroidManifest.xml` inside the `application` element:

```diff
<meta-data
   android:name="com.facebook.sdk.ApplicationId"
   android:value="@string/facebook_app_id"/>

+<meta-data
+   android:name="com.facebook.sdk.ClientToken"
+   android:value="@string/facebook_client_token"/>
```
