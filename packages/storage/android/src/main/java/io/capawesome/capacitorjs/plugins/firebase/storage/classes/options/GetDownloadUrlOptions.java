package io.capawesome.capacitorjs.plugins.firebase.storage.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.CustomExceptions;

public class GetDownloadUrlOptions {

    @NonNull
    private final String path;

    public GetDownloadUrlOptions(@NonNull PluginCall call) throws Exception {
        this.path = GetDownloadUrlOptions.getPathFromCall(call);
    }

    @NonNull
    public String getPath() {
        return path;
    }

    @NonNull
    private static String getPathFromCall(@NonNull PluginCall call) throws Exception {
        String path = call.getString("path");
        if (path == null) {
            throw CustomExceptions.PATH_MISSING;
        }
        return path;
    }
}
