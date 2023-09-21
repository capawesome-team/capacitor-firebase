package io.capawesome.capacitorjs.plugins.firebase.firestore.classes;

import com.getcapacitor.JSObject;

import org.json.JSONException;

import java.util.Map;

import io.capawesome.capacitorjs.plugins.firebase.firestore.FirebaseFirestoreHelper;

public class SetDocumentOptions {
    private String reference;
    private Map<String, Object> data;
    private boolean merge;

    public SetDocumentOptions(String reference, JSObject data, boolean merge) throws JSONException {
        this.reference = reference;
        this.data = FirebaseFirestoreHelper.createHashMapFromJSObject(data);
        this.merge = merge;
    }

    public String getReference() {
        return reference;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public boolean getMerge() {
        return merge;
    }
}
