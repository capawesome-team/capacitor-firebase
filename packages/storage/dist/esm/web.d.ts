import { WebPlugin } from '@capacitor/core';
import type { DeleteFileOptions, FirebaseStoragePlugin, GetDownloadUrlOptions, GetDownloadUrlResult, GetMetadataOptions, GetMetadataResult, ListFilesOptions, ListFilesResult, UpdateMetadataOptions, UploadFileCallback, UploadFileOptions, UseEmulatorOptions } from './definitions';
export declare class FirebaseStorageWeb extends WebPlugin implements FirebaseStoragePlugin {
    static readonly ERROR_BLOB_MISSING = "blob must be provided.";
    deleteFile(options: DeleteFileOptions): Promise<void>;
    getDownloadUrl(options: GetDownloadUrlOptions): Promise<GetDownloadUrlResult>;
    getMetadata(options: GetMetadataOptions): Promise<GetMetadataResult>;
    listFiles(options: ListFilesOptions): Promise<ListFilesResult>;
    updateMetadata(options: UpdateMetadataOptions): Promise<void>;
    uploadFile(options: UploadFileOptions, callback: UploadFileCallback): Promise<string>;
    useEmulator(options: UseEmulatorOptions): Promise<void>;
    private createUploadFileCallbackEvent;
}
