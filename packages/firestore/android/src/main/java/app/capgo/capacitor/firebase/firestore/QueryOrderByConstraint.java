package app.capgo.capacitor.firebase.firestore.classes.constraints;

import app.capgo.capacitor.firebase.firestore.interfaces.QueryNonFilterConstraint;
import com.getcapacitor.JSObject;
import com.google.firebase.firestore.Query;

public class QueryOrderByConstraint implements QueryNonFilterConstraint {

    private String fieldPath;
    private String directionStr;

    public QueryOrderByConstraint(JSObject queryConstraint) {
        this.fieldPath = queryConstraint.getString("fieldPath");
        this.directionStr = queryConstraint.getString("directionStr");
    }

    public String getFieldPath() {
        return fieldPath;
    }

    public String getDirectionStr() {
        return directionStr;
    }

    public Query toQuery(Query query, com.google.firebase.firestore.FirebaseFirestore firestoreInstance) {
        Query.Direction direction = directionStr.equals("desc") ? Query.Direction.DESCENDING : Query.Direction.ASCENDING;
        return query.orderBy(fieldPath, direction);
    }
}
