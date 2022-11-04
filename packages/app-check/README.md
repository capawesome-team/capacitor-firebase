# @capacitor-firebase/app-check

⚡️ Capacitor plugin for Firebase App Check.

## Installation

```bash
npm install @capacitor-firebase/app-check firebase
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://firebase.google.com/docs/android/setup) / [iOS](https://firebase.google.com/docs/ios/setup) / [Web](https://firebase.google.com/docs/web/setup)).

### Android

See [Set up your Firebase project](https://firebase.google.com/docs/app-check/android/play-integrity-provider#project-setup) and follow the instructions to set up your app correctly.

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$firebaseAppCheckPlayIntegrityVersion` version of `com.google.firebase:firebase-appcheck-playintegrity` (default: `16.1.0`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-firebase-plugin-demo](https://github.com/robingenz/capacitor-firebase-plugin-demo)

## Usage

```typescript
import { AppCheck } from '@capacitor-firebase/app-check';

const echo = async () => {
  await AppCheck.echo();
};
```

## API

<docgen-index>

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

</docgen-api>

## Changelog

See [CHANGELOG.md](/packages/app-check/CHANGELOG.md).

## License

See [LICENSE](/packages/app-check/LICENSE).
