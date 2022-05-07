# Firebase JavaScript SDK

> The [Firebase JavaScript SDK](https://firebase.google.com/docs/reference/js) implements the client-side libraries used by applications using Firebase services.

## How to use this plugin with the Firebase JavaScript SDK

By default, this plugin signs the user in only on the native layer of the app.
In order to use the Firebase JavaScript SDK, a sign-in on the web layer is required.
To do this, follow these steps:

1. [Add Firebase to your JavaScript project](https://firebase.google.com/docs/web/setup)
1. Set the configuration option `skipNativeAuth` to `true` (see [here](/packages/authentication/README.md#configuration)).
1. Sign in on the native layer, create web credentials and sign in on the web using [`signInWithCredential`](https://firebase.google.com/docs/reference/js/auth.md#signinwithcredential) (see [Examples](#examples)).

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
```

The dependencies used in these examples:

- `firebase@9.0.1`
- `@capacitor-firebase/authentication@0.1.0`
