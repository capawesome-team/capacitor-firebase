# @capacitor-firebase/functions

Unofficial Capacitor plugin for [Firebase Cloud Functions](https://firebase.google.com/docs/functions/).[^1]

## Installation

```bash
npm install @capacitor-firebase/functions
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://firebase.google.com/docs/android/setup) / [iOS](https://firebase.google.com/docs/ios/setup)).

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$firebaseFunctionsVersion` version of `com.google.firebase:firebase-functions` (default: `20.4.0`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-firebase-plugin-demo](https://github.com/robingenz/capacitor-firebase-plugin-demo)

## Usage

```typescript
import { FirebaseFunctions } from '@capacitor-firebase/functions';

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
| **`regionOrCustomDomain`** | <code>string</code> | one of: a) The region the callable functions are located in (ex: us-central1) b) A custom domain hosting the callable functions (ex: https://mydomain.com)                      |                   |       |


### Type Aliases


#### CallByNameResult

<code><a href="#callresult">CallResult</a>&lt;ResponseData&gt;</code>


#### CallByUrlResult

<code><a href="#callresult">CallResult</a>&lt;ResponseData&gt;</code>

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/functions/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/functions/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
