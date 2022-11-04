# Set up authentication using Game Center Sign-In

## Android

🚧 Currently not supported.

## iOS

1. Add `gc.apple.com` to the `providers` [configuration](https://github.com/capawesome-team/capacitor-firebase/tree/main/packages/authentication#configuration) array.
1.  Make sure you register your Apple app with Firebase. This means entering your app's bundle ID in the registration section along with additional optional information such as App Store ID and Team ID, etc. This will be required for securely verifying the audience of the user's Game Center credential before completing sign-in.
1. Enable Game Center as a sign-in provider for your Firebase project.

## Web

🚧 Currently not supported.
