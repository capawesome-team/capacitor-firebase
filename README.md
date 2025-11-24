<br />
<div align="center">
  <a href="https://capgo.app/">
    <img src="https://capgo.app/capgo_banner.webp" alt="Capgo" width="720" />
  </a>
  <br />
  <br />
  <h1>
    <img src="https://user-images.githubusercontent.com/13857929/161965231-b2c7f586-2ee8-4342-a71a-0532bb8b4c1f.png" alt="Capacitor Firebase" width="720" />
  </h1>
</div>
<br />
<p align="center">
  <a href="https://github.com/Cap-go/capacitor-firebase"><img src="https://img.shields.io/maintenance/yes/2025?style=flat-square" /></a>
  <a href="https://github.com/Cap-go/capacitor-firebase/actions/workflows/test.yml"><img src="https://img.shields.io/github/actions/workflow/status/Cap-go/capacitor-firebase/test.yml?branch=main&style=flat-square" /></a>
  <a href="https://github.com/Cap-go/capacitor-firebase"><img src="https://img.shields.io/github/license/Cap-go/capacitor-firebase?style=flat-square" /></a>
  <a href="https://github.com/Cap-go"><img src="https://img.shields.io/badge/part%20of-capgo-%234f46e5?style=flat-square" /></a>
  <a href="https://turborepo.org/"><img src="https://img.shields.io/badge/maintained%20with-turborepo-%237f6ab2?style=flat-square" /></a>
</p>

