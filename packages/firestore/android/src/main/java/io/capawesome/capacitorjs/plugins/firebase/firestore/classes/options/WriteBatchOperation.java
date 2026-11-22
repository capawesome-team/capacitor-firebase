package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;

public class WriteBatchOperation {

    @NonNull
    private final JSObject data;

    @Nullable
    private final SetOptions options;

    @Nullable
    private final String reference;

    @Nullable
    private final String type;

    public WriteBatchOperation(@NonNull JSObject operation) {
        this.type = operation.getString("type");
        this.reference = operation.getString("reference");
        JSObject data = operation.getJSObject("data");
        this.data = data == null ? new JSObject() : data;

        if (operation.has("options") && operation.getJSObject("options") != null) {
            JSObject optsObj = operation.getJSObject("options");
            this.options = new SetOptions(optsObj);
        } else {
            this.options = null;
        }
    }

    @NonNull
    public JSObject getData() {
        return data;
    }

    @Nullable
    public SetOptions getOptions() {
        return options;
    }

    @Nullable
    public String getReference() {
        return reference;
    }

    @Nullable
    public String getType() {
        return type;
    }
}
