package io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class AddConfigUpdateListenerOptions {

    @NonNull
    private final String callbackId;

    public AddConfigUpdateListenerOptions(@NonNull PluginCall call) {
        this.callbackId = AddConfigUpdateListenerOptions.getCallbackIdFromCall(call);
    }

    @NonNull
    public String getCallbackId() {
        return callbackId;
    }

    @NonNull
    private static String getCallbackIdFromCall(@NonNull PluginCall call) {
        return call.getCallbackId();
    }
}
