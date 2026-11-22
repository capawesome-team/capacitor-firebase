package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.CustomExceptions;

public class AddDocumentSnapshotListenerOptions {

    @NonNull
    private final String callbackId;

    private final boolean includeMetadataChanges;

    @NonNull
    private final String reference;

    @Nullable
    private final String serverTimestampBehavior;

    public AddDocumentSnapshotListenerOptions(@NonNull PluginCall call) throws Exception {
        this.reference = AddDocumentSnapshotListenerOptions.getReferenceFromCall(call);
        this.includeMetadataChanges = AddDocumentSnapshotListenerOptions.getIncludeMetadataChangesFromCall(call);
        this.serverTimestampBehavior = AddDocumentSnapshotListenerOptions.getServerTimestampBehaviorFromCall(call);
        this.callbackId = AddDocumentSnapshotListenerOptions.getCallbackIdFromCall(call);
    }

    @NonNull
    public String getCallbackId() {
        return callbackId;
    }

    @NonNull
    public String getReference() {
        return reference;
    }

    @Nullable
    public String getServerTimestampBehavior() {
        return serverTimestampBehavior;
    }

    public boolean isIncludeMetadataChanges() {
        return includeMetadataChanges;
    }

    @NonNull
    private static String getCallbackIdFromCall(@NonNull PluginCall call) {
        return call.getCallbackId();
    }

    private static boolean getIncludeMetadataChangesFromCall(@NonNull PluginCall call) {
        Boolean includeMetadataChanges = call.getBoolean("includeMetadataChanges");
        return includeMetadataChanges == null ? false : includeMetadataChanges;
    }

    @NonNull
    private static String getReferenceFromCall(@NonNull PluginCall call) throws Exception {
        String reference = call.getString("reference");
        if (reference == null) {
            throw CustomExceptions.REFERENCE_MISSING;
        }
        return reference;
    }

    @Nullable
    private static String getServerTimestampBehaviorFromCall(@NonNull PluginCall call) {
        return call.getString("serverTimestamps");
    }
}
