package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.CustomExceptions;

public class DeleteDocumentOptions {

    @NonNull
    private final String reference;

    public DeleteDocumentOptions(@NonNull PluginCall call) throws Exception {
        this.reference = DeleteDocumentOptions.getReferenceFromCall(call);
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
