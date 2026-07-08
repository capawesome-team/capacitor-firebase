# @capacitor-firebase/app

Unofficial Capacitor plugin for [Firebase App](https://firebase.google.com/docs).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The Firebase App plugin is typically used to access the core Firebase configuration of your app, for example:

- **Configuration checks**: Read the Firebase configuration options such as the API key, application ID, and project ID at runtime to verify that the app is connected to the correct Firebase project.
- **Multi-environment apps**: Distinguish between staging and production builds by inspecting the Google Cloud project ID.
- **Debug screens**: Display Firebase project details such as the storage bucket or database URL in an internal debug view.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 7.x.x          | 7.x.x             | Deprecated     |
| 6.x.x          | 6.x.x             | Deprecated     |
| 5.x.x          | 5.x.x             | Deprecated     |
| 1.x.x          | 4.x.x             | Deprecated     |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capacitor-firebase/app` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capacitor-firebase/app firebase
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#android) / [iOS](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#ios) / [Web](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#web)).

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$firebaseCommonVersion` version of `com.google.firebase:firebase-common` (default: `22.0.1`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

### iOS

#### Swift Package Manager

Add the following to your `capacitor.config.json` (or `capacitor.config.ts`) to avoid a [SwiftPM package identity collision](https://github.com/capawesome-team/capacitor-firebase/issues/959):

```json
{
  "experimental": {
    "ios": {
      "spm": {
        "packageOptions": {
          "@capacitor-firebase/app": {
            "symlink": true
          }
        }
      }
    }
  }
}
```

**Attention**: SPM `packageOptions` support requires Capacitor CLI **8.4.0+**.

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-firebase-plugin-demo](https://github.com/robingenz/capacitor-firebase-plugin-demo)

## Usage

Import the plugin and call its methods:

```typescript
import { FirebaseApp } from '@capacitor-firebase/app';
```

### Get the app name

Retrieve the unique name of the Firebase app:

```typescript
const getName = async () => {
  const result = await FirebaseApp.getName();
};
```

### Get the Firebase configuration options

Retrieve the configuration options of the Firebase app, such as the API key, application ID, and project ID:

```typescript
const getOptions = async () => {
  const result = await FirebaseApp.getOptions();
};
```

## API

<docgen-index>

* [`getName()`](#getname)
* [`getOptions()`](#getoptions)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getName()

```typescript
getName() => Promise<GetNameResult>
```

Get the name for this app.

**Returns:** <code>Promise&lt;<a href="#getnameresult">GetNameResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getOptions()

```typescript
getOptions() => Promise<GetOptionsResult>
```

Get the configuration options for this app.

**Returns:** <code>Promise&lt;<a href="#getoptionsresult">GetOptionsResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### GetNameResult

| Prop       | Type                | Description                  | Since |
| ---------- | ------------------- | ---------------------------- | ----- |
| **`name`** | <code>string</code> | The unique name of this app. | 0.1.0 |


#### GetOptionsResult

| Prop                | Type                | Description                                                    | Since |
| ------------------- | ------------------- | -------------------------------------------------------------- | ----- |
| **`apiKey`**        | <code>string</code> | API key used for authenticating requests from your app.        | 0.1.0 |
| **`applicationId`** | <code>string</code> | Google App ID used to uniquely identify an instance of an app. | 0.1.0 |
| **`databaseUrl`**   | <code>string</code> | The database root URL.                                         | 0.1.0 |
| **`gcmSenderId`**   | <code>string</code> | The Project Number.                                            | 0.1.0 |
| **`projectId`**     | <code>string</code> | The Google Cloud project ID.                                   | 0.1.0 |
| **`storageBucket`** | <code>string</code> | The Google Cloud Storage bucket name.                          | 0.1.0 |

</docgen-api>

## FAQ

### Which platforms does this plugin support?

The plugin supports Android, iOS, and Web. Both `getName()` and `getOptions()` are available on all three platforms.

### Do I need to configure anything before using this plugin?

No plugin-specific configuration is required. However, you must add Firebase to your project first. The [Installation](#installation) section links to the Firebase setup instructions for Android, iOS, and Web.

### What information does `getOptions` return?

The `getOptions()` method returns the configuration options of the Firebase app: the API key used for authenticating requests, the Google App ID, the database root URL, the project number, the Google Cloud project ID, and the Google Cloud Storage bucket name.

### How do I fix the SwiftPM package identity collision on iOS?

If you are using Swift Package Manager, add the `symlink` package option for `@capacitor-firebase/app` to your Capacitor configuration as described in the [Installation](#installation) section. Note that SPM `packageOptions` support requires Capacitor CLI 8.4.0 or later.

### Is this an official Firebase plugin?

No, this is an unofficial Capacitor plugin for the Firebase App SDK. The project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Firebase Analytics](https://capawesome.io/docs/sdks/capacitor/firebase/analytics/): Log events and track user interactions with Firebase Analytics.
- [Firebase Authentication](https://capawesome.io/docs/sdks/capacitor/firebase/authentication/): Authenticate users with Firebase Authentication.
- [Firebase Crashlytics](https://capawesome.io/docs/sdks/capacitor/firebase/crashlytics/): Track and report app crashes with Firebase Crashlytics.
- [Firebase Cloud Messaging](https://capawesome.io/docs/sdks/capacitor/firebase/cloud-messaging/): Receive push notifications with Firebase Cloud Messaging.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/app/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/app/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
