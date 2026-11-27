package io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.remoteconfig.classes.CustomExceptions;

public class GetOptions {

    @NonNull
    private final String key;

    public GetOptions(@NonNull PluginCall call) throws Exception {
        this.key = GetOptions.getKeyFromCall(call);
    }

    @NonNull
    public String getKey() {
        return key;
    }

    @NonNull
    private static String getKeyFromCall(@NonNull PluginCall call) throws Exception {
        String key = call.getString("key");
        if (key == null) {
            throw CustomExceptions.KEY_MISSING;
        }
        return key;
    }
}
