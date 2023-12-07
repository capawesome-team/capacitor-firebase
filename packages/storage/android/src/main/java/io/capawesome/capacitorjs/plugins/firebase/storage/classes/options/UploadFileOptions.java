package io.capawesome.capacitorjs.plugins.firebase.storage.classes.options;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.google.firebase.storage.StorageMetadata;
import io.capawesome.capacitorjs.plugins.firebase.storage.FirebaseStorageHelper;
import org.json.JSONException;

public class UploadFileOptions {

    private String path;
    private Uri uri;

    @Nullable
    private StorageMetadata metadata;

    private String callbackId;

    public UploadFileOptions(String path, String uri, @Nullable JSObject metadata, String callbackId) throws JSONException {
        this.path = path;
        this.uri = Uri.parse(uri);
        this.metadata = FirebaseStorageHelper.buildStorageMetadata(metadata);
        this.callbackId = callbackId;
    }

    public String getPath() {
        return path;
    }

    public Uri getUri() {
        return uri;
    }

    @Nullable
    public StorageMetadata getMetadata() {
        return metadata;
    }

    public String getCallbackId() {
        return callbackId;
    }
}
