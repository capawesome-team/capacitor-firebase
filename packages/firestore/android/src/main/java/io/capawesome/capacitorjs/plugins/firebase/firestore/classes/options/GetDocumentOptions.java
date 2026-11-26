package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.CustomExceptions;

public class GetDocumentOptions {

    @NonNull
    private final String reference;

    public GetDocumentOptions(@NonNull PluginCall call) throws Exception {
        this.reference = GetDocumentOptions.getReferenceFromCall(call);
    }

    @NonNull
    public String getReference() {
        return reference;
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
