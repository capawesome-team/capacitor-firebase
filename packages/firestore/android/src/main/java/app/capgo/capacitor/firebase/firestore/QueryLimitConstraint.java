package app.capgo.capacitor.firebase.firestore.classes.constraints;

import app.capgo.capacitor.firebase.firestore.interfaces.QueryNonFilterConstraint;
import com.getcapacitor.JSObject;
import com.google.firebase.firestore.Query;
import org.json.JSONException;

public class QueryLimitConstraint implements QueryNonFilterConstraint {

    private String type;
    private int limit;

    public QueryLimitConstraint(JSObject queryConstraint) throws JSONException {
        this.type = queryConstraint.getString("type");
        this.limit = queryConstraint.getInt("limit");
    }

    public int getLimit() {
        return limit;
    }

    public Query toQuery(Query query, com.google.firebase.firestore.FirebaseFirestore firestoreInstance) {
        if (type.equals("limit")) {
            return query.limit(limit);
        } else {
            return query.limitToLast(limit);
        }
    }
}
