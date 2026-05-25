package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.firestore.FirebaseFirestoreHelper;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.constraints.QueryCompositeFilterConstraint;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.QueryNonFilterConstraint;
import org.json.JSONException;

public class AddCollectionGroupSnapshotListenerOptions {

    @NonNull
    private String reference;

    @Nullable
    private QueryCompositeFilterConstraint compositeFilter;

    @NonNull
    private QueryNonFilterConstraint[] queryConstraints;

    private String callbackId;

    private boolean includeMetadataChanges;

    @Nullable
    private final String serverTimestampBehavior;

    public AddCollectionGroupSnapshotListenerOptions(
        String reference,
        @Nullable JSObject compositeFilter,
        @Nullable JSArray queryConstraints,
        @Nullable Boolean includeMetadataChanges,
        @Nullable String serverTimestampBehavior,
        String callbackId
    ) throws JSONException {
        this.reference = reference;
        this.compositeFilter = FirebaseFirestoreHelper.createQueryCompositeFilterConstraintFromJSObject(compositeFilter);
        this.queryConstraints = FirebaseFirestoreHelper.createQueryNonFilterConstraintArrayFromJSArray(queryConstraints);
        this.includeMetadataChanges = includeMetadataChanges == null ? false : includeMetadataChanges;
        this.serverTimestampBehavior = serverTimestampBehavior;
        this.callbackId = callbackId;
    }

    public String getReference() {
        return reference;
    }

    @Nullable
    public QueryCompositeFilterConstraint getCompositeFilter() {
        return compositeFilter;
    }

    @NonNull
    public QueryNonFilterConstraint[] getQueryConstraints() {
        return queryConstraints;
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
