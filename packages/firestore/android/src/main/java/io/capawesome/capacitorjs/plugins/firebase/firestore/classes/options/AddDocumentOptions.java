package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.CustomExceptions;

public class AddDocumentOptions {

    @Nullable
    private final String appName;

    @NonNull
    private final JSObject data;

    @NonNull
    private final String reference;

    public AddDocumentOptions(@NonNull PluginCall call) throws Exception {
        this.reference = AddDocumentOptions.getReferenceFromCall(call);
        this.data = AddDocumentOptions.getDataFromCall(call);
        this.appName = AddDocumentOptions.getAppNameFromCall(call);
    }

    @Nullable
    public String getAppName() {
        return appName;
    }

    @NonNull
    public JSObject getData() {
        return data;
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

    @NonNull
    private static String getReferenceFromCall(@NonNull PluginCall call) throws Exception {
        String reference = call.getString("reference");
        if (reference == null) {
            throw CustomExceptions.REFERENCE_MISSING;
        }
        return reference;
    }
}
