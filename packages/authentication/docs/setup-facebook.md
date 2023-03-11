# Set up authentication using Facebook Sign-In

## Android

1.  Add `facebook.com` to the `providers` [configuration](https://github.com/capawesome-team/capacitor-firebase/tree/main/packages/authentication#configuration) array.
1.  Add the following project variable to your `variables.gradle` file (usually `android/build.gradle`):
    ```diff
    ext {
    +    rgcfaIncludeFacebook = true
    }
    ```
    Run [`npx cap update`](https://capacitorjs.com/docs/cli/update) to update the native plugins and dependencies.
1.  On the [Facebook for Developers](https://developers.facebook.com/) site, get the App ID and an App Secret for your app.
1.  Enable Facebook Login:
    1. In the [Firebase console](https://console.firebase.google.com/), open the Auth section.
    1. On the **Sign in method** tab, enable the **Facebook** sign-in method and specify the **App ID** and **App Secret** you got from Facebook.
    1. Then, make sure your **OAuth redirect URI** (e.g. `my-app-12345.firebaseapp.com/__/auth/handler`) is listed as one of your **OAuth redirect URIs** in your Facebook app's settings page on the [Facebook for Developers](https://developers.facebook.com/) site in the **Product Settings > Facebook Login** config.
1.  Add the following `string` elements to `android/app/src/main/res/values/strings.xml` after the `resources` element:

    ```xml
    <string name="facebook_app_id">[APP_ID]</string>
    <string name="fb_login_protocol_scheme">fb[APP_ID]</string>
    <string name="facebook_client_token">[CLIENT_TOKEN]</string>
    ```

    `[APP_ID]` must be replaced with your [Facebook App-ID](https://developers.facebook.com/docs/android/getting-started/#app-id).  
    `[CLIENT_TOKEN]` must be replaced with your [Facebook Client Token](https://developers.facebook.com/docs/android/getting-started/#client-token).

1.  Add the following elements to `android/app/src/main/AndroidManifest.xml` inside the `application` element:

    ```xml
    <meta-data
       android:name="com.facebook.sdk.ApplicationId"
       android:value="@string/facebook_app_id"/>

    <meta-data
        android:name="com.facebook.sdk.ClientToken"
        android:value="@string/facebook_client_token"/>

    <activity
       android:name="com.facebook.FacebookActivity"
       android:configChanges="keyboard|keyboardHidden|screenLayout|screenSize|orientation"
       android:label="@string/app_name" />

    <activity
       android:name="com.facebook.CustomTabActivity"
       android:exported="true">
       <intent-filter>
          <action android:name="android.intent.action.VIEW" />
          <category android:name="android.intent.category.DEFAULT" />
          <category android:name="android.intent.category.BROWSABLE" />
          <data android:scheme="@string/fb_login_protocol_scheme" />
       </intent-filter>
    </activity>
    ```

> ⚠️ You may encounter the following error with Gradle lint: `Class referenced in the manifest, com.facebook.FacebookActivity, was not found in the project or the libraries [MissingClass]`. Check out this comment in that case: [issues/117#issuecomment-1208612107](https://github.com/capawesome-team/capacitor-firebase/issues/117#issuecomment-1208612107).

## iOS

1. Add `facebook.com` to the `providers` [configuration](https://github.com/capawesome-team/capacitor-firebase/tree/main/packages/authentication#configuration) array.
1. Add the `CapacitorFirebaseAuthentication/Facebook` pod to your `Podfile` (usually `ios/App/Podfile`):

   ```diff
   target 'App' do
   capacitor_pods
   # Add your Pods here
   +  pod 'CapacitorFirebaseAuthentication/Facebook', :path => '../../node_modules/@capacitor-firebase/authentication'
   end
   ```

   **Attention**: Do not add the pod in the section `def capacitor_pods`, but under the comment `# Add your Pods here` ([example](https://github.com/robingenz/capacitor-firebase-plugin-demo/blob/e1684a0af6871442ed0a87dceeeba6fd9ce0185d/ios/App/Podfile#L30)).

   Run [`npx cap update`](https://capacitorjs.com/docs/cli/update) to update the native plugins and dependencies.

1. On the [Facebook for Developers](https://developers.facebook.com/) site, get the App ID and an App Secret for your app.
1. Enable Facebook Login:
   1. In the [Firebase console](https://console.firebase.google.com/), open the Auth section.
   1. On the **Sign in method** tab, enable the **Facebook** sign-in method and specify the **App ID** and **App Secret** you got from Facebook.
   1. Then, make sure your **OAuth redirect URI** (e.g. `my-app-12345.firebaseapp.com/__/auth/handler`) is listed as one of your **OAuth redirect URIs** in your Facebook app's settings page on the [Facebook for Developers](https://developers.facebook.com/) site in the **Product Settings > Facebook Login** config.
1. Add the following import in your app's `AppDelegate.swift`:

   ```swift
   import FBSDKCoreKit
   ```

1. Update the following function in your app's `AppDelegate.swift`:

   ```diff
   func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
   +   ApplicationDelegate.shared.application(
   +      application,
   +      didFinishLaunchingWithOptions: launchOptions
   +   )
      return true
   }
   ```

1. Update the following function in your app's `AppDelegate.swift`:

   ```diff
   func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
   +   if ApplicationDelegate.shared.application(
   +      app,
   +      open: url,
   +      sourceApplication: options[UIApplication.OpenURLOptionsKey.sourceApplication] as? String,
   +      annotation: options[UIApplication.OpenURLOptionsKey.annotation]
   +   ) {
   +      return true
   +   } else {
         return ApplicationDelegateProxy.shared.application(app, open: url, options: options)
   +   }
   }
   ```

1. Add the following lines to your apps's `Info.plist`:

   ```xml
   <key>CFBundleURLTypes</key>
   <array>
   <dict>
   <key>CFBundleURLSchemes</key>
   <array>
      <string>fb[APP_ID]</string>
   </array>
   </dict>
   </array>
   <key>FacebookAppID</key>
   <string>[APP_ID]</string>
   <key>FacebookClientToken</key>
   <string>[CLIENT_TOKEN]</string>
   <key>FacebookDisplayName</key>
   <string>[APP_NAME]</string>
   <key>LSApplicationQueriesSchemes</key>
   <array>
   <string>fbapi</string>
   <string>fbapi20130214</string>
   <string>fbapi20130410</string>
   <string>fbapi20130702</string>
   <string>fbapi20131010</string>
   <string>fbapi20131219</string>
   <string>fbapi20140410</string>
   <string>fbapi20140116</string>
   <string>fbapi20150313</string>
   <string>fbapi20150629</string>
   <string>fbapi20160328</string>
   <string>fbauth</string>
   <string>fb-messenger-share-api</string>
   <string>fbauth2</string>
   <string>fbshareextension</string>
   </array>
   ```

   `[APP_ID]` must be replaced with your Facebook app ID.
   `[CLIENT_TOKEN]` must be replaced with your Facebook Client Token (App Dashboard > Settings > Advanced > Client Token).  
   `[APP_NAME]` must be replaced with your Facebook app name.

## Web

1. See [Before you begin](https://firebase.google.com/docs/auth/web/facebook-login#before_you_begin) and follow the instructions to configure and enable sign-in with Facebook correctly.
