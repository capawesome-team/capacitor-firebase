package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.CustomExceptions;

public class GetDocumentOptions {

    @Nullable
    private final String appName;

    @NonNull
    private final String reference;

    public GetDocumentOptions(@NonNull PluginCall call) throws Exception {
        this.reference = GetDocumentOptions.getReferenceFromCall(call);
        this.appName = GetDocumentOptions.getAppNameFromCall(call);
    }

    @Nullable
    public String getAppName() {
        return appName;
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
    private static String getReferenceFromCall(@NonNull PluginCall call) throws Exception {
        String reference = call.getString("reference");
        if (reference == null) {
            throw CustomExceptions.REFERENCE_MISSING;
        }
        return reference;
    }
}
