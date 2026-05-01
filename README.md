<br />
<div align="center">
  <h1>
    <img src="https://user-images.githubusercontent.com/13857929/161965231-b2c7f586-2ee8-4342-a71a-0532bb8b4c1f.png" alt="Capacitor Firebase" width="720" />
  </h1>
</div>
<br />
<p align="center">
  <a href="https://github.com/capawesome-team/capacitor-firebase"><img src="https://img.shields.io/maintenance/yes/2026?style=flat-square" /></a>
  <a href="https://github.com/capawesome-team/capacitor-firebase/actions/workflows/ci.yml"><img src="https://img.shields.io/github/actions/workflow/status/capawesome-team/capacitor-firebase/ci.yml?branch=main&style=flat-square" /></a>
  <a href="https://github.com/capawesome-team/capacitor-firebase"><img src="https://img.shields.io/github/license/capawesome-team/capacitor-firebase?style=flat-square" /></a>
  <a href="https://github.com/capawesome-team"><img src="https://img.shields.io/badge/part%20of-capawesome-%234f46e5?style=flat-square" /></a>
  <a href="https://turborepo.org/"><img src="https://img.shields.io/badge/maintained%20with-turborepo-%237f6ab2?style=flat-square" /></a>
  <a href="https://devlibrary.withgoogle.com/products/firebase/repos/robingenz-capacitor-firebase"><img src="https://img.shields.io/badge/part%20of-DevLibrary-9cf?color=4285F4&logoColor=4285F4&logo=google&style=flat-square" /></a>
</p>

## Features

Capacitor Firebase is a collection of Capacitor plugins that make it easier for you to use [Firebase](https://firebase.google.com/) in your Capacitor project.[^1]

- 🔋 Supports **Android, iOS and the Web**
- ⚡️ **Capacitor 8** support
- 🔥 **Firebase Web SDK** (modular) support
- 🦋 Consistent versioning (no more SDK versions conflicts)
- 👁 Unified Typescript definitions
- 📄 Full documentation
- ⚙️ Under active development, more plugins coming soon

## Maintainers

| Maintainer | GitHub                                    | Social                                        |
| ---------- | ----------------------------------------- | --------------------------------------------- |
| Robin Genz | [robingenz](https://github.com/robingenz) | [@robin_genz](https://twitter.com/robin_genz) |

## Installation

Each plugin has its own installation instructions.
Click on the name of the desired plugin under the [`Plugins`](#plugins) section to get to the installation guide.

## Plugins

| Name                                             | Package                              | Version                                                                                                                                                             | Downloads                                                                                                                                                                |
| ------------------------------------------------ | ------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| [Analytics](./packages/analytics)                | `@capawesome/capacitor-firebase-analytics`      | [![npm badge](https://img.shields.io/npm/v/@capawesome/capacitor-firebase-analytics?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-analytics)           | [![npm downloads](https://img.shields.io/npm/dw/@capawesome/capacitor-firebase-analytics?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-analytics)           |
| [App](./packages/app)                            | `@capawesome/capacitor-firebase-app`            | [![npm badge](https://img.shields.io/npm/v/@capawesome/capacitor-firebase-app?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-app)                       | [![npm downloads](https://img.shields.io/npm/dw/@capawesome/capacitor-firebase-app?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-app)                       |
| [App Check](./packages/app-check)                | `@capawesome/capacitor-firebase-app-check`      | [![npm badge](https://img.shields.io/npm/v/@capawesome/capacitor-firebase-app-check?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-app-check)           | [![npm downloads](https://img.shields.io/npm/dw/@capawesome/capacitor-firebase-app-check?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-app-check)           |
| [Authentication](./packages/authentication)      | `@capawesome/capacitor-firebase-authentication` | [![npm badge](https://img.shields.io/npm/v/@capawesome/capacitor-firebase-authentication?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-authentication) | [![npm downloads](https://img.shields.io/npm/dw/@capawesome/capacitor-firebase-authentication?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-authentication) |
| [Crashlytics](./packages/crashlytics)            | `@capawesome/capacitor-firebase-crashlytics`    | [![npm badge](https://img.shields.io/npm/v/@capawesome/capacitor-firebase-crashlytics?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-crashlytics)       | [![npm downloads](https://img.shields.io/npm/dw/@capawesome/capacitor-firebase-crashlytics?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-crashlytics)       |
| [Cloud Firestore](./packages/firestore)          | `@capawesome/capacitor-firebase-firestore`      | [![npm badge](https://img.shields.io/npm/v/@capawesome/capacitor-firebase-firestore?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-firestore)           | [![npm downloads](https://img.shields.io/npm/dw/@capawesome/capacitor-firebase-firestore?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-firestore)           |
| [Cloud Functions](./packages/functions)          | `@capawesome/capacitor-firebase-functions`      | [![npm badge](https://img.shields.io/npm/v/@capawesome/capacitor-firebase-functions?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-functions)           | [![npm downloads](https://img.shields.io/npm/dw/@capawesome/capacitor-firebase-functions?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-functions)           |
| [Cloud Messaging](./packages/messaging)          | `@capawesome/capacitor-firebase-messaging`      | [![npm badge](https://img.shields.io/npm/v/@capawesome/capacitor-firebase-messaging?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-messaging)           | [![npm downloads](https://img.shields.io/npm/dw/@capawesome/capacitor-firebase-messaging?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-messaging)           |
| [Cloud Storage](./packages/storage)              | `@capawesome/capacitor-firebase-storage`        | [![npm badge](https://img.shields.io/npm/v/@capawesome/capacitor-firebase-storage?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-storage)               | [![npm downloads](https://img.shields.io/npm/dw/@capawesome/capacitor-firebase-storage?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-storage)               |
| [Performance Monitoring](./packages/performance) | `@capawesome/capacitor-firebase-performance`    | [![npm badge](https://img.shields.io/npm/v/@capawesome/capacitor-firebase-performance?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-performance)       | [![npm downloads](https://img.shields.io/npm/dw/@capawesome/capacitor-firebase-performance?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-performance)       |
| [Remote Config](./packages/remote-config)        | `@capawesome/capacitor-firebase-remote-config`  | [![npm badge](https://img.shields.io/npm/v/@capawesome/capacitor-firebase-remote-config?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-remote-config)   | [![npm downloads](https://img.shields.io/npm/dw/@capawesome/capacitor-firebase-remote-config?style=flat-square)](https://www.npmjs.com/package/@capawesome/capacitor-firebase-remote-config)   |

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
