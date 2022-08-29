# Firebase JavaScript SDK

> The [Firebase JavaScript SDK](https://firebase.google.com/docs/reference/js) implements the client-side libraries used by applications using Firebase services.

## How to use this plugin with the Firebase JavaScript SDK

By default, this plugin signs the user in only on the native layer of the app.
In order to use the Firebase JavaScript SDK on Android and iOS, a sign-in on the web layer is required.
To do this, follow these steps:

1. [Add Firebase to your JavaScript project](https://firebase.google.com/docs/web/setup)
1. Set the configuration option [`skipNativeAuth`](/packages/authentication/README.md#configuration) to `true` (sometimes you also need `false`, see [Quirks](#quirks)).
1. Sign in on the native layer, create web credentials and sign in on the web using [`signInWithCredential`](https://firebase.google.com/docs/reference/js/auth.md#signinwithcredential) (see [Examples](#examples)).

## Quirks

When using the Firebase JS SDK on Android and iOS, you must be aware of the following:

- **Apple Sign-In** works on Android and iOS only with `skipNativeAuth=true` (see [here](https://github.com/robingenz/capacitor-firebase-authentication/issues/41#issuecomment-884106449)).
- **Twitter Sign-In** works on iOS only with `skipNativeAuth=false` (see [here](https://github.com/robingenz/capacitor-firebase-authentication/issues/93#issuecomment-939459594)).

**Note**: The [`skipNativeAuth`](/packages/authentication/README.md#configuration) configuration option can be overwritten for each plugin call individually (see `skipNativeAuth` parameter in [SignInOptions](/packages/authentication/README.md#signinoptions)).

## Examples

```js
import { FirebaseAuthentication } from '@capacitor-firebase/authentication';
import {
  getAuth,
  GoogleAuthProvider,
  OAuthProvider,
  PhoneAuthProvider,
  signInWithCredential,
} from 'firebase/auth';

const signInWithApple = async () => {
  // 1. Create credentials on the native layer
  const result = await FirebaseAuthentication.signInWithApple();
  // 2. Sign in on the web layer using the id token and nonce
  const provider = new OAuthProvider('apple.com');
  const credential = provider.credential({
    idToken: result.credential?.idToken,
    rawNonce: result.credential?.nonce,
  });
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

const signInWithPhoneNumber = async () => {
  // 1. Start phone number verification
  const { verificationId } = await FirebaseAuthentication.signInWithPhoneNumber(
    {
      phoneNumber: '123456789',
    },
  );
  // 2. Let the user enter the SMS code
  const verificationCode = window.prompt(
    'Please enter the verification code that was sent to your mobile device.',
  );
  // 3. Sign in on the web layer using the verification ID and verification code.
  const credential = PhoneAuthProvider.credential(
    verificationId || '',
    verificationCode || '',
  );
  const auth = getAuth();
  await signInWithCredential(auth, credential);
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
    }
  });
  // The link was successfully sent. Inform the user.
  // Save the email locally so you don't need to ask the user for it again
  // if they open the link on the same device.
  window.localStorage.setItem('emailForSignIn', email);
};

const signInWithEmailLink = async () => {
  // Get the email if available. This should be available if the user completes
  // the flow on the same device where they started it.
  const emailLink = window.location.href;
  // Confirm the link is a sign-in with email link.
  const result = await FirebaseAuthentication.isSignInWithEmailLink({
    emailLink,
  });
  if (
    result.isSignInWithEmailLink
  ) {
    let email = window.localStorage.getItem('emailForSignIn');
    if (!email) {
      // User opened the link on a different device. To prevent session fixation
      // attacks, ask the user to provide the associated email again.
      email = window.prompt(
        'Please provide your email for confirmation.',
      );
    }
    // The client SDK will parse the code from the link for you.
    const result = await FirebaseAuthentication.signInWithEmailLink({
      email,
      emailLink,
    });
    // Clear email from storage.
    window.localStorage.removeItem('emailForSignIn');
    return result.user;
  } else {
    alert('emailLink is invalid.');
  }
};
```

The dependencies used in these examples:

- `firebase@9.0.1`
- `@capacitor-firebase/authentication@0.1.0`
