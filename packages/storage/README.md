# @capacitor-firebase/storage

Unofficial Capacitor plugin for [Firebase Cloud Storage](https://firebase.google.com/docs/storage/).[^1]

## Installation

```bash
npm install @capacitor-firebase/storage
npx cap sync
```

Add Firebase to your project if you haven't already ([Android](https://firebase.google.com/docs/android/setup) / [iOS](https://firebase.google.com/docs/ios/setup)).

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$firebaseStorageVersion` version of `com.google.firebase:firebase-storage` (default: `20.2.1`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-firebase-plugin-demo](https://github.com/robingenz/capacitor-firebase-plugin-demo)

## Starter templates

The following starter templates are available:

- [Ionstarter Angular Firebase](https://ionstarter.dev/)

## Usage

```typescript
import { FirebaseStorage } from '@capacitor-firebase/storage';
import { Filesystem, Directory } from '@capacitor/filesystem';

const uploadFile = async () => {
  return new Promise((resolve, reject) => {
    await FirebaseStorage.uploadFile(
      {
        path: 'images/mountains.png',
        uri: 'file:///var/mobile/Containers/Data/Application/E397A70D-67E4-4258-236E-W1D9E12111D4/Library/Caches/092F8464-DE60-40B3-8A23-EB83160D9F9F/mountains.png',
      },
      (event, error) => {
        if (error) {
          reject(error);
        } else if (event?.completed) {
          resolve();
        }
      }
    );
  });
};

const downloadFile = async () => {
  const { downloadUrl } = await FirebaseStorage.getDownloadUrl({
    path: 'images/mountains.png',
  });
  const { path } = await Filesystem.downloadFile({
    url: downloadUrl,
    path: 'mountains.png',
    directory: Directory.Cache,
  });
  return path;
};

const getDownloadUrl = async () => {
  const { downloadUrl } = await FirebaseStorage.getDownloadUrl({
    path: 'images/mountains.png',
  });
  return downloadUrl;
};

const deleteFile = async () => {
  await FirebaseStorage.deleteFile({
    path: 'images/mountains.png',
  });
};

const listFiles = async () => {
  const { items } = await FirebaseStorage.listFiles({
    path: 'images',
  });
  return items;
};

const getMetadata = async () => {
  const result = await FirebaseStorage.getMetadata({
    path: 'images/mountains.png',
  });
  return result;
};

const updateMetadata = async () => {
  await FirebaseStorage.updateMetadata({
    path: 'images/mountains.png',
    metadata: {
      contentType: 'image/png',
      customMetadata: {
        foo: 'bar',
      },
    },
  });
};

const useEmulator = async () => {
  await FirebaseStorage.useEmulator({
    host: '10.0.2.2',
    port: 9001,
  });
};
```

## API

<docgen-index>

* [`deleteFile(...)`](#deletefile)
* [`getDownloadUrl(...)`](#getdownloadurl)
* [`getMetadata(...)`](#getmetadata)
* [`listFiles(...)`](#listfiles)
* [`updateMetadata(...)`](#updatemetadata)
* [`uploadFile(...)`](#uploadfile)
* [`useEmulator(...)`](#useemulator)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### deleteFile(...)

```typescript
deleteFile(options: DeleteFileOptions) => Promise<void>
```

Delete a file.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#deletefileoptions">DeleteFileOptions</a></code> |

**Since:** 5.3.0

--------------------


### getDownloadUrl(...)

```typescript
getDownloadUrl(options: GetDownloadUrlOptions) => Promise<GetDownloadUrlResult>
```

Get the download url for a file.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#getdownloadurloptions">GetDownloadUrlOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getdownloadurlresult">GetDownloadUrlResult</a>&gt;</code>

**Since:** 5.3.0

--------------------


### getMetadata(...)

```typescript
getMetadata(options: GetMetadataOptions) => Promise<GetMetadataResult>
```

Get the metadata for a file.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#getmetadataoptions">GetMetadataOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getmetadataresult">GetMetadataResult</a>&gt;</code>

**Since:** 5.3.0

--------------------


### listFiles(...)

```typescript
listFiles(options: ListFilesOptions) => Promise<ListFilesResult>
```

List files in a directory.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#listfilesoptions">ListFilesOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#listfilesresult">ListFilesResult</a>&gt;</code>

**Since:** 5.3.0

--------------------


### updateMetadata(...)

```typescript
updateMetadata(options: UpdateMetadataOptions) => Promise<void>
```

Update the metadata for a file.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#updatemetadataoptions">UpdateMetadataOptions</a></code> |

**Since:** 5.3.0

--------------------


### uploadFile(...)

```typescript
uploadFile(options: UploadFileOptions, callback: UploadFileCallback) => Promise<CallbackId>
```

Upload a file.

| Param          | Type                                                              |
| -------------- | ----------------------------------------------------------------- |
| **`options`**  | <code><a href="#uploadfileoptions">UploadFileOptions</a></code>   |
| **`callback`** | <code><a href="#uploadfilecallback">UploadFileCallback</a></code> |

**Returns:** <code>Promise&lt;string&gt;</code>

**Since:** 5.3.0

--------------------


### useEmulator(...)

```typescript
useEmulator(options: UseEmulatorOptions) => Promise<void>
```

Instrument your app to talk to the Cloud Storage emulator.

On Android, the cleartext traffic must be allowed. On the Capacitor configuration:
```
{
  server: {
    cleartext: true
  }
}
```
**The cleartext traffic is not intended for use in production.**

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#useemulatoroptions">UseEmulatorOptions</a></code> |

**Since:** 6.1.0

--------------------


### Interfaces


#### DeleteFileOptions

| Prop       | Type                | Description                                                   | Since |
| ---------- | ------------------- | ------------------------------------------------------------- | ----- |
| **`path`** | <code>string</code> | The full path to the file to delete, including the file name. | 5.3.0 |


#### GetDownloadUrlResult

| Prop              | Type                | Description                    | Since |
| ----------------- | ------------------- | ------------------------------ | ----- |
| **`downloadUrl`** | <code>string</code> | The download url for the file. | 5.3.0 |


#### GetDownloadUrlOptions

| Prop       | Type                | Description                                                                     | Since |
| ---------- | ------------------- | ------------------------------------------------------------------------------- | ----- |
| **`path`** | <code>string</code> | The full path to the file to get the download url for, including the file name. | 5.3.0 |


#### GetMetadataResult

| Prop                     | Type                | Description                                                                       | Since |
| ------------------------ | ------------------- | --------------------------------------------------------------------------------- | ----- |
| **`bucket`**             | <code>string</code> | The bucket this file is contained in.                                             | 5.3.0 |
| **`createdAt`**          | <code>number</code> | The timestamp at which the file was created in milliseconds since the epoch.      | 5.3.0 |
| **`generation`**         | <code>string</code> | The object's generation.                                                          | 5.3.0 |
| **`md5Hash`**            | <code>string</code> | The md5 hash of the file.                                                         | 5.3.0 |
| **`metadataGeneration`** | <code>string</code> | The object's metadata generation.                                                 | 5.3.0 |
| **`name`**               | <code>string</code> | The short name of this file, which is the last component of the full path.        | 5.3.0 |
| **`path`**               | <code>string</code> | The full path to the file, including the file name.                               | 5.3.0 |
| **`size`**               | <code>number</code> | The size of the file in bytes.                                                    | 5.3.0 |
| **`updatedAt`**          | <code>number</code> | The timestamp at which the file was last updated in milliseconds since the epoch. | 5.3.0 |


#### GetMetadataOptions

| Prop       | Type                | Description                                                                 | Since |
| ---------- | ------------------- | --------------------------------------------------------------------------- | ----- |
| **`path`** | <code>string</code> | The full path to the file to get the metadata for, including the file name. | 5.3.0 |


#### ListFilesResult

| Prop                | Type                            | Description                                                                           | Since |
| ------------------- | ------------------------------- | ------------------------------------------------------------------------------------- | ----- |
| **`items`**         | <code>StorageReference[]</code> | The list of files in the directory.                                                   | 5.3.0 |
| **`nextPageToken`** | <code>string</code>             | If set, there might be more results for this list. Use this token to resume the list. | 5.3.0 |


#### StorageReference

| Prop         | Type                | Description                                                                | Since |
| ------------ | ------------------- | -------------------------------------------------------------------------- | ----- |
| **`bucket`** | <code>string</code> | The bucket this file is contained in.                                      | 5.3.0 |
| **`path`**   | <code>string</code> | The full path to the file, including the file name.                        | 5.3.0 |
| **`name`**   | <code>string</code> | The short name of this file, which is the last component of the full path. | 5.3.0 |


#### ListFilesOptions

| Prop             | Type                | Description                                                                                                             | Default           | Since |
| ---------------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`path`**       | <code>string</code> | The full path to the directory to list files for.                                                                       |                   | 5.3.0 |
| **`maxResults`** | <code>number</code> | The maximum number of results to return.                                                                                | <code>1000</code> | 5.3.0 |
| **`pageToken`**  | <code>string</code> | The page token, returned by a previous call to this method. If provided, listing is resumed from the previous position. |                   | 5.3.0 |


#### UpdateMetadataOptions

| Prop           | Type                                                          | Description                                                                    | Since |
| -------------- | ------------------------------------------------------------- | ------------------------------------------------------------------------------ | ----- |
| **`path`**     | <code>string</code>                                           | The full path to the file to update the metadata for, including the file name. | 5.3.0 |
| **`metadata`** | <code><a href="#settablemetadata">SettableMetadata</a></code> | The metadata to update.                                                        | 5.3.0 |


#### SettableMetadata

| Prop                     | Type                                    | Description                                                    | Since |
| ------------------------ | --------------------------------------- | -------------------------------------------------------------- | ----- |
| **`cacheControl`**       | <code>string</code>                     | Served as the `Cache-Control` header on object download.       | 5.3.0 |
| **`contentDisposition`** | <code>string</code>                     | Served as the `Content-Disposition` header on object download. | 5.3.0 |
| **`contentEncoding`**    | <code>string</code>                     | Served as the `Content-Encoding` header on object download.    | 5.3.0 |
| **`contentLanguage`**    | <code>string</code>                     | Served as the `Content-Language` header on object download.    | 5.3.0 |
| **`contentType`**        | <code>string</code>                     | Served as the `Content-Type` header on object download.        | 5.3.0 |
| **`customMetadata`**     | <code>{ [key: string]: string; }</code> | Additional user-defined custom metadata.                       | 5.3.0 |


#### UploadFileOptions

| Prop           | Type                                                      | Description                                                           | Since |
| -------------- | --------------------------------------------------------- | --------------------------------------------------------------------- | ----- |
| **`blob`**     | <code>Blob</code>                                         | The data to upload. Only available for Web.                           | 5.3.0 |
| **`path`**     | <code>string</code>                                       | The full path where data should be uploaded, including the file name. | 5.3.0 |
| **`uri`**      | <code>string</code>                                       | The uri to the file to upload. Only available for Android and iOS.    | 5.3.0 |
| **`metadata`** | <code><a href="#uploadmetadata">UploadMetadata</a></code> | The metadata to set for the file.                                     | 5.4.0 |


#### UploadMetadata

| Prop          | Type                | Description                                                      | Since |
| ------------- | ------------------- | ---------------------------------------------------------------- | ----- |
| **`md5Hash`** | <code>string</code> | The base64-encoded MD5 hash of the file. Only available for Web. | 5.4.0 |


#### UploadFileCallbackEvent

| Prop                   | Type                 | Description                                                                         | Since |
| ---------------------- | -------------------- | ----------------------------------------------------------------------------------- | ----- |
| **`progress`**         | <code>number</code>  | The upload progress, as a percentage between 0 and 1.                               | 5.3.0 |
| **`bytesTransferred`** | <code>number</code>  | The number of bytes that have been transferred. Only available for Android and Web. | 5.3.0 |
| **`totalBytes`**       | <code>number</code>  | The total number of bytes to be transferred. Only available for Android and Web.    | 5.3.0 |
| **`completed`**        | <code>boolean</code> | Whether the upload is completed or not.                                             | 5.3.0 |


#### UseEmulatorOptions

| Prop       | Type                | Description                                                                                                                                                                     | Default           | Since |
| ---------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`host`** | <code>string</code> | The emulator host without any port or scheme. Note when using a Android Emulator device: 10.0.2.2 is the special IP address to connect to the 'localhost' of the host computer. |                   | 6.1.0 |
| **`port`** | <code>number</code> | The emulator port.                                                                                                                                                              | <code>9199</code> | 6.1.0 |


### Type Aliases


#### UploadFileCallback

<code>(event: <a href="#uploadfilecallbackevent">UploadFileCallbackEvent</a> | null, error: any): void</code>


#### CallbackId

<code>string</code>

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/storage/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-firebase/blob/main/packages/storage/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
