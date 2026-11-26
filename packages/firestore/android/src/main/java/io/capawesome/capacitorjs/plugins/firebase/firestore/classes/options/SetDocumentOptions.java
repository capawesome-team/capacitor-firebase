package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.CustomExceptions;

public class SetDocumentOptions {

    @Nullable
    private final String appName;

    @NonNull
    private final JSObject data;

    private final boolean merge;

    @NonNull
    private final String reference;

    public SetDocumentOptions(@NonNull PluginCall call) throws Exception {
        this.reference = SetDocumentOptions.getReferenceFromCall(call);
        this.data = SetDocumentOptions.getDataFromCall(call);
        this.merge = SetDocumentOptions.getMergeFromCall(call);
        this.appName = SetDocumentOptions.getAppNameFromCall(call);
    }

    @Nullable
    public String getAppName() {
        return appName;
    }

    @NonNull
    public JSObject getData() {
        return data;
    }

    public boolean getMerge() {
        return merge;
    }

    @NonNull
    public String getReference() {
        return reference;
    }

    @Nullable
    private static String getAppNameFromCall(@NonNull PluginCall call) {
        return call.getString("appName");
    }

    @NonNull
    private static JSObject getDataFromCall(@NonNull PluginCall call) throws Exception {
        JSObject data = call.getObject("data");
        if (data == null) {
            throw CustomExceptions.DATA_MISSING;
        }
        return data;
    }

    private static boolean getMergeFromCall(@NonNull PluginCall call) {
        return call.getBoolean("merge", false);
    }

    @NonNull
    private static String getReferenceFromCall(@NonNull PluginCall call) throws Exception {
        String reference = call.getString("reference");
        if (reference == null) {
            throw CustomExceptions.REFERENCE_MISSING;
        }
        return reference;
    }
}
