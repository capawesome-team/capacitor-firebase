package io.capawesome.capacitorjs.plugins.firebase.firestore.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.getcapacitor.JSObject;
import com.google.firebase.firestore.Filter;

import org.json.JSONException;

import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.QueryFilterConstraint;

public class QueryFieldFilterConstraint implements QueryFilterConstraint {
    @NonNull
    private String fieldPath;
    @NonNull
    private String opStr;
    @NonNull
    private Object value;

    public QueryFieldFilterConstraint(JSObject queryConstraint) throws JSONException {
        this.fieldPath = queryConstraint.getString("fieldPath", "");
        this.opStr = queryConstraint.getString("opStr", "");
        this.value = queryConstraint.get("value");
    }

    @Nullable
    public Filter toFilter() {
        switch (opStr) {
            case "<":
                return Filter.lessThan(fieldPath, value);
            case "<=":
                return Filter.lessThanOrEqualTo(fieldPath, value);
            case "==":
                return Filter.equalTo(fieldPath, value);
            case ">=":
                return Filter.greaterThanOrEqualTo(fieldPath, value);
            case ">":
                return Filter.greaterThan(fieldPath, value);
            case "!=":
                return Filter.notEqualTo(fieldPath, value);
            case "array-contains":
                return Filter.arrayContains(fieldPath, value);
            /*case "array-contains-any":
                return Filter.arrayContainsAny(fieldPath, value);
            case "in":
                return Filter.inArray(fieldPath, value);
            case "not-in":
                return Filter.notInArray(fieldPath, value);*/
            default:
                return null;
        }
    }
}
