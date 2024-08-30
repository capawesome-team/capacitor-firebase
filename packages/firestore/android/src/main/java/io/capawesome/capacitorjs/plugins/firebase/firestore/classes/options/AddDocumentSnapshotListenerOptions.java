package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.Nullable;

public class AddDocumentSnapshotListenerOptions {

    private String reference;
    private final boolean includeMetadataChanges;
    private String callbackId;

    public AddDocumentSnapshotListenerOptions(String reference, @Nullable Boolean includeMetadataChanges, String callbackId) {
        this.reference = reference;
        this.includeMetadataChanges = includeMetadataChanges == null ? false : includeMetadataChanges;
        this.callbackId = callbackId;
    }

    public String getReference() {
        return reference;
    }

    public boolean isIncludeMetadataChanges() {
        return includeMetadataChanges;
    }

    public String getCallbackId() {
        return callbackId;
    }
}
