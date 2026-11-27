package io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.firebase.crashlytics.classes.CustomExceptions;

public class SetEnabledOptions {

    private final boolean enabled;

    public SetEnabledOptions(@NonNull PluginCall call) throws Exception {
        this.enabled = SetEnabledOptions.getEnabledFromCall(call);
    }

    public boolean getEnabled() {
        return enabled;
    }

    private static boolean getEnabledFromCall(@NonNull PluginCall call) throws Exception {
        Boolean enabled = call.getBoolean("enabled");
        if (enabled == null) {
            throw CustomExceptions.ENABLED_MISSING;
        }
        return enabled;
    }
}
