# Changelog

## 1.0.0

### Major Changes

- [`83debcf`](https://github.com/capawesome-team/capacitor-firebase/commit/83debcf02500c6a9ecb363eb30b918ace7b416a5) ([#168](https://github.com/capawesome-team/capacitor-firebase/pull/168)): feat!: update to Capacitor 4 (**BREAKING CHANGES**: see [`BREAKING.md`](https://github.com/capawesome-team/capacitor-firebase#breaking-changes))

### Minor Changes

- [`20448e7`](https://github.com/capawesome-team/capacitor-firebase/commit/20448e7beb9c401ac2d36436035b33169105f0e4) ([#151](https://github.com/capawesome-team/capacitor-firebase/pull/151)): fix!: no more providers are loaded by default (**BREAKING CHANGES**: see [`BREAKING.md`](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/authentication/BREAKING.md))

## 0.5.1

### Patch Changes

- [`e3ab129`](https://github.com/capawesome-team/capacitor-firebase/commit/e3ab1295b5d4a1cde0d7345a9b8e6552b1be7a5c) ([#157](https://github.com/capawesome-team/capacitor-firebase/pull/157)): fix(web): `customParameters` is not applied

* [`4a0e4df`](https://github.com/capawesome-team/capacitor-firebase/commit/4a0e4df7275dfa080d09ac4b3fa9e43448158f9f) ([#147](https://github.com/capawesome-team/capacitor-firebase/pull/147)): fix(android): `NullPointerException`

- [`2165073`](https://github.com/capawesome-team/capacitor-firebase/commit/2165073ec68d29706dd6bec19a04540b9aaaee38) ([#154](https://github.com/capawesome-team/capacitor-firebase/pull/154)): feat: expose `AdditionalUserInfo`

* [`c8543f6`](https://github.com/capawesome-team/capacitor-firebase/commit/c8543f6985983f9a96dc6d435429af20841c539b) ([#149](https://github.com/capawesome-team/capacitor-firebase/pull/149)): fix(android): catch all native errors and pass to the webview

## 0.5.0

### Minor Changes

- [`e72bb95`](https://github.com/capawesome-team/capacitor-firebase/commit/e72bb9526ff7a18092dbe53bac8fb03eec314be4): feat(ios)!: update Facebook iOS SDK to `13.2.0` (**BREAKING CHANGES**: see [`BREAKING.md`](https://github.com/capawesome-team/capacitor-firebase/tree/main/packages/authentication/BREAKING.md))

## 0.3.1

### Patch Changes

- [`5da5fbf`](https://github.com/robingenz/capacitor-firebase/commit/5da5fbfaec6305d06f6909261cf46546331414fa) ([#107](https://github.com/robingenz/capacitor-firebase/pull/107)): fix(ios): provide access token for Twitter

* [`e4a4787`](https://github.com/robingenz/capacitor-firebase/commit/e4a47873adab9ceb6dd1800c8bfcf41f8060ab4e) ([#80](https://github.com/robingenz/capacitor-firebase/pull/80)): feat: support OAuth 2.0 scopes

- [`b7e1e6e`](https://github.com/robingenz/capacitor-firebase/commit/b7e1e6eb74986577082f574e1bcc05500283f68f) ([#98](https://github.com/robingenz/capacitor-firebase/pull/98)): feat(android): provide access token for Google

* [`35d4407`](https://github.com/robingenz/capacitor-firebase/commit/35d44079258e5abdd7c631e2ca801b32544173d3) ([#95](https://github.com/robingenz/capacitor-firebase/pull/95)): chore(deps): update native dependencies

## 0.3.0

### Patch Changes

- [`935744d`](https://github.com/robingenz/capacitor-firebase/commit/935744df7c0a6703606048db75e71d2b00132c57) ([#84](https://github.com/robingenz/capacitor-firebase/pull/84)): fix(web): apply `forceRefresh` option in the `getIdToken` method

## 0.2.2

### Patch Changes

- [`8da4212`](https://github.com/robingenz/capacitor-firebase/commit/8da42124fa4ea1656e6db247952cb84718e60e31) ([#59](https://github.com/robingenz/capacitor-firebase/pull/59)): feat: password authentication

* [`412e357`](https://github.com/robingenz/capacitor-firebase/commit/412e35796e3fd60167318785e4d06a07dee31dfe) ([#71](https://github.com/robingenz/capacitor-firebase/pull/71)): fix(ios): `removeAllListeners` method not found

- [`5648977`](https://github.com/robingenz/capacitor-firebase/commit/564897735718a9b5e559532c59c6a3c1e734e10f) ([#79](https://github.com/robingenz/capacitor-firebase/pull/79)): fix: `getIdToken` causes app crash if no user is signed in

## 0.2.1

### Patch Changes

- [`5c0f381`](https://github.com/robingenz/capacitor-firebase/commit/5c0f38164514dd38212cae8c4c7f28cfb8905416) ([#55](https://github.com/robingenz/capacitor-firebase/pull/55)): feat: provide access tokens for Google and Facebook

* [`243011c`](https://github.com/robingenz/capacitor-firebase/commit/243011cd2ef6960c74800c6539eb735bcf15fc8b) ([#58](https://github.com/robingenz/capacitor-firebase/pull/58)): fix: `accessToken` was returned as `idToken`

## 0.2.0

### Minor Changes

- [`ca9f3df`](https://github.com/robingenz/capacitor-firebase/commit/ca9f3df36e4d6e2446e7d70a1a8612d8f4941359) ([#22](https://github.com/robingenz/capacitor-firebase/pull/22)): feat: support Firebase Emulator

## 0.1.1

### Patch Changes

- [`51fd609`](https://github.com/robingenz/capacitor-firebase/commit/51fd6092d0e47242f797be9209cc7f33ccc39e93) ([#17](https://github.com/robingenz/capacitor-firebase/pull/17)): fix(android): build error

## 0.1.0

### Minor Changes

- [`be27343`](https://github.com/robingenz/capacitor-firebase/commit/be273433922cf71e55784d580fe0af4f95576c3c) ([#16](https://github.com/robingenz/capacitor-firebase/pull/16)): Initial release 🎉
