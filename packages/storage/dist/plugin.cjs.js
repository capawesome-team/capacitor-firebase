'use strict';

var core = require('@capacitor/core');
var storage = require('firebase/storage');

const FirebaseStorage = core.registerPlugin('FirebaseStorage', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.FirebaseStorageWeb()),
});

class FirebaseStorageWeb extends core.WebPlugin {
    async deleteFile(options) {
        const storage$1 = storage.getStorage();
        const storageRef = storage.ref(storage$1, options.path);
        await storage.deleteObject(storageRef);
    }
    async getDownloadUrl(options) {
        const storage$1 = storage.getStorage();
        const storageRef = storage.ref(storage$1, options.path);
        const downloadUrl = await storage.getDownloadURL(storageRef);
        return { downloadUrl };
    }
    async getMetadata(options) {
        const storage$1 = storage.getStorage();
        const storageRef = storage.ref(storage$1, options.path);
        const metadata = await storage.getMetadata(storageRef);
        const result = {
            bucket: metadata.bucket,
            createdAt: new Date(metadata.timeCreated).getTime(),
            generation: metadata.generation,
            metadataGeneration: metadata.metageneration,
            name: metadata.name,
            path: metadata.fullPath,
            size: metadata.size,
            updatedAt: new Date(metadata.updated).getTime(),
            cacheControl: metadata.cacheControl,
            contentDisposition: metadata.contentDisposition,
            contentEncoding: metadata.contentEncoding,
            contentLanguage: metadata.contentLanguage,
            contentType: metadata.contentType,
            customMetadata: metadata.customMetadata,
        };
        if (metadata.md5Hash) {
            result.md5Hash = metadata.md5Hash;
        }
        return result;
    }
    async listFiles(options) {
        const storage$1 = storage.getStorage();
        const storageRef = storage.ref(storage$1, options.path);
        const listOptions = {
            maxResults: options.maxResults,
            pageToken: options.pageToken,
        };
        const listResult = await storage.list(storageRef, listOptions);
        const result = {
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
    async updateMetadata(options) {
        const storage$1 = storage.getStorage();
        const storageRef = storage.ref(storage$1, options.path);
        const metadata = {
            cacheControl: options.metadata.cacheControl,
            contentDisposition: options.metadata.contentDisposition,
            contentEncoding: options.metadata.contentEncoding,
            contentLanguage: options.metadata.contentLanguage,
            contentType: options.metadata.contentType,
            customMetadata: options.metadata.customMetadata,
        };
        await storage.updateMetadata(storageRef, metadata);
    }
    async uploadFile(options, callback) {
        if (!options.blob) {
            throw new Error(FirebaseStorageWeb.ERROR_BLOB_MISSING);
        }
        const storage$1 = storage.getStorage();
        const storageRef = storage.ref(storage$1, options.path);
        let metadata;
        if (options.metadata) {
            metadata = {
                cacheControl: options.metadata.cacheControl,
                contentDisposition: options.metadata.contentDisposition,
                contentEncoding: options.metadata.contentEncoding,
                contentLanguage: options.metadata.contentLanguage,
                contentType: options.metadata.contentType,
                md5Hash: options.metadata.md5Hash,
                customMetadata: options.metadata.customMetadata,
            };
        }
        const uploadTask = storage.uploadBytesResumable(storageRef, options.blob, metadata);
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
    async useEmulator(options) {
        const storage$1 = storage.getStorage();
        const port = options.port || 9199;
        storage.connectStorageEmulator(storage$1, options.host, port);
    }
    createUploadFileCallbackEvent(snapshot) {
        const result = {
            progress: snapshot.bytesTransferred / snapshot.totalBytes,
            bytesTransferred: snapshot.bytesTransferred,
            totalBytes: snapshot.totalBytes,
            completed: snapshot.state === 'success',
        };
        return result;
    }
}
FirebaseStorageWeb.ERROR_BLOB_MISSING = 'blob must be provided.';

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    FirebaseStorageWeb: FirebaseStorageWeb
});

exports.FirebaseStorage = FirebaseStorage;
//# sourceMappingURL=plugin.cjs.js.map
