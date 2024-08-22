# @capacitor-firebase/authentication

Unofficial Capacitor plugin for [Firebase Authentication](https://firebase.google.com/docs/auth).[^1]

## Installation

```bash
npm install @capacitor-firebase/authentication firebase
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#android) / [iOS](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#ios) / [Web](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#web)).

On **iOS**, verify that this function is included in your app's `AppDelegate.swift`:

```swift
func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey: Any] = [:]) -> Bool {
  return ApplicationDelegateProxy.shared.application(app, open: url, options: options)
}
```

**Attention**: If you use this plugin on **iOS** in combination with `@capacitor-firebase/messaging`, then add the following to your app's `AppDelegate.swift`:

```diff
+ import FirebaseAuth

func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
+    if Auth.auth().canHandle(url) {
+      return true
+    }
    return ApplicationDelegateProxy.shared.application(app, open: url, options: options)
}
```

The further installation steps depend on the selected authentication method:

- [Apple Sign-In](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/docs/setup-apple.md)
- [Facebook Sign-In](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/docs/setup-facebook.md)
- [Game Center Sign-In](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/docs/setup-game-center.md)
- [GitHub Sign-In](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/docs/setup-github.md)
- [Google Sign-In](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/docs/setup-google.md)
- [Microsoft Sign-In](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/docs/setup-microsoft.md)
- [OpenID Connect Sign-In](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/docs/setup-oidc.md)
- [Play Games Sign-In](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/docs/setup-play-games.md)
- [Twitter Sign-In](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/docs/setup-twitter.md)
- [Yahoo Sign-In](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/docs/setup-yahoo.md)

- [Anonymous Sign-In](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/docs/setup-anonymous.md)
- [Email Link Sign-In](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/docs/setup-email-link.md)
- [Phone Number Sign-In](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/docs/setup-phone.md)
- [Custom Token Sign-In](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/docs/setup-custom-token.md)

**Attention**: Please note that this plugin uses third-party SDKs to offer native sign-in.
These SDKs can initialize on their own and collect various data.
For more information, see [Third-Party SDKs](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/docs/third-party-sdks.md).

## Configuration

<docgen-config>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

These configuration values are available:

| Prop                 | Type                  | Description                                                                                                                                                                                                                                                                                                    | Default            | Since |
| -------------------- | --------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`skipNativeAuth`** | <code>boolean</code>  | Configure whether the plugin should skip the native authentication. Only needed if you want to use the Firebase JavaScript SDK. This configuration option has no effect on Firebase account linking. **Note that the plugin may behave differently across the platforms.** Only available for Android and iOS. | <code>false</code> | 0.1.0 |
| **`providers`**      | <code>string[]</code> | Configure the providers that should be loaded by the plugin. Possible values: `["apple.com", "facebook.com", "gc.apple.com", "github.com", "google.com", "microsoft.com", "playgames.google.com", "twitter.com", "yahoo.com", "phone"]` Only available for Android and iOS.                                    | <code>[]</code>    | 0.1.0 |

### Examples

In `capacitor.config.json`:

```json
{
  "plugins": {
    "FirebaseAuthentication": {
      "skipNativeAuth": false,
      "providers": ["apple.com", "facebook.com"]
    }
  }
}
```

In `capacitor.config.ts`:

```ts
/// <reference types="@capacitor-firebase/authentication" />

import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    FirebaseAuthentication: {
      skipNativeAuth: false,
      providers: ["apple.com", "facebook.com"],
    },
  },
};

export default config;
```

</docgen-config>

## FAQ

1. **What does this plugin do?**  
   This plugin enables the use of [Firebase Authentication](https://firebase.google.com/docs/auth) in a Capacitor app.
   It uses the Firebase SDK for [Java](https://firebase.google.com/docs/reference/android) (Android), [Swift](https://firebase.google.com/docs/reference/swift) (iOS) and [JavaScript](https://firebase.google.com/docs/reference/js).
1. **What is the difference between the web implementation of this plugin and the Firebase JS SDK?**  
   The web implementation of this plugin encapsulates the Firebase JS SDK and enables a consistent interface across all platforms.
   You can decide if you prefer to use the web implementation or the Firebase JS SDK.
1. **What is the difference between the native and web authentication?**  
   For web authentication, the Firebase JS SDK is used. This only works to a limited extent on Android and iOS in the WebViews (see [here](https://developers.googleblog.com/2016/08/modernizing-oauth-interactions-in-native-apps.html)).
   For native authentication, the native SDKs from Firebase, Google, etc. are used.
   These offer all the functionalities that the Firebase JS SDK also offers on the web.
   However, after a login with the native SDK, the user is only logged in on the native layer of the app.
   If the user should also be logged in on the web layer (for example to access Cloud Firestore via Firebase JS SDK), additional steps are required (see [here](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/docs/firebase-js-sdk.md)).
1. **How can I use this plugin with the Firebase JavaScript SDK?**  
   See [here](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/docs/firebase-js-sdk.md).

## Firebase JavaScript SDK

[Here](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/docs/firebase-js-sdk.md) you can find information on how to use the plugin with the Firebase JS SDK.

## Demo

A working example can be found here: [robingenz/capacitor-firebase-authentication-demo](https://github.com/robingenz/capacitor-firebase-authentication-demo)

## Starter templates

The following starter templates are available:

- [Ionstarter Angular Firebase](https://ionstarter.dev/)

## Usage

```typescript
import { FirebaseAuthentication } from '@capacitor-firebase/authentication';

const applyActionCode = async () => {
  await FirebaseAuthentication.applyActionCode({ oobCode: '1234' });
};

const createUserWithEmailAndPassword = async () => {
  const result = await FirebaseAuthentication.createUserWithEmailAndPassword({
    email: 'mail@exmaple.com',
    password: '1234',
  });
  return result.user;
};

const confirmPasswordReset = async () => {
  await FirebaseAuthentication.confirmPasswordReset({
    oobCode: '1234',
    newPassword: '4321',
  });
};

const deleteUser = async () => {
  await FirebaseAuthentication.deleteUser();
};

const fetchSignInMethodsForEmail = async () => {
  const result = await FirebaseAuthentication.fetchSignInMethodsForEmail({
    email: 'mail@example.tld',
  });
  return result.signInMethods;
};

const getCurrentUser = async () => {
  const result = await FirebaseAuthentication.getCurrentUser();
  return result.user;
};

const getPendingAuthResult = async () => {
  const result = await FirebaseAuthentication.getPendingAuthResult();
  return result.user;
};

const getIdToken = async () => {
  const currentUser = await getCurrentUser();
  if (!currentUser) {
    return;
  }
  const result = await FirebaseAuthentication.getIdToken();
  return result.token;
};

const getPendingAuthResult = async () => {
  const result = await FirebaseAuthentication.getPendingAuthResult();
  return result.user;
};

const sendEmailVerification = async () => {
  const currentUser = await getCurrentUser();
  if (!currentUser) {
    return;
  }
  await FirebaseAuthentication.sendEmailVerification();
};

const sendPasswordResetEmail = async () => {
  await FirebaseAuthentication.sendPasswordResetEmail({
    email: 'mail@example.com',
  });
};

const sendSignInLinkToEmail = async () => {
  const email = 'mail@example.com';
  await FirebaseAuthentication.sendSignInLinkToEmail({
    email,
    actionCodeSettings: {
      // URL you want to redirect back to. The domain (www.example.com) for this
      // URL must be in the authorized domains list in the Firebase Console.
      url: 'https://www.example.com/finishSignUp?cartId=1234',
      // This must be true.
      handleCodeInApp: true,
      iOS: {
        bundleId: 'com.example.ios',
      },
      android: {
        packageName: 'com.example.android',
        installApp: true,
        minimumVersion: '12',
      },
      dynamicLinkDomain: 'example.page.link',
    },
  });
  // The link was successfully sent. Inform the user.
  // Save the email locally so you don't need to ask the user for it again
  // if they open the link on the same device.
  window.localStorage.setItem('emailForSignIn', email);
};

