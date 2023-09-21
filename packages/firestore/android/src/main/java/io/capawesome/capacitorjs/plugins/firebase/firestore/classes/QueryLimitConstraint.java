package io.capawesome.capacitorjs.plugins.firebase.firestore.classes;

import com.getcapacitor.JSObject;

import org.json.JSONException;

import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.QueryNonFilterConstraint;

public class QueryLimitConstraint implements QueryNonFilterConstraint {
    private int limit;

    public QueryLimitConstraint(JSObject queryConstraint) throws JSONException {
        this.limit = queryConstraint.getInt("limit");
    }

    public int getLimit() {
        return limit;
    }
}
