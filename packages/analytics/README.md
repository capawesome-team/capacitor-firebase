# @capacitor-firebase/analytics

Unofficial Capacitor plugin for [Firebase Analytics](https://firebase.google.com/docs/analytics).[^1]

## Installation

```bash
npm install @capacitor-firebase/analytics firebase
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#android) / [iOS](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#ios) / [Web](https://github.com/capawesome-team/capacitor-firebase/blob/main/docs/firebase-setup.md#web)).

### Android

#### Disable Analytics data collection

See [Disable Analytics data collection](https://firebase.google.com/docs/analytics/configure-data-collection?platform=android#disable_data_collection) if you want to disable Analytics data collection.

#### Disable Advertising ID collection

See [Disable Advertising ID collection](https://firebase.google.com/docs/analytics/configure-data-collection?platform=android#disable_advertising_id_collection) if you want to disable Advertising ID collection.

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$firebaseAnalyticsVersion` version of `com.google.firebase:firebase-analytics` (default: `22.2.0`)

### iOS

If you are using **CocoaPods** for your iOS project, you need to add the `CapacitorFirebaseAnalytics/Analytics` pod to your `Podfile` (usually `ios/App/Podfile`):

```diff
target 'App' do
capacitor_pods
# Add your Pods here
+  pod 'CapacitorFirebaseAnalytics/Analytics', :path => '../../node_modules/@capacitor-firebase/analytics'
end
```

**Attention**: Do not add the pod in the section `def capacitor_pods`, but under the comment `# Add your Pods here` ([example](https://github.com/robingenz/capacitor-firebase-plugin-demo/blob/e1684a0af6871442ed0a87dceeeba6fd9ce0185d/ios/App/Podfile#L30)).

#### Disable Analytics data collection

See [Disable Analytics data collection](https://firebase.google.com/docs/analytics/configure-data-collection?platform=ios#disable_data_collection) if you want to disable Analytics data collection.

#### Disable IDFA collection

If you are using **CocoaPods** for your iOS project and you want to disable IDFA collection, you can use the `CapacitorFirebaseAnalytics/AnalyticsWithoutAdIdSupport` pod instead of the `CapacitorFirebaseAnalytics/Analytics` pod:

```diff
target 'App' do
capacitor_pods
# Add your Pods here
-  pod 'CapacitorFirebaseAnalytics/Analytics', :path => '../../node_modules/@capacitor-firebase/analytics'
+  pod 'CapacitorFirebaseAnalytics/AnalyticsWithoutAdIdSupport', :path => '../../node_modules/@capacitor-firebase/analytics'
end
```

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-firebase-plugin-demo](https://github.com/robingenz/capacitor-firebase-plugin-demo)

## Usage

```typescript
import { FirebaseAnalytics } from '@capacitor-firebase/analytics';

const setUserId = async () => {
  await FirebaseAnalytics.setUserId({
    userId: '123',
  });
};

const setUserProperty = async () => {
  await FirebaseAnalytics.setUserProperty({
    key: 'language',
    value: 'en',
  });
};

const setCurrentScreen = async () => {
  await FirebaseAnalytics.setCurrentScreen({
    screenName: 'Login',
    screenClassOverride: 'LoginPage',
  });
};

const logEvent = async () => {
  await FirebaseAnalytics.logEvent({
    name: 'sign_up',
    params: { method: 'password' },
  });
};

const setSessionTimeoutDuration = async () => {
  await FirebaseAnalytics.setSessionTimeoutDuration({
    duration: '120',
  });
};

const setEnabled = async () => {
  await FirebaseAnalytics.setEnabled({
    enabled: true,
  });
};

const isEnabled = async () => {
  const { enabled } = await FirebaseAnalytics.isEnabled();
  return enabled;
};

const resetAnalyticsData = async () => {
  await FirebaseAnalytics.resetAnalyticsData();
};

const initiateOnDeviceConversionMeasurementWithEmailAddress = async () => {
  await FirebaseAnalytics.initiateOnDeviceConversionMeasurementWithEmailAddress({
    emailAddress: 'mail@example.com',
  });
};

const initiateOnDeviceConversionMeasurementWithPhoneNumber = async () => {
  await FirebaseAnalytics.initiateOnDeviceConversionMeasurementWithPhoneNumber({
    phoneNumber: '+49123456789',
  });
};

const initiateOnDeviceConversionMeasurementWithHashedEmailAddress = async () => {
  await FirebaseAnalytics.initiateOnDeviceConversionMeasurementWithHashedEmailAddress({
    emailAddressToHash: 'mail@example.com',
  });
};

const initiateOnDeviceConversionMeasurementWithHashedPhoneNumber = async () => {
  await FirebaseAnalytics.initiateOnDeviceConversionMeasurementWithHashedPhoneNumber({
    phoneNumberToHash: '+49123456789',
  });
};
```

## API

<docgen-index>

* [`getAppInstanceId()`](#getappinstanceid)
* [`setConsent(...)`](#setconsent)
* [`setUserId(...)`](#setuserid)
* [`setUserProperty(...)`](#setuserproperty)
* [`setCurrentScreen(...)`](#setcurrentscreen)
* [`logEvent(...)`](#logevent)
* [`setSessionTimeoutDuration(...)`](#setsessiontimeoutduration)
* [`setEnabled(...)`](#setenabled)
* [`isEnabled()`](#isenabled)
* [`resetAnalyticsData()`](#resetanalyticsdata)
* [`initiateOnDeviceConversionMeasurementWithEmailAddress(...)`](#initiateondeviceconversionmeasurementwithemailaddress)
* [`initiateOnDeviceConversionMeasurementWithPhoneNumber(...)`](#initiateondeviceconversionmeasurementwithphonenumber)
* [`initiateOnDeviceConversionMeasurementWithHashedEmailAddress(...)`](#initiateondeviceconversionmeasurementwithhashedemailaddress)
* [`initiateOnDeviceConversionMeasurementWithHashedPhoneNumber(...)`](#initiateondeviceconversionmeasurementwithhashedphonenumber)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getAppInstanceId()

```typescript
getAppInstanceId() => Promise<GetAppInstanceIdResult>
```

Retrieves the app instance id.

Only available for Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getappinstanceidresult">GetAppInstanceIdResult</a>&gt;</code>

**Since:** 1.4.0

--------------------


### setConsent(...)

```typescript
setConsent(options: SetConsentOptions) => Promise<void>
```

Sets the user's consent mode.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#setconsentoptions">SetConsentOptions</a></code> |

**Since:** 6.0.0

--------------------


### setUserId(...)

```typescript
setUserId(options: SetUserIdOptions) => Promise<void>
```

Sets the user ID property.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#setuseridoptions">SetUserIdOptions</a></code> |

**Since:** 0.1.0

--------------------


### setUserProperty(...)

```typescript
setUserProperty(options: SetUserPropertyOptions) => Promise<void>
```

Sets a custom user property to a given value.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setuserpropertyoptions">SetUserPropertyOptions</a></code> |

**Since:** 0.1.0

--------------------


### setCurrentScreen(...)

```typescript
setCurrentScreen(options: SetCurrentScreenOptions) => Promise<void>
```

Sets the current screen name.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setcurrentscreenoptions">SetCurrentScreenOptions</a></code> |

**Since:** 0.1.0

--------------------


### logEvent(...)

```typescript
logEvent(options: LogEventOptions) => Promise<void>
```

Logs an app event.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#logeventoptions">LogEventOptions</a></code> |

**Since:** 0.1.0

--------------------


### setSessionTimeoutDuration(...)

```typescript
setSessionTimeoutDuration(options: SetSessionTimeoutDurationOptions) => Promise<void>
```

Sets the duration of inactivity that terminates the current session.

Only available for Android and iOS.

| Param         | Type                                                                                          |
| ------------- | --------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setsessiontimeoutdurationoptions">SetSessionTimeoutDurationOptions</a></code> |

**Since:** 0.1.0

--------------------


### setEnabled(...)

```typescript
setEnabled(options: SetEnabledOptions) => Promise<void>
```

Enables/disables automatic data collection.
The value does not apply until the next run of the app.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#setenabledoptions">SetEnabledOptions</a></code> |

**Since:** 0.1.0

--------------------


### isEnabled()

```typescript
isEnabled() => Promise<IsEnabledResult>
```

Returns whether or not automatic data collection is enabled.

Only available for Web.

**Returns:** <code>Promise&lt;<a href="#isenabledresult">IsEnabledResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### resetAnalyticsData()

```typescript
resetAnalyticsData() => Promise<void>
```

Clears all analytics data for this app from the device.
Resets the app instance id.

Only available for Android and iOS.

**Since:** 0.1.0

--------------------


### initiateOnDeviceConversionMeasurementWithEmailAddress(...)

```typescript
initiateOnDeviceConversionMeasurementWithEmailAddress(options: InitiateOnDeviceConversionMeasurementWithEmailAddressOptions) => Promise<void>
```

Initiates on-device conversion measurement with an email address.

Only available for iOS.

| Param         | Type                                                                                                                                                  |
| ------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#initiateondeviceconversionmeasurementwithemailaddressoptions">InitiateOnDeviceConversionMeasurementWithEmailAddressOptions</a></code> |

**Since:** 7.2.0

--------------------


### initiateOnDeviceConversionMeasurementWithPhoneNumber(...)

```typescript
initiateOnDeviceConversionMeasurementWithPhoneNumber(options: InitiateOnDeviceConversionMeasurementWithPhoneNumberOptions) => Promise<void>
```

Initiates on-device conversion measurement with a phone number.

Only available for iOS.

| Param         | Type                                                                                                                                                |
| ------------- | --------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#initiateondeviceconversionmeasurementwithphonenumberoptions">InitiateOnDeviceConversionMeasurementWithPhoneNumberOptions</a></code> |

**Since:** 7.2.0

--------------------


### initiateOnDeviceConversionMeasurementWithHashedEmailAddress(...)

```typescript
initiateOnDeviceConversionMeasurementWithHashedEmailAddress(options: InitiateOnDeviceConversionMeasurementWithHashedEmailAddressOptions) => Promise<void>
```

Initiates on-device conversion measurement with a hashed email address.

Only available for iOS.

| Param         | Type                                                                                                                                                              |
| ------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#initiateondeviceconversionmeasurementwithhashedemailaddressoptions">InitiateOnDeviceConversionMeasurementWithHashedEmailAddressOptions</a></code> |

**Since:** 7.2.0

--------------------


### initiateOnDeviceConversionMeasurementWithHashedPhoneNumber(...)

```typescript
initiateOnDeviceConversionMeasurementWithHashedPhoneNumber(options: InitiateOnDeviceConversionMeasurementWithHashedPhoneNumberOptions) => Promise<void>
```

Initiates on-device conversion measurement with a hashed phone number.

Only available for iOS.

| Param         | Type                                                                                                                                                            |
| ------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#initiateondeviceconversionmeasurementwithhashedphonenumberoptions">InitiateOnDeviceConversionMeasurementWithHashedPhoneNumberOptions</a></code> |

**Since:** 7.2.0

--------------------


### Interfaces


#### GetAppInstanceIdResult

| Prop                | Type                | Description                                                                                                                                                                                             | Since |
| ------------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`appInstanceId`** | <code>string</code> | The app instance id. Not defined if `FirebaseAnalytics.<a href="#consenttype">ConsentType</a>.ANALYTICS_STORAGE` has been set to `FirebaseAnalytics.<a href="#consentstatus">ConsentStatus</a>.DENIED`. | 1.4.0 |


#### SetConsentOptions

| Prop         | Type                                                    | Description         | Since |
| ------------ | ------------------------------------------------------- | ------------------- | ----- |
| **`type`**   | <code><a href="#consenttype">ConsentType</a></code>     | The consent type.   | 6.0.0 |
| **`status`** | <code><a href="#consentstatus">ConsentStatus</a></code> | The consent status. | 6.0.0 |


#### SetUserIdOptions

| Prop         | Type                        | Since |
| ------------ | --------------------------- | ----- |
| **`userId`** | <code>string \| null</code> | 0.1.0 |


#### SetUserPropertyOptions

| Prop        | Type                        | Since |
| ----------- | --------------------------- | ----- |
| **`key`**   | <code>string</code>         | 0.1.0 |
| **`value`** | <code>string \| null</code> | 0.1.0 |


#### SetCurrentScreenOptions

| Prop                      | Type                        | Description                         | Default           | Since |
| ------------------------- | --------------------------- | ----------------------------------- | ----------------- | ----- |
| **`screenName`**          | <code>string \| null</code> |                                     |                   | 0.1.0 |
| **`screenClassOverride`** | <code>string \| null</code> | Only available for Android and iOS. | <code>null</code> | 0.1.0 |


#### LogEventOptions

| Prop         | Type                                 | Description                | Since |
| ------------ | ------------------------------------ | -------------------------- | ----- |
| **`name`**   | <code>string</code>                  | The event name.            | 0.1.0 |
| **`params`** | <code>{ [key: string]: any; }</code> | The optional event params. | 0.1.0 |


#### SetSessionTimeoutDurationOptions

| Prop           | Type                | Description          | Default           | Since |
| -------------- | ------------------- | -------------------- | ----------------- | ----- |
| **`duration`** | <code>number</code> | Duration in seconds. | <code>1800</code> | 0.1.0 |


#### SetEnabledOptions

| Prop          | Type                 | Since |
| ------------- | -------------------- | ----- |
| **`enabled`** | <code>boolean</code> | 0.1.0 |


#### IsEnabledResult

| Prop          | Type                 | Since |
| ------------- | -------------------- | ----- |
| **`enabled`** | <code>boolean</code> | 0.1.0 |


#### InitiateOnDeviceConversionMeasurementWithEmailAddressOptions

| Prop               | Type                | Description                                                          | Since |
| ------------------ | ------------------- | -------------------------------------------------------------------- | ----- |
| **`emailAddress`** | <code>string</code> | The email address to initiate on-device conversion measurement with. | 7.2.0 |


#### InitiateOnDeviceConversionMeasurementWithPhoneNumberOptions

| Prop              | Type                | Description                                                         | Since |
| ----------------- | ------------------- | ------------------------------------------------------------------- | ----- |
| **`phoneNumber`** | <code>string</code> | The phone number to initiate on-device conversion measurement with. | 7.2.0 |


#### InitiateOnDeviceConversionMeasurementWithHashedEmailAddressOptions

| Prop                     | Type                | Description                                                          | Since |
| ------------------------ | ------------------- | -------------------------------------------------------------------- | ----- |
| **`emailAddressToHash`** | <code>string</code> | The email address to initiate on-device conversion measurement with. | 7.2.0 |


#### InitiateOnDeviceConversionMeasurementWithHashedPhoneNumberOptions

| Prop                    | Type                | Description                                                         | Since |
| ----------------------- | ------------------- | ------------------------------------------------------------------- | ----- |
| **`phoneNumberToHash`** | <code>string</code> | The phone number to initiate on-device conversion measurement with. | 7.2.0 |


### Enums


#### ConsentType

| Members                      | Value                                  | Since |
| ---------------------------- | -------------------------------------- | ----- |
| **`AdPersonalization`**      | <code>'AD_PERSONALIZATION'</code>      | 6.0.0 |
| **`AdStorage`**              | <code>'AD_STORAGE'</code>              | 6.0.0 |
| **`AdUserData`**             | <code>'AD_USER_DATA'</code>            | 6.0.0 |
| **`AnalyticsStorage`**       | <code>'ANALYTICS_STORAGE'</code>       | 6.0.0 |
| **`FunctionalityStorage`**   | <code>'FUNCTIONALITY_STORAGE'</code>   | 6.0.0 |
| **`PersonalizationStorage`** | <code>'PERSONALIZATION_STORAGE'</code> | 6.0.0 |


#### ConsentStatus

| Members       | Value                  | Since |
| ------------- | ---------------------- | ----- |
| **`Granted`** | <code>'GRANTED'</code> | 6.0.0 |
| **`Denied`**  | <code>'DENIED'</code>  | 6.0.0 |

</docgen-api>

## Test your implementation

[Here](https://firebase.google.com/docs/analytics/debugview) you can find more information on how to test the Firebase Analytics implementation using the **DebugView**.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/analytics/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/analytics/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
