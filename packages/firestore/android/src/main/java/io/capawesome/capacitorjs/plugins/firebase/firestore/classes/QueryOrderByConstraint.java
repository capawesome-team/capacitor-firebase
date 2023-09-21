package io.capawesome.capacitorjs.plugins.firebase.firestore.classes;

import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.QueryNonFilterConstraint;

public class QueryOrderByConstraint implements QueryNonFilterConstraint {

    private String fieldPath;
    private String directionStr; // 'desc' | 'asc'

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
}
