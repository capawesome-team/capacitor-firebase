package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.firestore.FirebaseFirestoreHelper;
import java.util.Map;
import org.json.JSONException;

public class WriteBatchOperation {

    private String type;
    private String reference;
    private Map<String, Object> data;

    @Nullable
    private SetOptions options;

    public WriteBatchOperation(@NonNull JSObject operation) throws JSONException {
        this.type = operation.getString("type");
        this.reference = operation.getString("reference");
        this.data = FirebaseFirestoreHelper.createHashMapFromJSONObject(operation.getJSObject("data"));

        if (operation.has("options") && operation.getJSObject("options") != null) {
            JSObject optsObj = operation.getJSObject("options");
            this.options = new SetOptions(optsObj);
        } else {
            this.options = null;
        }
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

    public SetOptions getOptions() {
        return options;
    }
}
