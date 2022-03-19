# @capacitor-firebase/authentication

⚡️ Capacitor plugin for Firebase Authentication.

## Installation

```bash
npm install @capacitor-firebase/authentication firebase
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://firebase.google.com/docs/android/setup) / [iOS](https://firebase.google.com/docs/ios/setup) / [Web](https://firebase.google.com/docs/web/setup)).

On **iOS**, verify that this function is included in your app's `AppDelegate.swift`:

```swift
func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
  return ApplicationDelegateProxy.shared.application(app, open: url, options: options)
}
```

The further installation steps depend on the selected authentication method:

- [Apple Sign-In](docs/setup-apple.md)
- [Facebook Sign-In](docs/setup-facebook.md)
- [GitHub Sign-In](docs/setup-github.md)
- [Google Sign-In](docs/setup-google.md)
- [Microsoft Sign-In](docs/setup-microsoft.md)
- [Play Games Sign-In](docs/setup-play-games.md)
- [Twitter Sign-In](docs/setup-twitter.md)
- [Yahoo Sign-In](docs/setup-yahoo.md)
- [Phone Number Sign-In](docs/setup-phone.md)
- [Custom Token Sign-In](docs/custom-token.md)

**Attention**: Please note that this plugin uses third-party SDKs to offer native sign-in.
These SDKs can initialize on their own and collect various data.
[Here](docs/third-party-sdks.md) you can find more information.

## Configuration

<docgen-config>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

These configuration values are available:

| Prop                 | Type                  | Description                                                                                                                                                                                                                                                                                            | Default                                                                                                                                              |
| -------------------- | --------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`skipNativeAuth`** | <code>boolean</code>  | Configure whether the plugin should skip the native authentication. Only needed if you want to use the Firebase JavaScript SDK. Only available for Android and iOS.                                                                                                                                    | <code>false</code>                                                                                                                                   |
| **`providers`**      | <code>string[]</code> | Configure which providers you want to use so that only the providers you need are fully initialized. If you do not configure any providers, they will be all initialized. Please note that this does not prevent the automatic initialization of third-party SDKs. Only available for Android and iOS. | <code>["apple.com", "facebook.com", "github.com", "google.com", "microsoft.com", "playgames.google.com", "twitter.com", "yahoo.com", "phone"]</code> |

### Examples

In `capacitor.config.json`:

```json
{
  "plugins": {
    "FirebaseAuthentication": {
      "skipNativeAuth": false,
      "providers": ["apple.com", "google.com"]
    }
  }
}
```

In `capacitor.config.ts`:

```ts
/// <reference types="@capacitor/firebase-authentication" />

import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    FirebaseAuthentication: {
      skipNativeAuth: false,
      providers: ['apple.com', 'google.com'],
    },
  },
};

export default config;
```

</docgen-config>

## Demo

A working example can be found here: [robingenz/capacitor-firebase-authentication-demo](https://github.com/robingenz/capacitor-firebase-authentication-demo)

## Usage

```typescript
import { FirebaseAuthentication } from '@capacitor-firebase/authentication';

const getCurrentUser = async () => {
  const result = await FirebaseAuthentication.getCurrentUser();
  return result.user;
};

const getIdToken = async () => {
  const result = await FirebaseAuthentication.getIdToken();
  return result.token;
};

const setLanguageCode = async () => {
  await FirebaseAuthentication.setLanguageCode({ languageCode: 'en-US' });
};

const signInWithApple = async () => {
  await FirebaseAuthentication.signInWithApple();
};

const signInWithFacebook = async () => {
  await FirebaseAuthentication.signInWithFacebook();
};

const signInWithGithub = async () => {
  await FirebaseAuthentication.signInWithGithub();
};

const signInWithGoogle = async () => {
  await FirebaseAuthentication.signInWithGoogle();
};

const signInWithMicrosoft = async () => {
  await FirebaseAuthentication.signInWithMicrosoft();
};

const signInWithPlayGames = async () => {
  await FirebaseAuthentication.signInWithPlayGames();
};

const signInWithPhoneNumber = async () => {
  const { verificationId } = await FirebaseAuthentication.signInWithPhoneNumber(
    {
      phoneNumber: '123456789',
    },
  );
  const verificationCode = window.prompt(
    'Please enter the verification code that was sent to your mobile device.',
  );
  await FirebaseAuthentication.signInWithPhoneNumber({
    verificationId,
    verificationCode,
  });
};

const signInWithTwitter = async () => {
  await FirebaseAuthentication.signInWithTwitter();
};

const signInWithYahoo = async () => {
  await FirebaseAuthentication.signInWithYahoo();
};

const signOut = async () => {
  await FirebaseAuthentication.signOut();
};

