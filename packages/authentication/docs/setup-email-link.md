# Set up authentication link to the user's email address

## Android

1. See [Before you begin](https://firebase.google.com/docs/auth/android/email-link-auth#before_you_begin) and follow the instructions to configure sign-in with Email Link correctly.  
**Attention**: Skip step 2. The dependency for the Firebase Authentication Android library is already declared by the plugin.
1. See [Enable Email Link sign-in for your Firebase project](https://firebase.google.com/docs/auth/android/email-link-auth#enable_email_link_sign-in_for_your_firebase_project) and follow the instructions to enable the Email provider and Email link sign-in method for your Firebase project.
1. See [Handling Android App Links](https://developer.android.com/training/app-links#android-app-links) on how to enable App Links in order to open an Email Link directly in your app.
	1. Add the following intent filter to `android/app/src/main/AndroidManifest.xml` inside the `activity` element:
		```xml
		<intent-filter android:autoVerify="true">
			<action android:name="android.intent.action.VIEW" />

			<category android:name="android.intent.category.DEFAULT" />
			<category android:name="android.intent.category.BROWSABLE" />

			<data
				android:host="[DOMAIN_NAME]"
				android:scheme="https" />
		</intent-filter>
		```

		`[DOMAIN_NAME]` must be replaced with your domain name of the link.

## iOS

1. See [Before you begin](https://firebase.google.com/docs/auth/ios/email-link-auth#before_you_begin) and follow the instructions to configure sign-in with Email Link correctly.  
**Attention**: Skip step 2. The `FirebaseAuth` pod is already added by the plugin.
1. See [Enable Email Link sign-in for your Firebase project](https://firebase.google.com/docs/auth/ios/email-link-auth#enable_email_link_sign-in_for_your_firebase_project) and follow the instructions to enable the Email provider and Email link sign-in method for your Firebase project.
1. See [Support Universal Links](https://developer.apple.com/library/archive/documentation/General/Conceptual/AppSearch/UniversalLinks.html) on how to enable Universal Links in order to open an Email Link directly in your app.
	1. Add the following lines to your `ios/App/App/Info.plist` file:
		```xml
		<key>CFBundleURLTypes</key>
		<array>
			<dict>
				<key>CFBundleURLName</key>
				<string>$(PRODUCT_BUNDLE_IDENTIFIER)</string>
				<key>CFBundleURLSchemes</key>
				<array>
					<string>[DOMAIN_NAME]</string>
				</array>
			</dict>
		</array>
		```

		`[DOMAIN_NAME]` must be replaced with your domain name of the link.

	1. Add the following lines to your `ios/App/App/App.entitlements` file:
		```xml
		?xml version="1.0" encoding="UTF-8"?>
		<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
		<plist version="1.0">
		<dict>
			<key>com.apple.developer.associated-domains</key>
			<array>
				<string>applinks:[DOMAIN_NAME]</string>
			</array>
		</dict>
		</plist>
		```

		`[DOMAIN_NAME]` must be replaced with your domain name of the link.

## Web

1. See [Before you begin](https://firebase.google.com/docs/auth/web/email-link-auth#before_you_begin) and follow the instructions to configure sign-in with Email Link correctly.  
1. See [Enable Email Link sign-in for your Firebase project](https://firebase.google.com/docs/auth/web/email-link-auth#enable_email_link_sign-in_for_your_firebase_project) and follow the instructions to enable the Email provider and Email link sign-in method for your Firebase project.
