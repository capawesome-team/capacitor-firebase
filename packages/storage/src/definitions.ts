export interface FirebaseStoragePlugin {
  /**
   * Delete a file.
   *
   * @since 5.3.0
   */
  deleteFile(options: DeleteFileOptions): Promise<void>;
  /**
   * Get the download url for a file.
   *
   * @since 5.3.0
   */
  getDownloadUrl(options: GetDownloadUrlOptions): Promise<GetDownloadUrlResult>;
  /**
   * Get the metadata for a file.
   *
   * @since 5.3.0
   */
  getMetadata(options: GetMetadataOptions): Promise<GetMetadataResult>;
  /**
   * List files in a directory.
   *
   * @since 5.3.0
   */
  listFiles(options: ListFilesOptions): Promise<ListFilesResult>;
  /**
   * Update the metadata for a file.
   *
   * @since 5.3.0
   */
  updateMetadata(options: UpdateMetadataOptions): Promise<void>;
  /**
   * Upload a file.
   *
   * @since 5.3.0
   */
  uploadFile(
    options: UploadFileOptions,
    callback: UploadFileCallback,
  ): Promise<CallbackId>;
}

/**
 * @since 5.3.0
 */
export interface DeleteFileOptions {
  /**
   * The full path to the file to delete, including the file name.
   *
   * @since 5.3.0
   * @example 'mountains.png'
   * @example 'images/mountains.png'
   */
  path: string;
}

/**
 * @since 5.3.0
 */
export interface GetDownloadUrlOptions {
  /**
   * The full path to the file to get the download url for, including the file name.
   *
   * @since 5.3.0
   * @example 'mountains.png'
   * @example 'images/mountains.png'
   */
  path: string;
}

/**
 * @since 5.3.0
 */
export interface GetDownloadUrlResult {
  /**
   * The download url for the file.
   *
   * @since 5.3.0
   */
  downloadUrl: string;
}

/**
 * @since 5.3.0
 */
export interface GetMetadataOptions {
  /**
   * The full path to the file to get the metadata for, including the file name.
   *
   * @since 5.3.0
   * @example 'mountains.png'
   * @example 'images/mountains.png'
   */
  path: string;
}

/**
 * @since 5.3.0
 */
export interface GetMetadataResult {
  /**
   * The bucket this file is contained in.
   *
   * @since 5.3.0
   */
  bucket: string;
  /**
   * The timestamp at which the file was created in milliseconds since the epoch.
   *
   * @since 5.3.0
   * @example 1697304435933
   */
  createdAt?: number;
  /**
   * The object's generation.
   *
   * @since 5.3.0
   * @see https://cloud.google.com/storage/docs/metadata#generation-number
   */
  generation: string;
  /**
   * The md5 hash of the file.
   *
   * @since 5.3.0
   */
  md5Hash?: string;
  /**
   * The object's metadata generation.
   *
   * @since 5.3.0
   * @see https://cloud.google.com/storage/docs/metadata#generation-number
   */
  metadataGeneration: string;
  /**
   * The short name of this file, which is the last component of the full path.
   *
   * @since 5.3.0
   * @example 'mountains.png'
   */
  name?: string;
  /**
   * The full path to the file, including the file name.
   *
   * @since 5.3.0
   * @example 'images/mountains.png'
   */
  path?: string;
  /**
   * The size of the file in bytes.
   *
   * @since 5.3.0
   */
  size: number;
  /**
   * The timestamp at which the file was last updated in milliseconds since the epoch.
   *
   * @since 5.3.0
   * @example 1697304435933
   */
  updatedAt: number;
}

/**
 * @since 5.3.0
 */
export interface ListFilesOptions {
  /**
   * The full path to the directory to list files for.
   *
   * @since 5.3.0
   */
  path: string;
  /**
   * The maximum number of results to return.
   *
   * @since 5.3.0
   * @default 1000
   */
  maxResults?: number;
  /**
   * The page token, returned by a previous call to this method.
   * If provided, listing is resumed from the previous position.
   *
   * @since 5.3.0
   */
  pageToken?: string;
}

