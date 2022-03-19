# Set up authentication using Yahoo Sign-In

## Android

1. See [Before you begin](https://firebase.google.com/docs/auth/android/yahoo-oauth#before_you_begin) and follow the instructions to configure sign-in with Yahoo correctly.

## iOS

1. See [Before you begin](https://firebase.google.com/docs/auth/ios/yahoo-oauth#before_you_begin) and follow the instructions to configure and enable sign-in with Yahoo correctly.
1. Add custom URL schemes to your Xcode project:
   1. Open your project configuration.
      Select your app from the **TARGETS** section, then select the **Info** tab, and expand the **URL Types** section.
   1. Click the **+** button, and add a URL scheme for your reversed client ID.
      You find this value in your `GoogleService-Info.plist` configuration file.
      Look for the `REVERSED_CLIENT_ID` key and paste the value of that key into the **URL Schemes** box on the configuration page.
      Leave the other fields blank.

## Web

1. See [Before you begin](https://firebase.google.com/docs/auth/web/yahoo-oauth#before_you_begin) and follow the instructions to configure and enable sign-in with Yahoo correctly.
