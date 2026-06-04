package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.firestore.FirebaseFirestoreHelper;
import java.util.Map;
import org.json.JSONException;

public class AddDocumentOptions {

    private String reference;
    private Map<String, Object> data;

    public AddDocumentOptions(String reference, JSObject data, @NonNull com.google.firebase.firestore.FirebaseFirestore firestore)
        throws JSONException {
        this.reference = reference;
        this.data = FirebaseFirestoreHelper.createHashMapFromJSONObject(data, firestore);
    }

    public String getReference() {
        return reference;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
