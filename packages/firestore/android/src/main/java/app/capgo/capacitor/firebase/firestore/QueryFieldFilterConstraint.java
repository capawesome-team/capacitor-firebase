package app.capgo.capacitor.firebase.firestore.classes.constraints;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import app.capgo.capacitor.firebase.firestore.FirebaseFirestoreHelper;
import app.capgo.capacitor.firebase.firestore.interfaces.QueryFilterConstraint;
import com.getcapacitor.JSObject;
import com.google.firebase.firestore.Filter;
import java.util.List;
import org.json.JSONException;

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
        this.value = FirebaseFirestoreHelper.createObjectFromJSValue(queryConstraint.get("value"));
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
            case "array-contains-any":
                return Filter.arrayContainsAny(fieldPath, (List<? extends Object>) value);
            case "in":
                return Filter.inArray(fieldPath, (List<? extends Object>) value);
            case "not-in":
                return Filter.notInArray(fieldPath, (List<? extends Object>) value);
            default:
                return null;
        }
    }
}
