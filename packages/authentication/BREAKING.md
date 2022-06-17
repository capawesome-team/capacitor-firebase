# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases of Capacitor Firebase Authentication plugin.

## Versions

- [Version 0.4.x](#version-04x)

## Version 0.4.x

Add the following `string` element to `android/app/src/main/res/values/strings.xml` after the `resources` element:

```diff
<string name="facebook_app_id">[APP_ID]</string>
<string name="fb_login_protocol_scheme">fb[APP_ID]</string>
+ <string name="facebook_client_token">[CLIENT_TOKEN]</string>
```

Add the following `meta-data` element to `android/app/src/main/AndroidManifest.xml` inside the `application` element:

```diff
<meta-data
   android:name="com.facebook.sdk.ApplicationId"
   android:value="@string/facebook_app_id"/>

+<meta-data 
+   android:name="com.facebook.sdk.ClientToken" 
+   android:value="@string/facebook_client_token"/>
```
