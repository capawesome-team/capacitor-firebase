package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.Nullable;

public class AddDocumentSnapshotListenerOptions {

    private String reference;
    private final boolean includeMetadataChanges;

    @Nullable
    private final String serverTimestampBehavior;

    private String callbackId;

    public AddDocumentSnapshotListenerOptions(
        String reference,
        @Nullable Boolean includeMetadataChanges,
        @Nullable String serverTimestampBehavior,
        String callbackId
    ) {
        this.reference = reference;
        this.includeMetadataChanges = includeMetadataChanges == null ? false : includeMetadataChanges;
        this.serverTimestampBehavior = serverTimestampBehavior;
        this.callbackId = callbackId;
    }

    public String getReference() {
        return reference;
    }

    public boolean isIncludeMetadataChanges() {
        return includeMetadataChanges;
    }

    @Nullable
    public String getServerTimestampBehavior() {
        return serverTimestampBehavior;
    }

    public String getCallbackId() {
        return callbackId;
    }
}
