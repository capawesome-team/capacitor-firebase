package io.capawesome.capacitorjs.plugins.firebase.storage.classes.options;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.CustomExceptions;

public class UploadFileOptions {

    @NonNull
    private final String callbackId;

    @Nullable
    private final JSObject metadata;

    @NonNull
    private final String path;

    @NonNull
    private final Uri uri;

    public UploadFileOptions(@NonNull PluginCall call) throws Exception {
        this.path = UploadFileOptions.getPathFromCall(call);
        this.uri = UploadFileOptions.getUriFromCall(call);
        this.metadata = UploadFileOptions.getMetadataFromCall(call);
        this.callbackId = UploadFileOptions.getCallbackIdFromCall(call);
    }

    @NonNull
    public String getCallbackId() {
        return callbackId;
    }

    @Nullable
    public JSObject getMetadata() {
        return metadata;
    }

    @NonNull
    public String getPath() {
        return path;
    }

    @NonNull
    public Uri getUri() {
        return uri;
    }

    @NonNull
    private static String getCallbackIdFromCall(@NonNull PluginCall call) {
        return call.getCallbackId();
    }

    @Nullable
    private static JSObject getMetadataFromCall(@NonNull PluginCall call) {
        return call.getObject("metadata");
    }

    @NonNull
    private static String getPathFromCall(@NonNull PluginCall call) throws Exception {
        String path = call.getString("path");
        if (path == null) {
            throw CustomExceptions.PATH_MISSING;
        }
        return path;
    }

    @NonNull
    private static Uri getUriFromCall(@NonNull PluginCall call) throws Exception {
        String uri = call.getString("uri");
        if (uri == null) {
            throw CustomExceptions.URI_MISSING;
        }
        return Uri.parse(uri);
    }
}
