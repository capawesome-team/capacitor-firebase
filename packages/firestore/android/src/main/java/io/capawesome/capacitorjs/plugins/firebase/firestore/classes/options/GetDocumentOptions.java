package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.Nullable;

public class GetDocumentOptions {

    private final String reference;

    @Nullable
    private final String serverTimestampBehavior;

    public GetDocumentOptions(String reference, @Nullable String serverTimestampBehavior) {
        this.reference = reference;
        this.serverTimestampBehavior = serverTimestampBehavior;
    }

    public String getReference() {
        return reference;
    }

    @Nullable
    public String getServerTimestampBehavior() {
        return serverTimestampBehavior;
    }
}
