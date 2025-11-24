package app.capgo.capacitor.firebase.firestore.classes.options;

import com.getcapacitor.JSObject;
import app.capgo.capacitor.firebase.firestore.FirebaseFirestoreHelper;
import java.util.Map;
import org.json.JSONException;

public class SetDocumentOptions {

    private String reference;
    private Map<String, Object> data;
    private boolean merge;

    public SetDocumentOptions(String reference, JSObject data, boolean merge) throws JSONException {
        this.reference = reference;
        this.data = FirebaseFirestoreHelper.createHashMapFromJSONObject(data);
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