/**
 * @since 5.3.0
 */
export interface ListFilesResult {
  /**
   * The list of files in the directory.
   *
   * @since 5.3.0
   */
  items: StorageReference[];
  /**
   * If set, there might be more results for this list.
   * Use this token to resume the list.
   *
   * @since 5.3.0
   */
  nextPageToken?: string;
}

/**
 * @since 5.3.0
 */
export interface StorageReference {
  /**
   * The bucket this file is contained in.
   *
   * @since 5.3.0
   */
  bucket: string;
  /**
   * The full path to the file, including the file name.
   *
   * @since 5.3.0
   * @example 'images/mountains.png'
   */
  path: string;
  /**
   * The short name of this file, which is the last component of the full path.
   *
   * @since 5.3.0
   * @example 'mountains.png'
   */
  name: string;
}

/**
 * @since 5.3.0
 */
export interface UpdateMetadataOptions {
  /**
   * The full path to the file to update the metadata for, including the file name.
   *
   * @since 5.3.0
   */
  path: string;
  /**
   * The metadata to update.
   *
   * @since 5.3.0
   */
  metadata: SettableMetadata;
}

/**
 * @since 5.3.0
 */
export interface SettableMetadata {
  /**
   * Served as the `Cache-Control` header on object download.
   *
   * @since 5.3.0
   */
  cacheControl?: string;
  /**
   * Served as the `Content-Disposition` header on object download.
   *
   * @since 5.3.0
   */
  contentDisposition?: string;
  /**
   * Served as the `Content-Encoding` header on object download.
   *
   * @since 5.3.0
   */
  contentEncoding?: string;
  /**
   * Served as the `Content-Language` header on object download.
   *
   * @since 5.3.0
   */
  contentLanguage?: string;
  /**
   * Served as the `Content-Type` header on object download.
   *
   * @since 5.3.0
   */
  contentType?: string;
  /**
   * Additional user-defined custom metadata.
   *
   * @since 5.3.0
   */
  customMetadata?: { [key: string]: string };
}

/**
 * @since 5.3.0
 */
export interface UploadFileOptions {
  /**
   * The data to upload.
   *
   * Only available on Web.
   *
   * @since 5.3.0
   */
  blob?: Blob;
  /**
   * The full path where data should be uploaded, including the file name.
   *
   * @since 5.3.0
   * @example 'mountains.png'
   * @example 'images/mountains.png'
   */
  path: string;
  /**
   * The uri to the file to upload.
   *
   * Only available on Android and iOS.
   *
   * @since 5.3.0
   * @example 'content://com.google.android.apps.photos.contentprovider/-1/1/content://media/external/images/media/1000000214/ORIGINAL/NONE/image/png/mountains'
   * @example 'file:///var/mobile/Containers/Data/Application/E397A70D-67E4-4258-236E-W1D9E12111D4/Library/Caches/092F8464-DE60-40B3-8A23-EB83160D9F9F/mountains.png'
   */
  uri?: string;
}

/**
 * @since 5.3.0
 */
export type UploadFileCallback = (
  event: UploadFileCallbackEvent | null,
  error: any,
) => void;

/**
 * @since 5.3.0
 */
export interface UploadFileCallbackEvent {
  /**
   * The upload progress, as a percentage from 0 to 100.
   *
   * @since 5.3.0
   * @example 50
   */
  progress: number;
  /**
   * The number of bytes that have been transferred.
   *
   * Only available on Android and Web.
   *
   * @since 5.3.0
   * @example 1000
   */
  bytesTransferred?: number;
  /**
   * The total number of bytes to be transferred.
   *
   * Only available on Android and Web.
   *
   * @since 5.3.0
   * @example 2000
   */
  totalBytes?: number;
  /**
   * Whether the upload is completed or not.
   *
   * @since 5.3.0
   */
  completed: boolean;
}

/**
 * @since 5.3.0
 */
export type CallbackId = string;
