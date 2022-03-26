# @capacitor-firebase/app

⚡️ Capacitor plugin for Firebase App.

## Installation

```bash
npm install @capacitor-firebase/app firebase
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://firebase.google.com/docs/android/setup) / [iOS](https://firebase.google.com/docs/ios/setup) / [Web](https://firebase.google.com/docs/web/setup)).

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

--------------------


### getOptions()

```typescript
getOptions() => Promise<GetOptionsResult>
```

Get the configuration options for this app.

**Returns:** <code>Promise&lt;<a href="#getoptionsresult">GetOptionsResult</a>&gt;</code>

--------------------


### Interfaces


#### GetNameResult

| Prop       | Type                | Description                  |
| ---------- | ------------------- | ---------------------------- |
| **`name`** | <code>string</code> | The unique name of this app. |


#### GetOptionsResult

| Prop                | Type                | Description                                                    |
| ------------------- | ------------------- | -------------------------------------------------------------- |
| **`apiKey`**        | <code>string</code> | API key used for authenticating requests from your app.        |
| **`applicationId`** | <code>string</code> | Google App ID used to uniquely identify an instance of an app. |
| **`databaseUrl`**   | <code>string</code> | The database root URL.                                         |
| **`gcmSenderId`**   | <code>string</code> | The Project Number.                                            |
| **`projectId`**     | <code>string</code> | The Google Cloud project ID.                                   |
| **`storageBucket`** | <code>string</code> | The Google Cloud Storage bucket name.                          |

</docgen-api>

## Changelog

See [CHANGELOG.md](./CHANGELOG.md).

## License

See [LICENSE](./LICENSE).
