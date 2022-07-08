# @capacitor-firebase/authentication

⚡️ Capacitor plugin for Firebase Authentication.

## Installation

```bash
npm install @capacitor-firebase/authentication firebase
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://firebase.google.com/docs/android/setup) / [iOS](https://firebase.google.com/docs/ios/setup) / [Web](https://firebase.google.com/docs/web/setup)).

On **Android**, please make sure that you are using **Java 11**.

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

- [Apple Sign-In](/packages/authentication/docs/setup-apple.md)
- [Facebook Sign-In](/packages/authentication/docs/setup-facebook.md)
- [GitHub Sign-In](/packages/authentication/docs/setup-github.md)
- [Google Sign-In](/packages/authentication/docs/setup-google.md)
- [Microsoft Sign-In](/packages/authentication/docs/setup-microsoft.md)
- [Play Games Sign-In](/packages/authentication/docs/setup-play-games.md)
- [Twitter Sign-In](/packages/authentication/docs/setup-twitter.md)
- [Yahoo Sign-In](/packages/authentication/docs/setup-yahoo.md)
- [Phone Number Sign-In](/packages/authentication/docs/setup-phone.md)
- [Custom Token Sign-In](/packages/authentication/docs/custom-token.md)

**Attention**: Please note that this plugin uses third-party SDKs to offer native sign-in.
These SDKs can initialize on their own and collect various data.
[Here](docs/third-party-sdks.md) you can find more information.

## Configuration

<docgen-config>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

These configuration values are available:

| Prop                 | Type                  | Description                                                                                                                                                                                                                                                                                            | Default                                                                                                                                              | Since |
| -------------------- | --------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`skipNativeAuth`** | <code>boolean</code>  | Configure whether the plugin should skip the native authentication. Only needed if you want to use the Firebase JavaScript SDK. Only available for Android and iOS.                                                                                                                                    | <code>false</code>                                                                                                                                   | 0.1.0 |
| **`providers`**      | <code>string[]</code> | Configure which providers you want to use so that only the providers you need are fully initialized. If you do not configure any providers, they will be all initialized. Please note that this does not prevent the automatic initialization of third-party SDKs. Only available for Android and iOS. | <code>["apple.com", "facebook.com", "github.com", "google.com", "microsoft.com", "playgames.google.com", "twitter.com", "yahoo.com", "phone"]</code> | 0.1.0 |

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
      providers: ["apple.com", "google.com"],
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

const getCurrentUser = async () => {
  const result = await FirebaseAuthentication.getCurrentUser();
  return result.user;
};

const getIdToken = async () => {
  const currentUser = getCurrentUser();
  if (!currentUser) {
    return;
  }
  const result = await FirebaseAuthentication.getIdToken();
  return result.token;
};

const sendEmailVerification = async () => {
  const currentUser = getCurrentUser();
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

const setLanguageCode = async () => {
  await FirebaseAuthentication.setLanguageCode({ languageCode: 'en-US' });
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

const signInWithFacebook = async () => {
  const result = await FirebaseAuthentication.signInWithFacebook();
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

const signInWithPlayGames = async () => {
  const result = await FirebaseAuthentication.signInWithPlayGames();
  return result.user;
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
  const result = await FirebaseAuthentication.signInWithPhoneNumber({
    verificationId,
    verificationCode,
  });
  return result.user;
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
  const currentUser = getCurrentUser();
  if (!currentUser) {
    return;
  }
  await FirebaseAuthentication.updateEmail({
    newEmail: 'new.mail@example.com',
  });
};

const updatePassword = async () => {
  const currentUser = getCurrentUser();
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

* [`applyActionCode(...)`](#applyactioncode)
* [`createUserWithEmailAndPassword(...)`](#createuserwithemailandpassword)
* [`confirmPasswordReset(...)`](#confirmpasswordreset)
* [`getCurrentUser()`](#getcurrentuser)
* [`getIdToken(...)`](#getidtoken)
* [`sendEmailVerification()`](#sendemailverification)
* [`sendPasswordResetEmail(...)`](#sendpasswordresetemail)
* [`setLanguageCode(...)`](#setlanguagecode)
* [`signInWithApple(...)`](#signinwithapple)
* [`signInWithCustomToken(...)`](#signinwithcustomtoken)
* [`signInWithEmailAndPassword(...)`](#signinwithemailandpassword)
* [`signInWithFacebook(...)`](#signinwithfacebook)
* [`signInWithGithub(...)`](#signinwithgithub)
* [`signInWithGoogle(...)`](#signinwithgoogle)
* [`signInWithMicrosoft(...)`](#signinwithmicrosoft)
* [`signInWithPhoneNumber(...)`](#signinwithphonenumber)
* [`signInWithPlayGames(...)`](#signinwithplaygames)
* [`signInWithTwitter(...)`](#signinwithtwitter)
* [`signInWithYahoo(...)`](#signinwithyahoo)
* [`signOut()`](#signout)
* [`updateEmail(...)`](#updateemail)
* [`updatePassword(...)`](#updatepassword)
* [`useAppLanguage()`](#useapplanguage)
* [`useEmulator(...)`](#useemulator)
* [`addListener('authStateChange', ...)`](#addlistenerauthstatechange)
* [`removeAllListeners()`](#removealllisteners)
* [`verifyPhoneNumber(...)`](#verifyphonenumber)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

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


### getCurrentUser()

```typescript
getCurrentUser() => Promise<GetCurrentUserResult>
```

Fetches the currently signed-in user.

**Returns:** <code>Promise&lt;<a href="#getcurrentuserresult">GetCurrentUserResult</a>&gt;</code>

**Since:** 0.1.0

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


### sendEmailVerification()

```typescript
sendEmailVerification() => Promise<void>
```

Sends a verification email to the currently signed in user.

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


### signInWithApple(...)

```typescript
signInWithApple(options?: SignInOptions | undefined) => Promise<SignInResult>
```

Starts the Apple sign-in flow.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#signinoptions">SignInOptions</a></code> |

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


### signInWithFacebook(...)

```typescript
signInWithFacebook(options?: SignInOptions | undefined) => Promise<SignInResult>
```

Starts the Facebook sign-in flow.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#signinoptions">SignInOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### signInWithGithub(...)

```typescript
signInWithGithub(options?: SignInOptions | undefined) => Promise<SignInResult>
```

Starts the GitHub sign-in flow.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#signinoptions">SignInOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### signInWithGoogle(...)

```typescript
signInWithGoogle(options?: SignInOptions | undefined) => Promise<SignInResult>
```

Starts the Google sign-in flow.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#signinoptions">SignInOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### signInWithMicrosoft(...)

```typescript
signInWithMicrosoft(options?: SignInOptions | undefined) => Promise<SignInResult>
```

Starts the Microsoft sign-in flow.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#signinoptions">SignInOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


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

**Since:** 0.1.0

--------------------


### signInWithPlayGames(...)

```typescript
signInWithPlayGames(options?: SignInOptions | undefined) => Promise<SignInResult>
```

Starts the Play Games sign-in flow.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#signinoptions">SignInOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### signInWithTwitter(...)

```typescript
signInWithTwitter(options?: SignInOptions | undefined) => Promise<SignInResult>
```

Starts the Twitter sign-in flow.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#signinoptions">SignInOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### signInWithYahoo(...)

```typescript
signInWithYahoo(options?: SignInOptions | undefined) => Promise<SignInResult>
```

Starts the Yahoo sign-in flow.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#signinoptions">SignInOptions</a></code> |

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
addListener(eventName: 'authStateChange', listenerFunc: AuthStateChangeListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

Listen for the user's sign-in state changes.

| Param              | Type                                                                        |
| ------------------ | --------------------------------------------------------------------------- |
| **`eventName`**    | <code>'authStateChange'</code>                                              |
| **`listenerFunc`** | <code><a href="#authstatechangelistener">AuthStateChangeListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

**Since:** 0.1.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.1.0

--------------------


### verifyPhoneNumber(...)

```typescript
verifyPhoneNumber(options?: { phoneNumber?: string | undefined; } | undefined) => Promise<{ verificationId: string; }>
```

Sends an SMS to the user. If no phoneNumber is provided, uses the first hint from the resolver session (must call signInWithEmailAndPassword first to grab the resolver session when 2nd factor is needed)

| Param         | Type                                   |
| ------------- | -------------------------------------- |
| **`options`** | <code>{ phoneNumber?: string; }</code> |

**Returns:** <code>Promise&lt;{ verificationId: string; }&gt;</code>

**Since:** New forked Repo

--------------------


### Interfaces


#### ApplyActionCodeOptions

| Prop          | Type                | Description                           | Since |
| ------------- | ------------------- | ------------------------------------- | ----- |
| **`oobCode`** | <code>string</code> | A verification code sent to the user. | 0.2.2 |


#### SignInResult

| Prop             | Type                                                              | Description                                               | Since |
| ---------------- | ----------------------------------------------------------------- | --------------------------------------------------------- | ----- |
| **`user`**       | <code><a href="#user">User</a> \| null</code>                     | The currently signed-in user, or null if there isn't any. | 0.1.0 |
| **`credential`** | <code><a href="#authcredential">AuthCredential</a> \| null</code> | Credentials returned by an auth provider.                 | 0.1.0 |


#### User

| Prop                | Type                        | Since |
| ------------------- | --------------------------- | ----- |
| **`displayName`**   | <code>string \| null</code> | 0.1.0 |
| **`email`**         | <code>string \| null</code> | 0.1.0 |
| **`emailVerified`** | <code>boolean</code>        | 0.1.0 |
| **`isAnonymous`**   | <code>boolean</code>        | 0.1.0 |
| **`phoneNumber`**   | <code>string \| null</code> | 0.1.0 |
| **`photoUrl`**      | <code>string \| null</code> | 0.1.0 |
| **`providerId`**    | <code>string</code>         | 0.1.0 |
| **`tenantId`**      | <code>string \| null</code> | 0.1.0 |
| **`uid`**           | <code>string</code>         | 0.1.0 |


#### AuthCredential

| Prop              | Type                | Description                                                                                                                              | Since |
| ----------------- | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`providerId`**  | <code>string</code> | The authentication provider ID for the credential.                                                                                       | 0.1.0 |
| **`accessToken`** | <code>string</code> | The OAuth access token associated with the credential if it belongs to an OAuth provider.                                                | 0.1.0 |
| **`idToken`**     | <code>string</code> | The OAuth ID token associated with the credential if it belongs to an OIDC provider.                                                     | 0.1.0 |
| **`secret`**      | <code>string</code> | The OAuth access token secret associated with the credential if it belongs to an OAuth 1.0 provider.                                     | 0.1.0 |
| **`nonce`**       | <code>string</code> | The random string used to make sure that the ID token you get was granted specifically in response to your app's authentication request. | 0.1.0 |


#### CreateUserWithEmailAndPasswordOptions

| Prop           | Type                | Since |
| -------------- | ------------------- | ----- |
| **`email`**    | <code>string</code> | 0.2.2 |
| **`password`** | <code>string</code> | 0.2.2 |


#### ConfirmPasswordResetOptions

| Prop              | Type                | Description                           | Since |
| ----------------- | ------------------- | ------------------------------------- | ----- |
| **`oobCode`**     | <code>string</code> | A verification code sent to the user. | 0.2.2 |
| **`newPassword`** | <code>string</code> | The new password.                     | 0.2.2 |


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


#### SendPasswordResetEmailOptions

| Prop        | Type                | Since |
| ----------- | ------------------- | ----- |
| **`email`** | <code>string</code> | 0.2.2 |


#### SetLanguageCodeOptions

| Prop               | Type                | Description           | Since |
| ------------------ | ------------------- | --------------------- | ----- |
| **`languageCode`** | <code>string</code> | BCP 47 language code. | 0.1.0 |


#### SignInOptions

| Prop                   | Type                                 | Description                                                                                       | Since |
| ---------------------- | ------------------------------------ | ------------------------------------------------------------------------------------------------- | ----- |
| **`customParameters`** | <code>SignInCustomParameter[]</code> | Configures custom parameters to be passed to the identity provider during the OAuth sign-in flow. | 0.1.0 |
| **`scopes`**           | <code>string[]</code>                | Scopes to request from provider.                                                                  | 0.3.1 |


#### SignInCustomParameter

| Prop        | Type                | Description                                                        | Since |
| ----------- | ------------------- | ------------------------------------------------------------------ | ----- |
| **`key`**   | <code>string</code> | The custom parameter key (e.g. `login_hint`).                      | 0.1.0 |
| **`value`** | <code>string</code> | The custom parameter value (e.g. `user@firstadd.onmicrosoft.com`). | 0.1.0 |


#### SignInWithCustomTokenOptions

| Prop        | Type                | Description                       | Since |
| ----------- | ------------------- | --------------------------------- | ----- |
| **`token`** | <code>string</code> | The custom token to sign in with. | 0.1.0 |


#### SignInWithEmailAndPasswordOptions

| Prop           | Type                | Description              | Since |
| -------------- | ------------------- | ------------------------ | ----- |
| **`email`**    | <code>string</code> | The users email address. | 0.2.2 |
| **`password`** | <code>string</code> | The users password.      | 0.2.2 |


#### SignInWithPhoneNumberResult

| Prop                 | Type                | Description                                                             | Since |
| -------------------- | ------------------- | ----------------------------------------------------------------------- | ----- |
| **`verificationId`** | <code>string</code> | The verification ID, which is needed to identify the verification code. | 0.1.0 |


#### SignInWithPhoneNumberOptions

| Prop                   | Type                | Description                                                                                                                                         | Since |
| ---------------------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`phoneNumber`**      | <code>string</code> | The phone number to be verified.                                                                                                                    | 0.1.0 |
| **`verificationId`**   | <code>string</code> | The verification ID which will be returned when `signInWithPhoneNumber` is called for the first time. The `verificationCode` must also be provided. | 0.1.0 |
| **`verificationCode`** | <code>string</code> | The verification code from the SMS message. The `verificationId` must also be provided.                                                             | 0.1.0 |


#### UpdateEmailOptions

| Prop           | Type                | Description            | Since |
| -------------- | ------------------- | ---------------------- | ----- |
| **`newEmail`** | <code>string</code> | The new email address. | 0.2.2 |


#### UpdatePasswordOptions

| Prop              | Type                | Description       | Since |
| ----------------- | ------------------- | ----------------- | ----- |
| **`newPassword`** | <code>string</code> | The new password. | 0.2.2 |


#### UseEmulatorOptions

| Prop       | Type                | Description                          | Default           | Since |
| ---------- | ------------------- | ------------------------------------ | ----------------- | ----- |
| **`host`** | <code>string</code> | The emulator host (e.g. `10.0.2.2`). |                   | 0.2.0 |
| **`port`** | <code>number</code> | The emulator port (e.g. `9099`).     | <code>9099</code> | 0.2.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### AuthStateChange

| Prop       | Type                                          | Description                                               | Since |
| ---------- | --------------------------------------------- | --------------------------------------------------------- | ----- |
| **`user`** | <code><a href="#user">User</a> \| null</code> | The currently signed-in user, or null if there isn't any. | 0.1.0 |


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
   If the user should also be logged in on the web layer, additional steps are required (see [here](/packages/authentication/docs/firebase-js-sdk.md)).
1. **How can I use this plugin with the Firebase JavaScript SDK?**  
   See [here](/packages/authentication/docs/firebase-js-sdk.md).

## Changelog

See [CHANGELOG.md](/packages/authentication/CHANGELOG.md).

## License

See [LICENSE](/packages/authentication/LICENSE).

## Credits

This plugin is based on the [Capacitor Firebase Authentication plugin](https://github.com/robingenz/capacitor-firebase-authentication).
Thanks to everyone who contributed to the project!
