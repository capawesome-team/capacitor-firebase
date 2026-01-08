# Breaking Changes

## Upcoming

### iOS Minimum Version Requirement

- **Minimum iOS version increased from 14.0 to 15.0**

  The minimum iOS deployment target has been updated to iOS 15.0 to support Firebase SDK 12.6.0 and newer features. Applications targeting iOS 14.x will need to update their deployment target.

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

### GoogleAppMeasurementOnDeviceConversion Dependency Removed

- **GoogleAppMeasurementOnDeviceConversion dependency removed**

  The GoogleAppMeasurementOnDeviceConversion dependency has been removed from the plugin. This shouldn't affect you much.

## Summary

To upgrade to the latest version of this plugin, ensure your iOS project meets these requirements:

1. **iOS 15.0 or higher** as the minimum deployment target
2. **Update** Firebase SDK versions to 12.6.0 if pinned in your Podfile
3. **Run** `pod install` after making these changes

For most users, only updating the iOS deployment target will be necessary.

