package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.CustomExceptions;
import org.json.JSONException;

public class WriteBatchOptions {

    @NonNull
    private final WriteBatchOperation[] operations;

    public WriteBatchOptions(@NonNull PluginCall call) throws Exception {
        this.operations = WriteBatchOptions.getOperationsFromCall(call);
    }

    @NonNull
    public WriteBatchOperation[] getOperations() {
        return operations;
    }

    @NonNull
    private static WriteBatchOperation[] createWriteBatchOperationArrayFromJSArray(@Nullable JSArray operations) throws JSONException {
        if (operations == null) {
            return new WriteBatchOperation[0];
        } else {
            WriteBatchOperation[] writeBatchOperations = new WriteBatchOperation[operations.length()];
            for (int i = 0; i < operations.length(); i++) {
                JSObject operation = JSObject.fromJSONObject(operations.getJSONObject(i));
                writeBatchOperations[i] = new WriteBatchOperation(operation);
            }
            return writeBatchOperations;
        }
    }

    @NonNull
    private static WriteBatchOperation[] getOperationsFromCall(@NonNull PluginCall call) throws Exception {
        JSArray operations = call.getArray("operations");
        if (operations == null) {
            throw CustomExceptions.OPERATIONS_MISSING;
        }
        return WriteBatchOptions.createWriteBatchOperationArrayFromJSArray(operations);
    }
}