> **Note:** This is a fork of the excellent [capacitor-firebase](https://github.com/capawesome-team/capacitor-firebase) by [Robin Genz](https://github.com/robingenz). We are deeply grateful for Robin's pioneering work on these plugins. This fork is maintained by the Capgo team with a focus on automatically releasing new versions whenever Firebase SDKs are updated, ensuring users always have access to the latest Firebase features and fixes through automated releases via Renovate.</p>

## Features

Capacitor Firebase is a collection of Capacitor plugins that make it easier for you to use [Firebase](https://firebase.google.com/) in your Capacitor project.[^1]

- 🔋 Supports **Android, iOS and the Web**
- ⚡️ **Capacitor 7** support
- 🔥 **Firebase Web SDK** (modular) support
- 🦋 Consistent versioning (no more SDK versions conflicts)
- 👁 Unified Typescript definitions
- 📄 Full documentation
- 🤖 **Automated releases with latest Firebase SDKs** via Renovate
- ⚙️ Under active development, more plugins coming soon

## What's Different in This Fork?

This fork differs from the original [capawesome-team/capacitor-firebase](https://github.com/capawesome-team/capacitor-firebase) in its release philosophy:

### Release Cadence
- **Original (Capawesome)**: Releases approximately once per year, typically aligned with major Capacitor version updates
- **This Fork (Capgo)**: Automatically releases new versions whenever Firebase releases updates to their iOS, Android, or Web SDKs

### Key Benefits
- **Always Latest Firebase SDKs**: Powered by Renovate, which monitors Firebase SDK releases and automatically creates new plugin versions
- **Faster Access to Features**: Get the latest Firebase features and bug fixes without waiting months for the next major release
- **Continuous Updates**: No need to wait for Capacitor major versions to get Firebase SDK updates

We maintain full compatibility with the original plugins while ensuring you're always on the cutting edge of Firebase technology. This means more frequent releases focused on keeping Firebase SDKs up-to-date, rather than the annual release cycle of the original.

## Maintainers

| Maintainer | GitHub | Organization |
| ---------- | ------ | ------------ |
| Capgo Team | [Cap-go](https://github.com/Cap-go) | [Capgo](https://capgo.app) |

**Original Author**: [Robin Genz](https://github.com/robingenz) ([@robin_genz](https://twitter.com/robin_genz))

## Installation

Each plugin has its own installation instructions.
Click on the name of the desired plugin under the [`Plugins`](#plugins) section to get to the installation guide.

## Plugins

| Name                                             | Package                              | Version                                                                                                                                                             | Downloads                                                                                                                                                                |
| ------------------------------------------------ | ------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| [Analytics](./packages/analytics)                | `@capgo/capacitor-firebase-analytics`      | [![npm badge](https://img.shields.io/npm/v/@capgo/capacitor-firebase-analytics?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-analytics)           | [![npm downloads](https://img.shields.io/npm/dw/@capgo/capacitor-firebase-analytics?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-analytics)           |
| [App](./packages/app)                            | `@capgo/capacitor-firebase-app`            | [![npm badge](https://img.shields.io/npm/v/@capgo/capacitor-firebase-app?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-app)                       | [![npm downloads](https://img.shields.io/npm/dw/@capgo/capacitor-firebase-app?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-app)                       |
| [App Check](./packages/app-check)                | `@capgo/capacitor-firebase-app-check`      | [![npm badge](https://img.shields.io/npm/v/@capgo/capacitor-firebase-app-check?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-app-check)           | [![npm downloads](https://img.shields.io/npm/dw/@capgo/capacitor-firebase-app-check?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-app-check)           |
| [Authentication](./packages/authentication)      | `@capgo/capacitor-firebase-authentication` | [![npm badge](https://img.shields.io/npm/v/@capgo/capacitor-firebase-authentication?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-authentication) | [![npm downloads](https://img.shields.io/npm/dw/@capgo/capacitor-firebase-authentication?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-authentication) |
| [Crashlytics](./packages/crashlytics)            | `@capgo/capacitor-firebase-crashlytics`    | [![npm badge](https://img.shields.io/npm/v/@capgo/capacitor-firebase-crashlytics?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-crashlytics)       | [![npm downloads](https://img.shields.io/npm/dw/@capgo/capacitor-firebase-crashlytics?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-crashlytics)       |
| [Cloud Firestore](./packages/firestore)          | `@capgo/capacitor-firebase-firestore`      | [![npm badge](https://img.shields.io/npm/v/@capgo/capacitor-firebase-firestore?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-firestore)           | [![npm downloads](https://img.shields.io/npm/dw/@capgo/capacitor-firebase-firestore?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-firestore)           |
| [Cloud Functions](./packages/functions)          | `@capgo/capacitor-firebase-functions`      | [![npm badge](https://img.shields.io/npm/v/@capgo/capacitor-firebase-functions?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-functions)           | [![npm downloads](https://img.shields.io/npm/dw/@capgo/capacitor-firebase-functions?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-functions)           |
| [Cloud Messaging](./packages/messaging)          | `@capgo/capacitor-firebase-messaging`      | [![npm badge](https://img.shields.io/npm/v/@capgo/capacitor-firebase-messaging?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-messaging)           | [![npm downloads](https://img.shields.io/npm/dw/@capgo/capacitor-firebase-messaging?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-messaging)           |
| [Cloud Storage](./packages/storage)              | `@capgo/capacitor-firebase-storage`        | [![npm badge](https://img.shields.io/npm/v/@capgo/capacitor-firebase-storage?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-storage)               | [![npm downloads](https://img.shields.io/npm/dw/@capgo/capacitor-firebase-storage?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-storage)               |
| [Performance Monitoring](./packages/performance) | `@capgo/capacitor-firebase-performance`    | [![npm badge](https://img.shields.io/npm/v/@capgo/capacitor-firebase-performance?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-performance)       | [![npm downloads](https://img.shields.io/npm/dw/@capgo/capacitor-firebase-performance?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-performance)       |
| [Remote Config](./packages/remote-config)        | `@capgo/capacitor-firebase-remote-config`  | [![npm badge](https://img.shields.io/npm/v/@capgo/capacitor-firebase-remote-config?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-remote-config)   | [![npm downloads](https://img.shields.io/npm/dw/@capgo/capacitor-firebase-remote-config?style=flat-square)](https://www.npmjs.com/package/@capgo/capacitor-firebase-remote-config)   |

## Changelogs

Each plugin has its own `CHANGELOG.md` file which contains information about version changes.
Click on the name of the desired plugin under the [`Plugins`](#plugins) section to get to the plugin folder.

## Breaking Changes

Each plugin has its own `BREAKING.md` file which contains information about breaking changes.
Click on the name of the desired plugin under the [`Plugins`](#plugins) section to get to the plugin folder.

## Contributing

See [CONTRIBUTING.md](./CONTRIBUTING.md).

## License

See [LICENSE](./LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
