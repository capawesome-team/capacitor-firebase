package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.Nullable;

public class AddDocumentSnapshotListenerOptions {

    private String reference;
    private final boolean includeMetadataChanges;
    private String callbackId;

    @Nullable
    private final String serverTimestampBehavior;

    public AddDocumentSnapshotListenerOptions(
        String reference,
        @Nullable Boolean includeMetadataChanges,
        String callbackId,
        @Nullable String serverTimestampBehavior
    ) {
        this.reference = reference;
        this.includeMetadataChanges = includeMetadataChanges == null ? false : includeMetadataChanges;
        this.callbackId = callbackId;
        this.serverTimestampBehavior = serverTimestampBehavior;
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

    @Nullable
    public String getServerTimestampBehavior() {
        return serverTimestampBehavior;
    }
}
