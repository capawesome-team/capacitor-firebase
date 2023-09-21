package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.constraints;

import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.QueryNonFilterConstraint;
import org.json.JSONException;

public class QueryLimitConstraint implements QueryNonFilterConstraint {

    private int limit;

    public QueryLimitConstraint(JSObject queryConstraint) throws JSONException {
        this.limit = queryConstraint.getInt("limit");
    }

    public int getLimit() {
        return limit;
    }
}
