package io.capawesome.capacitorjs.plugins.firebase.firestore.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.firestore.classes.CustomExceptions;

public class RemoveSnapshotListenerOptions {

    @NonNull
    private final String callbackId;

    public RemoveSnapshotListenerOptions(@NonNull PluginCall call) throws Exception {
        this.callbackId = RemoveSnapshotListenerOptions.getCallbackIdFromCall(call);
    }

    @NonNull
    public String getCallbackId() {
        return callbackId;
    }

    @NonNull
    private static String getCallbackIdFromCall(@NonNull PluginCall call) throws Exception {
        String callbackId = call.getString("callbackId");
        if (callbackId == null) {
            throw CustomExceptions.CALLBACK_ID_MISSING;
        }
        return callbackId;
    }
}
