# Firebase SDK Update Versioning Policy

Defines when a Firebase SDK update (Web, iOS, or Android) ships as a minor vs a major plugin version.

## Minor release

All of the following must hold:

- Firebase SDK upgrade is minor or patch (same major) on Web, iOS, and Android.
- No plugin imports a Web SDK symbol added after the current `firebase` `peerDependency` floor.
- No change to the plugin's Android `minSdkVersion`, `compileSdkVersion`, `targetSdkVersion`, or iOS deployment target.
- No change to the plugin's TypeScript API surface or observable runtime behavior.

## Major release

Any of the following triggers a major:

- Firebase SDK major bump on any platform.
- Android `minSdkVersion` or iOS deployment target must be raised.
- Plugin's TypeScript API surface or runtime behavior changes.
- `firebase` `peerDependency` floor must be raised to exclude a previously supported version.

If a single update mixes minor- and major-qualifying changes, it ships as a major.

## Notes

- Build-time toolchain bumps (e.g. minimum Xcode/Swift) are not breaking for app consumers but must be called out in the release notes.
- The release notes must specify the new Firebase JS, iOS, and Android SDK versions.

## Rationale

Strict semver would force a major for every Firebase bump (noisy, discourages keeping native SDKs current). Always-major pinning delays bug fixes. The criteria above reserve majors for updates that actually require consumer action.
