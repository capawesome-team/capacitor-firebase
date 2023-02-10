# Add Firebase to your project

These are the required steps to **add Firebase to your Capacitor project**.

**Prerequisites:**

- You have already created a Firebase project (see [Step 1: Create a Firebase project](https://firebase.google.com/docs/android/setup))

**Note:** The steps are based on the official Firebase instructions: [Android](https://firebase.google.com/docs/android/setup) / [iOS](https://firebase.google.com/docs/ios/setup) / [Web](https://firebase.google.com/docs/web/setup).

## Android

### Register your app with Firebase

1. Go to the [Firebase console](https://console.firebase.google.com/).
1. In the center of the project overview page, click the **Android** icon to launch the setup workflow.
   If you've already added an app to your Firebase project, click **Add app** to display the platform options.
1. Enter your app's package name ([`capacitor.config.json`](https://capacitorjs.com/docs/config) -> `appId`) in the **Android package name** field.
1. (Optional) Enter other app information: **App nickname** ([`capacitor.config.json`](https://capacitorjs.com/docs/config) -> `appName`) and **Debug signing certificate SHA-1**.
1. Click **Register app**.

### Add a Firebase configuration file

1. Download and then add the Firebase Android configuration file (`google-services.json`) to your app:
   1. Click **Download google-services.json** to obtain your Firebase Android config file.
   1. Move your config file into the **module (app-level)** root directory of your app: `/android/app/google-services.json`
1. To make the values in your `google-services.json` config file accessible to Firebase SDKs, you need the [Google services Gradle plugin](https://developers.google.com/android/guides/google-services-plugin) (`google-services`).

   1. In your **root-level (project-level)** Gradle file (`<project>/build.gradle`), add the Google services plugin as a buildscript dependency: https://github.com/robingenz/capacitor-firebase-plugin-demo/blob/9e0e2b245b1655271c7979357f32580a2a683168/android/build.gradle#L11

   1. In your module (app-level) Gradle file (usually <project>/<app-module>/build.gradle), add the Google services plugin: https://github.com/robingenz/capacitor-firebase-plugin-demo/blob/9e0e2b245b1655271c7979357f32580a2a683168/android/app/build.gradle#L49

## iOS

### Register your app with Firebase

1. Go to the [Firebase console](https://console.firebase.google.com/).
1. In the center of the project overview page, click the **iOS+** icon to launch the setup workflow.
   If you've already added an app to your Firebase project, click **Add app** to display the platform options.
1. Enter your app's bundle ID ([`capacitor.config.json`](https://capacitorjs.com/docs/config) -> `appId`) in the **bundle ID** field.
1. (Optional) Enter other app information: **App nickname** ([`capacitor.config.json`](https://capacitorjs.com/docs/config) -> `appName`) and **App Store ID**.
1. Click **Register app**.

### Add a Firebase configuration file

1. Click **Download GoogleService-Info.plist** to obtain your Firebase Apple platforms config file (`GoogleService-Info.plist`).
1. Move your config file into the **App** root directory of your app: `/ios/App/App/GoogleService-Info.plist`.
1. Now register the file in your XCode project by dragging it from `/ios/App/App/GoogleService-Info.plist` into the XCode file explorer into the folder `/App/App`.
   If prompted, select to add the config file to all targets.

## Web

### Register your app with Firebase

1. Go to the [Firebase console](https://console.firebase.google.com/).
1. In the center of the project overview page, click the **Web+** icon to launch the setup workflow.
   If you've already added an app to your Firebase project, click **Add app** to display the platform options.
1. Enter your app's nickname.
   This nickname is an internal, convenience identifier and is only visible to you in the Firebase console.
1. Click **Register app**.
1. Follow the on-screen instructions to add and initialize the Firebase SDK in your app.
