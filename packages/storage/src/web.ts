import { WebPlugin } from '@capacitor/core';
import type {
  ListOptions,
  SettableMetadata,
  UploadTaskSnapshot,
} from 'firebase/storage';
import {
  deleteObject,
  getDownloadURL,
  getMetadata,
  getStorage,
  list,
  ref,
  updateMetadata,
  uploadBytesResumable,
} from 'firebase/storage';

import type {
  DeleteFileOptions,
  FirebaseStoragePlugin,
  GetDownloadUrlOptions,
  GetDownloadUrlResult,
  GetMetadataOptions,
  GetMetadataResult,
  ListFilesOptions,
  ListFilesResult,
  UpdateMetadataOptions,
  UploadFileCallback,
  UploadFileCallbackEvent,
  UploadFileOptions,
} from './definitions';

export class FirebaseStorageWeb
  extends WebPlugin
  implements FirebaseStoragePlugin
{
  public static readonly ERROR_BLOB_MISSING = 'blob must be provided.';

  public async deleteFile(options: DeleteFileOptions): Promise<void> {
    const storage = getStorage();
    const storageRef = ref(storage, options.path);
    await deleteObject(storageRef);
  }

  public async getDownloadUrl(
    options: GetDownloadUrlOptions,
  ): Promise<GetDownloadUrlResult> {
    const storage = getStorage();
    const storageRef = ref(storage, options.path);
    const downloadUrl = await getDownloadURL(storageRef);
    return { downloadUrl };
  }

  public async getMetadata(
    options: GetMetadataOptions,
  ): Promise<GetMetadataResult> {
    const storage = getStorage();
    const storageRef = ref(storage, options.path);
    const metadata = await getMetadata(storageRef);
    const result: GetMetadataResult = {
      bucket: metadata.bucket,
      createdAt: new Date(metadata.timeCreated).getTime(),
      generation: metadata.generation,
      metadataGeneration: metadata.metageneration,
      name: metadata.name,
      path: metadata.fullPath,
      size: metadata.size,
      updatedAt: new Date(metadata.updated).getTime(),
    };
    if (metadata.md5Hash) {
      result.md5Hash = metadata.md5Hash;
    }
    return result;
  }

  public async listFiles(options: ListFilesOptions): Promise<ListFilesResult> {
    const storage = getStorage();
    const storageRef = ref(storage, options.path);
    const listOptions: ListOptions = {
      maxResults: options.maxResults,
      pageToken: options.pageToken,
    };
    const listResult = await list(storageRef, listOptions);
    const result: ListFilesResult = {
      items: listResult.items.map(item => ({
        bucket: item.bucket,
        name: item.name,
        path: item.fullPath,
      })),
    };
    if (listResult.nextPageToken) {
      result.nextPageToken = listResult.nextPageToken;
    }
    return result;
  }

  public async updateMetadata(options: UpdateMetadataOptions): Promise<void> {
    const storage = getStorage();
    const storageRef = ref(storage, options.path);
    const metadata: SettableMetadata = {
      cacheControl: options.metadata.cacheControl,
      contentDisposition: options.metadata.contentDisposition,
      contentEncoding: options.metadata.contentEncoding,
      contentLanguage: options.metadata.contentLanguage,
      contentType: options.metadata.contentType,
      customMetadata: options.metadata.customMetadata,
    };
    await updateMetadata(storageRef, metadata);
  }

  public async uploadFile(
    options: UploadFileOptions,
    callback: UploadFileCallback,
  ): Promise<string> {
    if (!options.blob) {
      throw new Error(FirebaseStorageWeb.ERROR_BLOB_MISSING);
    }
    const storage = getStorage();
    const storageRef = ref(storage, options.path);
    const uploadTask = uploadBytesResumable(storageRef, options.blob);
    uploadTask.on('state_changed', {
      next: snapshot => {
        const result = this.createUploadFileCallbackEvent(snapshot);
        callback(result, undefined);
      },
      error: error => {
        callback(null, error);
      },
      complete: () => {
        const result = this.createUploadFileCallbackEvent(uploadTask.snapshot);
        callback(result, undefined);
      },
    });
    return Date.now().toString();
  }

  private createUploadFileCallbackEvent(
    snapshot: UploadTaskSnapshot,
  ): UploadFileCallbackEvent {
    const result: UploadFileCallbackEvent = {
      progress: (snapshot.bytesTransferred / snapshot.totalBytes) * 100,
      bytesTransferred: snapshot.bytesTransferred,
      totalBytes: snapshot.totalBytes,
      completed: snapshot.state === 'success',
    };
    return result;
  }
}
