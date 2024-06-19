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

const echo = async () => {
};
```

## API

<docgen-index>

* [`callByName(...)`](#callbyname)
* [`callByUrl(...)`](#callbyurl)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### callByName(...)

```typescript
callByName(options: CallByNameOptions) => Promise<CallByNameResult>
```

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#callbynameoptions">CallByNameOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#callresult">CallResult</a>&gt;</code>

--------------------


### callByUrl(...)

```typescript
callByUrl(options: CallByUrlOptions) => Promise<CallByUrlResult>
```

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#callbyurloptions">CallByUrlOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#callresult">CallResult</a>&gt;</code>

--------------------


### Interfaces


#### CallResult

| Prop       | Type             |
| ---------- | ---------------- |
| **`data`** | <code>any</code> |


#### CallByNameOptions

| Prop       | Type                |
| ---------- | ------------------- |
| **`name`** | <code>string</code> |


#### CallByUrlOptions

| Prop      | Type                |
| --------- | ------------------- |
| **`url`** | <code>string</code> |


### Type Aliases


#### CallByNameResult

<code><a href="#callresult">CallResult</a></code>


#### CallByUrlResult

<code><a href="#callresult">CallResult</a></code>

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/functions/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/functions/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
