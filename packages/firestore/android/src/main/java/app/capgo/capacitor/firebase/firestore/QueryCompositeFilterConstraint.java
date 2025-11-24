package app.capgo.capacitor.firebase.firestore.classes.constraints;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.google.firebase.firestore.Filter;
import app.capgo.capacitor.firebase.firestore.interfaces.QueryFilterConstraint;
import java.util.ArrayList;
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
        ArrayList<Filter> filters = new ArrayList<>();
        for (QueryFilterConstraint constraint : queryConstraints) {
            Filter filter = constraint.toFilter();
            if (filter != null) {
                filters.add(filter);
            }
        }
        switch (type) {
            case "and":
                return Filter.and(filters.toArray(new Filter[0]));
            case "or":
                return Filter.or(filters.toArray(new Filter[0]));
            default:
                return null;
        }
    }
}
