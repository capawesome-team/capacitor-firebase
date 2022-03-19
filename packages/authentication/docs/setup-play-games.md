# Set up authentication using Play Games Sign-In

## Android

1.  Add the following project variable to your `variables.gradle` file (usually `android/build.gradle`):
    ```diff
    ext {
    +    rgcfaIncludeGoogle = true
    }
    ```
    Run [`npx cap update`](https://capacitorjs.com/docs/cli/update) to update the native plugins and dependencies.
1.  See [Before you begin](https://firebase.google.com/docs/auth/android/play-games#before_you_begin) and follow the instructions to configure sign-in with Play Games correctly.  
    **Attention**: Skip step 2 in [Set up your Android project](https://firebase.google.com/docs/auth/android/play-games#set_up_your_android_project). The dependency for the Firebase Authentication Android library is already declared by the plugin.

## iOS

🚧 Currently not supported.

## Web

🚧 Currently not supported.
