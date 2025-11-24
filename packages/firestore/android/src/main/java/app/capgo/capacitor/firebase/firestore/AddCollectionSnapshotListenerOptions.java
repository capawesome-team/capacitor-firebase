package app.capgo.capacitor.firebase.firestore.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import app.capgo.capacitor.firebase.firestore.FirebaseFirestoreHelper;
import app.capgo.capacitor.firebase.firestore.classes.constraints.QueryCompositeFilterConstraint;
import app.capgo.capacitor.firebase.firestore.interfaces.QueryNonFilterConstraint;
import org.json.JSONException;

public class AddCollectionSnapshotListenerOptions {

    @NonNull
    private String reference;

    @Nullable
    private QueryCompositeFilterConstraint compositeFilter;

    @NonNull
    private QueryNonFilterConstraint[] queryConstraints;

    private String callbackId;

    private final boolean includeMetadataChanges;

    public AddCollectionSnapshotListenerOptions(
        String reference,
        @Nullable JSObject compositeFilter,
        @Nullable JSArray queryConstraints,
        @Nullable Boolean includeMetadataChanges,
        String callbackId
    ) throws JSONException {
        this.reference = reference;
        this.compositeFilter = FirebaseFirestoreHelper.createQueryCompositeFilterConstraintFromJSObject(compositeFilter);
        this.queryConstraints = FirebaseFirestoreHelper.createQueryNonFilterConstraintArrayFromJSArray(queryConstraints);
        this.includeMetadataChanges = includeMetadataChanges == null ? false : includeMetadataChanges;
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

    public String getCallbackId() {
        return callbackId;
    }
}
