package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.CustomExceptions;

public class AddCollectionGroupSnapshotListenerOptions {

    @NonNull
    private final String callbackId;

    @Nullable
    private final JSObject compositeFilter;

    private final boolean includeMetadataChanges;

    @Nullable
    private final JSArray queryConstraints;

    @NonNull
    private final String reference;

    @Nullable
    private final String serverTimestampBehavior;

    public AddCollectionGroupSnapshotListenerOptions(@NonNull PluginCall call) throws Exception {
        this.reference = AddCollectionGroupSnapshotListenerOptions.getReferenceFromCall(call);
        this.compositeFilter = AddCollectionGroupSnapshotListenerOptions.getCompositeFilterFromCall(call);
        this.queryConstraints = AddCollectionGroupSnapshotListenerOptions.getQueryConstraintsFromCall(call);
        this.includeMetadataChanges = AddCollectionGroupSnapshotListenerOptions.getIncludeMetadataChangesFromCall(call);
        this.serverTimestampBehavior = AddCollectionGroupSnapshotListenerOptions.getServerTimestampBehaviorFromCall(call);
        this.callbackId = AddCollectionGroupSnapshotListenerOptions.getCallbackIdFromCall(call);
    }

    @NonNull
    public String getCallbackId() {
        return callbackId;
    }

    @Nullable
    public JSObject getCompositeFilter() {
        return compositeFilter;
    }

    @Nullable
    public JSArray getQueryConstraints() {
        return queryConstraints;
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

    @Nullable
    private static JSObject getCompositeFilterFromCall(@NonNull PluginCall call) {
        return call.getObject("compositeFilter");
    }

    private static boolean getIncludeMetadataChangesFromCall(@NonNull PluginCall call) {
        Boolean includeMetadataChanges = call.getBoolean("includeMetadataChanges");
        return includeMetadataChanges == null ? false : includeMetadataChanges;
    }

    @Nullable
    private static JSArray getQueryConstraintsFromCall(@NonNull PluginCall call) {
        return call.getArray("queryConstraints");
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
