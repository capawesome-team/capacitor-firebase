package io.capawesome.capacitorjs.plugins.firebase.analytics.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.analytics.classes.CustomExceptions;

public class SetUserPropertyOptions {

    @NonNull
    private final String key;

    @Nullable
    private final String value;

    public SetUserPropertyOptions(@NonNull PluginCall call) throws Exception {
        this.key = SetUserPropertyOptions.getKeyFromCall(call);
        this.value = SetUserPropertyOptions.getValueFromCall(call);
    }

    @NonNull
    public String getKey() {
        return key;
    }

    @Nullable
    public String getValue() {
        return value;
    }

    @NonNull
    private static String getKeyFromCall(@NonNull PluginCall call) throws Exception {
        String key = call.getString("key");
        if (key == null) {
            throw CustomExceptions.KEY_MISSING;
        }
        return key;
    }

    @Nullable
    private static String getValueFromCall(@NonNull PluginCall call) {
        return call.getString("value", null);
    }
}
