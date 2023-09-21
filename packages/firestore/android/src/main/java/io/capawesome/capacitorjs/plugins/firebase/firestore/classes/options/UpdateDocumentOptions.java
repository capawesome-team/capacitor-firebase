package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.firestore.FirebaseFirestoreHelper;
import java.util.Map;
import org.json.JSONException;

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
