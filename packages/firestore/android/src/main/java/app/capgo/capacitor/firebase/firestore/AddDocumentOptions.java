package app.capgo.capacitor.firebase.firestore.classes.options;

import com.getcapacitor.JSObject;
import app.capgo.capacitor.firebase.firestore.FirebaseFirestoreHelper;
import java.util.Map;
import org.json.JSONException;

public class AddDocumentOptions {

    private String reference;
    private Map<String, Object> data;

    public AddDocumentOptions(String reference, JSObject data) throws JSONException {
        this.reference = reference;
        this.data = FirebaseFirestoreHelper.createHashMapFromJSONObject(data);
    }

    public String getReference() {
        return reference;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
