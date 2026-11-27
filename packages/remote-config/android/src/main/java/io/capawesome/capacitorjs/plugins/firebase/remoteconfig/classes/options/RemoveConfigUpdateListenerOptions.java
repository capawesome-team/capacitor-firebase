package io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.CustomExceptions;

public class RemoveConfigUpdateListenerOptions {

    @NonNull
    private final String callbackId;

    public RemoveConfigUpdateListenerOptions(@NonNull PluginCall call) throws Exception {
        this.callbackId = RemoveConfigUpdateListenerOptions.getCallbackIdFromCall(call);
    }

    @NonNull
    public String getCallbackId() {
        return callbackId;
    }

    @NonNull
    private static String getCallbackIdFromCall(@NonNull PluginCall call) throws Exception {
        // The option is named `id`. The README called it `callbackId` before, so apps may still send that name.
        String callbackId = call.getString("id", call.getString("callbackId"));
        if (callbackId == null) {
            throw CustomExceptions.ID_MISSING;
        }
        return callbackId;
    }
}
