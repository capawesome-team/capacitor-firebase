package io.capawesome.capacitorjs.plugins.firebase.storage.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.storage.classes.CustomExceptions;

public class UpdateMetadataOptions {

    @NonNull
    private final JSObject metadata;

    @NonNull
    private final String path;

    public UpdateMetadataOptions(@NonNull PluginCall call) throws Exception {
        this.path = UpdateMetadataOptions.getPathFromCall(call);
        this.metadata = UpdateMetadataOptions.getMetadataFromCall(call);
    }

    @NonNull
    public JSObject getMetadata() {
        return metadata;
    }

    @NonNull
    public String getPath() {
        return path;
    }

    @NonNull
    private static JSObject getMetadataFromCall(@NonNull PluginCall call) throws Exception {
        JSObject metadata = call.getObject("metadata");
        if (metadata == null) {
            throw CustomExceptions.METADATA_MISSING;
        }
        return metadata;
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
