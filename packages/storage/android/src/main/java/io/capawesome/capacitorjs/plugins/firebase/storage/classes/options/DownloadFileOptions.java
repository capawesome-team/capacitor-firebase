package io.capawesome.capacitorjs.plugins.firebase.storage.classes.options;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.CustomExceptions;

public class DownloadFileOptions {

    @NonNull
    private final String callbackId;

    @NonNull
    private final String path;

    @NonNull
    private final Uri uri;

    public DownloadFileOptions(@NonNull PluginCall call) throws Exception {
        this.path = DownloadFileOptions.getPathFromCall(call);
        this.uri = DownloadFileOptions.getUriFromCall(call);
        this.callbackId = DownloadFileOptions.getCallbackIdFromCall(call);
    }

    @NonNull
    public String getCallbackId() {
        return callbackId;
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
