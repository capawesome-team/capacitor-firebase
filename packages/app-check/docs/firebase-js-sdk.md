# Firebase JavaScript SDK

> The [Firebase JavaScript SDK](https://firebase.google.com/docs/reference/js) implements the client-side libraries used by applications using Firebase services.

By default, this plugin uses the Firebase JS SDK on the Web, the native Firebase Android SDK on Android, and the native Firebase iOS SDK on iOS. 
If you want to use the Firebase JS SDK on Android and iOS as well, follow the instructions in this guide.

## Installation

Add Firebase to your JavaScript project if you haven't already (see [here](https://firebase.google.com/docs/web/setup)).

## Usage

Initialize App Check on the native layer, create a custom provider and then initialize app check on the web layer using the token from the native layer.

```ts
import { FirebaseAppCheck } from '@capgo/capacitor-firebase-app-check';
import { getApp } from 'firebase/app';
import { initializeAppCheck, CustomProvider } from 'firebase/app-check';

const initialize = async () => {
    // 1. Initialize on the native layer
    await FirebaseAppCheck.initialize();
    // 2. Set up a custom provider
    const provider = new CustomProvider({
        getToken: () => {
            return FirebaseAppCheck.getToken();
        },
    });
    const app = getApp();
    // 3. Initialize on the web layer
    await initializeAppCheck(app, { provider });
};
```
