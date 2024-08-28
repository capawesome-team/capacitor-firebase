package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.Nullable;

public class AddDocumentSnapshotListenerOptions {

    private String reference;
    private String callbackId;
    private final boolean includeMetadataChanges;

    public AddDocumentSnapshotListenerOptions(
            String reference,
            String callbackId,
            @Nullable Boolean includeMetadataChanges
    ) {
        this.reference = reference;
        this.callbackId = callbackId;
        this.includeMetadataChanges = includeMetadataChanges != null ? includeMetadataChanges : false;
    }

    public String getReference() {
        return reference;
    }

    public String getCallbackId() {
        return callbackId;
    }

    public boolean isIncludeMetadataChanges() {
        return includeMetadataChanges;
    }
}
