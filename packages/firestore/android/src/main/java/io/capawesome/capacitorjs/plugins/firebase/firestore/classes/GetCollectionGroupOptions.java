package io.capawesome.capacitorjs.plugins.firebase.firestore.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;

import org.json.JSONException;

import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.QueryNonFilterConstraint;

public class GetCollectionGroupOptions {
    @NonNull
    private String reference;
    @Nullable
    private QueryCompositeFilterConstraint compositeFilter;
    @NonNull
    private QueryNonFilterConstraint[] queryConstraints;

    public GetCollectionGroupOptions(String reference, @Nullable JSObject compositeFilter, @Nullable JSArray queryConstraints) throws JSONException {
        this.reference = reference;
        if (compositeFilter == null) {
            this.compositeFilter = null;
        } else {
            this.compositeFilter = new QueryCompositeFilterConstraint(compositeFilter);
        }
        if (queryConstraints == null) {
            this.queryConstraints = new QueryNonFilterConstraint[0];
        } else {
            this.queryConstraints = new QueryNonFilterConstraint[queryConstraints.length()];
            for (int i = 0; i < queryConstraints.length(); i++) {
                JSObject queryConstraint = JSObject.fromJSONObject(queryConstraints.getJSONObject(i));
                String queryConstraintType = queryConstraint.getString("type");
                switch (queryConstraintType) {
                    case "orderBy":
                        this.queryConstraints[i] = new QueryOrderByConstraint(queryConstraint);
                        break;
                    case "limit":
                        this.queryConstraints[i] = new QueryLimitConstraint(queryConstraint);
                        break;
                    case "startAt":
                    case "startAfter":
                        this.queryConstraints[i] = new QueryStartAtConstraint(queryConstraint);
                        break;
                    case "endAt":
                    case "endBefore":
                        this.queryConstraints[i] = new QueryEndAtConstraint(queryConstraint);
                        break;
                }
            }
        }
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
