# @capacitor-firebase/app

Unofficial Capacitor plugin for [Firebase App](https://firebase.google.com/docs).[^1]

## Installation

```bash
npm install @capacitor-firebase/app firebase
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#android) / [iOS](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#ios) / [Web](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#web)).

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$firebaseCommonVersion` version of `com.google.firebase:firebase-common` (default: `20.2.0`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-firebase-plugin-demo](https://github.com/robingenz/capacitor-firebase-plugin-demo)

## Usage

```typescript
import { FirebaseApp } from '@capacitor-firebase/app';

const getName = async () => {
  const result = await FirebaseApp.getName();
};

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

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/app/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/app/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
