# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

### iOS Minimum Version Requirement

- [Version 8.x.x](#version-8xx)
- [Version 7.x.x](#version-7xx)
- [Version 6.x.x](#version-6xx)
- [Version 5.x.x](#version-5xx)
- [Version 1.x.x](#version-1xx)

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **24** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

### Variables

- On Android, the `firebaseCrashlyticsVersion` variable has been updated to `20.0.3`.

## Version 7.x.x

  **Migration Required:**

  1. Update your app's iOS deployment target to 15.0 or higher in Xcode
  2. Update the platform version in your `ios/App/Podfile`:
     ```ruby
     platform :ios, '15.0'
     ```
  3. Run `pod install` to update dependencies

### Firebase SDK Version Update

- **Firebase SDK updated to version 12.6.0**

  The Firebase iOS SDK has been updated to version 12.6.0 to include the latest features, improvements, and bug fixes from Google.

  **What changed:**

  - All the latest Firebase features and improvements from Google
  - Improved performance and stability
  - Latest security updates

  **Migration Required:**

  If you use CocoaPods and have the SDK version pinned in your Podfile, update it accordingly.

## Summary

To upgrade to the latest version of this plugin, ensure your iOS project meets these requirements:

1. **iOS 15.0 or higher** as the minimum deployment target
2. **Update** Firebase SDK versions to 12.6.0 if pinned in your Podfile
3. **Run** `pod install` after making these changes

For most users, only updating the iOS deployment target will be necessary.

