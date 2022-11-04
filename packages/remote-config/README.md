# @capacitor-firebase/remote-config

⚡️ Capacitor plugin for Firebase Remote Config.

## Installation

```bash
npm install @capacitor-firebase/remote-config firebase
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://firebase.google.com/docs/android/setup) / [iOS](https://firebase.google.com/docs/ios/setup) / [Web](https://firebase.google.com/docs/web/setup)).

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$firebaseConfigVersion` version of `com.google.firebase:firebase-config` (default: `21.2.0`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-firebase-plugin-demo](https://github.com/robingenz/capacitor-firebase-plugin-demo)

## Usage

```typescript
import { FirebaseRemoteConfig } from '@capacitor-firebase/remote-config';

const echo = async () => {
  await FirebaseRemoteConfig.echo();
};
```

## API

<docgen-index>

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

</docgen-api>

## Changelog

See [CHANGELOG.md](/packages/remote-config/CHANGELOG.md).

## License

See [LICENSE](/packages/remote-config/LICENSE).
