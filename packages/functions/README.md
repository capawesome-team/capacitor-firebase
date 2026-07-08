# @capacitor-firebase/functions

Unofficial Capacitor plugin for [Firebase Cloud Functions](https://firebase.google.com/docs/functions/).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The Firebase Cloud Functions plugin is typically used to run backend logic from your Capacitor app without managing your own server, for example:

- **Serverless backend logic**: Call callable Cloud Functions by name to run trusted server-side code from your app.
- **Structured data exchange**: Pass strings, numbers, booleans, arrays, and objects to a function and process the returned result.
- **Custom domains and regions**: Call functions that are deployed in a specific region or hosted on a custom domain by their URL.
- **Local development**: Test your functions against the Cloud Functions emulator before deploying them to production.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 7.x.x          | 7.x.x             | Deprecated     |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capacitor-firebase/functions` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capacitor-firebase/functions
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://firebase.google.com/docs/android/setup) / [iOS](https://firebase.google.com/docs/ios/setup)).

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$firebaseFunctionsVersion` version of `com.google.firebase:firebase-functions` (default: `22.1.0`)

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
          "@capacitor-firebase/functions": {
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
import { FirebaseFunctions } from '@capacitor-firebase/functions';
```

### Call a function by name

Call a callable Cloud Function by its name. You can pass strings, numbers, booleans, arrays, and objects as data and receive the result of the function in the `data` property. Use the `region` option if your function is not deployed in the default region:

```typescript
const callByName = async () => {
    const { data } = await FirebaseFunctions.callByName({
        name: 'helloWorld',
        data: {
            string: 'Hello World!',
            number: 123,
            boolean: true,
            array: [1, 2, 3],
            object: {
                key: 'value'
            }
        }
    });
    return data;
};
```

### Call a function by URL

If your callable function is hosted on a custom domain or you prefer to address it directly, call it by its URL:

```typescript
const callByUrl = async () => {
    const { data } = await FirebaseFunctions.callByUrl({
        url: 'https://us-central1-YOUR_PROJECT_ID.cloudfunctions.net/helloWorld',
        data: {
            string: 'Hello World!',
            number: 123,
            boolean: true,
            array: [1, 2, 3],
            object: {
                key: 'value'
            }
        }
    });
    return data;
};
```

### Use the Cloud Functions emulator

During development, you can instrument your app to talk to the local Cloud Functions emulator. When using an Android emulator device, `10.0.2.2` is the special IP address to connect to the `localhost` of the host computer. Note that on Android, the cleartext traffic must be allowed, which is not intended for use in production:

```typescript
const useEmulator = async () => {
  await FirebaseFunctions.useEmulator({
    host: '10.0.2.2',
    port: 9001,
  });
};
```

## API

<docgen-index>

* [`callByName(...)`](#callbyname)
* [`callByUrl(...)`](#callbyurl)
* [`useEmulator(...)`](#useemulator)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### callByName(...)

```typescript
callByName<RequestData = unknown, ResponseData = unknown>(options: CallByNameOptions<RequestData>) => Promise<CallByNameResult<ResponseData>>
```

Call a callable function by name.

| Param         | Type                                                                               |
| ------------- | ---------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#callbynameoptions">CallByNameOptions</a>&lt;RequestData&gt;</code> |

**Returns:** <code>Promise&lt;<a href="#callbynameresult">CallByNameResult</a>&lt;ResponseData&gt;&gt;</code>

**Since:** 6.1.0

--------------------


### callByUrl(...)

```typescript
callByUrl<RequestData = unknown, ResponseData = unknown>(options: CallByUrlOptions<RequestData>) => Promise<CallByUrlResult<ResponseData>>
```

Call a callable function by URL.

| Param         | Type                                                                             |
| ------------- | -------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#callbyurloptions">CallByUrlOptions</a>&lt;RequestData&gt;</code> |

**Returns:** <code>Promise&lt;<a href="#callbyurlresult">CallByUrlResult</a>&lt;ResponseData&gt;&gt;</code>

**Since:** 6.1.0

--------------------


### useEmulator(...)

```typescript
useEmulator(options: UseEmulatorOptions) => Promise<void>
```

Instrument your app to talk to the Cloud Functions emulator.

On Android, the cleartext traffic must be allowed. On the Capacitor configuration:
```
{
  server: {
    cleartext: true
  }
}
```
**The cleartext traffic is not intended for use in production.**

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#useemulatoroptions">UseEmulatorOptions</a></code> |

**Since:** 6.1.0

--------------------


### Interfaces


#### CallResult

| Prop       | Type                      | Description                          | Since |
| ---------- | ------------------------- | ------------------------------------ | ----- |
| **`data`** | <code>ResponseData</code> | The result of the callable function. | 6.1.0 |


#### CallByNameOptions

| Prop         | Type                | Description                          | Since |
| ------------ | ------------------- | ------------------------------------ | ----- |
| **`name`**   | <code>string</code> | The name of the callable function.   | 6.1.0 |
| **`region`** | <code>string</code> | The region of the callable function. | 6.1.0 |


#### CallByUrlOptions

| Prop      | Type                | Description                       | Since |
| --------- | ------------------- | --------------------------------- | ----- |
| **`url`** | <code>string</code> | The URL of the callable function. | 6.1.0 |


#### UseEmulatorOptions

| Prop                       | Type                | Description                                                                                                                                                                     | Default           | Since |
| -------------------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`host`**                 | <code>string</code> | The emulator host without any port or scheme. Note when using a Android Emulator device: 10.0.2.2 is the special IP address to connect to the 'localhost' of the host computer. |                   | 6.1.0 |
| **`port`**                 | <code>number</code> | The emulator port.                                                                                                                                                              | <code>5001</code> | 6.1.0 |
| **`regionOrCustomDomain`** | <code>string</code> | The region the callable functions are located in or a custom domain hosting the callable functions.                                                                             |                   |       |


### Type Aliases


#### CallByNameResult

<code><a href="#callresult">CallResult</a>&lt;ResponseData&gt;</code>


#### CallByUrlResult

<code><a href="#callresult">CallResult</a>&lt;ResponseData&gt;</code>

</docgen-api>

## FAQ

### What is the difference between `callByName` and `callByUrl`?

The `callByName(...)` method calls a callable function by its name and, optionally, its region. The `callByUrl(...)` method calls a callable function by its full URL instead, which is useful if the function is hosted on a custom domain.

### What data types can I pass to a callable function?

You can pass strings, numbers, booleans, arrays, and nested objects as data to a callable function. The result of the function is returned in the `data` property of the call result.

### How do I test my functions locally?

Use the `useEmulator(...)` method to instrument your app to talk to the local Cloud Functions emulator, as shown in the [usage example](#use-the-cloud-functions-emulator) above. When testing on an Android emulator device, use `10.0.2.2` as the host to reach the `localhost` of the host computer. Keep in mind that on Android, the cleartext traffic must be allowed for this, which is not intended for use in production.

### Why do I get a SwiftPM package identity collision on iOS?

This is a known issue when using the plugin with Swift Package Manager. Add the `symlink` package option for `@capacitor-firebase/functions` to your Capacitor configuration as described in the [Installation](#installation) section. Note that SPM `packageOptions` support requires Capacitor CLI 8.4.0 or later.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Firebase Authentication](https://capawesome.io/docs/sdks/capacitor/firebase/authentication/): Sign in users with Firebase Authentication.
- [Firebase Cloud Firestore](https://capawesome.io/docs/sdks/capacitor/firebase/cloud-firestore/): Store and sync app data in Cloud Firestore.
- [Firebase Cloud Storage](https://capawesome.io/docs/sdks/capacitor/firebase/cloud-storage/): Upload and download files with Firebase Cloud Storage.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/functions/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/functions/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
