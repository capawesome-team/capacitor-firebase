package io.capawesome.capacitorjs.plugins.firebase.firestore.classes;

import com.getcapacitor.JSObject;

import io.capawesome.capacitorjs.plugins.firebase.firestore.interfaces.QueryNonFilterConstraint;

public class QueryEndAtConstraint implements QueryNonFilterConstraint {
    private String reference;

    public QueryEndAtConstraint(JSObject queryConstraint) {
        this.reference = queryConstraint.getString("reference");
    }

    public String getReference() {
        return reference;
    }
}
