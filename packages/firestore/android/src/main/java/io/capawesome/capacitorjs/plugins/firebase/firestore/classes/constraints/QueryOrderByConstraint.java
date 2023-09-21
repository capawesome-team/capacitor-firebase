package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.constraints;

import com.getcapacitor.JSObject;
import com.google.firebase.firestore.Query;

import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.QueryNonFilterConstraint;

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