const setLanguageCode = async () => {
  await FirebaseAuthentication.setLanguageCode({ languageCode: 'en-US' });
};

const signInAnonymously = async () => {
  const result = await FirebaseAuthentication.signInAnonymously();
  return result.user;
};

const signInWithApple = async () => {
  const result = await FirebaseAuthentication.signInWithApple();
  return result.user;
};

const signInWithCustomToken = async () => {
  const result = await FirebaseAuthentication.signInWithCustomToken({
    token: '1234',
  });
  return result.user;
};

const signInWithEmailAndPassword = async () => {
  const result = await FirebaseAuthentication.signInWithEmailAndPassword({
    email: 'mail@example.com',
    password: '1234',
  });
  return result.user;
};

const signInWithEmailLink = async () => {
  // Get the email if available. This should be available if the user completes
  // the flow on the same device where they started it.
  const emailLink = window.location.href;
  // Confirm the link is a sign-in with email link.
  const { isSignInWithEmailLink } =
    await FirebaseAuthentication.isSignInWithEmailLink({
      emailLink,
    });
  if (!isSignInWithEmailLink) {
    return;
  }
  let email = window.localStorage.getItem('emailForSignIn');
  if (!email) {
    // User opened the link on a different device. To prevent session fixation
    // attacks, ask the user to provide the associated email again.
    email = window.prompt('Please provide your email for confirmation.');
  }
  // The client SDK will parse the code from the link for you.
  const result = await FirebaseAuthentication.signInWithEmailLink({
    email,
    emailLink,
  });
  // Clear email from storage.
  window.localStorage.removeItem('emailForSignIn');
  return result.user;
};

const signInWithFacebook = async () => {
  const result = await FirebaseAuthentication.signInWithFacebook();
  return result.user;
};

const signInWithGameCenter = async () => {
  const result = await FirebaseAuthentication.signInWithGameCenter();
  return result.user;
};

const signInWithGithub = async () => {
  const result = await FirebaseAuthentication.signInWithGithub();
  return result.user;
};

const signInWithGoogle = async () => {
  const result = await FirebaseAuthentication.signInWithGoogle();
  return result.user;
};

const signInWithMicrosoft = async () => {
  const result = await FirebaseAuthentication.signInWithMicrosoft();
  return result.user;
};

const signInWithOpenIdConnect = async () => {
  const result = await FirebaseAuthentication.signInWithOpenIdConnect({
    providerId: 'oidc.example.com',
  });
  return result.user;
};

const signInWithPlayGames = async () => {
  const result = await FirebaseAuthentication.signInWithPlayGames();
  return result.user;
};

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

const signInWithTwitter = async () => {
  const result = await FirebaseAuthentication.signInWithTwitter();
  return result.user;
};

const signInWithYahoo = async () => {
  const result = await FirebaseAuthentication.signInWithYahoo();
  return result.user;
};

const signOut = async () => {
  await FirebaseAuthentication.signOut();
};

const updateEmail = async () => {
  const currentUser = await getCurrentUser();
  if (!currentUser) {
    return;
  }
  await FirebaseAuthentication.updateEmail({
    newEmail: 'new.mail@example.com',
  });
};

const updatePassword = async () => {
  const currentUser = await getCurrentUser();
  if (!currentUser) {
    return;
  }
  await FirebaseAuthentication.updatePassword({
    newPassword: '4321',
  });
};

const useAppLanguage = async () => {
  await FirebaseAuthentication.useAppLanguage();
};

