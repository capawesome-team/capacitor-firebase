# Contributing

This guide provides instructions for contributing to this Capacitor plugins.

## Developing

### Local Setup

1. Fork and clone the repo.
1. Install the dependencies.

    ```shell
    npm install
    ```

1. Install SwiftLint if you're on macOS.

    ```shell
    brew install swiftlint
    ```

### Scripts

#### `npm run build`

Build the plugin web assets and generate plugin API documentation using [`@capacitor/docgen`](https://github.com/ionic-team/capacitor-docgen).

It will compile the TypeScript code from `/packages/<package>/src/` into ESM JavaScript in `/packages/<package>/dist/esm/`. 
These files are used in apps with bundlers when your plugin is imported.

Then, Rollup will bundle the code into a single file at `/packages/<package>/dist/plugin.js`. 
This file is used in apps without bundlers by including it as a script in `index.html`.

#### `npm run verify`

Build and validate the web and native projects.

This is useful to run in CI to verify that the plugin builds for all platforms.

#### `npm run lint` / `npm run fmt`

Check formatting and code quality, autoformat/autofix if possible.

This template is integrated with ESLint, Prettier, and SwiftLint. 
We want to have a consistent style and structure for easier cooperation.

## Publishing

These packages are published via GitHub Actions using [changesets action](https://github.com/changesets/action).

> **Note**: The [`files`](https://docs.npmjs.com/cli/v7/configuring-npm/package-json#files) array in `/packages/<package>/package.json` specifies which files get published. 
If you rename files/directories or add files elsewhere, you may need to update it.
