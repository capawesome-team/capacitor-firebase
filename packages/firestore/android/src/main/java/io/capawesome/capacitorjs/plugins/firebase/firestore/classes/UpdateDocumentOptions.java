package io.capawesome.capacitorjs.plugins.firebase.firestore.classes;

import com.getcapacitor.JSObject;

import org.json.JSONException;

import java.util.Map;

import io.capawesome.capacitorjs.plugins.firebase.firestore.FirebaseFirestoreHelper;

public class UpdateDocumentOptions {
    private String reference;
    private Map<String, Object> data;

    public UpdateDocumentOptions(String reference, JSObject data) throws JSONException {
        this.reference = reference;
        this.data = FirebaseFirestoreHelper.createHashMapFromJSObject(data);
    }

    public String getReference() {
        return reference;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