const useEmulator = async () => {
  await FirebaseAuthentication.useEmulator({
    host: '10.0.2.2',
    port: 9099,
  });
};
```

## API

<docgen-index>

* [`initWithFirebaseConfig(...)`](#initwithfirebaseconfig)
* [`firebaseAppIsInitialized(...)`](#firebaseappisinitialized)
* [`useFirebaseApp(...)`](#usefirebaseapp)
* [`currentFirebaseApp()`](#currentfirebaseapp)
* [`applyActionCode(...)`](#applyactioncode)
* [`confirmPasswordReset(...)`](#confirmpasswordreset)
* [`confirmVerificationCode(...)`](#confirmverificationcode)
* [`createUserWithEmailAndPassword(...)`](#createuserwithemailandpassword)
* [`deleteUser()`](#deleteuser)
* [`fetchSignInMethodsForEmail(...)`](#fetchsigninmethodsforemail)
* [`getCurrentUser()`](#getcurrentuser)
* [`getPendingAuthResult()`](#getpendingauthresult)
* [`getIdToken(...)`](#getidtoken)
* [`getRedirectResult()`](#getredirectresult)
* [`getTenantId()`](#gettenantid)
* [`isSignInWithEmailLink(...)`](#issigninwithemaillink)
* [`linkWithApple(...)`](#linkwithapple)
* [`linkWithEmailAndPassword(...)`](#linkwithemailandpassword)
* [`linkWithEmailLink(...)`](#linkwithemaillink)
* [`linkWithFacebook(...)`](#linkwithfacebook)
* [`linkWithGameCenter(...)`](#linkwithgamecenter)
* [`linkWithGithub(...)`](#linkwithgithub)
* [`linkWithGoogle(...)`](#linkwithgoogle)
* [`linkWithMicrosoft(...)`](#linkwithmicrosoft)
* [`linkWithOpenIdConnect(...)`](#linkwithopenidconnect)
* [`linkWithPhoneNumber(...)`](#linkwithphonenumber)
* [`linkWithPlayGames(...)`](#linkwithplaygames)
* [`linkWithTwitter(...)`](#linkwithtwitter)
* [`linkWithYahoo(...)`](#linkwithyahoo)
* [`reload()`](#reload)
* [`revokeAccessToken(...)`](#revokeaccesstoken)
* [`sendEmailVerification(...)`](#sendemailverification)
* [`sendPasswordResetEmail(...)`](#sendpasswordresetemail)
* [`sendSignInLinkToEmail(...)`](#sendsigninlinktoemail)
* [`setLanguageCode(...)`](#setlanguagecode)
* [`setPersistence(...)`](#setpersistence)
* [`setTenantId(...)`](#settenantid)
* [`signInAnonymously()`](#signinanonymously)
* [`signInWithApple(...)`](#signinwithapple)
* [`signInWithCustomToken(...)`](#signinwithcustomtoken)
* [`signInWithEmailAndPassword(...)`](#signinwithemailandpassword)
* [`signInWithEmailLink(...)`](#signinwithemaillink)
* [`signInWithFacebook(...)`](#signinwithfacebook)
* [`signInWithGameCenter(...)`](#signinwithgamecenter)
* [`signInWithGithub(...)`](#signinwithgithub)
* [`signInWithGoogle(...)`](#signinwithgoogle)
* [`signInWithMicrosoft(...)`](#signinwithmicrosoft)
* [`signInWithOpenIdConnect(...)`](#signinwithopenidconnect)
* [`signInWithPhoneNumber(...)`](#signinwithphonenumber)
* [`signInWithPlayGames(...)`](#signinwithplaygames)
* [`signInWithTwitter(...)`](#signinwithtwitter)
* [`signInWithYahoo(...)`](#signinwithyahoo)
* [`signOut()`](#signout)
* [`unlink(...)`](#unlink)
* [`updateEmail(...)`](#updateemail)
* [`updatePassword(...)`](#updatepassword)
* [`updateProfile(...)`](#updateprofile)
* [`useAppLanguage()`](#useapplanguage)
* [`useEmulator(...)`](#useemulator)
* [`addListener('authStateChange', ...)`](#addlistenerauthstatechange)
* [`addListener('phoneVerificationCompleted', ...)`](#addlistenerphoneverificationcompleted)
* [`addListener('phoneVerificationFailed', ...)`](#addlistenerphoneverificationfailed)
* [`addListener('phoneCodeSent', ...)`](#addlistenerphonecodesent)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### initWithFirebaseConfig(...)

```typescript
initWithFirebaseConfig(options: { name: string; config: { "appId": string; "messagingSenderId": string; "clientId": string; "apiKey": string; "projectId": string; "storageBucket": string; "databaseURL": string; }; }) => Promise<void>
```

| Param         | Type                                                                                                                                                                                   |
| ------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ name: string; config: { appId: string; messagingSenderId: string; clientId: string; apiKey: string; projectId: string; storageBucket: string; databaseURL: string; }; }</code> |

--------------------


### firebaseAppIsInitialized(...)

```typescript
firebaseAppIsInitialized(options: { name: string; }) => Promise<{ result: boolean; }>
```

| Param         | Type                           |
| ------------- | ------------------------------ |
| **`options`** | <code>{ name: string; }</code> |

**Returns:** <code>Promise&lt;{ result: boolean; }&gt;</code>

--------------------


### useFirebaseApp(...)

```typescript
useFirebaseApp(options: { name: string | "default"; }) => Promise<void>
```

| Param         | Type                           |
| ------------- | ------------------------------ |
| **`options`** | <code>{ name: string; }</code> |

--------------------


### currentFirebaseApp()

```typescript
currentFirebaseApp() => Promise<{ name: string; }>
```

**Returns:** <code>Promise&lt;{ name: string; }&gt;</code>

--------------------


### applyActionCode(...)

```typescript
applyActionCode(options: ApplyActionCodeOptions) => Promise<void>
```

Applies a verification code sent to the user by email.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#applyactioncodeoptions">ApplyActionCodeOptions</a></code> |

**Since:** 0.2.2

--------------------


### confirmPasswordReset(...)

```typescript
confirmPasswordReset(options: ConfirmPasswordResetOptions) => Promise<void>
```

Completes the password reset process.

| Param         | Type                                                                                |
| ------------- | ----------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#confirmpasswordresetoptions">ConfirmPasswordResetOptions</a></code> |

**Since:** 0.2.2

--------------------


### confirmVerificationCode(...)

```typescript
confirmVerificationCode(options: ConfirmVerificationCodeOptions) => Promise<SignInResult>
```

Finishes the phone number verification process.

| Param         | Type                                                                                      |
| ------------- | ----------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#confirmverificationcodeoptions">ConfirmVerificationCodeOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 5.0.0

--------------------


### createUserWithEmailAndPassword(...)

```typescript
createUserWithEmailAndPassword(options: CreateUserWithEmailAndPasswordOptions) => Promise<SignInResult>
```

Creates a new user account with email and password.
If the new account was created, the user is signed in automatically.

| Param         | Type                                                                                                    |
| ------------- | ------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#createuserwithemailandpasswordoptions">CreateUserWithEmailAndPasswordOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 0.2.2

--------------------


### deleteUser()

```typescript
deleteUser() => Promise<void>
```

Deletes and signs out the user.

**Since:** 1.3.0

--------------------


### fetchSignInMethodsForEmail(...)

```typescript
fetchSignInMethodsForEmail(options: FetchSignInMethodsForEmailOptions) => Promise<FetchSignInMethodsForEmailResult>
```

Fetches the sign-in methods for an email address.

| Param         | Type                                                                                            |
| ------------- | ----------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#fetchsigninmethodsforemailoptions">FetchSignInMethodsForEmailOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#fetchsigninmethodsforemailresult">FetchSignInMethodsForEmailResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### getCurrentUser()

```typescript
getCurrentUser() => Promise<GetCurrentUserResult>
```

Fetches the currently signed-in user.

**Returns:** <code>Promise&lt;<a href="#getcurrentuserresult">GetCurrentUserResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getPendingAuthResult()

```typescript
getPendingAuthResult() => Promise<SignInResult>
```

Returns the <a href="#signinresult">`SignInResult`</a> if your app launched a web sign-in flow and the OS cleans up the app while in the background.

Only available for Android.

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### getIdToken(...)

```typescript
getIdToken(options?: GetIdTokenOptions | undefined) => Promise<GetIdTokenResult>
```

Fetches the Firebase Auth ID Token for the currently signed-in user.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#getidtokenoptions">GetIdTokenOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getidtokenresult">GetIdTokenResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getRedirectResult()

```typescript
getRedirectResult() => Promise<SignInResult>
```

Returns the <a href="#signinresult">`SignInResult`</a> from the redirect-based sign-in flow.

If sign-in was unsuccessful, fails with an error.
If no redirect operation was called, returns a <a href="#signinresult">`SignInResult`</a> with a null user.

Only available for Web.

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 1.3.0

--------------------


### getTenantId()

```typescript
getTenantId() => Promise<GetTenantIdResult>
```

Get the tenant id.

**Returns:** <code>Promise&lt;<a href="#gettenantidresult">GetTenantIdResult</a>&gt;</code>

**Since:** 1.1.0

--------------------


### isSignInWithEmailLink(...)

```typescript
isSignInWithEmailLink(options: IsSignInWithEmailLinkOptions) => Promise<IsSignInWithEmailLinkResult>
```

Checks if an incoming link is a sign-in with email link suitable for `signInWithEmailLink`.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#issigninwithemaillinkoptions">IsSignInWithEmailLinkOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#issigninwithemaillinkresult">IsSignInWithEmailLinkResult</a>&gt;</code>

**Since:** 1.1.0

--------------------


### linkWithApple(...)

```typescript
linkWithApple(options?: SignInWithOAuthOptions | undefined) => Promise<LinkResult>
```

Links the user account with Apple authentication provider.

The user must be logged in on the native layer.
The `skipNativeAuth` configuration option has no effect here.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithoauthoptions">SignInWithOAuthOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 1.1.0

--------------------


### linkWithEmailAndPassword(...)

```typescript
linkWithEmailAndPassword(options: LinkWithEmailAndPasswordOptions) => Promise<LinkResult>
```

Links the user account with Email authentication provider.

The user must be logged in on the native layer.
The `skipNativeAuth` configuration option has no effect here.

| Param         | Type                                                                                        |
| ------------- | ------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#linkwithemailandpasswordoptions">LinkWithEmailAndPasswordOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 1.1.0

--------------------


### linkWithEmailLink(...)

```typescript
linkWithEmailLink(options: LinkWithEmailLinkOptions) => Promise<LinkResult>
```

Links the user account with Email authentication provider.

The user must be logged in on the native layer.
The `skipNativeAuth` configuration option has no effect here.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#linkwithemaillinkoptions">LinkWithEmailLinkOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 1.1.0

--------------------


### linkWithFacebook(...)

```typescript
linkWithFacebook(options?: SignInWithOAuthOptions | undefined) => Promise<LinkResult>
```

Links the user account with Facebook authentication provider.

The user must be logged in on the native layer.
The `skipNativeAuth` configuration option has no effect here.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithoauthoptions">SignInWithOAuthOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 1.1.0

--------------------


### linkWithGameCenter(...)

```typescript
linkWithGameCenter(options?: SignInWithOAuthOptions | undefined) => Promise<LinkResult>
```

Links the user account with Game Center authentication provider.

The user must be logged in on the native layer.
The `skipNativeAuth` configuration option has no effect here.

Only available for iOS.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithoauthoptions">SignInWithOAuthOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 1.3.0

--------------------


### linkWithGithub(...)

```typescript
linkWithGithub(options?: SignInWithOAuthOptions | undefined) => Promise<LinkResult>
```

Links the user account with GitHub authentication provider.

The user must be logged in on the native layer.
The `skipNativeAuth` configuration option has no effect here.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithoauthoptions">SignInWithOAuthOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 1.1.0

--------------------


### linkWithGoogle(...)

```typescript
linkWithGoogle(options?: SignInWithOAuthOptions | undefined) => Promise<LinkResult>
```

Links the user account with Google authentication provider.

The user must be logged in on the native layer.
The `skipNativeAuth` configuration option has no effect here.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithoauthoptions">SignInWithOAuthOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 1.1.0

--------------------


### linkWithMicrosoft(...)

```typescript
linkWithMicrosoft(options?: SignInWithOAuthOptions | undefined) => Promise<LinkResult>
```

Links the user account with Microsoft authentication provider.

The user must be logged in on the native layer.
The `skipNativeAuth` configuration option has no effect here.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithoauthoptions">SignInWithOAuthOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 1.1.0

--------------------


### linkWithOpenIdConnect(...)

```typescript
linkWithOpenIdConnect(options: LinkWithOpenIdConnectOptions) => Promise<LinkResult>
```

Links the user account with an OpenID Connect provider.

| Param         | Type                                                                                      |
| ------------- | ----------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithopenidconnectoptions">SignInWithOpenIdConnectOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 6.1.0

--------------------


### linkWithPhoneNumber(...)

```typescript
linkWithPhoneNumber(options: LinkWithPhoneNumberOptions) => Promise<void>
```

Links the user account with Phone Number authentication provider.

The user must be logged in on the native layer.
The `skipNativeAuth` configuration option has no effect here.

Use the `phoneVerificationCompleted` listener to be notified when the verification is completed.
Use the `phoneVerificationFailed` listener to be notified when the verification is failed.
Use the `phoneCodeSent` listener to get the verification id.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithphonenumberoptions">SignInWithPhoneNumberOptions</a></code> |

**Since:** 1.1.0

--------------------


### linkWithPlayGames(...)

```typescript
linkWithPlayGames(options?: SignInWithOAuthOptions | undefined) => Promise<LinkResult>
```

Links the user account with Play Games authentication provider.

The user must be logged in on the native layer.
The `skipNativeAuth` configuration option has no effect here.

Only available for Android.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithoauthoptions">SignInWithOAuthOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 1.1.0

--------------------


### linkWithTwitter(...)

```typescript
linkWithTwitter(options?: SignInWithOAuthOptions | undefined) => Promise<LinkResult>
```

Links the user account with Twitter authentication provider.

The user must be logged in on the native layer.
The `skipNativeAuth` configuration option has no effect here.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithoauthoptions">SignInWithOAuthOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 1.1.0

--------------------


### linkWithYahoo(...)

```typescript
linkWithYahoo(options?: SignInWithOAuthOptions | undefined) => Promise<LinkResult>
```

Links the user account with Yahoo authentication provider.

The user must be logged in on the native layer.
The `skipNativeAuth` configuration option has no effect here.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithoauthoptions">SignInWithOAuthOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 1.1.0

--------------------


### reload()

```typescript
reload() => Promise<void>
```

Reloads user account data, if signed in.

**Since:** 1.3.0

--------------------


### revokeAccessToken(...)

```typescript
revokeAccessToken(options: RevokeAccessTokenOptions) => Promise<void>
```

Revokes the given access token. Currently only supports Apple OAuth access tokens.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#revokeaccesstokenoptions">RevokeAccessTokenOptions</a></code> |

**Since:** 6.1.0

--------------------


### sendEmailVerification(...)

```typescript
sendEmailVerification(options?: SendEmailVerificationOptions | undefined) => Promise<void>
```

Sends a verification email to the currently signed in user.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#sendemailverificationoptions">SendEmailVerificationOptions</a></code> |

**Since:** 0.2.2

--------------------


### sendPasswordResetEmail(...)

```typescript
sendPasswordResetEmail(options: SendPasswordResetEmailOptions) => Promise<void>
```

Sends a password reset email.

| Param         | Type                                                                                    |
| ------------- | --------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#sendpasswordresetemailoptions">SendPasswordResetEmailOptions</a></code> |

**Since:** 0.2.2

--------------------


### sendSignInLinkToEmail(...)

```typescript
sendSignInLinkToEmail(options: SendSignInLinkToEmailOptions) => Promise<void>
```

Sends a sign-in email link to the user with the specified email.

To complete sign in with the email link, call `signInWithEmailLink` with the email address and the email link supplied in the email sent to the user.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#sendsigninlinktoemailoptions">SendSignInLinkToEmailOptions</a></code> |

**Since:** 1.1.0

--------------------


### setLanguageCode(...)

```typescript
setLanguageCode(options: SetLanguageCodeOptions) => Promise<void>
```

Sets the user-facing language code for auth operations.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setlanguagecodeoptions">SetLanguageCodeOptions</a></code> |

**Since:** 0.1.0

--------------------


### setPersistence(...)

```typescript
setPersistence(options: SetPersistenceOptions) => Promise<void>
```

Sets the type of persistence for the currently saved auth session.

Only available for Web.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#setpersistenceoptions">SetPersistenceOptions</a></code> |

**Since:** 5.2.0

--------------------


### setTenantId(...)

```typescript
setTenantId(options: SetTenantIdOptions) => Promise<void>
```

Sets the tenant id.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#settenantidoptions">SetTenantIdOptions</a></code> |

**Since:** 1.1.0

--------------------


### signInAnonymously()

```typescript
signInAnonymously() => Promise<SignInResult>
```

Signs in as an anonymous user.

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 1.1.0

--------------------


### signInWithApple(...)

```typescript
signInWithApple(options?: SignInWithOAuthOptions | undefined) => Promise<SignInResult>
```

Starts the Apple sign-in flow.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithoauthoptions">SignInWithOAuthOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### signInWithCustomToken(...)

```typescript
signInWithCustomToken(options: SignInWithCustomTokenOptions) => Promise<SignInResult>
```

Starts the Custom Token sign-in flow.

This method cannot be used in combination with `skipNativeAuth` on Android and iOS.
In this case you have to use the `signInWithCustomToken` interface of the Firebase JS SDK directly.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithcustomtokenoptions">SignInWithCustomTokenOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### signInWithEmailAndPassword(...)

```typescript
signInWithEmailAndPassword(options: SignInWithEmailAndPasswordOptions) => Promise<SignInResult>
```

Starts the sign-in flow using an email and password.

| Param         | Type                                                                                            |
| ------------- | ----------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithemailandpasswordoptions">SignInWithEmailAndPasswordOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 0.2.2

--------------------


### signInWithEmailLink(...)

```typescript
signInWithEmailLink(options: SignInWithEmailLinkOptions) => Promise<SignInResult>
```

Signs in using an email and sign-in email link.

| Param         | Type                                                                              |
| ------------- | --------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithemaillinkoptions">SignInWithEmailLinkOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 1.1.0

--------------------


### signInWithFacebook(...)

```typescript
signInWithFacebook(options?: SignInWithOAuthOptions | undefined) => Promise<SignInResult>
```

Starts the Facebook sign-in flow.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithoauthoptions">SignInWithOAuthOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### signInWithGameCenter(...)

```typescript
signInWithGameCenter(options?: SignInWithOAuthOptions | SignInOptions | undefined) => Promise<SignInResult>
```

Starts the Game Center sign-in flow.

Only available for iOS.

| Param         | Type                                                                                                                    |
| ------------- | ----------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithoauthoptions">SignInWithOAuthOptions</a> \| <a href="#signinoptions">SignInOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 1.3.0

--------------------


### signInWithGithub(...)

```typescript
signInWithGithub(options?: SignInWithOAuthOptions | undefined) => Promise<SignInResult>
```

Starts the GitHub sign-in flow.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithoauthoptions">SignInWithOAuthOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### signInWithGoogle(...)

```typescript
signInWithGoogle(options?: SignInWithOAuthOptions | undefined) => Promise<SignInResult>
```

Starts the Google sign-in flow.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithoauthoptions">SignInWithOAuthOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### signInWithMicrosoft(...)

```typescript
signInWithMicrosoft(options?: SignInWithOAuthOptions | undefined) => Promise<SignInResult>
```

Starts the Microsoft sign-in flow.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithoauthoptions">SignInWithOAuthOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### signInWithOpenIdConnect(...)

```typescript
signInWithOpenIdConnect(options: SignInWithOpenIdConnectOptions) => Promise<SignInResult>
```

Starts the OpenID Connect sign-in flow.

| Param         | Type                                                                                      |
| ------------- | ----------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithopenidconnectoptions">SignInWithOpenIdConnectOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 6.1.0

--------------------


### signInWithPhoneNumber(...)

```typescript
signInWithPhoneNumber(options: SignInWithPhoneNumberOptions) => Promise<void>
```

Starts the sign-in flow using a phone number.

Use the `phoneVerificationCompleted` listener to be notified when the verification is completed.
Use the `phoneVerificationFailed` listener to be notified when the verification is failed.
Use the `phoneCodeSent` listener to get the verification id.

Only available for Android and iOS.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithphonenumberoptions">SignInWithPhoneNumberOptions</a></code> |

**Since:** 0.1.0

--------------------


### signInWithPlayGames(...)

```typescript
signInWithPlayGames(options?: SignInWithOAuthOptions | undefined) => Promise<SignInResult>
```

Starts the Play Games sign-in flow.

Only available for Android.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithoauthoptions">SignInWithOAuthOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### signInWithTwitter(...)

```typescript
signInWithTwitter(options?: SignInWithOAuthOptions | undefined) => Promise<SignInResult>
```

Starts the Twitter sign-in flow.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithoauthoptions">SignInWithOAuthOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### signInWithYahoo(...)

```typescript
signInWithYahoo(options?: SignInWithOAuthOptions | undefined) => Promise<SignInResult>
```

Starts the Yahoo sign-in flow.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithoauthoptions">SignInWithOAuthOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### signOut()

```typescript
signOut() => Promise<void>
```

Starts the sign-out flow.

**Since:** 0.1.0

--------------------


### unlink(...)

```typescript
unlink(options: UnlinkOptions) => Promise<UnlinkResult>
```

Unlinks a provider from a user account.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#unlinkoptions">UnlinkOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#unlinkresult">UnlinkResult</a>&gt;</code>

**Since:** 1.1.0

--------------------


### updateEmail(...)

```typescript
updateEmail(options: UpdateEmailOptions) => Promise<void>
```

Updates the email address of the currently signed in user.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#updateemailoptions">UpdateEmailOptions</a></code> |

**Since:** 0.1.0

--------------------


### updatePassword(...)

```typescript
updatePassword(options: UpdatePasswordOptions) => Promise<void>
```

Updates the password of the currently signed in user.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#updatepasswordoptions">UpdatePasswordOptions</a></code> |

**Since:** 0.1.0

--------------------


### updateProfile(...)

```typescript
updateProfile(options: UpdateProfileOptions) => Promise<void>
```

Updates a user's profile data.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#updateprofileoptions">UpdateProfileOptions</a></code> |

**Since:** 1.3.0

--------------------


### useAppLanguage()

```typescript
useAppLanguage() => Promise<void>
```

Sets the user-facing language code to be the default app language.

**Since:** 0.1.0

--------------------


### useEmulator(...)

```typescript
useEmulator(options: UseEmulatorOptions) => Promise<void>
```

Instrument your app to talk to the Authentication emulator.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#useemulatoroptions">UseEmulatorOptions</a></code> |

**Since:** 0.2.0

--------------------


### addListener('authStateChange', ...)

```typescript
addListener(eventName: 'authStateChange', listenerFunc: AuthStateChangeListener) => Promise<PluginListenerHandle>
```

Listen for the user's sign-in state changes.

**Attention:** This listener is not triggered when the `skipNativeAuth` is used. Use the Firebase JavaScript SDK instead.

| Param              | Type                                                                        |
| ------------------ | --------------------------------------------------------------------------- |
| **`eventName`**    | <code>'authStateChange'</code>                                              |
| **`listenerFunc`** | <code><a href="#authstatechangelistener">AuthStateChangeListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('phoneVerificationCompleted', ...)

```typescript
addListener(eventName: 'phoneVerificationCompleted', listenerFunc: PhoneVerificationCompletedListener) => Promise<PluginListenerHandle>
```

Listen for a completed phone verification.

This listener only fires in two situations:
1. **Instant verification**: In some cases the phone number can be instantly
verified without needing to send or enter a verification code.
2. **Auto-retrieval**: On some devices Google Play services can automatically
detect the incoming verification SMS and perform verification without
user action.

Only available for Android.

| Param              | Type                                                                                              |
| ------------------ | ------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'phoneVerificationCompleted'</code>                                                         |
| **`listenerFunc`** | <code><a href="#phoneverificationcompletedlistener">PhoneVerificationCompletedListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 1.3.0

--------------------


### addListener('phoneVerificationFailed', ...)

```typescript
addListener(eventName: 'phoneVerificationFailed', listenerFunc: PhoneVerificationFailedListener) => Promise<PluginListenerHandle>
```

Listen for a failed phone verification.

| Param              | Type                                                                                        |
| ------------------ | ------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'phoneVerificationFailed'</code>                                                      |
| **`listenerFunc`** | <code><a href="#phoneverificationfailedlistener">PhoneVerificationFailedListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 1.3.0

--------------------


### addListener('phoneCodeSent', ...)

```typescript
addListener(eventName: 'phoneCodeSent', listenerFunc: PhoneCodeSentListener) => Promise<PluginListenerHandle>
```

Listen for a phone verification code.

| Param              | Type                                                                    |
| ------------------ | ----------------------------------------------------------------------- |
| **`eventName`**    | <code>'phoneCodeSent'</code>                                            |
| **`listenerFunc`** | <code><a href="#phonecodesentlistener">PhoneCodeSentListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 1.3.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.1.0

--------------------


### Interfaces


#### ApplyActionCodeOptions

| Prop          | Type                | Description                           | Since |
| ------------- | ------------------- | ------------------------------------- | ----- |
| **`oobCode`** | <code>string</code> | A verification code sent to the user. | 0.2.2 |


#### ConfirmPasswordResetOptions

| Prop              | Type                | Description                           | Since |
| ----------------- | ------------------- | ------------------------------------- | ----- |
| **`oobCode`**     | <code>string</code> | A verification code sent to the user. | 0.2.2 |
| **`newPassword`** | <code>string</code> | The new password.                     | 0.2.2 |


#### SignInResult

| Prop                     | Type                                                                      | Description                                                     | Since |
| ------------------------ | ------------------------------------------------------------------------- | --------------------------------------------------------------- | ----- |
| **`user`**               | <code><a href="#user">User</a> \| null</code>                             | The currently signed-in user, or null if there isn't any.       | 0.1.0 |
| **`credential`**         | <code><a href="#authcredential">AuthCredential</a> \| null</code>         | Credentials returned by an auth provider.                       | 0.1.0 |
| **`additionalUserInfo`** | <code><a href="#additionaluserinfo">AdditionalUserInfo</a> \| null</code> | Additional user information from a federated identity provider. | 0.5.1 |


#### User

| Prop                | Type                                                  | Description                                                          | Since |
| ------------------- | ----------------------------------------------------- | -------------------------------------------------------------------- | ----- |
| **`displayName`**   | <code>string \| null</code>                           |                                                                      | 0.1.0 |
| **`email`**         | <code>string \| null</code>                           |                                                                      | 0.1.0 |
| **`emailVerified`** | <code>boolean</code>                                  |                                                                      | 0.1.0 |
| **`isAnonymous`**   | <code>boolean</code>                                  |                                                                      | 0.1.0 |
| **`metadata`**      | <code><a href="#usermetadata">UserMetadata</a></code> | The user's metadata.                                                 | 5.2.0 |
| **`phoneNumber`**   | <code>string \| null</code>                           |                                                                      | 0.1.0 |
| **`photoUrl`**      | <code>string \| null</code>                           |                                                                      | 0.1.0 |
| **`providerData`**  | <code>UserInfo[]</code>                               | Additional per provider such as displayName and profile information. | 5.2.0 |
| **`providerId`**    | <code>string</code>                                   |                                                                      | 0.1.0 |
| **`tenantId`**      | <code>string \| null</code>                           |                                                                      | 0.1.0 |
| **`uid`**           | <code>string</code>                                   |                                                                      | 0.1.0 |


#### UserMetadata

| Prop                 | Type                | Description                                                   | Since |
| -------------------- | ------------------- | ------------------------------------------------------------- | ----- |
| **`creationTime`**   | <code>number</code> | Time the user was created in milliseconds since the epoch.    | 5.2.0 |
| **`lastSignInTime`** | <code>number</code> | Time the user last signed in in milliseconds since the epoch. | 5.2.0 |


#### UserInfo

| Prop              | Type                        | Description                                                                               | Since |
| ----------------- | --------------------------- | ----------------------------------------------------------------------------------------- | ----- |
| **`displayName`** | <code>string \| null</code> | The display name of the user.                                                             | 5.2.0 |
| **`email`**       | <code>string \| null</code> | The email of the user.                                                                    | 5.2.0 |
| **`phoneNumber`** | <code>string \| null</code> | The phone number normalized based on the E.164 standard (e.g. +16505550101) for the user. | 5.2.0 |
| **`photoUrl`**    | <code>string \| null</code> | The profile photo URL of the user.                                                        | 5.2.0 |
| **`providerId`**  | <code>string</code>         | The provider used to authenticate the user.                                               | 5.2.0 |
| **`uid`**         | <code>string</code>         | The user's unique ID.                                                                     | 5.2.0 |


#### AuthCredential

| Prop                    | Type                | Description                                                                                                                              | Since |
| ----------------------- | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`accessToken`**       | <code>string</code> | The OAuth access token associated with the credential if it belongs to an OAuth provider.                                                | 0.1.0 |
| **`authorizationCode`** | <code>string</code> | A token that the app uses to interact with the server. Only available for Apple Sign-in on iOS.                                          | 1.2.0 |
| **`idToken`**           | <code>string</code> | The OAuth ID token associated with the credential if it belongs to an OIDC provider.                                                     | 0.1.0 |
| **`nonce`**             | <code>string</code> | The random string used to make sure that the ID token you get was granted specifically in response to your app's authentication request. | 0.1.0 |
| **`providerId`**        | <code>string</code> | The authentication provider ID for the credential.                                                                                       | 0.1.0 |
| **`secret`**            | <code>string</code> | The OAuth access token secret associated with the credential if it belongs to an OAuth 1.0 provider.                                     | 0.1.0 |
| **`serverAuthCode`**    | <code>string</code> | The server auth code. Only available for Google Sign-in and Play Games Sign-In on Android and iOS.                                       | 5.2.0 |


#### AdditionalUserInfo

| Prop             | Type                                     | Description                                                 | Since |
| ---------------- | ---------------------------------------- | ----------------------------------------------------------- | ----- |
| **`isNewUser`**  | <code>boolean</code>                     | Whether the user is new (sign-up) or existing (sign-in).    | 0.5.1 |
| **`profile`**    | <code>{ [key: string]: unknown; }</code> | Map containing IDP-specific user data.                      | 0.5.1 |
| **`providerId`** | <code>string</code>                      | Identifier for the provider used to authenticate this user. | 0.5.1 |
| **`username`**   | <code>string</code>                      | The username if the provider is GitHub or Twitter.          | 0.5.1 |


#### ConfirmVerificationCodeOptions

| Prop                   | Type                | Description                                                                                                                                        | Since |
| ---------------------- | ------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`verificationId`**   | <code>string</code> | The verification ID received from the `phoneCodeSent` listener. The `verificationCode` option must also be provided.                               | 5.0.0 |
| **`verificationCode`** | <code>string</code> | The verification code either received from the `phoneCodeSent` listener or entered by the user. The `verificationId` option must also be provided. | 5.0.0 |


#### CreateUserWithEmailAndPasswordOptions

| Prop           | Type                | Since |
| -------------- | ------------------- | ----- |
| **`email`**    | <code>string</code> | 0.2.2 |
| **`password`** | <code>string</code> | 0.2.2 |


#### FetchSignInMethodsForEmailResult

| Prop                | Type                  | Description                                                                                                                                                                                                                                                                                     | Since |
| ------------------- | --------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`signInMethods`** | <code>string[]</code> | The sign-in methods for the specified email address. This list is empty when [Email Enumeration Protection](https://cloud.google.com/identity-platform/docs/admin/email-enumeration-protection) is enabled, irrespective of the number of authentication methods available for the given email. | 6.0.0 |


#### FetchSignInMethodsForEmailOptions

| Prop        | Type                | Description               | Since |
| ----------- | ------------------- | ------------------------- | ----- |
| **`email`** | <code>string</code> | The user's email address. | 6.0.0 |


#### GetCurrentUserResult

| Prop       | Type                                          | Description                                               | Since |
| ---------- | --------------------------------------------- | --------------------------------------------------------- | ----- |
| **`user`** | <code><a href="#user">User</a> \| null</code> | The currently signed-in user, or null if there isn't any. | 0.1.0 |


#### GetIdTokenResult

| Prop        | Type                | Description                            | Since |
| ----------- | ------------------- | -------------------------------------- | ----- |
| **`token`** | <code>string</code> | The Firebase Auth ID token JWT string. | 0.1.0 |


#### GetIdTokenOptions

| Prop               | Type                 | Description                                   | Since |
| ------------------ | -------------------- | --------------------------------------------- | ----- |
| **`forceRefresh`** | <code>boolean</code> | Force refresh regardless of token expiration. | 0.1.0 |


#### GetTenantIdResult

| Prop           | Type                        | Description                                     | Since |
| -------------- | --------------------------- | ----------------------------------------------- | ----- |
| **`tenantId`** | <code>string \| null</code> | The tenant id. `null` if it has never been set. | 1.1.0 |


#### IsSignInWithEmailLinkResult

| Prop                        | Type                 | Description                                                                                   |
| --------------------------- | -------------------- | --------------------------------------------------------------------------------------------- |
| **`isSignInWithEmailLink`** | <code>boolean</code> | Whether an incoming link is a signup with email link suitable for `signInWithEmailLink(...)`. |


#### IsSignInWithEmailLinkOptions

| Prop            | Type                | Description                                | Since |
| --------------- | ------------------- | ------------------------------------------ | ----- |
| **`emailLink`** | <code>string</code> | The link sent to the user's email address. | 1.1.0 |


#### SignInWithOAuthOptions

| Prop                   | Type                                 | Description                                                                                                                                                                                                                                                                                                       | Default              | Since |
| ---------------------- | ------------------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------- | ----- |
| **`customParameters`** | <code>SignInCustomParameter[]</code> | Configures custom parameters to be passed to the identity provider during the OAuth sign-in flow. Supports Apple, Facebook, GitHub, Google, Microsoft, Twitter and Yahoo on Web. Supports Apple, GitHub, Microsoft, Twitter and Yahoo on Android. Supports Facebook, GitHub, Microsoft, Twitter and Yahoo on iOS. |                      | 1.1.0 |
| **`mode`**             | <code>'popup' \| 'redirect'</code>   | Whether to use the popup-based OAuth authentication flow or the full-page redirect flow. If you choose `redirect`, you will get the result of the call via the `authStateChange` listener after the redirect. Only available for Web.                                                                             | <code>'popup'</code> | 1.3.0 |
| **`scopes`**           | <code>string[]</code>                | Scopes to request from provider. Supports Apple, Facebook, GitHub, Google, Microsoft, Twitter and Yahoo on Web. Supports Apple, GitHub, Google, Microsoft, Twitter, Yahoo and Play Games on Android. Supports Facebook, GitHub, Google, Microsoft, Twitter and Yahoo on iOS.                                      |                      | 1.1.0 |


#### SignInCustomParameter

| Prop        | Type                | Description                                                        | Since |
| ----------- | ------------------- | ------------------------------------------------------------------ | ----- |
| **`key`**   | <code>string</code> | The custom parameter key (e.g. `login_hint`).                      | 0.1.0 |
| **`value`** | <code>string</code> | The custom parameter value (e.g. `user@firstadd.onmicrosoft.com`). | 0.1.0 |


#### LinkWithEmailAndPasswordOptions

| Prop           | Type                | Description               | Since |
| -------------- | ------------------- | ------------------------- | ----- |
| **`email`**    | <code>string</code> | The user's email address. | 1.1.0 |
| **`password`** | <code>string</code> | The user's password.      | 1.1.0 |


#### LinkWithEmailLinkOptions

| Prop            | Type                | Description                                | Since |
| --------------- | ------------------- | ------------------------------------------ | ----- |
| **`email`**     | <code>string</code> | The user's email address.                  | 1.1.0 |
| **`emailLink`** | <code>string</code> | The link sent to the user's email address. | 1.1.0 |


#### SignInWithOpenIdConnectOptions

| Prop             | Type                | Description                     | Since |
| ---------------- | ------------------- | ------------------------------- | ----- |
| **`providerId`** | <code>string</code> | The OpenID Connect provider ID. | 6.1.0 |


#### SignInWithPhoneNumberOptions

| Prop                    | Type                 | Description                                                                                                                                                   | Default            | Since |
| ----------------------- | -------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`phoneNumber`**       | <code>string</code>  | The phone number to be verified in E.164 format.                                                                                                              |                    | 0.1.0 |
| **`recaptchaVerifier`** | <code>unknown</code> | The reCAPTCHA verifier. Must be an instance of `firebase.auth.RecaptchaVerifier`. Only available for Web.                                                     |                    | 5.2.0 |
| **`resendCode`**        | <code>boolean</code> | Resend the verification code to the specified phone number. `signInWithPhoneNumber` must be called once before using this option. Only available for Android. | <code>false</code> | 1.3.0 |
| **`timeout`**           | <code>number</code>  | The maximum amount of time in seconds to wait for the SMS auto-retrieval. Use 0 to disable SMS-auto-retrieval. Only available for Android.                    | <code>60</code>    | 5.4.0 |


#### RevokeAccessTokenOptions

| Prop        | Type                | Description                 | Since |
| ----------- | ------------------- | --------------------------- | ----- |
| **`token`** | <code>string</code> | The access token to revoke. | 6.1.0 |


#### SendEmailVerificationOptions

| Prop                     | Type                                                              | Description                                                                                               | Since |
| ------------------------ | ----------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------- | ----- |
| **`actionCodeSettings`** | <code><a href="#actioncodesettings">ActionCodeSettings</a></code> | Structure that contains the required continue/state URL with optional Android and iOS bundle identifiers. | 6.1.0 |


#### ActionCodeSettings

An interface that defines the required continue/state URL with optional Android and iOS
bundle identifiers.

| Prop                    | Type                                                                                 | Description                                                                                                                                                                                |
| ----------------------- | ------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **`android`**           | <code>{ installApp?: boolean; minimumVersion?: string; packageName: string; }</code> | Sets the Android package name.                                                                                                                                                             |
| **`handleCodeInApp`**   | <code>boolean</code>                                                                 | When set to true, the action code link will be be sent as a Universal Link or Android App Link and will be opened by the app if installed.                                                 |
| **`iOS`**               | <code>{ bundleId: string; }</code>                                                   | Sets the iOS bundle ID.                                                                                                                                                                    |
| **`url`**               | <code>string</code>                                                                  | Sets the link continue/state URL.                                                                                                                                                          |
| **`dynamicLinkDomain`** | <code>string</code>                                                                  | When multiple custom dynamic link domains are defined for a project, specify which one to use when the link is to be opened via a specified mobile app (for example, `example.page.link`). |


#### SendPasswordResetEmailOptions

| Prop                     | Type                                                              | Description                                                                                               | Since |
| ------------------------ | ----------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------- | ----- |
| **`email`**              | <code>string</code>                                               |                                                                                                           | 0.2.2 |
| **`actionCodeSettings`** | <code><a href="#actioncodesettings">ActionCodeSettings</a></code> | Structure that contains the required continue/state URL with optional Android and iOS bundle identifiers. | 6.1.0 |


#### SendSignInLinkToEmailOptions

| Prop                     | Type                                                              | Description                                                                                               | Since |
| ------------------------ | ----------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------- | ----- |
| **`email`**              | <code>string</code>                                               | The user's email address.                                                                                 | 1.1.0 |
| **`actionCodeSettings`** | <code><a href="#actioncodesettings">ActionCodeSettings</a></code> | Structure that contains the required continue/state URL with optional Android and iOS bundle identifiers. | 1.1.0 |


#### SetLanguageCodeOptions

| Prop               | Type                | Description           | Since |
| ------------------ | ------------------- | --------------------- | ----- |
| **`languageCode`** | <code>string</code> | BCP 47 language code. | 0.1.0 |


#### SetPersistenceOptions

| Prop              | Type                                                | Description            | Since |
| ----------------- | --------------------------------------------------- | ---------------------- | ----- |
| **`persistence`** | <code><a href="#persistence">Persistence</a></code> | The persistence types. | 5.2.0 |


#### Persistence

An interface covering the possible persistence mechanism types.

| Prop       | Type                                        | Description                                                                                                                                                                                                                                                   |
| ---------- | ------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`type`** | <code>'SESSION' \| 'LOCAL' \| 'NONE'</code> | Type of <a href="#persistence">Persistence</a>. - 'SESSION' is used for temporary persistence such as `sessionStorage`. - 'LOCAL' is used for long term persistence such as `localStorage` or `IndexedDB`. - 'NONE' is used for in-memory, or no persistence. |


#### SetTenantIdOptions

| Prop           | Type                | Description    | Since |
| -------------- | ------------------- | -------------- | ----- |
| **`tenantId`** | <code>string</code> | The tenant id. | 1.1.0 |


#### SignInWithCustomTokenOptions

| Prop        | Type                | Description                       | Since |
| ----------- | ------------------- | --------------------------------- | ----- |
| **`token`** | <code>string</code> | The custom token to sign in with. | 0.1.0 |


#### SignInWithEmailAndPasswordOptions

| Prop           | Type                | Description               | Since |
| -------------- | ------------------- | ------------------------- | ----- |
| **`email`**    | <code>string</code> | The user's email address. | 0.2.2 |
| **`password`** | <code>string</code> | The user's password.      | 0.2.2 |


#### SignInWithEmailLinkOptions

| Prop            | Type                | Description                                | Since |
| --------------- | ------------------- | ------------------------------------------ | ----- |
| **`email`**     | <code>string</code> | The user's email address.                  | 1.1.0 |
| **`emailLink`** | <code>string</code> | The link sent to the user's email address. | 1.1.0 |


#### SignInOptions

| Prop                 | Type                 | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              | Since |
| -------------------- | -------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`skipNativeAuth`** | <code>boolean</code> | Whether the plugin should skip the native authentication or not. Only needed if you want to use the Firebase JavaScript SDK. This value overwrites the configrations value of the `skipNativeAuth` option. If no value is set, the configuration value is used. **Note that the plugin may behave differently across the platforms.** `skipNativeAuth` cannot be used in combination with `signInWithCustomToken`, `createUserWithEmailAndPassword` or `signInWithEmailAndPassword`. Only available for Android and iOS. | 1.1.0 |


#### UnlinkResult

| Prop       | Type                                          | Description                                               | Since |
| ---------- | --------------------------------------------- | --------------------------------------------------------- | ----- |
| **`user`** | <code><a href="#user">User</a> \| null</code> | The currently signed-in user, or null if there isn't any. | 1.1.0 |


#### UnlinkOptions

| Prop             | Type                                              | Description             | Since |
| ---------------- | ------------------------------------------------- | ----------------------- | ----- |
| **`providerId`** | <code><a href="#providerid">ProviderId</a></code> | The provider to unlink. | 1.1.0 |


#### UpdateEmailOptions

| Prop           | Type                | Description            | Since |
| -------------- | ------------------- | ---------------------- | ----- |
| **`newEmail`** | <code>string</code> | The new email address. | 0.2.2 |


#### UpdatePasswordOptions

| Prop              | Type                | Description       | Since |
| ----------------- | ------------------- | ----------------- | ----- |
| **`newPassword`** | <code>string</code> | The new password. | 0.2.2 |


#### UpdateProfileOptions

| Prop              | Type                        | Description              | Since |
| ----------------- | --------------------------- | ------------------------ | ----- |
| **`displayName`** | <code>string \| null</code> | The user's display name. | 1.3.0 |
| **`photoUrl`**    | <code>string \| null</code> | The user's photo URL.    | 1.3.0 |


#### UseEmulatorOptions

| Prop         | Type                | Description                                   | Default             | Since |
| ------------ | ------------------- | --------------------------------------------- | ------------------- | ----- |
| **`host`**   | <code>string</code> | The emulator host without any port or scheme. |                     | 0.2.0 |
| **`port`**   | <code>number</code> | The emulator port.                            | <code>9099</code>   | 0.2.0 |
| **`scheme`** | <code>string</code> | The emulator scheme. Only available for Web.  | <code>"http"</code> | 5.2.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### AuthStateChange

| Prop       | Type                                          | Description                                               | Since |
| ---------- | --------------------------------------------- | --------------------------------------------------------- | ----- |
| **`user`** | <code><a href="#user">User</a> \| null</code> | The currently signed-in user, or null if there isn't any. | 0.1.0 |


#### PhoneVerificationCompletedEvent

| Prop                   | Type                | Description                                                                                                       | Since |
| ---------------------- | ------------------- | ----------------------------------------------------------------------------------------------------------------- | ----- |
| **`verificationCode`** | <code>string</code> | The verification code sent to the user's phone number. If instant verification is used, this property is not set. | 5.0.0 |


#### PhoneVerificationFailedEvent

| Prop          | Type                | Description        | Since |
| ------------- | ------------------- | ------------------ | ----- |
| **`message`** | <code>string</code> | The error message. | 1.3.0 |


#### PhoneCodeSentEvent

| Prop                 | Type                | Description                                                             | Since |
| -------------------- | ------------------- | ----------------------------------------------------------------------- | ----- |
| **`verificationId`** | <code>string</code> | The verification ID, which is needed to identify the verification code. | 1.3.0 |


### Type Aliases


#### LinkWithOAuthOptions

<code><a href="#signinwithoauthoptions">SignInWithOAuthOptions</a></code>


#### LinkResult

<code><a href="#signinresult">SignInResult</a></code>


#### LinkWithOpenIdConnectOptions

<code><a href="#signinwithopenidconnectoptions">SignInWithOpenIdConnectOptions</a></code>


#### LinkWithPhoneNumberOptions

<code><a href="#signinwithphonenumberoptions">SignInWithPhoneNumberOptions</a></code>


#### AuthStateChangeListener

Callback to receive the user's sign-in state change notifications.

<code>(change: <a href="#authstatechange">AuthStateChange</a>): void</code>


#### PhoneVerificationCompletedListener

Callback to receive the verification code sent to the user's phone number.

<code>(event: <a href="#phoneverificationcompletedevent">PhoneVerificationCompletedEvent</a>): void</code>


#### PhoneVerificationFailedListener

Callback to receive notifications of failed phone verification.

<code>(event: <a href="#phoneverificationfailedevent">PhoneVerificationFailedEvent</a>): void</code>


#### PhoneCodeSentListener

Callback to receive the verification ID.

<code>(event: <a href="#phonecodesentevent">PhoneCodeSentEvent</a>): void</code>


### Enums


#### Persistence

| Members              | Value                           | Description                                  | Since |
| -------------------- | ------------------------------- | -------------------------------------------- | ----- |
| **`IndexedDbLocal`** | <code>'INDEXED_DB_LOCAL'</code> | Long term persistence using IndexedDB.       | 5.2.0 |
| **`InMemory`**       | <code>'IN_MEMORY'</code>        | No persistence.                              | 5.2.0 |
| **`BrowserLocal`**   | <code>'BROWSER_LOCAL'</code>    | Long term persistence using local storage.   | 5.2.0 |
| **`BrowserSession`** | <code>'BROWSER_SESSION'</code>  | Temporary persistence using session storage. | 5.2.0 |


#### ProviderId

| Members           | Value                               |
| ----------------- | ----------------------------------- |
| **`APPLE`**       | <code>'apple.com'</code>            |
| **`FACEBOOK`**    | <code>'facebook.com'</code>         |
| **`GAME_CENTER`** | <code>'gc.apple.com'</code>         |
| **`GITHUB`**      | <code>'github.com'</code>           |
| **`GOOGLE`**      | <code>'google.com'</code>           |
| **`MICROSOFT`**   | <code>'microsoft.com'</code>        |
| **`PLAY_GAMES`**  | <code>'playgames.google.com'</code> |
| **`TWITTER`**     | <code>'twitter.com'</code>          |
| **`YAHOO`**       | <code>'yahoo.com'</code>            |
| **`PASSWORD`**    | <code>'password'</code>             |
| **`PHONE`**       | <code>'phone'</code>                |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/LICENSE).

## Credits

This plugin is based on the [Capacitor Firebase Authentication](https://github.com/robingenz/capacitor-firebase-authentication) plugin.
Thanks to everyone who contributed to the project!

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
