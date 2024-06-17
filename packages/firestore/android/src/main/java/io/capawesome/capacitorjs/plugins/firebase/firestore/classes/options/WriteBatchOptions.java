package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import org.json.JSONException;

public class WriteBatchOptions {

    private WriteBatchOperation[] operations;

    public WriteBatchOptions(@Nullable JSArray operations) throws JSONException {
        this.operations = createWriteBatchOperationArrayFromJSArray(operations);
    }

    @NonNull
    private static WriteBatchOperation[] createWriteBatchOperationArrayFromJSArray(@Nullable JSArray data) throws JSONException {
        if (data == null) {
            return new WriteBatchOperation[0];
        } else {
            WriteBatchOperation[] operations = new WriteBatchOperation[data.length()];
            for (int i = 0; i < data.length(); i++) {
                JSObject operation = JSObject.fromJSONObject(data.getJSONObject(i));
                operations[i] = new WriteBatchOperation(operation);
            }
            return operations;
        }
    }

    @NonNull
    public WriteBatchOperation[] getOperations() {
        return operations;
    }
}
