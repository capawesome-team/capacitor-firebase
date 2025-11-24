# Contributing

This guide provides instructions for contributing to this Capacitor plugins.

## Developing

### Local Setup

1. Fork and clone the repo.
1. Install the dependencies.

    ```shell
    bun install
    ```

1. Install SwiftLint if you're on macOS.

    ```shell
    brew install swiftlint
    ```

### Scripts

#### `bun run build`

Build the plugin web assets and generate plugin API documentation using [`@capacitor/docgen`](https://github.com/ionic-team/capacitor-docgen).

It will compile the TypeScript code from `/packages/<package>/src/` into ESM JavaScript in `/packages/<package>/dist/esm/`.
These files are used in apps with bundlers when your plugin is imported.

Then, Rollup will bundle the code into a single file at `/packages/<package>/dist/plugin.js`.
This file is used in apps without bundlers by including it as a script in `index.html`.

#### `bun run verify`

Build and validate the web and native projects.

This is useful to run in CI to verify that the plugin builds for all platforms.

#### `bun run lint` / `bun run fmt`

Check formatting and code quality, autoformat/autofix if possible.

This template is integrated with ESLint, Prettier, and SwiftLint.
We want to have a consistent style and structure for easier cooperation.

## Upstream Sync

This repository is a fork of [capawesome-team/capacitor-firebase](https://github.com/capawesome-team/capacitor-firebase) and automatically syncs changes from the upstream repository.

### Automated Sync Process

A GitHub Action runs every Monday at 9 AM UTC to:
1. Check for new changes in the upstream repository
2. Create a pull request with the changes
3. Run all tests (Android, iOS, Web, and lint)
4. Auto-merge if all tests pass

You can also manually trigger a sync:
- Go to Actions → Sync from Upstream → Run workflow

### Handling Sync Conflicts

If the automated sync encounters merge conflicts:
- An issue will be created automatically
- Manual resolution is required
- Follow the instructions in the created issue

## Publishing

These packages are published individually via GitHub Actions:

1. **Version Bump**: When code changes are merged to `main`, the `bump_version.yml` workflow:
   - Detects which packages changed
   - Updates documentation
   - Creates version tags (format: `package-name/vX.Y.Z`)

2. **Build & Publish**: When tags are pushed, the `build.yml` workflow:
   - Builds the specific package
   - Generates AI changelog
   - Publishes to npm with provenance
   - Creates GitHub release

> **Note**: The [`files`](https://docs.npmjs.com/cli/v7/configuring-npm/package-json#files) array in `/packages/<package>/package.json` specifies which files get published.
If you rename files/directories or add files elsewhere, you may need to update it.
