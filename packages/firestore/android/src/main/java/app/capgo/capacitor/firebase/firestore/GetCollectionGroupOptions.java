package app.capgo.capacitor.firebase.firestore.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import app.capgo.capacitor.firebase.firestore.FirebaseFirestoreHelper;
import app.capgo.capacitor.firebase.firestore.classes.constraints.QueryCompositeFilterConstraint;
import app.capgo.capacitor.firebase.firestore.interfaces.QueryNonFilterConstraint;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import org.json.JSONException;

public class GetCollectionGroupOptions {

    @NonNull
    private String reference;

    @Nullable
    private QueryCompositeFilterConstraint compositeFilter;

    @NonNull
    private QueryNonFilterConstraint[] queryConstraints;

    public GetCollectionGroupOptions(String reference, @Nullable JSObject compositeFilter, @Nullable JSArray queryConstraints)
        throws JSONException {
        this.reference = reference;
        this.compositeFilter = FirebaseFirestoreHelper.createQueryCompositeFilterConstraintFromJSObject(compositeFilter);
        this.queryConstraints = FirebaseFirestoreHelper.createQueryNonFilterConstraintArrayFromJSArray(queryConstraints);
    }

    @NonNull
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
}
