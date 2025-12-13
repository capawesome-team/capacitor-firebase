package app.capgo.capacitor.firebase.storage;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import app.capgo.capacitor.firebase.storage.classes.events.UploadFileCallbackEvent;
import app.capgo.capacitor.firebase.storage.classes.options.DeleteFileOptions;
import app.capgo.capacitor.firebase.storage.classes.options.GetDownloadUrlOptions;
import app.capgo.capacitor.firebase.storage.classes.options.GetMetadataOptions;
import app.capgo.capacitor.firebase.storage.classes.options.ListFilesOptions;
import app.capgo.capacitor.firebase.storage.classes.options.UpdateMetadataOptions;
import app.capgo.capacitor.firebase.storage.classes.options.UploadFileOptions;
import app.capgo.capacitor.firebase.storage.classes.results.GetDownloadUrlResult;
import app.capgo.capacitor.firebase.storage.classes.results.GetMetadataResult;
import app.capgo.capacitor.firebase.storage.classes.results.ListFilesResult;
import app.capgo.capacitor.firebase.storage.enums.UploadFileState;
import app.capgo.capacitor.firebase.storage.interfaces.EmptyResultCallback;
import app.capgo.capacitor.firebase.storage.interfaces.NonEmptyEventCallback;
import app.capgo.capacitor.firebase.storage.interfaces.NonEmptyResultCallback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirebaseStorage {

    private FirebaseStoragePlugin plugin;

    public FirebaseStorage(FirebaseStoragePlugin plugin) {
        this.plugin = plugin;
    }

    public void deleteFile(@NonNull DeleteFileOptions options, @NonNull EmptyResultCallback callback) {
        String path = options.getPath();

        StorageReference storageReference = getFirebaseStorageInstance().getReference(path);
        storageReference
            .delete()
            .addOnSuccessListener(aVoid -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void getDownloadUrl(@NonNull GetDownloadUrlOptions options, @NonNull NonEmptyResultCallback callback) {
        String path = options.getPath();

        StorageReference storageReference = getFirebaseStorageInstance().getReference(path);
        storageReference
            .getDownloadUrl()
            .addOnSuccessListener(uri -> {
                GetDownloadUrlResult result = new GetDownloadUrlResult(uri);
                callback.success(result);
            })
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void getMetadata(@NonNull GetMetadataOptions options, @NonNull NonEmptyResultCallback callback) {
        String path = options.getPath();

        StorageReference storageReference = getFirebaseStorageInstance().getReference(path);
        storageReference
            .getMetadata()
            .addOnSuccessListener(storageMetadata -> {
                GetMetadataResult result = new GetMetadataResult(storageMetadata);
                callback.success(result);
            })
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void listFiles(@NonNull ListFilesOptions options, @NonNull NonEmptyResultCallback callback) {
        String path = options.getPath();
        int maxResults = options.getMaxResults();
        String pageToken = options.getPageToken();

        StorageReference storageReference = getFirebaseStorageInstance().getReference(path);
        Task<ListResult> task;
        if (pageToken == null) {
            task = storageReference.list(maxResults);
        } else {
            task = storageReference.list(maxResults, pageToken);
        }
        task
            .addOnSuccessListener(listResult -> {
                ListFilesResult result = new ListFilesResult(listResult);
                callback.success(result);
            })
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void updateMetadata(@NonNull UpdateMetadataOptions options, @NonNull EmptyResultCallback callback) {
        String path = options.getPath();
        StorageMetadata metadata = options.getMetadata();

        StorageReference storageReference = getFirebaseStorageInstance().getReference(path);
        storageReference
            .updateMetadata(metadata)
            .addOnSuccessListener(aVoid -> callback.success())
            .addOnFailureListener(exception -> callback.error(exception));
    }

    public void uploadFile(@NonNull UploadFileOptions options, @NonNull NonEmptyEventCallback callback) {
        String path = options.getPath();
        Uri uri = options.getUri();
        @Nullable
        StorageMetadata metadata = options.getMetadata();

        StorageReference storageReference = getFirebaseStorageInstance().getReference(path);
        UploadTask uploadTask;
        if (metadata == null) {
            uploadTask = storageReference.putFile(uri);
        } else {
            uploadTask = storageReference.putFile(uri, metadata);
        }
        uploadTask
            .addOnProgressListener(taskSnapshot -> {
                UploadFileCallbackEvent result = new UploadFileCallbackEvent(taskSnapshot, UploadFileState.RUNNING);
                callback.success(result);
            })
            .addOnSuccessListener(taskSnapshot -> {
                UploadFileCallbackEvent result = new UploadFileCallbackEvent(taskSnapshot, UploadFileState.SUCCESS);
                callback.success(result);
                callback.release();
            })
            .addOnFailureListener(exception -> {
                callback.error(exception);
                callback.release();
            });
    }

    public void useEmulator(@NonNull String host, int port) {
        getFirebaseStorageInstance().useEmulator(host, port);
    }

    private com.google.firebase.storage.FirebaseStorage getFirebaseStorageInstance() {
        return com.google.firebase.storage.FirebaseStorage.getInstance();
    }
}
