# Changelog

## 1.1.0

### Patch Changes

- [`3b45e52`](https://github.com/capawesome-team/capacitor-firebase/commit/3b45e52066b72ce5da337da6227fede8b74259e9): fix: set plugin config class name to `FirebaseMessaging`

## 1.0.0

### Major Changes

- [`83debcf`](https://github.com/capawesome-team/capacitor-firebase/commit/83debcf02500c6a9ecb363eb30b918ace7b416a5) ([#168](https://github.com/capawesome-team/capacitor-firebase/pull/168)): feat!: update to Capacitor 4 (**BREAKING CHANGES**: see [`BREAKING.md`](https://github.com/capawesome-team/capacitor-firebase#breaking-changes))

### Minor Changes

- [`fa93e28`](https://github.com/capawesome-team/capacitor-firebase/commit/fa93e28a2a34acf99b5d62b52be18836dcf7472f) ([#145](https://github.com/capawesome-team/capacitor-firebase/pull/145)): refactor!: `tokenReceived` event is no longer triggered by the `getToken` method (**BREAKING CHANGES**: see [`BREAKING.md`](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/BREAKING.md))

## 0.5.1

### Patch Changes

- [`d78f6ae`](https://github.com/capawesome-team/capacitor-firebase/commit/d78f6ae46a4359fcca0d28efcbefe73f354306d0) ([#144](https://github.com/capawesome-team/capacitor-firebase/pull/144)): fix(ios): initial `tokenReceived` event contains invalid token value

* [`c8543f6`](https://github.com/capawesome-team/capacitor-firebase/commit/c8543f6985983f9a96dc6d435429af20841c539b) ([#149](https://github.com/capawesome-team/capacitor-firebase/pull/149)): fix(android): catch all native errors and pass to the webview

## 0.5.0

### Minor Changes

- [`e72bb95`](https://github.com/capawesome-team/capacitor-firebase/commit/e72bb9526ff7a18092dbe53bac8fb03eec314be4): fix(android)!: `removeDeliveredNotifications` crash (**BREAKING CHANGES**: see [`BREAKING.md`](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/messaging/BREAKING.md))

### Patch Changes

- [`e72bb95`](https://github.com/capawesome-team/capacitor-firebase/commit/e72bb9526ff7a18092dbe53bac8fb03eec314be4): fix(ios): APNS device token not set before retrieving FCM Token

* [`e72bb95`](https://github.com/capawesome-team/capacitor-firebase/commit/e72bb9526ff7a18092dbe53bac8fb03eec314be4): fix: `GetTokenOptions` parameter is now optional

## 0.3.1

### Patch Changes

- [`c1a30cb`](https://github.com/robingenz/capacitor-firebase/commit/c1a30cb785ee0b3a4b1f75d61f665ed11ecd8ab3) ([#106](https://github.com/robingenz/capacitor-firebase/pull/106)): fix(web): `messaging/unsupported-browser` error when loading the plugin

* [`098b7aa`](https://github.com/robingenz/capacitor-firebase/commit/098b7aa522947b8c8a58c3978c59ee08a7d0975c) ([#108](https://github.com/robingenz/capacitor-firebase/pull/108)): feat: add `isSupported` method

- [`35d4407`](https://github.com/robingenz/capacitor-firebase/commit/35d44079258e5abdd7c631e2ca801b32544173d3) ([#95](https://github.com/robingenz/capacitor-firebase/pull/95)): chore(deps): update native dependencies

## 0.3.0

### Minor Changes

- [`40b786a`](https://github.com/robingenz/capacitor-firebase/commit/40b786ae42e2669d8efc9062e60b73972ca45d11) ([#89](https://github.com/robingenz/capacitor-firebase/pull/89)): fix(ios)!: receive background notifications (**BREAKING CHANGES**: see `BREAKING.md`)

### Patch Changes

- [`20db43e`](https://github.com/robingenz/capacitor-firebase/commit/20db43eddc798f04384f0d54877053ffd6a4b13b) ([#90](https://github.com/robingenz/capacitor-firebase/pull/90)): fix(android): `getToken` should trigger `tokenReceived`

* [`0cf1456`](https://github.com/robingenz/capacitor-firebase/commit/0cf14560c5e8b26a887eea4c26b6dc86d928a310) ([#85](https://github.com/robingenz/capacitor-firebase/pull/85)): fix: typescript definitions

## 0.2.2

### Patch Changes

- [`ceea868`](https://github.com/robingenz/capacitor-firebase/commit/ceea868e796c1be3be99e716e4d27bdd4bd85803) ([#77](https://github.com/robingenz/capacitor-firebase/pull/77)): Initial release 🎉
