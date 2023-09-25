package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.constraints;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.google.firebase.firestore.Filter;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.QueryFilterConstraint;
import org.json.JSONArray;
import org.json.JSONException;

public class QueryCompositeFilterConstraint implements QueryFilterConstraint {

    @NonNull
    private String type;

    @NonNull
    private QueryFilterConstraint[] queryConstraints;

    public QueryCompositeFilterConstraint(JSObject compositeFilter) throws JSONException {
        this.type = compositeFilter.getString("type");
        JSONArray queryConstraints = compositeFilter.getJSONArray("queryConstraints");
        this.queryConstraints = new QueryFilterConstraint[queryConstraints.length()];
        for (int i = 0; i < queryConstraints.length(); i++) {
            JSObject queryConstraint = JSObject.fromJSONObject(queryConstraints.getJSONObject(i));
            String queryConstraintType = queryConstraint.getString("type");
            if (queryConstraintType.equals("where")) {
                this.queryConstraints[i] = new QueryFieldFilterConstraint(queryConstraint);
            } else {
                this.queryConstraints[i] = new QueryCompositeFilterConstraint(queryConstraint);
            }
        }
    }

    @NonNull
    public String getType() {
        return type;
    }

    @NonNull
    public QueryFilterConstraint[] getConstraints() {
        return queryConstraints;
    }

    @Nullable
    public Filter toFilter() {
        Filter filter = null;
        for (QueryFilterConstraint constraint : queryConstraints) {
            if (filter == null) {
                filter = constraint.toFilter();
            } else {
                switch (type) {
                    case "and":
                        filter = filter.and(constraint.toFilter());
                        break;
                    case "or":
                        filter = filter.or(constraint.toFilter());
                        break;
                }
            }
        }
        return filter;
    }
}
