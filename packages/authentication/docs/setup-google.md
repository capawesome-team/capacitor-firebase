# Set up authentication using Google Sign-In

## Android

1. Add `google.com` to the `providers` [configuration](https://github.com/capawesome-team/capacitor-firebase/tree/main/packages/authentication#configuration) array.
1. Add the following project variable to your `variables.gradle` file (usually `android/build.gradle`):
   ```diff
   ext {
   +    rgcfaIncludeGoogle = true
   }
   ```
   Run [`npx cap update`](https://capacitorjs.com/docs/cli/update) to update the native plugins and dependencies.
1. Specify your app's SHA-1 fingerprint from the [Settings page](https://console.firebase.google.com/project/_/settings/general/) of the Firebase console.
   Refer to [Authenticating Your Client](https://developers.google.com/android/guides/client-auth) for details on how to get your app's SHA-1 fingerprint.
1. Enable Google Sign-In in the Firebase console:
   1. Open the **Auth** section in the [Firebase console](https://console.firebase.google.com/).
   1. Open the **Sign-in method** tab and enable **Google** Sign In.

## iOS

1. Add `google.com` to the `providers` [configuration](https://github.com/capawesome-team/capacitor-firebase/tree/main/packages/authentication#configuration) array.
1. Add the `CapacitorFirebaseAuthentication/Google` pod to your `Podfile` (usually `ios/App/Podfile`):

   ```diff
   target 'App' do
     capacitor_pods
     # Add your Pods here
   +  pod 'CapacitorFirebaseAuthentication/Google', :path => '../../node_modules/@capacitor-firebase/authentication'
   end
   ```

   **Attention**: Do not add the pod in the section `def capacitor_pods`, but under the comment `# Add your Pods here` ([example](https://github.com/robingenz/capacitor-firebase-plugin-demo/blob/e1684a0af6871442ed0a87dceeeba6fd9ce0185d/ios/App/Podfile#L30)).

   Run [`npx cap update`](https://capacitorjs.com/docs/cli/update) to update the native plugins and dependencies.

1. Add the following post install script to your `Podfile` (usually `ios/App/Podfile`) to disable code signing for bundles:
   ```ruby
   post_install do |installer|
       installer.pods_project.targets.each do |target|
           target.build_configurations.each do |config|
               if target.respond_to?(:product_type) and target.product_type == "com.apple.product-type.bundle"
                   target.build_configurations.each do |config|
                       config.build_settings['CODE_SIGNING_ALLOWED'] = 'NO'
                   end
               end
           end
       end
   end
   ```
   (See [here](https://stackoverflow.com/q/73765469/6731412) for more information.)
1. Add custom URL schemes to your Xcode project:
   1. Open your project configuration.
      Select your app from the **TARGETS** section, then select the **Info** tab, and expand the **URL Types** section.
   1. Click the **+** button, and add a URL scheme for your reversed client ID.
      You find this value in your `GoogleService-Info.plist` configuration file.
      Look for the `REVERSED_CLIENT_ID` key and paste the value of that key into the **URL Schemes** box on the configuration page.
      Leave the other fields blank.

## Web

1. See [Before you begin](https://firebase.google.com/docs/auth/web/google-signin#before_you_begin) and follow the instructions to configure and enable sign-in with Google correctly.
