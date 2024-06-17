package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.NonNull;

import com.getcapacitor.JSObject;

import org.json.JSONException;

import java.util.Map;

import io.capawesome.capacitorjs.plugins.firebase.firestore.FirebaseFirestoreHelper;

public class WriteBatchOperation {
    private String type;
    private String reference;
    private Map<String, Object> data;

    public WriteBatchOperation(@NonNull JSObject operation) throws JSONException {
        this.type = operation.getString("type");
        this.reference = operation.getString("reference");
        this.data = FirebaseFirestoreHelper.createHashMapFromJSONObject(operation.getJSObject("data"));
    }

    public String getType() {
        return type;
    }

    public String getReference() {
        return reference;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