const useAppLanguage = async () => {
  await FirebaseAuthentication.useAppLanguage();
};
```

## API

<docgen-index>

- [`getCurrentUser()`](#getcurrentuser)
- [`getIdToken(...)`](#getidtoken)
- [`setLanguageCode(...)`](#setlanguagecode)
- [`signInWithApple(...)`](#signinwithapple)
- [`signInWithFacebook(...)`](#signinwithfacebook)
- [`signInWithGithub(...)`](#signinwithgithub)
- [`signInWithGoogle(...)`](#signinwithgoogle)
- [`signInWithMicrosoft(...)`](#signinwithmicrosoft)
- [`signInWithPlayGames(...)`](#signinwithplaygames)
- [`signInWithTwitter(...)`](#signinwithtwitter)
- [`signInWithYahoo(...)`](#signinwithyahoo)
- [`signInWithPhoneNumber(...)`](#signinwithphonenumber)
- [`signInWithCustomToken(...)`](#signinwithcustomtoken)
- [`signOut()`](#signout)
- [`useAppLanguage()`](#useapplanguage)
- [`addListener('authStateChange', ...)`](#addlistenerauthstatechange)
- [`removeAllListeners()`](#removealllisteners)
- [Interfaces](#interfaces)
- [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getCurrentUser()

```typescript
getCurrentUser() => Promise<GetCurrentUserResult>
```

Fetches the currently signed-in user.

**Returns:** <code>Promise&lt;<a href="#getcurrentuserresult">GetCurrentUserResult</a>&gt;</code>

---

### getIdToken(...)

```typescript
getIdToken(options?: GetIdTokenOptions | undefined) => Promise<GetIdTokenResult>
```

Fetches the Firebase Auth ID Token for the currently signed-in user.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#getidtokenoptions">GetIdTokenOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getidtokenresult">GetIdTokenResult</a>&gt;</code>

---

### setLanguageCode(...)

```typescript
setLanguageCode(options: SetLanguageCodeOptions) => Promise<void>
```

Sets the user-facing language code for auth operations.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setlanguagecodeoptions">SetLanguageCodeOptions</a></code> |

---

### signInWithApple(...)

```typescript
signInWithApple(options?: SignInOptions | undefined) => Promise<SignInResult>
```

Starts the Apple sign-in flow.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#signinoptions">SignInOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

---

### signInWithFacebook(...)

```typescript
signInWithFacebook(options?: SignInOptions | undefined) => Promise<SignInResult>
```

Starts the Facebook sign-in flow.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#signinoptions">SignInOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

---

### signInWithGithub(...)

```typescript
signInWithGithub(options?: SignInOptions | undefined) => Promise<SignInResult>
```

Starts the GitHub sign-in flow.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#signinoptions">SignInOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

---

### signInWithGoogle(...)

```typescript
signInWithGoogle(options?: SignInOptions | undefined) => Promise<SignInResult>
```

Starts the Google sign-in flow.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#signinoptions">SignInOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

---

### signInWithMicrosoft(...)

```typescript
signInWithMicrosoft(options?: SignInOptions | undefined) => Promise<SignInResult>
```

Starts the Microsoft sign-in flow.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#signinoptions">SignInOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

---

### signInWithPlayGames(...)

```typescript
signInWithPlayGames(options?: SignInOptions | undefined) => Promise<SignInResult>
```

Starts the Play Games sign-in flow.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#signinoptions">SignInOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

---

### signInWithTwitter(...)

```typescript
signInWithTwitter(options?: SignInOptions | undefined) => Promise<SignInResult>
```

Starts the Twitter sign-in flow.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#signinoptions">SignInOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

---

### signInWithYahoo(...)

```typescript
signInWithYahoo(options?: SignInOptions | undefined) => Promise<SignInResult>
```

Starts the Yahoo sign-in flow.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#signinoptions">SignInOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

---

### signInWithPhoneNumber(...)

```typescript
signInWithPhoneNumber(options: SignInWithPhoneNumberOptions) => Promise<SignInWithPhoneNumberResult>
```

Starts the sign-in flow using a phone number.

Either the phone number or the verification code and verification ID must be provided.

Only available for Android and iOS.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#signinwithphonenumberoptions">SignInWithPhoneNumberOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinwithphonenumberresult">SignInWithPhoneNumberResult</a>&gt;</code>

---

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

---

### signOut()

```typescript
signOut() => Promise<void>
```

Starts the sign-out flow.

---

### useAppLanguage()

```typescript
useAppLanguage() => Promise<void>
```

Sets the user-facing language code to be the default app language.

---

### addListener('authStateChange', ...)

```typescript
addListener(eventName: 'authStateChange', listenerFunc: AuthStateChangeListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

Listen for the user's sign-in state changes.

| Param              | Type                                                                        |
| ------------------ | --------------------------------------------------------------------------- |
| **`eventName`**    | <code>'authStateChange'</code>                                              |
| **`listenerFunc`** | <code><a href="#authstatechangelistener">AuthStateChangeListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

---

### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

---

### Interfaces

#### GetCurrentUserResult

| Prop       | Type                                          | Description                                               |
| ---------- | --------------------------------------------- | --------------------------------------------------------- |
| **`user`** | <code><a href="#user">User</a> \| null</code> | The currently signed-in user, or null if there isn't any. |

#### User

| Prop                | Type                        |
| ------------------- | --------------------------- |
| **`displayName`**   | <code>string \| null</code> |
| **`email`**         | <code>string \| null</code> |
| **`emailVerified`** | <code>boolean</code>        |
| **`isAnonymous`**   | <code>boolean</code>        |
| **`phoneNumber`**   | <code>string \| null</code> |
| **`photoUrl`**      | <code>string \| null</code> |
| **`providerId`**    | <code>string</code>         |
| **`tenantId`**      | <code>string \| null</code> |
| **`uid`**           | <code>string</code>         |

#### GetIdTokenResult

| Prop        | Type                | Description                            |
| ----------- | ------------------- | -------------------------------------- |
| **`token`** | <code>string</code> | The Firebase Auth ID token JWT string. |

#### GetIdTokenOptions

| Prop               | Type                 | Description                                   |
| ------------------ | -------------------- | --------------------------------------------- |
| **`forceRefresh`** | <code>boolean</code> | Force refresh regardless of token expiration. |

#### SetLanguageCodeOptions

| Prop               | Type                | Description                             |
| ------------------ | ------------------- | --------------------------------------- |
| **`languageCode`** | <code>string</code> | BCP 47 language code. Example: `en-US`. |

#### SignInResult

| Prop             | Type                                                              | Description                                               |
| ---------------- | ----------------------------------------------------------------- | --------------------------------------------------------- |
| **`user`**       | <code><a href="#user">User</a> \| null</code>                     | The currently signed-in user, or null if there isn't any. |
| **`credential`** | <code><a href="#authcredential">AuthCredential</a> \| null</code> | Credentials returned by an auth provider.                 |

#### AuthCredential

| Prop              | Type                | Description                                                                                                                              |
| ----------------- | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------- |
| **`providerId`**  | <code>string</code> | The authentication provider ID for the credential. Example: `google.com`.                                                                |
| **`accessToken`** | <code>string</code> | The OAuth access token associated with the credential if it belongs to an OAuth provider.                                                |
| **`idToken`**     | <code>string</code> | The OAuth ID token associated with the credential if it belongs to an OIDC provider.                                                     |
| **`secret`**      | <code>string</code> | The OAuth access token secret associated with the credential if it belongs to an OAuth 1.0 provider.                                     |
| **`nonce`**       | <code>string</code> | The random string used to make sure that the ID token you get was granted specifically in response to your app's authentication request. |

#### SignInOptions

| Prop                   | Type                                 | Description                                                                                       |
| ---------------------- | ------------------------------------ | ------------------------------------------------------------------------------------------------- |
| **`customParameters`** | <code>SignInCustomParameter[]</code> | Configures custom parameters to be passed to the identity provider during the OAuth sign-in flow. |

#### SignInCustomParameter

| Prop        | Type                | Description                                                        |
| ----------- | ------------------- | ------------------------------------------------------------------ |
| **`key`**   | <code>string</code> | The custom parameter key (e.g. `login_hint`).                      |
| **`value`** | <code>string</code> | The custom parameter value (e.g. `user@firstadd.onmicrosoft.com`). |

#### SignInWithPhoneNumberResult

| Prop                 | Type                | Description                                                             |
| -------------------- | ------------------- | ----------------------------------------------------------------------- |
| **`verificationId`** | <code>string</code> | The verification ID, which is needed to identify the verification code. |

#### SignInWithPhoneNumberOptions

| Prop                   | Type                | Description                                                                                                                                         |
| ---------------------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`phoneNumber`**      | <code>string</code> | The phone number to be verified.                                                                                                                    |
| **`verificationId`**   | <code>string</code> | The verification ID which will be returned when `signInWithPhoneNumber` is called for the first time. The `verificationCode` must also be provided. |
| **`verificationCode`** | <code>string</code> | The verification code from the SMS message. The `verificationId` must also be provided.                                                             |

#### SignInWithCustomTokenOptions

| Prop        | Type                | Description                       |
| ----------- | ------------------- | --------------------------------- |
| **`token`** | <code>string</code> | The custom token to sign in with. |

#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |

#### AuthStateChange

| Prop       | Type                                          | Description                                               |
| ---------- | --------------------------------------------- | --------------------------------------------------------- |
| **`user`** | <code><a href="#user">User</a> \| null</code> | The currently signed-in user, or null if there isn't any. |

### Type Aliases

#### AuthStateChangeListener

Callback to receive the user's sign-in state change notifications.

<code>(change: <a href="#authstatechange">AuthStateChange</a>): void</code>

</docgen-api>

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
   If the user should also be logged in on the web layer, additional steps are required (see [here](./docs/firebase-js-sdk.md)).
1. **How can I use this plugin with the Firebase JavaScript SDK?**  
   See [here](./docs/firebase-js-sdk.md).

## Changelog

See [CHANGELOG.md](./CHANGELOG.md).

## License

See [LICENSE](./LICENSE).
