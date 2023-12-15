# Firebase JavaScript SDK

> The [Firebase JavaScript SDK](https://firebase.google.com/docs/reference/js) implements the client-side libraries used by applications using Firebase services.

By default, this plugin uses the Firebase JS SDK on the Web, the native Firebase Android SDK on Android, and the native Firebase iOS SDK on iOS.
This means, by default, this plugin signs the user in only on the native layer of the app.
If you want to use the Firebase JS SDK on Android and iOS as well, follow the instructions in this guide.

## Installation

Add Firebase to your JavaScript project if you haven't already (see [here](https://firebase.google.com/docs/web/setup)).

Next you need to call `initializeAuth` with `persistence` set to `indexedDBLocalPersistence`:

```ts
import { Capacitor } from '@capacitor/core';
import { getApp } from 'firebase/app';
import {
  getAuth,
  indexedDBLocalPersistence,
  initializeAuth,
} from 'firebase/auth';

const getFirebaseAuth = async () => {
  if (Capacitor.isNativePlatform()) {
    return initializeAuth(getApp(), {
      persistence: indexedDBLocalPersistence,
    });
  } else {
    return getAuth();
  }
};
```

This makes sure that the user is still signed in the next time the app is started.

## Usage

Sign in the user on the native layer, create web credentials and sign in the user on the web layer using [`signInWithCredential`](https://firebase.google.com/docs/reference/js/auth.md#signinwithcredential).

```js
import { FirebaseAuthentication } from '@capacitor-firebase/authentication';
import {
  getAuth,
  GoogleAuthProvider,
  OAuthProvider,
  PhoneAuthProvider,
  signInWithCredential,
  EmailAuthProvider,
  signOut,
} from 'firebase/auth';

const signInWithApple = async () => {
  // 1. Create credentials on the native layer
  const result = await FirebaseAuthentication.signInWithApple({
    skipNativeAuth: true,
  });
  // 2. Sign in on the web layer using the id token and nonce
  const provider = new OAuthProvider('apple.com');
  const credential = provider.credential({
    idToken: result.credential?.idToken,
    rawNonce: result.credential?.nonce,
  });
  const auth = getAuth();
  await signInWithCredential(auth, credential);
};

const signInWithFacebook = async () => {
  // 1. Create credentials on the native layer
  const result = await FirebaseAuthentication.signInWithFacebook();
  // 2. Sign in on the web layer using the access token
  const credential = FacebookAuthProvider.credential(
    result.credential?.accessToken,
  );
  const auth = getAuth();
  await signInWithCredential(auth, credential);
};

const signInWithGoogle = async () => {
  // 1. Create credentials on the native layer
  const result = await FirebaseAuthentication.signInWithGoogle();
  // 2. Sign in on the web layer using the id token
  const credential = GoogleAuthProvider.credential(result.credential?.idToken);
  const auth = getAuth();
  await signInWithCredential(auth, credential);
};

const signInWithPhoneNumber = async () => {
  return new Promise(async resolve => {
    await FirebaseAuthentication.addListener('phoneCodeSent', async event => {
      // 2. Let the user enter the SMS code
      const verificationCode = window.prompt(
        'Please enter the verification code that was sent to your mobile device.',
      );
      // 3. Sign in on the web layer using the verification ID and verification code.
      const credential = PhoneAuthProvider.credential(
        event.verificationId,
        verificationCode,
      );
      const auth = getAuth();
      await signInWithCredential(auth, credential);
      resolve();
    });
    // 1. Start phone number verification
    await FirebaseAuthentication.signInWithPhoneNumber({
      phoneNumber: '123456789',
      timeout: 0, // Disable SMS auto-retrieval
    });
  });
};

const signInWithTwitter = async () => {
  // 1. Create credentials on the native layer
  const result = await FirebaseAuthentication.signInWithTwitter({
    skipNativeAuth: false,
  });
  // 2. Sign in on the web layer using the access token and secret
  const credential = TwitterAuthProvider.credential(
    result.credential?.accessToken,
    result.credential?.secret,
  );
  const auth = getAuth();
  await signInWithCredential(auth, credential);
};

const linkWithGoogle = async () => {
  // 1. Create credentials on the native layer
  const result = await FirebaseAuthentication.signInWithGoogle();
  // 2. Link on the web layer using the id token
  const credential = GoogleAuthProvider.credential(result.credential?.idToken);
  const auth = getAuth();
  await linkWithCredential(auth, credential);
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
  const credential = EmailAuthProvider.credentialWithLink(email, emailLink);
  const auth = getAuth();
  const result = await signInWithCredential(auth, credential);
  // Clear email from storage.
  window.localStorage.removeItem('emailForSignIn');
  return result.user;
};

const signOut = async () => {
  // 1. Sign out on the native layer
  await FirebaseAuthentication.signOut();
  // 1. Sign out on the web layer
  const auth = getAuth();
  await signOut(auth);
};
```

## Quirks

When using the Firebase JS SDK on Android and iOS, you must be aware of the following:

- **Apple Sign-In**: Works on Android and iOS only with `skipNativeAuth=true` (see [here](https://github.com/robingenz/capacitor-firebase-authentication/issues/41#issuecomment-884106449)).
- **Microsoft Sign-In**: Not supported (see https://github.com/capawesome-team/capacitor-firebase/discussions/216#discussioncomment-3803525)
- **Twitter Sign-In**: Works on iOS only with `skipNativeAuth=false` (see [here](https://github.com/robingenz/capacitor-firebase-authentication/issues/93#issuecomment-939459594)).

**Note**: The [`skipNativeAuth`](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/README.md#configuration) configuration option can be overwritten for each plugin call individually (see `skipNativeAuth` parameter in [SignInOptions](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/README.md#signinoptions)).
